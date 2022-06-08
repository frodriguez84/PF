package com.example.proyectofinal.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Geopoint(

    var type: String,
    var coordinates: ArrayList<Double>,
    //var latitud : String,
    //var longitud : String

): Parcelable
