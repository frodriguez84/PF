package com.example.proyectofinal.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.view.View
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.MyFragment
import com.example.proyectofinal.entities.UserRepository
import com.example.proyectofinal.entities.UserRepository.ListDti
import com.example.proyectofinal.entities.UserRepository.listOfFavs
import com.example.proyectofinal.entities.UserRepository.userMailLogin
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import me.tankery.lib.circularseekbar.CircularSeekBar


class HomeViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()


    private lateinit var beachName : TextView
    private lateinit var pcAforo : CircularSeekBar
    private lateinit var aforoView: TextView

    private lateinit var pcTemp : CircularSeekBar
    private lateinit var tempView: TextView

    private lateinit var pcPark : CircularSeekBar
    private lateinit var parkView: TextView

    private lateinit var listPopupWindowButton: Button
    private lateinit var listPopupWindow: ListPopupWindow

    private lateinit var aforo : String
    private var temp : Float = 0F
    private  var park : Float = 0F

    private var lugDispo : Int = 0





    fun populateFavs() {
        db.collection("users").document(userMailLogin).get().addOnSuccessListener {
            listOfFavs = it.get("favs") as ArrayList<String>
        }
    }

    fun showDti(v: View, context: Context) {
        listPopupWindowButton = v.findViewById(R.id.list_popup_button)
        listPopupWindow =
            ListPopupWindow(context, null, androidx.transition.R.attr.listPopupWindowStyle)

        listPopupWindow.anchorView = listPopupWindowButton

        val adapter =
            ArrayAdapter(context, R.layout.list_popup_window_item, UserRepository.ListDtiNombres)
        listPopupWindow.setAdapter(adapter)

        listPopupWindow.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->

            showData(position, v)
            UserRepository.dtiDocument = position.toString()
            UserRepository.userBeachSelect = position.toString()
            listPopupWindow.dismiss()

        }

        listPopupWindowButton.setOnClickListener {

            listPopupWindow.show()

        }
    }


    fun showData(pos: Int, v: View) {

        beachName = v.findViewById(R.id.nameBeachView)
        aforoView = v.findViewById(R.id.aforoTextView)
        pcAforo = v.findViewById(R.id.pcaforo)

        tempView = v.findViewById(R.id.tempTextView)
        pcTemp = v.findViewById(R.id.pctemp)

        parkView = v.findViewById(R.id.parkTextView)
        pcPark = v.findViewById(R.id.pcpark)

        var dti =  ListDti[pos]


        beachName.text = dti.name
        aforo = dti.aforo
        temp =  dti.temperatura

        pcPark.max = dti.maxParking
        pcPark.progress = dti.parking


        tempView.text = dti.temperatura.toString()+"°"
        pcTemp.progress = temp
        park = dti.parking

        lugDispo = (dti.maxParking - dti.parking).toInt()

        parkView.text = lugDispo.toString()+" Disponibles"

        when(aforo){
            "bajo"-> {
                aforoView.text = "Bajo"
                pcAforo.progress = 25F
            }
            "medio"-> {
                aforoView.text = "Medio"
                pcAforo.progress = 50F
            }
            "altos"-> {
                aforoView.text = "Alto"
                pcAforo.progress = 75F
            }
            "lleno"-> {
                aforoView.text = "Lleno"
                pcAforo.progress = 100F
            }
        }

    }

    fun cleanLogUser() {
        userMailLogin = ""
    }

    fun dtiCercano(v : View){

        var dtiCerca = 0
        var distEntreDTIyUser = 9999999999999999F
        var position = 0

        for (dti in ListDti){

            val locationA = Location("punto A")

            locationA.latitude = UserRepository.userLatitud.toDouble()
            locationA.longitude = UserRepository.userLongitud.toDouble()

            val locationB = Location("punto B")

            locationB.latitude = dti.location.coordinates[0]
            locationB.longitude = dti.location.coordinates[1]

            val distance = locationA.distanceTo(locationB)

            if (distance < distEntreDTIyUser ){
                dtiCerca = position
                distEntreDTIyUser = distance
            }
            position++
        }
        UserRepository.dtiDocument = dtiCerca.toString()
        showData(dtiCerca , v )
    }

    fun dialog(context: Context, activity : Activity) {
        AlertDialog.Builder(context)
            .setMessage("Cerrar Aplicacion?")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { dialog, whichButton ->
                FirebaseAuth.getInstance().signOut()
                cleanLogUser()
                activity.finish()
            }
            .setNegativeButton("Cancelar") { dialog, whichButton ->

            }
            .show()

    }

}


