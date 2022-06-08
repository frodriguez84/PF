package com.example.proyectofinal.entities

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface APIService {

    @GET("dti")
    fun getDtiList(): Call<List<Dti>>

    @GET("users")
    fun getBackEnd(): Call<List<Dti>>

    @GET()
    fun getDtiByid(id: String): Call<Dti>

}