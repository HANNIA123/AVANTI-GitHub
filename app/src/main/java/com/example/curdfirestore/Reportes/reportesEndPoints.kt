package com.example.curdfirestore.Reportes

import com.example.avanti.HorarioData
import com.example.avanti.HorarioDataReturn
import com.example.avanti.ReporteData
import com.example.avanti.Usuario.BASE_URL
import com.example.avanti.Usuario.newUrl
import com.example.avanti.ViajeData
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

data class RespuestaApiReporte(
    val success: Boolean,
    val message: String,
    val userId: String
)


object RetrofitClientReporte {
    val apiService: ApiServiceReporte by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceReporte::class.java)
    }
}



interface ApiServiceReporte{
    @POST("/api/reporte/registrarreporte") // Reemplaza con la ruta de tu endpoint
    fun enviarReporte(@Body reporteData: ReporteData): Call<RespuestaApiReporte>

}