package com.example.curdfirestore.Viaje.Pantallas.Editar

import android.annotation.SuppressLint
import android.os.Build

import androidx.annotation.RequiresApi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.DateRange

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults


import androidx.compose.material.icons.filled.ArrowForward


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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

import com.example.avanti.Usuario.Conductor.Pantallas.maxh


import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId

import com.example.curdfirestore.Viaje.Funciones.convertirADia
import com.example.curdfirestore.Viaje.Funciones.convertirANumDia
import com.example.curdfirestore.Viaje.Funciones.convertirATrayecto
import com.example.curdfirestore.Viaje.Funciones.convertirStringATime
import com.example.curdfirestore.Viaje.Funciones.convertirTrayecto
import com.example.curdfirestore.Viaje.Funciones.trayectoANum
import com.example.curdfirestore.Viaje.Pantallas.cabeceraEditarAtras
import com.example.curdfirestore.Viaje.Pantallas.dialogSeleccionDia
import com.example.curdfirestore.Viaje.Pantallas.dialogoConfirmarEditarViaje
import com.example.curdfirestore.Viaje.Pantallas.dialogoSeleccionLugares
import com.example.curdfirestore.Viaje.Pantallas.dialogoSeleccionTrayecto
import com.example.curdfirestore.Viaje.Pantallas.dialogoTarifa
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState


