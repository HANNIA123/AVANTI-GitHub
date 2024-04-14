package com.example.curdfirestore.Solicitud.Pantallas

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun dialogoSolicitudAceptada(
    onDismiss: () -> Unit,
    navController: NavController,
    userId:String
) {
    val fadeInAlpha = animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1000), label = ""
    ).value

    val scope = rememberCoroutineScope()

    scope.launch {
        // Espera 3 segundos antes de cerrar el diálogo automáticamente
        //delay(2000)
        delay(1000)
        navController.navigate("ver_solicitudes_conductor/$userId")
        onDismiss()

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f)),
    ) {
        Dialog(
            onDismissRequest = { },
            // No permitir cerrar el diálogo al tocar fuera de él
            content = {
                Column(
                    modifier = Modifier

                        .padding(15.dp)
                        .background(Color.White)
                ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Solicitud aceptada",
                        style = TextStyle(Color(137,  13, 88 )),
                        fontSize = 16.sp,
                       textAlign = TextAlign.Center
                    )
                    Image(
                        modifier = Modifier
                            .height(140.dp)
                            .width(120.dp)
                            .align(Alignment.CenterHorizontally), // Centrar horizontalmente
                        painter = painterResource(id = R.drawable.solaceptada),
                        contentDescription = "Imagen pregunta",
                        contentScale = ContentScale.FillBounds,
                        alpha = fadeInAlpha
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

            },
        )
    }
}



@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun dialogoSolicitudRechazada(
    onDismiss: () -> Unit,
    navController: NavController,
    userId:String
) {
    val fadeInAlpha = animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1000), label = ""
    ).value

    val scope = rememberCoroutineScope()

    scope.launch {
        // Espera 3 segundos antes de cerrar el diálogo automáticamente
        //delay(2000)
        delay(1000)
        navController.navigate("ver_solicitudes_conductor/$userId")
        onDismiss()

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f)),
    ) {
        Dialog(
            onDismissRequest = { },
            // No permitir cerrar el diálogo al tocar fuera de él
            content = {
                Column(
                    modifier = Modifier

                        .padding(15.dp)
                        .background(Color.White)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Solicitud rechazada",
                        style = TextStyle(Color(137,  13, 88 )),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Image(
                        modifier = Modifier
                            .height(140.dp)
                            .width(120.dp)
                            .align(Alignment.CenterHorizontally), // Centrar horizontalmente
                        painter = painterResource(id = R.drawable.solrechazada),
                        contentDescription = "Imagen pregunta",
                        contentScale = ContentScale.FillBounds,
                        alpha = fadeInAlpha
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

            },
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun dialogoSolicitudNoPermitida(
    onDismiss: () -> Unit,

) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f)),
    ) {
        Dialog(
            onDismissRequest = { },
            // No permitir cerrar el diálogo al tocar fuera de él
            content = {
                Column(
                    modifier = Modifier

                        .padding(15.dp)
                        .background(Color.White)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Ya no tienes lugares disponibles para este viaje. ¿Deseas eliminar esta solicitud?",
                        style = TextStyle(Color(137,  13, 88 )),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                    Image(
                        modifier = Modifier
                            .height(140.dp)
                            .width(120.dp)
                            .align(Alignment.CenterHorizontally), // Centrar horizontalmente
                        painter = painterResource(id = R.drawable.pregunta),
                        contentDescription = "Imagen pregunta",
                        contentScale = ContentScale.FillBounds,
                        alpha = fadeInAlpha
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

                          //Rechazar
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
