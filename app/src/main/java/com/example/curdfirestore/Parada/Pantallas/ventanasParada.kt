package com.example.curdfirestore.Parada.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.curdfirestore.R


@Composable
fun ventanaAgregarOrigenParada(
    textVentana: String,
    show: Boolean,
    onDismiss: (Boolean) -> Unit,
    onConfirm: (Boolean) -> Unit,
) {
    if (show) {
        Dialog(onDismissRequest = { onDismiss(false) }) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                Text(
                    text = "Agregar parada",
                    modifier = Modifier.padding(2.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )
                Text(
                    text = textVentana,
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
                        painter = painterResource(id = R.drawable.ubica),
                        contentDescription = "Icono Viajes",
                        tint = Color(137, 13, 88)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Segundo botón para "No"
                        TextButton(onClick = {
                            onDismiss(false)
                        }) {
                            Text(text = "No")
                        }
                        // Primer botón para "Sí"
                        TextButton(onClick = {
                            onConfirm(true)
                        }) {
                            Text(text = "Si")
                        }


                    }
                }
            }
        }
    }
}


@Composable
fun myDiaologExitosa(
    navController: NavController,
    email: String,
    idViaje: String,
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(5.dp)
            ) {
                Text(
                    text = "Confirmación",
                    modifier = Modifier.padding(2.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 15.sp
                    )
                )
                Text(
                    text = "Viaje registrado",
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
                        painter = painterResource(id = R.drawable.cheque),
                        contentDescription = "Icono Viajes",
                        tint = Color(137, 13, 88)
                    )

                    // Primer botón para "Ver viaje"
                    TextButton(onClick = {

                        val pantalla = "viaje"
                        navController.navigate("ver_viaje/$idViaje/$email/$pantalla")
                    }) {
                        Text(text = "Ver viaje")
                    }

                    // Segundo botón (agregado) - Puedes personalizar el texto y la acción según tus necesidades
                    TextButton(onClick = {
                        // Acción para el segundo botón
                        var conpantalla="nomuestra"
                        navController.navigate("nueva_parada/$idViaje/$email/$conpantalla")
                    }) {
                        Text(text = "Nueva parada")
                    }
                }
            }
        }
    }
}


