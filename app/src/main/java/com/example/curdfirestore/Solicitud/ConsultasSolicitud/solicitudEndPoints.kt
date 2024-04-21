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
import retrofit2.http.PUT
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
    suspend fun obtenerSolicitudbyHorario(@Path("id") horarioId: String): SolicitudData
    @POST("$newUrl/api/solicitud/registrarsolicitud") // Reemplaza con la ruta de tu endpoint
    fun enviarSolicitud(@Body solicitudData: SolicitudData): Call<RespuestaApiSolicitud>
//Caro-- 43-53








    //Hannia 54-64
    @GET("$newUrl/api/solicitud/obtenersolicitudesconductor/{id}")
    suspend fun obtenerSolicitudesCon(@Path("id") userId: String): Response<List<SolicitudData>> // Obtener una lista de solicitudes con el id dado

    @PUT("$newUrl/api/solicitud/modificarstatussolicitud/{id}/{status}")
    fun modificarStatusSoli(
        @Path("id") viajeId: String,
        @Path("status") status: String
    ): Call<RespuestaApiSolicitud>

    //Consulta solicitudes por el id de un viaje y su status
    @GET("$newUrl/api/solicitud/solicitudesbyviaje/{idviaje}/{status}")
    suspend fun obtenerSolicitudesbyViaje(
        @Path("idviaje") idviaje: String,
        @Path("status") status: String
    ): List<SolicitudData>

    //Obtener solicitud teniendo su id











}