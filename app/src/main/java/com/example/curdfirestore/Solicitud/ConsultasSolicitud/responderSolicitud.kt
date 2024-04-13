package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import android.annotation.SuppressLint
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.RetrofitClientSolicitud.apiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("SuspiciousIndentation")
suspend fun conResponderSolicitud(
    solicitudId: String,
    status: String,
    onResponseReceived: (Boolean) -> Unit
) {
    println("modificar status")
    GlobalScope.launch(Dispatchers.IO) {
        val call = apiService.modificarStatusSoli(solicitudId, status)
        call.enqueue(object : Callback<RespuestaApiSolicitud> {
            override fun onResponse(
                call: Call<RespuestaApiSolicitud>,
                response: Response<RespuestaApiSolicitud>
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

            override fun onFailure(call: Call<RespuestaApiSolicitud>, t: Throwable) {
                // Manejar errores de red o excepciones
                t.printStackTrace()
                onResponseReceived(false) // Enviar false en caso de error
            }
        })
    }
}
/*@Composable
fun conResponderSolicitud(
    solicitudId: String,
    status:String,
) {
    var confirm by remember { mutableStateOf(false) }
    val call = apiService.modificarStatusSoli(solicitudId, status)
    call.enqueue(object : Callback<RespuestaApiSolicitud> {
        override fun onResponse(call: Call<RespuestaApiSolicitud>, response: Response<RespuestaApiSolicitud>) {
            if (response.isSuccessful) {
                // La modificación fue exitosa
                val respuestaApi = response.body()
                // Realizar acciones adicionales si es necesario
                confirm=true

            } else {
                // Ocurrió un error, manejar según sea necesario
                // Puedes obtener más información del error desde response.errorBody()
            }
        }

        override fun onFailure(call: Call<RespuestaApiSolicitud>, t: Throwable) {
            // Manejar errores de red o excepciones
            t.printStackTrace()
        }
    })

}

*/
/*
Ojooo--- Funcion de enviar notificacion estaba dentro, pero debe ir aparte
    var noticacionData= NoticacionData(
        notificacion_tipo = "sa",
        notificacion_id_solicitud = solicitudId,
        notificacion_id_viaje = viajeId,
        notificacion_usu_destino = userPasajero,
        notificacion_usu_origen = userConductor,
        //notificacion_token = fcmToken
    )
     var ejecutado by remember {
        mutableStateOf(false)
    }
    var usuarioCon= conObtenerUsuarioId(correo = userConductor)

    var usuarioPas = conObtenerUsuarioId(correo = userPasajero)



    if (confirm){ //Se modifico exitosamente

        if(status=="Aceptada"){
            if(ejecutado==false){

                if(usuarioCon!=null && usuarioPas!=null){
                    //Estas funciones solo existen, pero no estan implementadas
                    GuardarNotificacion(noticacionData = noticacionData)
                    enviarNotificacion(usuarioCon.usu_nombre, usuarioCon.usu_primer_apellido,
                        usuarioPas.usu_token, "sa",
                        onSuccess = {
                            println("Notificación enviada exitosamente")
                        },
                        onError = { errorMessage ->
                            println(errorMessage)
                        }
                    )
                }
            }
            ejecutado=true


            VentanaSolicitudAceptada(navController, userId ,show1,{show1=false }, {})
        }
        if(status=="Rechazada"){
            show2=true
            VentanaSolicitudRechazada(navController, userId ,show2,{show2=false }, {})
        }

    }

 */
