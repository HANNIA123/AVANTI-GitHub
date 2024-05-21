package com.example.curdfirestore.Solicitud

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.avanti.HorarioData
import com.example.avanti.NoticacionData
import com.example.avanti.ParadaData
import com.example.avanti.SolicitudData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaFormatoddmmyyyy
import com.example.avanti.ui.theme.Aplicacion.obtenerHoraActual
import com.example.curdfirestore.Horario.ConsultasHorario.conActualizarSolicitudHorario
import com.example.curdfirestore.Notificaciones.Consultas.conRegistrarNotificacion
import com.example.curdfirestore.Notificaciones.Consultas.enviarNotificacion
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conRegistrarSolicitud
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.textInMarker
import com.google.android.gms.maps.model.LatLng


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ventanaEnviarSolicitud(
    navController: NavController,
    email: String,
    parada: ParadaData,
    horario: HorarioData,
    horarioId: String,
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    var boton by remember { mutableStateOf(false) }
    var confirmN by remember { mutableStateOf(false) }
    var confirm by remember { mutableStateOf(false) }
    var enviado by remember { mutableStateOf(false) }

    var ejecutado by remember { mutableStateOf(false) }
    if (show) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),

            ) {
            Dialog(onDismissRequest = { onDismiss() }) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(10.dp)

                ) {

                    var markerLatO by remember { mutableStateOf(0.0) }
                    var markerLonO by remember { mutableStateOf(0.0) }
                    val markerCoordenadasLatLngO = convertirStringALatLng(parada.par_ubicacion)

                    if (markerCoordenadasLatLngO != null) {
                        markerLatO = markerCoordenadasLatLngO.latitude
                        markerLonO = markerCoordenadasLatLngO.longitude
                        // Hacer algo con las coordenadas LatLng
                        println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
                    } else {
                        // La conversión falló
                        println("Error al convertir la cadena a LatLng")
                    }

                    val addressP = convertCoordinatesToAddress(LatLng(markerLatO, markerLonO))

                    if (parada.par_nombre == "Origen") {

                        Text(
                            text = "Tu punto de partida",
                            modifier = Modifier.padding(2.dp),

                            style = TextStyle(
                                Color(137, 13, 88),
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Center

                        )

                        textInMarker(Label = "Dirección: ", Text = addressP)
                        // TextInMarker(Label ="Trayecto: ", Text = "${horario.horario_trayecto} hrs")
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        233,
                                        168,
                                        219
                                    )
                                ),
                                onClick = {
                                    // Cerrar el diálogo cuando se hace clic en "Cerrar"
                                    onDismiss()
                                }) {
                                Text(
                                    text = "Cerrar", style = TextStyle(
                                        fontSize = 15.sp,
                                        color = Color.White
                                    )
                                )
                            }
                        }

                    } else if (parada.par_nombre == "Destino") {

                        Text(
                            text = "Tu punto de llegada",
                            modifier = Modifier.padding(2.dp),

                            style = TextStyle(
                                Color(137, 13, 88),
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        textInMarker(Label = "Dirección: ", Text = addressP)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        233,
                                        168,
                                        219
                                    )
                                ),
                                onClick = {
                                    // Cerrar el diálogo cuando se hace clic en "Cerrar"
                                    onDismiss()
                                }) {
                                Text(
                                    text = "Cerrar", style = TextStyle(
                                        fontSize = 15.sp,
                                        color = Color.White
                                    )
                                )
                            }
                        }

                    } else {
                        Text(
                            text = "Información de la parada encontrada",
                            modifier = Modifier.padding(2.dp),

                            style = TextStyle(
                                Color(137, 13, 88),
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Center
                        )

                        if (!enviado) {

                            textInMarker(Label = "Parada: ", Text = parada.par_nombre)
                            if (horario.horario_trayecto == "0") {
                                textInMarker(Label = "Origen: ", Text = "UPIITA-IPN")
                                textInMarker(Label = "Destino: ", Text = addressP)


                            } else {
                                textInMarker(Label = "Origen: ", Text = addressP)
                                textInMarker(Label = "Destino: ", Text = "UPIITA-IPN")

                            }
                            textInMarker(
                                Label = "Horario aproximado: ",
                                Text = "${parada.par_hora} hrs"
                            )

                            val viaje= conObtenerViajeId(viajeId = parada.viaje_id)
                            viaje?.let {
                                textInMarker(
                                    Label = "Tarifa establecida: ",
                                    Text = "$ ${viaje.viaje_tarifa} "
                                )
                            }


                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(
                                                233,
                                                168,
                                                219
                                            )
                                        ),
                                        onClick = {
                                            // Cerrar el diálogo cuando se hace clic en "Cerrar"
                                            onDismiss()
                                        }) {
                                        Text(
                                            text = "Cerrar", style = TextStyle(
                                                fontSize = 15.sp,
                                                color = Color.White
                                            )
                                        )
                                    }

                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(
                                                137,
                                                13,
                                                88
                                            )
                                        ),
                                        onClick = {
                                            //Guardar en la bd
                                            boton = true
                                        }) {
                                        Text(
                                            text = "Solicitar", style = TextStyle(
                                                fontSize = 15.sp,
                                                color = Color.White
                                            )
                                        )
                                    }

                                }
                            }

                        } else {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Tu solicitud ha sido enviada. Te avisaremos cuando el conductor la acepte",
                                    modifier = Modifier.padding(2.dp),

                                    style = TextStyle(
                                        Color.Black,
                                        fontSize = 15.sp
                                    ),
                                    textAlign = TextAlign.Center
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            backgroundColor = Color(
                                                233,
                                                168,
                                                219
                                            )
                                        ),
                                        onClick = {
                                            // Cerrar el diálogo cuando se hace clic en "Cerrar"
                                            navController.navigate(route = "ver_itinerario_pasajero/$email")

                                        }) {
                                        Text(
                                            text = "Cerrar", style = TextStyle(
                                                fontSize = 15.sp,
                                                color = Color.White
                                            )
                                        )
                                    }
                                }

                            }

                        }


                    }
                }

            }

        }
    }


    val usuarioPas= conObtenerUsuarioId(correo = email)
    val usuarioCon= conObtenerUsuarioId(correo = parada.user_id)

    if (boton == true && ejecutado == false) {

        val fecha_now = obtenerFechaFormatoddmmyyyy()


        val solicitudData = usuarioPas?.let {
            SolicitudData(
                viaje_id = parada.viaje_id,
                horario_id = horarioId,
                conductor_id = parada.user_id,
                pasajero_id = email,
                pasajero_token= it.usu_token,
                parada_id = parada.par_id,
                horario_trayecto = horario.horario_trayecto,
                solicitud_status = "Pendiente",
                solicitud_date = fecha_now,
                solicitud_viaje_iniciado="no",
                solicitud_validacion_pasajero="pendiente",
                solicitud_validacion_conductor= "pendiente"


            )
        }

        val notificacionData = NoticacionData(
            notificacion_tipo = "sr",
            notificacion_usu_origen = email, //conductor
            notificacion_usu_destino = parada.user_id, //pasajero
            notificacion_id_viaje = parada.viaje_id,
            notificacion_fecha = obtenerFechaFormatoddmmyyyy(),
            notificacion_hora = obtenerHoraActual().toString()
        )
        if (solicitudData != null) {
            conRegistrarSolicitud(solicitudData)
        } //Registra la solicitud en la BD

        conActualizarSolicitudHorario(horarioId = horarioId, status ="Si" )

        LaunchedEffect(Unit) {
            if(!confirmN) {
                conRegistrarNotificacion(notificacionData) { respuestaExitosa ->
                    confirmN = respuestaExitosa
                }
            }
        }

        //---------------------------ENVIAR NOTIFICACIÓN-------------------------------------

        enviarNotificacion(usuarioPas!!.usu_nombre, usuarioPas.usu_segundo_apellido, usuarioCon!!.usu_token, "sr", parada.user_id,
            onSuccess = {
                println("Notificación enviada exitosamente")
            },
            onError = { errorMessage ->
                println(errorMessage)
            }
        )

        //---------------------------ENVIAR NOTIFICACIÓN-------------------------------------

        //GuardarNotificacion(noticacionData = notificacionData)
        ejecutado = true
        enviado = true

    }


}
