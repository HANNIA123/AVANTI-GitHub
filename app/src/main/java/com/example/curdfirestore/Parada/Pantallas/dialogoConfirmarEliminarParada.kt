package com.example.curdfirestore.Parada.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.avanti.NoticacionData
import com.example.avanti.SolicitudData
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaFormatoddmmyyyy
import com.example.avanti.ui.theme.Aplicacion.obtenerHoraActual
import com.example.curdfirestore.Horario.ConsultasHorario.actualizarHorarioPas
import com.example.curdfirestore.Notificaciones.Consultas.conRegistrarNotificacion
import com.example.curdfirestore.Parada.ConsultasParada.eliminarParada
import com.example.curdfirestore.R
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesPorViaje
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.eliminarSolicitudPorCampo
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeRT
import com.example.curdfirestore.Viaje.ConsultasViaje.editarCampoViajeSinRuta

@Composable
fun dialogoConfirmarEliminarParada(
    onDismiss: () -> Unit,
    viajeId: String,
    userId: String,
    paradaId:String,
    navController: NavController

) {

    var eliminar by remember {
        mutableStateOf(false)
    }
    var ejecutado by remember {
        mutableStateOf(false)
    }
    var confirmN by remember {
        mutableStateOf(false)
    }
    var solicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }
    conObtenerSolicitudesPorViaje(viajeId, "Aceptada") { resultado ->
        solicitudes = resultado
    }

    val viaje= conObtenerViajeRT(viajeId = viajeId)

    if (eliminar) {
        if (solicitudes != null) {
            for ((index, solicitud) in solicitudes!!.withIndex()) {
                actualizarHorarioPas(solicitud.horario_id, "horario_solicitud", "No")

                val notificacionData = NoticacionData(
                    notificacion_tipo = "ve",
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
                            confirmN = respuestaExitosa
                        }
                    }

                }
                if (index == solicitudes!!.size - 1) {
                    eliminarSolicitudPorCampo(valorCampo = paradaId, campoBuscar = "parada_id")

                    ejecutado = true
                }
            }

        }
var ejecutadoPar by remember {
    mutableStateOf(false)
}
        viaje?.let {
            if(!ejecutadoPar) {
                val numParadas = viaje.viaje_paradas.toInt()
                println("num actual $numParadas")
                val nuevo = numParadas - 1
                println("nuevooo $nuevo")
                editarCampoViajeSinRuta(viajeId, "viaje_paradas", nuevo.toString())

                ejecutadoPar = true
            }
            eliminarParada(paradaId, navController, userId, viajeId)
        }

    }



        Dialog(
            onDismissRequest = {
                onDismiss()
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(15.dp)

                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Image(
                        modifier = Modifier
                            .height(190.dp),
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "Imagen pregunta",
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        text = "Se eliminará la parada y las solcitudes asociadas. ¿Deseas continuar?",
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black

                        )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(modifier = Modifier.align(Alignment.End)) {
                        TextButton(onClick = { onDismiss() }) {
                            Text(
                                text = "CANCELAR",
                                style = TextStyle(
                                    Color(137, 67, 242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        TextButton(
                            onClick = {
                                eliminar = true
                            }) {
                            Text(
                                text = "ACEPTAR",
                                style = TextStyle(
                                    Color(137, 67, 242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }
                    }

                }
            },

            )

}