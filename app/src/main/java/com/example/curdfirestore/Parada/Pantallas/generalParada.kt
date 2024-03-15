package com.example.curdfirestore.Parada.Pantallas

import android.annotation.SuppressLint
import android.os.Build

import androidx.annotation.RequiresApi

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
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults

import androidx.compose.material.icons.filled.Info


import androidx.compose.material3.ExperimentalMaterial3Api

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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.ParadaData
import com.example.avanti.Usuario.Conductor.Pantallas.maxh
import com.example.avanti.ui.theme.Aplicacion.cabeceraConBotonAtras
import com.example.curdfirestore.Parada.ConsultasParada.actualizarNumParadas
import com.example.curdfirestore.Parada.ConsultasParada.conRegistrarParada
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun generalParada(
    navController: NavController,
    viajeId: String,
    userId: String,
    comPantalla:String
) {
    var showPar by remember {
        mutableStateOf(true)
    }

    var ejecutado by remember {
        mutableStateOf(false)
    }



    ///
    println("Con pamtalla $showPar")
    if (comPantalla == "muestra") {
        var seleccion by remember {
            mutableStateOf(false)
        }
        var viaje= conObtenerViajeId(viajeId = viajeId)

        var textVentana by remember { mutableStateOf("") }
        var textGuardar by remember { mutableStateOf("") }
        var ubicacionP by remember { mutableStateOf("") }
        var horarioP by remember { mutableStateOf("") }

        viaje?.let{


                if (viaje!!.viaje_trayecto == "0") {
                    textVentana = "¿Deseas agregar tu ubicación de destino como una parada?"
                    textGuardar = "Destino de un viaje"
                    ubicacionP = viaje!!.viaje_destino
                    horarioP = viaje!!.viaje_hora_llegada


                } else {
                    textVentana = "¿Deseas agregar tu ubicación de origen como una parada?"
                    textGuardar = "Origen de un viaje"
                    ubicacionP = viaje!!.viaje_origen
                    horarioP = viaje!!.viaje_hora_partida
                }



                if (showPar) {
                    ventanaAgregarOrigenParada(
                        textVentana,
                        showPar,
                        onDismiss = { choice ->
                            println("El usuario seleccionó No: $choice")
                            showPar = false


                        },
                        onConfirm = { choice ->
                            seleccion = choice
                            println("El usuario seleccionó Sí: $choice")
                            showPar = false


                        }
                    )
                }

                if (seleccion) {
                    val paradaData = ParadaData(
                        viaje_id = viajeId,
                        par_nombre = textGuardar,
                        par_hora = horarioP,
                        par_ubicacion = ubicacionP,
                        user_id = userId
                    )


                    if (ejecutado == false) {
                        var numParadas by remember { mutableStateOf("") }

                        conRegistrarParada(paradaData)
                        var numParadasInt = 0
                        var newNum = 0

                        numParadas = viaje.viaje_paradas
                        numParadasInt = numParadas.toInt()
                        newNum = numParadasInt + 1

                        actualizarNumParadas(viajeId, "viaje_paradas", newNum.toString())



                        ejecutado = true

                    }
                }

        }



    }










    /////////////


    var selectedHora by remember {
        mutableStateOf("")
    }
    var pickedTimeFin by remember {
        mutableStateOf(LocalTime.now())
    }
    var isDialogOpenFin by remember { mutableStateOf(false) }

    val timeDialogStateFin = rememberMaterialDialogState(isDialogOpenFin)

    val tamIcono = 55.dp

    var botonSiguiente by remember {
        mutableStateOf(false)
    }

    BoxWithConstraints {
        maxh = this.maxHeight
    }


    var nombre by remember {
        mutableStateOf("Ingresa el nombre")
    }
    var horaFin by remember {
        mutableStateOf("Horario aproximado ")
    }



    //Variables para validar que los campos esten completos
    var campoHoraF by remember {
        mutableStateOf(false)
    }
    var campoNombre by remember {
        mutableStateOf(false)
    }
    if (selectedHora != "") {
        horaFin = "Hora de encuentro: $selectedHora hrs"
    }

    Scaffold()
    {

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

            cabeceraConBotonAtras(titulo = "Registro de parada", navController = navController)

            Box(
                modifier = Modifier
                    .fillMaxWidth(),

                contentAlignment = Alignment.Center
            )
            {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .background(
                            Color.White
                        ),
                    verticalArrangement = Arrangement.Center

                )
                {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxHeight()
                    ) {

                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Por favor ingresa los datos para que los pasajeros puedan encontrar " +
                                    "las paradas de tu viaje.",
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = Color(86, 86, 86),
                                textAlign = TextAlign.Justify

                            )
                        )
                        Spacer(modifier = Modifier.height(40.dp))

                        Text(
                            text = "Nombre para identificar a esta parada",
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
                                    //showDialogDia = true
                                    // show = true
                                }
                                .background(Color.White)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Icono días",
                                modifier = Modifier
                                    .size(tamIcono)
                                    .padding(10.dp, 5.dp),
                                tint = Color(137, 13, 86)
                            )
                            TextField(
                                value = nombre,
                                onValueChange = { newText -> nombre = newText },

                                modifier = Modifier
                                    .background(Color.White)
                                    .padding(start = 15.dp, end = 10.dp)
                                    .weight(1f),

                                textStyle = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black

                                ),

                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.White,

                                    ),

                                )

                        }

                        if (campoNombre) {
                            Text(
                                text = "*Por favor ingresa el nombre",
                                style = TextStyle(
                                    color = Color(86, 86, 86)
                                )

                            )
                        }

                        Spacer(modifier = Modifier.height(40.dp))



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
                                    //dialogReloj = true
                                    isDialogOpenFin = true

                                }

                        ) {
                            Icon(

                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = "Icono horario",
                                modifier = Modifier
                                    .size(tamIcono)
                                    .padding(10.dp, 5.dp),
                                tint = Color(137, 13, 86)
                            )
                            Text(
                                text = horaFin,
                                textAlign = TextAlign.Left,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp, 15.dp, 10.dp, 10.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black

                                )
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
                            androidx.compose.material.Text(
                                text = "Siguiente", style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
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


    }



    LaunchedEffect(nombre) {
        campoNombre = false
    }


    LaunchedEffect(horaFin) {
        campoHoraF = false
    }




    if (botonSiguiente) {

        //Validar que haya llenado todos los campos


        if (nombre == "Ingresa el nombre"  || nombre == ""  || selectedHora == "" ) {
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


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MyviajeParada() {
    val navController = rememberNavController()

    generalParada(navController = navController, "123", userId = "hannia", "muestra")
}

