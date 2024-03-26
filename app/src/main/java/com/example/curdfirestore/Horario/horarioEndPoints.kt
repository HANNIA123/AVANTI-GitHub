package com.example.curdfirestore.Horario

import com.example.avanti.HorarioData
import com.example.avanti.Usuario.BASE_URL
import com.example.avanti.Usuario.RespuestaApiViaje
import com.example.avanti.Usuario.newUrl
import com.example.avanti.ViajeData
import com.example.avanti.ViajeDataReturn
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class RespuestaApiHorario(
    val success: Boolean,
    val message: String,
    val horarioId: String
)


object RetrofitClientHorario {
    val apiService: ApiServiceHorario by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceHorario::class.java)
    }
}



interface ApiServiceHorario{

    @POST("$newUrl/api/horario/registrarhorario") // Reemplaza con la ruta de tu endpoint
    fun registrarHorario(@Body horarioData: HorarioData): Call<RespuestaApiHorario>


}