package com.example.proyectofinal.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))

    }
    private fun closeSplash(){
        this.finish()
    }

    override fun onRestart() {
        super.onRestart()
        closeSplash()
    }
}