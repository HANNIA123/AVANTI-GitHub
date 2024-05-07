package com.example.curdfirestore.Parada.Pantallas

import android.annotation.SuppressLint
import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun generalParada(
    navController: NavController,
    viajeId: String,
    userId: String,
    comPantalla: String,
    pantallaRegresa: String

) {

    var showPar by remember {
        mutableStateOf(true)
    }
    var maxh by remember {
        mutableStateOf(0.dp)
    }
    BoxWithConstraints {
        maxh = this.maxHeight
    }
    var viaje = conObtenerViajeId(viajeId = viajeId)


    val tamIcono = 55.dp

    var selectedHora by remember {
        mutableStateOf("")
    }
    var pickedTimeFin by remember {
        mutableStateOf(LocalTime.now())
    }
    var isDialogOpenFin by remember { mutableStateOf(false) }

    val timeDialogStateFin = rememberMaterialDialogState(isDialogOpenFin)

    var botonSiguiente by remember {
        mutableStateOf(false)
    }

    var nombre by remember {
        mutableStateOf("")
    }
    var horaFin by remember {
        mutableStateOf("Ingresa el horario")
    }
    //Variables para validar que los campos esten completos
    var campoHoraF by remember {
        mutableStateOf(false)
    }
    var campoNombre by remember {
        mutableStateOf(false)
    }
    if (selectedHora != "") {
        horaFin = "$selectedHora hrs"
    }

    LaunchedEffect(nombre) {
        campoNombre = false
    }

    LaunchedEffect(horaFin) {
        campoHoraF = false
    }

    Scaffold()
    {


        Box() {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color(239, 239, 239)
                    )
                    .height(maxh)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                cabeceraAtrasParada(
                    titulo = "Registro de parada",
                    navController = navController,
                    userid = userId,
                    regresar = pantallaRegresa,
                    viajeid = viajeId
                )

                val alturaT = maxh - 70.dp - 60.dp
                val espacio = 50.dp
                Column(
                    modifier = Modifier
                        .padding(30.dp)
                        .background(
                            Color.White
                        )
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center

                )
                {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxHeight()
                    ) {

                        Spacer(modifier = Modifier.height(20.dp))
                       /* Text(
                            text = "Ingresa los datos para las paradas de tu viaje.",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color(86, 86, 86),
                                textAlign = TextAlign.Justify

                            )
                        )*/


                        Box(contentAlignment = Alignment.Center, modifier=Modifier.fillMaxWidth()
                        ){


                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(210.dp),
                                painter = painterResource(id = R.drawable.registroparada),
                                contentDescription = "Imagen  parada",
                                contentScale = ContentScale.FillBounds
                            )
                        }


                        Spacer(modifier = Modifier.height(30.dp))

                        Text(
                            text = "Nombre para identificar a esta parada",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color(86, 86, 86),
                                textAlign = TextAlign.Justify
                            )
                        )


                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, Color.LightGray)
                                .background(Color.White)
                        ) {

                            OutlinedTextField(
                                value = nombre,
                                onValueChange = { newText -> nombre = newText },
                                modifier = Modifier
                                    .background(Color.White)
                                    .fillMaxWidth(),

                                textStyle = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black

                                ),
                                singleLine = true,

                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White,

                                    ),
                                trailingIcon = {// 4
                                    androidx.compose.material.Icon(
                                        modifier = Modifier
                                            .size(tamIcono)
                                            .padding(8.dp),
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = "Icono nombre",
                                        tint = Color(137, 13, 88)
                                    )
                                },


                                )
                        }

                        if (campoNombre) {
                            Text(
                                text = "*Por favor ingresa el nombre",
                                style = TextStyle(
                                    color = Color(86, 86, 86),
                                    fontSize = 12.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(espacio))

                        Text(
                            text = "Horario aproximado en que llegarás a esta parada",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color(86, 86, 86),
                                textAlign = TextAlign.Justify

                            )
                        )
                        Spacer(modifier = Modifier.height(5.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    Color.LightGray
                                )
                                .clickable {
                                    timeDialogStateFin.show()
                                    isDialogOpenFin = true
                                }
                        ) {
                            Text(
                                text = horaFin,
                                modifier = Modifier
                                    .weight(1f) // Ocupa todo el espacio disponible en la fila
                                    .padding(15.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black
                                )
                            )
                            // Spacer(modifier = Modifier.weight(1f)) // Espacio flexible para alinear el icono al final
                            Icon(
                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = "Icono horario",
                                modifier = Modifier
                                    .size(tamIcono)
                                    .padding(10.dp, 5.dp),
                                tint = Color(137, 13, 86)
                            )
                        }



                        if (campoHoraF) {
                            Text(
                                text = "*Por favor ingresa el horario ",
                                style = TextStyle(
                                    color = Color(86, 86, 86)
                                )

                            )
                        }

                        Spacer(modifier = Modifier.height(90.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    137,
                                    13,
                                    88
                                )
                            ),
                            onClick = {
                                botonSiguiente = true
                                // navController.navigate(route = "perfil_conductor/$userID")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Siguiente", style = TextStyle(
                                    fontSize = 22.sp,
                                    color = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))


                    }
                }


            }


            if (comPantalla == "muestra") {
                viaje?.let {

                    if (showPar) {
                        ventanaAgregarOrigenParada(
                            onDismiss = { showPar = false },
                            viaje,
                            viajeId,
                            userId
                        )
                    }
                }
            }

        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (timeDialogStateFin.showing) {
                        Color.Black.copy(alpha = 0.5f)
                    } else {
                        Color.Black.copy(alpha = 0.0f)
                    }
                ) // Fondo oscuro con transparencia
        ) {
            MaterialDialog(
                dialogState = timeDialogStateFin,
                buttons = {
                    positiveButton(
                        text = "Aceptar",
                    )
                    negativeButton(text = "Cancelar")
                }
            ) {

                timepicker(
                    initialTime = LocalTime.NOON,
                    title = "Selecciona el horario de llegada a la parada",

                    ) {
                    pickedTimeFin = it
                    selectedHora = pickedTimeFin.toString()
                }
            }
        }





        if (botonSiguiente) {
            //Validar que haya llenado todos los campos

            if (nombre == "Ingresa el nombre" || nombre == "" || selectedHora == "") {
                if (nombre == "") {
                    campoNombre = true
                }
                if (nombre == "Ingresa el nombre") {
                    campoNombre = true
                }

                if (selectedHora == "") {
                    campoHoraF = true
                }

            } else {
                //  navController.navigate(route = "registrar_origen_conductor/$userId/$diaCon/$selectedHoraInicio/$selectedHoraFin/$selectedLugares/$selectedTarifa")
                navController.navigate("registrar_parada_barra/$userId/$viajeId/$nombre/$selectedHora")
                println("Campos completos de parada")
            }

            botonSiguiente = false

        }


    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MyviajeParada() {
    val navController = rememberNavController()

    generalParada(navController = navController, "123", userId = "hannia", "muestra", "viaje")
}

