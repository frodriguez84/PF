package com.example.proyectofinal.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.proyectofinal.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var v: View
    private var lat: Double = 0.0
    private var long: Double = 0.0
    private lateinit var nombre: String

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
        enableLocation()
    }

    private fun createMarker() {
        val gopoint = LatLng(lat, long)
        map.addMarker(MarkerOptions().position(gopoint).title("Destino: $nombre"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(gopoint))
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(gopoint, 16f),
            4000,
            null
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_maps, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        val geo = MapsFragmentArgs.fromBundle(requireArguments()).geo
        nombre = MapsFragmentArgs.fromBundle(requireArguments()).nombre
        lat = geo.latitud.toDouble()
        long = geo.longitud.toDouble()

        return v
    }

    private fun isLocationPermissionsGranted(): Boolean {
        var permission = false
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            permission = true
        }
        return permission
    }

    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionsGranted()) {
            map.isMyLocationEnabled = true
        } else {
            requestPermissionLocation()
        }
    }

    private fun requestPermissionLocation() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) && ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            Toast.makeText(requireContext(), "Acepta los permisos", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE_LOCATION
            )
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true

            } else {
                Toast.makeText(
                    requireContext(),
                    "Vaya a ajustes y acepte los permisos",
                    Toast.LENGTH_SHORT
                ).show()

            }
            else -> {
            }
        }


    }
}