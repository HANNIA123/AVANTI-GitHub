package com.example.curdfirestore.Horario.Pantallas

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.curdfirestore.Horario.ConsultasHorario.conEditarStatusHorario
import com.example.curdfirestore.Notificaciones.Consultas.conRegistrarNotificacion
import com.example.curdfirestore.R
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conActualizarSolicitudByStatus

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun dialogoConfirmarCancelacionP(
    onDismiss: () -> Unit,
    horarioId: String,
    userId: String,
    horarioStatus: String,
    solicitud: SolicitudData,
    navController: NavController

) {
    var cancelado by remember {
        mutableStateOf(false)
    }
    var ejecutado by remember {
        mutableStateOf(false)
    }
    var confirmN by remember {
        mutableStateOf(false)
    }


    val textoBot = if (horarioStatus == "Disponible") {
        "El horario será cancelado hasta que vuelvas activarlo ¿Deseas continuar?"
    } else {
        "El horario estará disponible nuevamente ¿Deseas continuar?"
    }

    val newStatus = if (horarioStatus == "Disponible") {
        "Cancelado"
    } else {
        "Disponible"
    }


    val tipoNot = if (newStatus == "Cancelado") {
        "cv" //viaje cancelado
    } else {
        "vd" //viaje disponible
    }

    val notificacionData = NoticacionData(
        notificacion_tipo = tipoNot,
        notificacion_usu_origen = userId,
        notificacion_usu_destino = solicitud.conductor_id,
        notificacion_id_viaje = solicitud.viaje_id,
        notificacion_id_solicitud = solicitud.solicitud_id,
        notificacion_fecha = obtenerFechaFormatoddmmyyyy(),
        notificacion_hora = obtenerHoraActual(),


        )
    if (cancelado) {
        if (!ejecutado) {
            LaunchedEffect(Unit) {
                conRegistrarNotificacion(notificacionData) { respuestaExitosa ->
                    confirmN = respuestaExitosa
                }
                conActualizarSolicitudByStatus(
                    documentId = solicitud.solicitud_id,
                    campo = "solicitud_activa_pas",
                    valor = tipoNot
                )


                conEditarStatusHorario(
                    navController = navController,
                    horarioId = horarioId, userId = userId, nuevoStatus = newStatus
                )
                ejecutado = true
                onDismiss()

            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),

        ) {
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
                        painter = painterResource(id = R.drawable.pregunta),
                        contentDescription = "Imagen pregunta",
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
                        text = textoBot,
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
                                cancelado = true

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

}