/*
--------------------------------
   setOf(1) -> "UPIITA como origen"
        setOf(2) -> "UPIITA como destino"
        ----------------------------
*/
//Pantalla para agregar el formulario con la información general del viaje
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun generalViajeConEditar(
    navController: NavController,
    userId: String,
    viajeId: String
) {

var showEditar by remember {
    mutableStateOf(true)
}

    val tamEspacio = 15.dp
    val tamIcono = 55.dp
    val viaje = conObtenerViajeId(viajeId = viajeId)

    viaje?.let {




        val trayectoI = trayectoANum(viaje.viaje_trayecto)
        val diaI = convertirANumDia(nombreDia = viaje.viaje_dia)
        val horaII = convertirStringATime(viaje.viaje_hora_partida)
        val horaIF = convertirStringATime(viaje.viaje_hora_llegada)
        val uOrigen = viaje.viaje_origen
        val uDestino = viaje.viaje_destino

        var selectedHoraInicio by remember {
            mutableStateOf(viaje.viaje_hora_partida)
        }
        var selectedHoraFin by remember {
            mutableStateOf(viaje.viaje_hora_llegada)
        }

        var pickedTimeInicio by remember {
            mutableStateOf(horaII)
        }
        var pickedTimeFin by remember {
            mutableStateOf(horaIF)
        }


        var isDialogOpenInicio by remember { mutableStateOf(false) }
        var isDialogOpenFin by remember { mutableStateOf(false) }

        val timeDialogStateInicio = rememberMaterialDialogState(isDialogOpenInicio)
        val timeDialogStateFin = rememberMaterialDialogState(isDialogOpenFin)


        var showDialogDia by remember { mutableStateOf(false) }
        var selectedDays by remember { mutableStateOf(setOf(diaI)) }

        var showDialogTrayecto by remember { mutableStateOf(false) }


        var selectedTrayecto by remember { mutableStateOf(setOf(trayectoI)) }

        var showDialogTarifa by remember { mutableStateOf(false) }
        var selectedTarifa by remember {
            mutableStateOf(viaje.viaje_tarifa)
        }

        var showDialogLugares by remember {
            mutableStateOf(false)
        }
        var selectedLugares by remember {
            mutableStateOf(viaje.viaje_num_lugares)
        }


        var botonSiguiente by remember {
            mutableStateOf(false)
        }
        BoxWithConstraints {
            maxh = this.maxHeight
        }
        var diaCon by remember {
            mutableStateOf(viaje.viaje_dia)
        }

        var trayectoCon by remember {
            mutableStateOf("")
        }

        var tarifa by remember {
            mutableStateOf("Tarifa: $${viaje.viaje_tarifa}")
        }

        var lugares by remember {
            mutableStateOf("Lugares: ${viaje.viaje_num_lugares}")
        }
        var dia by remember {
            mutableStateOf("Día del viaje: ${viaje.viaje_dia}")
        }
        val trayectoStr = convertirTrayecto(viaje.viaje_trayecto)
        var trayecto by remember {
            mutableStateOf("Tipo de trayecto: $trayectoStr")
        }
        var horaInicio by remember {
            mutableStateOf("Hora de inicio: ${viaje.viaje_hora_partida} hrs")
        }
        var horaFin by remember {
            mutableStateOf("Hora de termino: ${viaje.viaje_hora_llegada} hrs")
        }

        if (selectedTarifa != "") {
            tarifa = "Tarifa: $$selectedTarifa "
        }
        if (selectedLugares != "") {
            lugares = "Lugares: $selectedLugares "
        }
        if (selectedHoraInicio != "") {
            horaInicio = "Inicio del viaje: $selectedHoraInicio hrs "
        }
        if (selectedHoraFin != "") {
            horaFin = "Fin del viaje: $selectedHoraFin hrs"
        }

        if (selectedTrayecto.isNotEmpty()) {
            trayectoCon = convertirATrayecto(numTrayecto = selectedTrayecto)
            trayecto = "Trayecto: $trayectoCon"
        }
        // Mostrar días seleccionados
        if (selectedDays.isNotEmpty()) {
            diaCon = convertirADia(numDia = selectedDays)
            dia = "Día del viaje: $diaCon"

        }


        var validador by remember {
            mutableStateOf(0)
        }


        LaunchedEffect(validador) {
            botonSiguiente = false
        }

        Scaffold(


        ) {

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
                val ruta = if (viaje.viaje_paradas=="0") {
              "ver_mapa_viaje_sin/$viajeId/$userId"
                } else {
                    "ver_mapa_viaje/$viajeId/$userId"
                }

                cabeceraEditarAtras(
                    "Registrar destino",
                    navController,
                    ruta
                )
             //   cabeceraConBotonAtras(titulo = "Editar viaje", navController = navController)

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),

                    contentAlignment = Alignment.TopCenter
                )
                {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .background(
                                Color.White
                            )
                    )
                    {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxHeight()
                        ) {

                            Spacer(modifier = Modifier.height(tamEspacio))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        1.dp,
                                        Color.LightGray
                                    )
                                    .clickable {
                                        showDialogDia = true
                                        // show = true
                                    }
                            ) {

                                Icon(
                                    imageVector = Icons.Filled.DateRange,
                                    contentDescription = "Icono días",
                                    modifier = Modifier
                                        .size(tamIcono)
                                        .padding(10.dp, 5.dp),
                                    tint = Color(137, 13, 86)
                                )
                                Text(
                                    text = dia,
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


                            Spacer(modifier = Modifier.height(tamEspacio))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        1.dp,
                                        Color.LightGray
                                    )
                                    .clickable {
                                        showDialogTrayecto = true

                                    }

                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = "Icono trayecto",
                                    modifier = Modifier
                                        .size(tamIcono)
                                        .padding(10.dp, 5.dp),
                                    tint = Color(137, 13, 86)
                                )
                                Text(
                                    text = trayecto,
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

                            Spacer(modifier = Modifier.height(tamEspacio))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        1.dp,
                                        Color.LightGray
                                    )
                                    .clickable {
                                        timeDialogStateInicio.show()
                                        //dialogReloj = true
                                        isDialogOpenInicio = true

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
                                    text = horaInicio,
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
                            Spacer(modifier = Modifier.height(tamEspacio))
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

                            Spacer(modifier = Modifier.height(tamEspacio))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        1.dp,
                                        Color.LightGray
                                    )
                                    .clickable {
                                        showDialogLugares = true

                                    }

                            ) {
                                Icon(

                                    painter = painterResource(id = R.drawable.carro),
                                    contentDescription = "Icono lugares",
                                    modifier = Modifier
                                        .size(tamIcono)
                                        .padding(10.dp, 5.dp),
                                    tint = Color(137, 13, 86)
                                )
                                Text(
                                    text = lugares,
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

                            Spacer(modifier = Modifier.height(tamEspacio))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(
                                        1.dp,
                                        Color.LightGray
                                    )
                                    .clickable {
                                        showDialogTarifa = true

                                    },


                                ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.tarifa),
                                    contentDescription = "Icono tarifa",
                                    modifier = Modifier
                                        .size(tamIcono)
                                        .padding(10.dp, 5.dp),
                                    tint = Color(137, 13, 86)
                                )
                                Text(
                                    text = tarifa,

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

                            Spacer(modifier = Modifier.height(50.dp))
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


            // Diálogo para la selección de días
            if (showDialogTrayecto) {
                dialogoSeleccionTrayecto(
                    onDismiss = { showDialogTrayecto = false },
                    onDaysSelected = { selectedTrayecto = it }
                )
            }


            // Diálogo para la selección de días
            if (showDialogDia) {
                dialogSeleccionDia(
                    onDismiss = { showDialogDia = false },
                    onDaysSelected = { selectedDays = it }
                )
            }

            if (showDialogLugares) {
                dialogoSeleccionLugares(
                    onDismiss = { showDialogLugares = false },
                    numLugares = { nuevoLugares ->
                        // Actualizar la variable de estado con el nuevo valor de la tarifa
                        selectedLugares = nuevoLugares
                    }
                )
            }


            // Diálogo para la tarifa
            if (showDialogTarifa) {
                dialogoTarifa(
                    onDismiss = { showDialogTarifa = false },
                    newTarifia = { nuevaTarifa ->
                        // Actualizar la variable de estado con el nuevo valor de la tarifa
                        selectedTarifa = nuevaTarifa
                    }
                )
            }
            if (showEditar) {
                dialogoConfirmarEditarViaje(
                    onDismiss = { showEditar = false },
                    viajeId,
                    userId,
                    viaje.viaje_paradas,
                    navController
                )

            }



            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (timeDialogStateInicio.showing) {
                            Color.Black.copy(alpha = 0.5f)
                        } else {
                            Color.Black.copy(alpha = 0.0f)
                        }
                    ) // Fondo oscuro con transparencia
            ) {
                MaterialDialog(
                    dialogState = timeDialogStateInicio,
                    buttons = {
                        positiveButton(
                            text = "ACEPTAR",
                        )
                        negativeButton(text = "CANCELAR")
                    }
                ) {

                    timepicker(
                        initialTime = pickedTimeInicio,
                        title = "Selecciona el horario de inicio del viaje",

                        ) {
                        pickedTimeInicio = it
                        selectedHoraInicio = pickedTimeInicio.toString()
                    }
                }
            }

            ///

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
                            text = "ACEPTAR",
                        )
                        negativeButton(text = "CANCELAR")
                    }
                ) {

                    timepicker(
                        initialTime = pickedTimeFin,
                        title = "Selecciona el horario de fin del viaje",

                        ) {
                        pickedTimeFin = it
                        selectedHoraFin = pickedTimeFin.toString()
                    }
                }
            }

        }

        if (botonSiguiente) {


            //Definir si elegirá origen o destino
            if (selectedTrayecto.toString() == "[1]") { //UPIITA como origen
               navController.navigate(route = "registrar_destino_conductor_editar/$userId/$diaCon/$selectedHoraInicio/$selectedHoraFin/$selectedLugares/$selectedTarifa/$uDestino/$viajeId")
            }
            if (selectedTrayecto.toString() == "[2]") {// //UPIITA como destino
                navController.navigate(route = "registrar_origen_conductor_editar/$userId/$diaCon/$selectedHoraInicio/$selectedHoraFin/$selectedLugares/$selectedTarifa/$uOrigen/$viajeId")
              }

            botonSiguiente = false

        }
    }
}


/*
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Myviaje() {
    val navController = rememberNavController()

    generalViajeCon(navController = navController, userId = "hannia")
}

*/