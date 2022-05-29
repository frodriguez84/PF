package com.example.proyectofinal.viewmodels

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.entities.Favoritos
import com.example.proyectofinal.entities.UserRepository.userMailLogin
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {

    var db = FirebaseFirestore.getInstance()

    fun registerOK(context: Context) {

        db.collection("users").document(userMailLogin).set(
            hashMapOf(
                "favs" to mutableListOf<Favoritos>()
            ))
        Toast.makeText(context, "Registro de usuario exitoso, inicie sesion", Toast.LENGTH_SHORT).show()
        //Snackbar.make(v, "Registro exitoso, inicie sesion", Snackbar.LENGTH_SHORT).show()
    }
    fun registerFail(context: Context){
        Toast.makeText(context, "se ha producido un error registrando al usuaio", Toast.LENGTH_SHORT).show()
        //Snackbar.make(v, "Error:" + "se ha producido un error registrando al usuaio", Snackbar.LENGTH_SHORT).show()
    }

    fun loginFail(context: Context){
        Toast.makeText(context, "USUARIO or PASSWORD incorrecto", Toast.LENGTH_SHORT).show()
        //Snackbar.make(v, "USUARIO or PASSWORD incorrecto", Snackbar.LENGTH_SHORT).show()

    }

    fun clearFields(email: TextView, pass: TextView) {
        email.text = ""
        pass.text = ""
    }
}