package com.example.avanti.Usuario

import com.example.avanti.UserData
import com.example.avanti.VehicleData

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


public var BASE_URL = "http://192.168.1.13:3000/"
//public var BASE_URL = "https://us-central1-avanti-c4ba7.cloudfunctions.net/app/"

object RetrofitClientUsuario {
    val apiService: ApiServiceUsuario by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceUsuario::class.java)
    }
}


//const val newUrl = "https://us-central1-avanti-c4ba7.cloudfunctions.net/app"
const val newUrl = ""

interface ApiServiceUsuario {
    @GET("$newUrl/api/usuario/obtener/{id}")
    suspend fun obtenerUsuario(@Path("id") usuarioId: String): UserData //Obtiene los datos de un id dado

    @GET("$newUrl/api/vehiculo/{id}")
    suspend fun pasarVehiculo(@Path("id") vehiculoId: String): VehicleData //Obtiene los datos de un id dado
}