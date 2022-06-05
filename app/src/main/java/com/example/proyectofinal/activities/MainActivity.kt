package com.example.proyectofinal.activities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.APIService
import com.example.proyectofinal.entities.Dti
import com.example.proyectofinal.entities.RestEngine
import com.example.proyectofinal.entities.UserRepository
import com.example.proyectofinal.entities.UserRepository.ListDtiNombres
import com.example.proyectofinal.entities.UserRepository.userLatitud
import com.example.proyectofinal.entities.UserRepository.userLongitud
import com.google.android.gms.location.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {


    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    var fm = supportFragmentManager
    private val PERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_login) as NavHostFragment
        bottomNavView = findViewById(R.id.bottom_bar)
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> hideBottomBar()
                R.id.beachFragment -> hideBottomBar()
                R.id.recuMailFragment -> hideBottomBar()
                R.id.editProfileFragment -> hideBottomBar()
                R.id.contactoFragment -> hideBottomBar()
                R.id.formularioFragment -> hideBottomBar()
                R.id.mapsFragment -> hideBottomBar()
                else -> showBottomBar()
            }
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) { getLastLocation() }
            withContext(Dispatchers.IO) { callServiceGetDti() }
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "dark_mode") {
            val prefs = sharedPreferences?.getString(key, "1")

            when (prefs?.toInt()) {
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_NO
                    )
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(
                        AppCompatDelegate.MODE_NIGHT_YES
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
            .registerOnSharedPreferenceChangeListener(this)
    }

    private fun showBottomBar() {
        val navBar: BottomNavigationView = findViewById(R.id.bottom_bar)
        navBar.visibility = View.VISIBLE
    }

    private fun hideBottomBar() {
        val navBar: BottomNavigationView = findViewById(R.id.bottom_bar)
        navBar.visibility = View.GONE
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        userLatitud = location.latitude.toString()
                        userLongitud = location.longitude.toString()
                        Log.d("TestLatitud", location.latitude.toString())
                        Log.d("TestLongitud", location.longitude.toString())

                    }
                }

            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0

        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()!!
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation
            Log.d("Test", mLastLocation.latitude.toString())
            Log.d("Test", mLastLocation.longitude.toString())
        }
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }

    private fun callServiceGetDti() {
        val dtiService: APIService = RestEngine.getRetrofitDtis().create(APIService::class.java)
        val result: Call<List<Dti>> = dtiService.getDtiList()

        result.enqueue(object : Callback<List<Dti>> {
            override fun onResponse(
                call: Call<List<Dti>>,
                response: Response<List<Dti>>
            ) {
                val r = response.body()
                if (r != null) {
                    UserRepository.ListDti = r
                }
                getDtiNames(UserRepository.ListDti)
            }

            override fun onFailure(call: Call<List<Dti>>, t: Throwable) {

            }
        })
    }

    fun getDtiNames(list: List<Dti>): MutableList<String> {

        for (l in list) {
            ListDtiNombres.addAll(listOf(l.nombre))
        }
        return ListDtiNombres
    }
}


