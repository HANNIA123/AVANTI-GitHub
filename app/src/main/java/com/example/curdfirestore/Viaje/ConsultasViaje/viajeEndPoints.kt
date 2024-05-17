package com.example.avanti.Usuario


import com.example.avanti.ViajeData
import com.example.avanti.ViajeDataReturn
import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


//public var BASE_URL = "https://us-central1-avanti-c4ba7.cloudfunctions.net/app/"
data class RespuestaApiViaje(
    val success: Boolean,
    val message: String,
    val viajeId: String,
    val viaje_paradas: String
)



object RetrofitClientViaje {
    val apiService: ApiServiceViaje by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceViaje::class.java)
    }
}



interface ApiServiceViaje{

    //Agregado 13/03/2024 - hannia
    @POST("$newUrl/api/viaje/registrarviaje") // Reemplaza con la ruta de tu endpoint
    fun registrarViaje(@Body viajeData: ViajeData): Call<RespuestaApiViaje>

    @GET("$newUrl/api/viaje/obtenerviaje/{id}")
    suspend fun obtenerViaje(@Path("id") viajeId: String): ViajeData //Obtiene los datos de un id dado

    @GET("$newUrl/api/viaje/itinerarioviajes/{id}")
    suspend fun obtenerItinerarioCon(@Path("id") userId: String): List<ViajeDataReturn> // Obtener una lista de viajes para el viaje con el ID dado

    @PUT("$newUrl/api/viaje/actualizarstatus/{id}/{status}")
    fun actualizarStatusViaje(
        @Path("id") viajeId: String,
        @Path("status") nuevoStatus: String
    ): Call<RespuestaApiViaje>


    @PUT("$newUrl/api/viaje/editarviaje/{id}")
    fun actualizarViaje(
        @Path("id") viajeId: String,
        @Body viajeData: ViajeData
    ): Call<RespuestaApiViaje>



}