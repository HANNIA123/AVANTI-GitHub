package com.example.curdfirestore.Parada.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.curdfirestore.R

@Composable
fun ventanaNoEncontrado(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    userId: String,
    navController: NavController
) {
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
                    Text(
                        text = "Lo sentimos....",
                        modifier = Modifier.padding(2.dp),
                        style = TextStyle(
                            Color(137, 13, 88),
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Center

                    )

                    Text(
                        text = "En este momento no hay ningún viaje que coincida con tu búsqueda",
                        modifier = Modifier
                            .padding(2.dp),
                        style = TextStyle(
                            color = Color(104, 104, 104),
                            fontSize = 15.sp,
                            textAlign = TextAlign.Justify,
                        )
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(70.dp)
                                .padding(5.dp),
                            painter = painterResource(id = R.drawable.triste),
                            contentDescription = "Icono triste",
                            tint = Color(137, 13, 88)
                        )

                        // Primer botón para "Ver viaje"
                        TextButton(onClick = {
                            //onDismiss()
                            navController.navigate(route = "ver_itinerario_pasajero/$userId")

                            //navController.navigate("ver_itinerario_pasajero/$email")
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
            }
        }
    }
}


@Composable
fun mensajeNoEn(
    userId: String,
    navController: NavController
) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
        ) {
            Text(
                text = "Lo sentimos....",
                modifier = Modifier.padding(2.dp),
                style = TextStyle(
                    Color(137, 13, 88),
                    fontSize = 18.sp
                ),
                textAlign = TextAlign.Center

            )

            Text(
                text = "Las paradas encontradas estan demasiado lejos.",
                modifier = Modifier
                    .padding(2.dp),
                style = TextStyle(
                    color = Color(104, 104, 104),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Justify,
                )
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .size(70.dp)
                        .padding(5.dp),
                    painter = painterResource(id = R.drawable.triste),
                    contentDescription = "Icono triste",
                    tint = Color(137, 13, 88)
                )

                // Primer botón para "Ver viaje"
                TextButton(onClick = {
                    //onDismiss()
                    navController.navigate(route = "ver_itinerario_pasajero/$userId")

                    //navController.navigate("ver_itinerario_pasajero/$email")
                }) {
                    Text(
                        text = "VOLVER",
                        style = TextStyle(
                            Color(137, 67, 242),
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    )
                }


            }
        }
    }


