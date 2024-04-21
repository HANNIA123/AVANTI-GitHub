package com.example.curdfirestore.Notificaciones.Consultas

import com.example.avanti.HorarioData
import com.example.avanti.NoticacionData
import com.example.avanti.ParadaData
import com.example.avanti.SolicitudData
import com.example.avanti.Usuario.BASE_URL
import com.example.avanti.Usuario.RespuestaApiParada
import com.example.avanti.Usuario.newUrl
import com.example.curdfirestore.Horario.RespuestaApiHorario
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class RespuestaApiNotificacion(
    val success: Boolean,
    val message: String,
    val notificacionId: String,

)



object RetrofitClientNotificacion{
    val apiService: ApiServiceNotificacion by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceNotificacion::class.java)
    }
}




interface ApiServiceNotificacion{

    @POST("$newUrl/api/notificacion/registrarnotificacion") // Reemplaza con la ruta de tu endpoint
    fun registrarNotificacion(@Body notificacionData: NoticacionData): Call<RespuestaApiNotificacion>


}