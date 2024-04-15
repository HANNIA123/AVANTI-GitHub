package com.example.curdfirestore.Notificaciones.Consultas

import android.annotation.SuppressLint
import com.example.avanti.NoticacionData
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.RetrofitClientSolicitud.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("SuspiciousIndentation")
 suspend fun conRegistrarNotificacion(
    notificacionData: NoticacionData,
    onResponseReceived: (Boolean) -> Unit
) {

    GlobalScope.launch(Dispatchers.IO) {
        val call = RetrofitClientNotificacion.apiService.registrarNotificacion(notificacionData)
        call.enqueue(object : Callback<RespuestaApiNotificacion> {
            override fun onResponse(
                call: Call<RespuestaApiNotificacion>,
                response: Response<RespuestaApiNotificacion>
            ) {
                if (response.isSuccessful) {
                    println("estatus ecitos")
                    // La modificación fue exitosa
                    val respuestaApi = response.body()
                    onResponseReceived(true) // Enviar true cuando la respuesta es exitosa
                } else {
                    // Ocurrió un error, manejar según sea necesario
                    onResponseReceived(false) // Enviar false en caso de error
                }
            }

            override fun onFailure(call: Call<RespuestaApiNotificacion>, t: Throwable) {
                // Manejar errores de red o excepciones
                t.printStackTrace()
                onResponseReceived(false) // Enviar false en caso de error
            }
        })
    }





    /*

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiServiceNotificacion::class.java)
    val call: Call<RespuestaApiNotificacion> = apiService.registrarNotificacion(notificacionData)

    var resp by remember { mutableStateOf("") }

    call.enqueue(object : Callback<RespuestaApiNotificacion> {
        override fun onResponse(
            call: Call<RespuestaApiNotificacion>,
            response: Response<RespuestaApiNotificacion>
        ) {
            if (response.isSuccessful) {
                val respuesta = response.body()?.message ?: "Mensaje nulo"
                val idHorario = response.body()?.notificacionId.toString()
                resp = respuesta
            }
        }

        override fun onFailure(call: Call<RespuestaApiNotificacion>, t: Throwable) {
            // Manejar el fallo
            resp = "Error: ${t.message}"
        }
    })

     */
}
