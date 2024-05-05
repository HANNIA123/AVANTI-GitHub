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
                    val respuestaApi = response.body()
                    onResponseReceived(true) // Enviar true cuando la respuesta es exitosa
                } else {
                    onResponseReceived(false) // Enviar false en caso de error
                }
            }
            override fun onFailure(call: Call<RespuestaApiNotificacion>, t: Throwable) {
                t.printStackTrace()
                onResponseReceived(false) // Enviar false en caso de error
            }
        })
    }
}


