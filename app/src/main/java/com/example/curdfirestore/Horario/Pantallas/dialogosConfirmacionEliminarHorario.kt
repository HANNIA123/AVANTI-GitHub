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
import com.example.avanti.UserData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaFormatoddmmyyyy
import com.example.avanti.ui.theme.Aplicacion.obtenerHoraActual
import com.example.curdfirestore.Horario.ConsultasHorario.eliminarHorario
import com.example.curdfirestore.Notificaciones.Consultas.conRegistrarNotificacion
import com.example.curdfirestore.Notificaciones.Consultas.enviarNotificacion
import com.example.curdfirestore.R
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.eliminarSolicitudPorHorarioId

import com.example.curdfirestore.Viaje.ConsultasViaje.aumentarLugaresDeViaje


@Composable
fun dialogoConfirmarEliminarHorarioP(
    onDismiss: () -> Unit,
    horarioId:String,
    userId:String,
    navController: NavController

) {


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
                // Contenido del diálogo
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
                        text = "Este viaje se eliminará definitivamente. ¿Deseas continuar?",
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


                    Row(modifier = Modifier.align(Alignment.End)){
                        TextButton(onClick = { onDismiss() }) {
                            Text(text = "CANCELAR",
                                style = TextStyle(
                                    Color(137,67,242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        TextButton(
                            onClick = {
                                eliminarHorario(documentId = horarioId,navController, userId)

                            }) {
                            Text(text = "ACEPTAR",
                                style = TextStyle(
                                    Color(137,67,242),
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


@Composable
fun dialogoConfirmarEliminarHorarioSE(
    onDismiss: () -> Unit,
    horarioId:String,
    userId:String,
    navController: NavController

) {

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
                // Contenido del diálogo
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
                        text = "Este viaje se eliminará definitivamente. ¿Deseas continuar?",
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


                    Row(modifier = Modifier.align(Alignment.End)){
                        TextButton(onClick = { onDismiss() }) {
                            Text(text = "CANCELAR",
                                style = TextStyle(
                                    Color(137,67,242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        TextButton(
                            onClick = {
                                eliminarHorario(documentId = horarioId,navController, userId)
                                eliminarSolicitudPorHorarioId(horarioId)
                            }) {
                            Text(text = "ACEPTAR",
                                style = TextStyle(
                                    Color(137,67,242),
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun dialogoConfirmarEliminarHorarioSEA(
    onDismiss: () -> Unit,
    horarioId:String,
    viajeId: String,
    userId:String,
    solicitud: SolicitudData,
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

    var usuarioPas by remember { mutableStateOf<UserData?>(null) }
    var usuarioCon by remember { mutableStateOf<UserData?>(null) }

    usuarioPas = conObtenerUsuarioId(correo = userId)
    usuarioCon = conObtenerUsuarioId(correo = solicitud.conductor_id)

    val notificacionData = NoticacionData(
        notificacion_tipo = "ve",
        notificacion_usu_origen = userId,
        notificacion_usu_destino = solicitud.conductor_id,
        notificacion_id_viaje = solicitud.viaje_id,
        notificacion_id_solicitud = solicitud.solicitud_id,
        notificacion_fecha = obtenerFechaFormatoddmmyyyy(),
        notificacion_hora = obtenerHoraActual(),


        )
    if (eliminar) {
        if (!ejecutado) {
            LaunchedEffect(Unit) {
                conRegistrarNotificacion(notificacionData) { respuestaExitosa ->
                    confirmN = respuestaExitosa
                }
                eliminarHorario(documentId = horarioId,navController, userId)
                aumentarLugaresDeViaje(viajeId)
                eliminarSolicitudPorHorarioId(horarioId)

                //---------------------------ENVIAR NOTIFICACIÓN-------------------------------------
                enviarNotificacion(usuarioPas!!.usu_nombre, usuarioPas!!.usu_primer_apellido,
                    usuarioCon!!.usu_token, "ve", solicitud.conductor_id,
                    onSuccess = {
                        println("Notificación enviada exitosamente")
                    },
                    onError = { errorMessage ->
                        println(errorMessage)
                    }
                )
                //---------------------------ENVIAR NOTIFICACIÓN-------------------------------------


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

            }, // Cierra el diálogo al tocar fuera de él
            content = {
                // Contenido del diálogo
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
                        text = "Este viaje se eliminará definitivamente. ¿Deseas continuar?",
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


                    Row(modifier = Modifier.align(Alignment.End)){
                        TextButton(onClick = { onDismiss() }) {
                            Text(text = "CANCELAR",
                                style = TextStyle(
                                    Color(137,67,242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        TextButton(
                            onClick = {
                              eliminar=true
                            }) {
                            Text(text = "ACEPTAR",
                                style = TextStyle(
                                    Color(137,67,242),
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