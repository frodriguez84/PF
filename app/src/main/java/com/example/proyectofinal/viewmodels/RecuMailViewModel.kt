package com.example.proyectofinal.viewmodels

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RecuMailViewModel : ViewModel() {

    private lateinit var mAuth : FirebaseAuth

    companion object{
        private const val SEND_FAIL: String = "No se puedo enviar un correo para restablecer tu contraseña"
        private const val MAIL_EMPTY: String = "Debe ingresar el mail"
        private const val SEND_OK: String = "Se ha enviado un correo para restablecer tu contraseña"
        private const val LANGUAGE_ES: String = "es"
    }

    fun resetPassword(emailRecu: String ,v: View, c: Context) {

        mAuth = FirebaseAuth.getInstance()
        mAuth.setLanguageCode(LANGUAGE_ES)
        mAuth.sendPasswordResetEmail(emailRecu).addOnCompleteListener {

            if(it.isSuccessful){ sendOk(c) }
            else { sendFail(c) }
        }
    }

    fun mailEmpty(c : Context){
        Toast.makeText(c, MAIL_EMPTY, Toast.LENGTH_SHORT).show()
    }

    private fun sendOk(c : Context){
        Toast.makeText( c , SEND_OK ,
            Toast.LENGTH_SHORT).show()
    }
    private fun sendFail(c : Context){
        Toast.makeText( c , SEND_FAIL ,
            Toast.LENGTH_SHORT).show()
    }

}