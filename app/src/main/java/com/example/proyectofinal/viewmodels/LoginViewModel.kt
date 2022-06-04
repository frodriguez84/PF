package com.example.proyectofinal.viewmodels

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.proyectofinal.R
import com.example.proyectofinal.entities.Favoritos
import com.example.proyectofinal.entities.UserRepository.userMailLogin
import com.example.proyectofinal.fragments.LoginFragmentDirections
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {

    var db = FirebaseFirestore.getInstance()
    private lateinit var email : TextView
    private lateinit var pass : TextView

    fun login(v: View, c: Context){
        email = v.findViewById(R.id.emailText)
        pass = v.findViewById(R.id.passText)

        if (email.text.isNotEmpty() && pass.text.isNotEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email.text.toString(),
                pass.text.toString()
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    userMailLogin = email.text.toString()
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    v.findNavController().navigate(action)
                    clearFields(email,pass)
                } else {
                    loginFail(c)
                }
            }
        }
    }

    fun register(v: View, c: Context){
        email = v.findViewById(R.id.emailText)
        pass = v.findViewById(R.id.passText)

        if (email.text.isNotEmpty() && pass.text.isNotEmpty()) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                email.text.toString(),
                pass.text.toString()
            ).addOnCompleteListener {

                if (it.isSuccessful) {
                    userMailLogin = email.text.toString()
                    registerOK(c)
                } else {
                    registerFail(c)
                }
            }
        } else {
            registerFail(c)
        }
    }

    fun registerOK(context: Context) {

        db.collection("users").document(userMailLogin).set(
            hashMapOf(
                "favs" to mutableListOf<Favoritos>()
            ))
        Toast.makeText(context, "Registro de usuario exitoso, inicie sesion", Toast.LENGTH_SHORT).show()

    }
    fun registerFail(context: Context){
        Toast.makeText(context, "se ha producido un error registrando al usuario", Toast.LENGTH_SHORT).show()

    }

    fun loginFail(context: Context){
        Toast.makeText(context, "USUARIO o PASSWORD incorrecto", Toast.LENGTH_SHORT).show()


    }

    fun clearFields(email: TextView, pass: TextView) {
        email.text = ""
        pass.text = ""
    }
}