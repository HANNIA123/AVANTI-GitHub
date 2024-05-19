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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
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
import com.example.avanti.ui.theme.Aplicacion.convertirStringAHora
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeRT
import com.example.curdfirestore.Viaje.Pantallas.FilaIconoTexto3
import com.example.curdfirestore.Viaje.Pantallas.cabeceraEditarAtras
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
    val viaje = conObtenerViajeRT(viajeId = viajeId)


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
    var campoValidaHora by remember {
        mutableStateOf(false)
    }
    var infoViaje by remember {
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
        campoValidaHora = false
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



                viaje?.let {
                    val ruta = if (viaje.viaje_paradas == "0") {
                        "ver_mapa_viaje_sin/$viajeId/$userId"
                    } else {
                        "ver_mapa_viaje/$viajeId/$userId"
                    }

                    cabeceraEditarAtras(
                        "Registrar parada",
                        navController,
                        ruta
                    )
                }



                val alturaT = maxh - 70.dp - 60.dp - 210.dp - 10.dp - 55.dp - 60.dp - 10.dp - 190.dp
                val espacio = (alturaT / 3) - 20.dp
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

                        Spacer(modifier = Modifier.height(10.dp))


                        Box(
                            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()
                        ) {


                            Image(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(210.dp),
                                painter = painterResource(id = R.drawable.registroparada),
                                contentDescription = "Imagen  parada",
                                contentScale = ContentScale.FillBounds
                            )
                        }


                        Spacer(modifier = Modifier.height(espacio))

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
                                    .fillMaxWidth()
                                    .height(60.dp),

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

                        FilaIconoTexto3(
                            icono = R.drawable.clock,
                            texto = horaFin,
                            onClick = {
                                timeDialogStateFin.show()
                                isDialogOpenFin = true
                            },
                            mostrarTextoError = campoHoraF,
                            mensaje = "*Por favor ingresa el horario"

                        )

                        if (campoValidaHora) {
                            Text(
                                text = "*Verifica el horario de la parada",
                                style = TextStyle(
                                    color = Color(86, 86, 86),
                                    fontSize = 12.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    infoViaje = true
                                },
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Icono",
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(10.dp, 5.dp),
                                tint = Color(86, 86, 86)
                            )

                            Text(
                                text = "Ver mi viaje",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color(86, 86, 86),
                                    textAlign = TextAlign.Justify
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(espacio))
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

            if (infoViaje) {
                viaje?.let {
                    dialogoInfViajeParada(
                        onDismiss = { infoViaje = false },
                        viaje = viaje
                    )
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

                viaje?.let {
                    val horaIni = convertirStringAHora(viaje.viaje_hora_llegada)
                    val horaFin = convertirStringAHora(viaje.viaje_hora_partida)
                    val horaPar = convertirStringAHora(selectedHora)

                    if (
                        (horaFin.isBefore(horaPar) && horaIni.isAfter(horaPar)) || horaPar==horaIni || horaPar==horaFin) {

                        navController.navigate("registrar_parada_barra/$userId/$viajeId/$nombre/$selectedHora")
                    } else {
                        campoValidaHora = true

                    }

                }


            }


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

