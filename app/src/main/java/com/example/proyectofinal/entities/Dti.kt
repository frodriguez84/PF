package com.example.proyectofinal.entities

import android.media.Image
import android.os.Parcelable
import android.widget.ImageView
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
data class Dti (

    var nombre : String,
    var id : Int,
    var geopoint : Geopoint,
    var aforo : String ,
    var altOla : String ,
    var bandera : String ,
    var dirViento : String ,
    var maxAforo : String ,
    var maxPark : String ,
    var parking : String ,
    var temperatura : String ,
    var velViento : String ,
    var uv : String,
    var image: Int


) : Parcelable {

}
