package com.example.curdfirestore.Viaje.Funciones

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.NoticacionData
import com.example.avanti.SolicitudData
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaFormatoddmmyyyy
import com.example.avanti.ui.theme.Aplicacion.obtenerHoraActual
import com.example.curdfirestore.Notificaciones.Consultas.conRegistrarNotificacion

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun registrarNotificacionViaje(
    tipoNot:String,
    solicitudes: List<SolicitudData>,
    userId:String,
    viajeId:String
){
    var notEnviada by remember {
        mutableStateOf(false)
    }
    var ejecutado by remember {
        mutableStateOf(false)
    }
    for ((index, solicitud) in solicitudes!!.withIndex()) {

        val notificacionData = NoticacionData(
            notificacion_tipo = tipoNot,
            notificacion_usu_origen = userId,
            notificacion_usu_destino = solicitud.pasajero_id,
            notificacion_id_viaje = viajeId,
            notificacion_id_solicitud = solicitud.solicitud_id,
            notificacion_fecha = obtenerFechaFormatoddmmyyyy(),
            notificacion_hora = obtenerHoraActual(),

            )
        if (!ejecutado) {
            LaunchedEffect(Unit) {
                conRegistrarNotificacion(notificacionData) { respuestaExitosa ->
                    notEnviada = respuestaExitosa
                }
            }

        }
        if (index == solicitudes.size - 1) {
            ejecutado = true
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun registrarNotificacionViajePas(
    tipoNot:String,
    solicitud: SolicitudData,
    userId:String,
    viajeId:String
){
    var notEnviada by remember {
        mutableStateOf(false)
    }
    var ejecutado by remember {
        mutableStateOf(false)
    }


        val notificacionData = NoticacionData(
            notificacion_tipo = tipoNot,
            notificacion_usu_origen = userId,
            notificacion_usu_destino = solicitud.conductor_id,
            notificacion_id_viaje = viajeId,
            notificacion_id_solicitud = solicitud.solicitud_id,
            notificacion_fecha = obtenerFechaFormatoddmmyyyy(),
            notificacion_hora = obtenerHoraActual(),

            )
        if (!ejecutado) {
            LaunchedEffect(Unit) {
                conRegistrarNotificacion(notificacionData) { respuestaExitosa ->
                    notEnviada = respuestaExitosa
                }
                ejecutado=false
            }

        }


}