package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import com.example.avanti.ParadaData
import com.example.avanti.SolicitudData
import com.example.avanti.Usuario.BASE_URL
import com.example.avanti.Usuario.RespuestaApiParada
import com.example.avanti.Usuario.newUrl
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class RespuestaApiSolicitud(
    val success: Boolean,
    val message: String,
    val paradaId: String,
    val userId:String
)


object RetrofitClientSolicitud{
    val apiService: ApiServiceSolicitud by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServiceSolicitud::class.java)
    }
}




interface ApiServiceSolicitud{

    @GET("$newUrl/api/solicitud/obtenersolicitudhorario/{id}")
    suspend fun obtenerSolicitud(@Path("id") solicitudId: String): SolicitudData
    @POST("$newUrl/api/solicitud/registrarsolicitud") // Reemplaza con la ruta de tu endpoint
    fun enviarSolicitud(@Body solicitudData: SolicitudData): Call<RespuestaApiSolicitud>
//Caro-- 43-53









    //Hannia 54-64









    ///
}