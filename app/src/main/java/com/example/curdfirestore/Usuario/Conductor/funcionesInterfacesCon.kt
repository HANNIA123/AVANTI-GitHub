package com.example.curdfirestore.Usuario.Conductor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.avanti.ui.theme.Aplicacion.encabezado
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaHoyCompleto
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.DayButton


@Composable
fun menuCon(
    navController: NavController,
    userID:String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        IconButton(onClick = {

            navController.navigate(route = "home/$userID")
        }) {
            Icon(
                modifier = Modifier
                    .size(35.dp),
                imageVector = Icons.Filled.Home,
                contentDescription = "Icono Home- inicio de viajes",
                tint = Color(137, 13, 88),

                )
        }
        IconButton(onClick = {

            navController.navigate(route = "viaje_inicio/$userID")
        }) {
            Icon(
                modifier = Modifier
                    .size(35.dp),

                painter = painterResource(id = R.drawable.car),
                contentDescription = "Icono Viajes",
                tint = Color(137, 13, 88)
            )
        }
        IconButton(onClick = {

            navController.navigate(route = "cuenta_conductor/$userID")
        }) {

            Icon(
                modifier = Modifier
                    .size(35.dp),
                painter = painterResource(id = R.drawable.btuser),
                contentDescription = "Icono Usuario",
                tint = Color(137, 13, 88),

                )
        }
    }
}

//
@Composable
fun tituloPantallaInicio(){
    var fechaHoy= obtenerFechaHoyCompleto()

    Row {
        Row (
            modifier = Modifier
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){



            Text(
                text = fechaHoy,
                style = TextStyle(
                    color = Color(71, 12, 107),
                    fontSize = 28.sp,
                    textAlign = TextAlign.Start

                )
            )

        }
        encabezado()
    }
}

@Composable
fun menuDesplegableCon(
    onDismiss: () -> Unit,
    navController: NavController,
                        userID:String,
                        ){
    val maxWidth =
        LocalConfiguration.current.screenWidthDp.dp / 2 // La mitad del ancho de la pantalla

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),


        contentAlignment = Alignment.TopStart
    ) {

        Box(
            modifier = Modifier
                .width(maxWidth)
                .fillMaxHeight()
                .background(Color.White)
                .padding(top = 20.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(end = 16.dp)

            ){


                TextButton(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                        navController.navigate(route = "home/$userID")
                    }) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp),

                        tint = Color(137, 13, 88),
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "Inicio",
                        textAlign = TextAlign.Left,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color(137, 13, 88),

                            )
                    )
                }

                Spacer(modifier= Modifier.height(20.dp))


                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

println("boroon presionado")
                        navController.navigate(route = "viaje_inicio/$userID")
//                        onDismiss()
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.carro),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp),
                        tint = Color(137, 13, 88),
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Viaje",
                        textAlign = TextAlign.Left,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color(137, 13, 88),

                            )
                    )
                }
                Spacer(modifier= Modifier.height(20.dp))

                TextButton(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.LightGray
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {

                        navController.navigate(route = "cuenta_conductor/$userID")
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.btuser),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp),
                        tint = Color(137, 13, 88),
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Text(
                        text = "Cuenta",
                        textAlign = TextAlign.Left,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color(137, 13, 88),

                            )
                    )
                }


            }


            Dialog(
                onDismissRequest = {
                                   onDismiss()

                    //expanded = false
                }, // Cierra el diálogo al tocar fuera de él
                content = {
                    // Contenido del diálogo
                    Column(
                        modifier = Modifier
                            .width(maxWidth)

                            .padding(16.dp),

                        ) {

                    }
                },

                )
        }

    }


}
