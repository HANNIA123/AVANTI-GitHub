package com.example.curdfirestore.Reportes.Pantallas

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
import com.example.curdfirestore.Horario.ConsultasHorario.actualizarHorarioPas
import com.example.curdfirestore.Horario.ConsultasHorario.eliminarHorario
import com.example.curdfirestore.R
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.eliminarSolicitudById
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.eliminarSolicitudPorHorarioId
import com.example.curdfirestore.Viaje.ConsultasViaje.aumentarLugaresDeViaje

@Composable
fun dialogoBorrarPasajero(
    onDismiss: () -> Unit,
    userId:String,
    viajeId:String,
    idsolicitud: String,
    horarioId:String,
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
                        text = "Este pasajero se eliminará de tus viajes. ¿Deseas continuar? $idsolicitud",
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
                                eliminarSolicitudById(idsolicitud, navController, userId, "ver_pasajeros_conductor")
                                aumentarLugaresDeViaje(viajeId)
                                actualizarHorarioPas(horarioId,"horario_solicitud", "No" )
                                //Falta la creacion de Notificacion

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