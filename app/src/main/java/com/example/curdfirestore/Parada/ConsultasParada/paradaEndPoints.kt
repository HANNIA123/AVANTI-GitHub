package com.example.avanti.Usuario

import com.example.avanti.ParadaData
import com.example.avanti.UserData
import com.example.avanti.VehicleData
import com.example.avanti.ViajeData
import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


//public var BASE_URL = "https://us-central1-avanti-c4ba7.cloudfunctions.net/app/"
data class RespuestaApiParada(
    val success: Boolean,
    val message: String,
    val paradaId: String
)


object RetrofitClientParada{
    val apiService: ApiServiceParada by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceParada::class.java)
    }
}




interface ApiServiceParada{

    //Agregado 13/03/2024 - hannia
    @POST("$newUrl/api/parada/registrarparada") // Reemplaza con la ruta de tu endpoint
    fun registrarParada(@Body paradaData: ParadaData): Call<RespuestaApiParada>


}