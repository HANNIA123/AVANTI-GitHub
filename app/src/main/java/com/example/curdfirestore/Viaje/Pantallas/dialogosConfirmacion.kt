package com.example.curdfirestore.Viaje.Pantallas

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
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.ConsultasViaje.conEditarStatusViaje
import com.example.curdfirestore.Viaje.ConsultasViaje.eliminarViaje

@Composable
fun dialogoConfirmarCancelacion(
    onDismiss: () -> Unit,
    viajeId:String,
    userId:String,
    viajeStatus:String,
    navController: NavController

) {

    var textoBot = if(viajeStatus=="Disponible"){
        "El viaje será cancelado hasta que vuelvas activarlo ¿Deseas continuar?"
    }
    else{
        "El viaje estrá disponible nuevamente ¿Deseas continuar?"
    }

    val newStatus = if(viajeStatus=="Disponible"){
        "Cancelado"
    }
    else{
        "Disponible"
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
                                //onDaysSelected(if (selectedTrayecto != 0) setOf(selectedTrayecto) else emptySet())
                               // newTarifia(tarifa)
                                var pantalla="no"
conEditarStatusViaje(navController = navController,
    viajeId = viajeId, userId = userId, nuevoStatus = newStatus)
                               // actualizarStatusViaje(viajeId, "viaje_status", newStatus)


onDismiss()
//                               onDismiss()
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
fun dialogoConfirmarEliminarViaje(
    onDismiss: () -> Unit,
    viajeId:String,
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
eliminarViaje(documentId = viajeId,navController, userId)
                               // onDismiss()
//                               onDismiss()
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
fun dialogoConfirmarEditarViaje(
    onDismiss: () -> Unit,
    viajeId:String,
    userId:String,
    numParadas:String,
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
                        text = "Si editas el viaje, los pasajeros serán eliminados y deberán enviarte nuevamente la solicitud. ¿Deseas continuar?",
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
                        TextButton(onClick = {
                            val ruta = if (numParadas=="0") {
                                "ver_mapa_viaje_sin/$viajeId/$userId"
                            } else {
                                "ver_mapa_viaje/$viajeId/$userId"
                            }
                            navController.navigate(ruta)
                            onDismiss() }
                        ) {
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

                                 onDismiss()

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