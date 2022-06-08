package com.example.proyectofinal.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Geo(

    var latitud: String,
    var longitud: String

): Parcelable
