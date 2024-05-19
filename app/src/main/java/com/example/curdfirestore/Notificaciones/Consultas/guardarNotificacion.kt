package com.example.curdfirestore.Notificaciones.Consultas

import android.annotation.SuppressLint
import com.example.avanti.NoticacionData
import com.example.avanti.ReporteData
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.RetrofitClientSolicitud.apiService
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
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



fun conRegistrarNotificacionNew(notificacionData: NoticacionData) {

    val db: FirebaseFirestore = Firebase.firestore
    val viajesRef = db.collection("notificacion")

    viajesRef.add(notificacionData)
        .addOnSuccessListener { documentReference ->
            println("notificacion agregado correctamente a Firebase con ID: ${documentReference.id}")
            // editarCampoViajeSinRuta(documentId = viaje.viaje_id, campo = "viaje_id_iniciado", valor=documentReference.id )
        }
        .addOnFailureListener { e ->
            println("Error al agregar el viaje a Firebase: $e")
        }

}
