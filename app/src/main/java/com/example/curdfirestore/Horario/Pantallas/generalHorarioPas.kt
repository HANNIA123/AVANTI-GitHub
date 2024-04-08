package com.example.curdfirestore.Horario.Pantallas

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
import androidx.compose.foundation.layout.width


import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults


import androidx.compose.material.icons.filled.ArrowForward


import androidx.compose.material3.Icon


import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController

import com.example.avanti.Usuario.Conductor.Pantallas.maxh
import com.example.avanti.ui.theme.Aplicacion.cabeceraConBotonAtras

import com.example.curdfirestore.R

import com.example.curdfirestore.Viaje.Funciones.convertirADia
import com.example.curdfirestore.Viaje.Funciones.convertirATrayecto
import com.example.curdfirestore.Viaje.Pantallas.cabeceraEditarAtras
import com.example.curdfirestore.Viaje.Pantallas.dialogSeleccionDia
import com.example.curdfirestore.Viaje.Pantallas.dialogoSeleccionTrayecto
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime


/*
-------------------------------------
   setOf(1) -> "UPIITA como origen"
   setOf(2) -> "UPIITA como destino"
 ------------------------------------
*/
//Pantalla para agregar el formulario con la información general del horario
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun generalViajePas(
    navController: NavController,
    userId: String
) {



    var selectedHoraInicio by remember {
        mutableStateOf("")
    }
    var selectedHoraFin by remember {
        mutableStateOf("")
    }

    var pickedTimeInicio by remember {
        mutableStateOf(LocalTime.now())
    }

    var isDialogOpenInicio by remember { mutableStateOf(false) }
    val timeDialogStateInicio = rememberMaterialDialogState(isDialogOpenInicio)

    var tamEspacio = 15.dp
    var tamIcono = 55.dp

    var showDialogDia by remember { mutableStateOf(false) }
    var selectedDays by remember { mutableStateOf(emptySet<Int>()) }

    var showDialogTrayecto by remember { mutableStateOf(false) }
    var selectedTrayecto by remember { mutableStateOf(emptySet<Int>()) }

    var botonSiguiente by remember {
        mutableStateOf(false)
    }
    BoxWithConstraints {
        maxh = this.maxHeight
    }
    var diaCon by remember {
        mutableStateOf("")
    }

    var trayectoCon by remember {
        mutableStateOf("")
    }

    var dia by remember {
        mutableStateOf("Día del viaje")
    }
    var trayecto by remember {
        mutableStateOf("Tipo de trayecto")
    }
    var horaInicio by remember {
        mutableStateOf("Hora de inicio del viaje")
    }
    var horaFin by remember {
        mutableStateOf("Hora de termino del viaje")
    }
    var horallegada by remember {
        mutableStateOf("Hora de llegada a la UPIITA")
    }
    var horasalida by remember {
        mutableStateOf("Hora de salida de la UPIITA")
    }

    if (selectedHoraInicio != "") {
        //horaInicio = "Inicio del viaje: $selectedHoraInicio hrs "
        if(selectedTrayecto.toString() == "[1]"){
            horasalida="Salida de la UPIITA: $selectedHoraInicio hrs "
        }else if (selectedTrayecto.toString() == "[2]"){
            horallegada="Llegada a la UPIITA: $selectedHoraInicio hrs "
        }else{
            horaInicio = "Inicio del viaje: $selectedHoraInicio hrs "
        }
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

    //Variables para validar que los campos esten completos
    var campoDia by remember {
        mutableStateOf(false)
    }

    var campoTrayecto by remember {
        mutableStateOf(false)
    }

    var campoHoraI by remember {
        mutableStateOf(false)
    }


    var validador by remember {
        mutableStateOf(0)
    }


    LaunchedEffect(validador) {
        botonSiguiente=false
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

cabeceraEditarAtras(titulo = "Registrar horario", navController = navController, ruta = "horario_inicio/$userId")

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

                        if(campoDia) {
                            Text(
                                text = "*Por favor ingresa el día",
                                style = TextStyle(
                                    color = Color(86, 86, 86)
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
                        if(campoTrayecto) {
                            Text(
                                text = "*Por favor ingresa el trayecto",
                                style = TextStyle(
                                    color = Color(86, 86, 86)
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
                                text = when {
                                    selectedTrayecto.toString() == "[1]" -> horasalida
                                    selectedTrayecto.toString() == "[2]" -> horallegada
                                    else -> horaInicio
                                },
                                textAlign = TextAlign.Left,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp, 15.dp, 10.dp, 10.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black

                                )
                            )
                            println("horaInicio $horaInicio" )
                            println("horasalida $horasalida" )
                            println("horallegada $horallegada" )
                        }
                        if(campoHoraI) {
                            Text(
                                text = "*Por favor ingresa el horario de inicio",
                                style = TextStyle(
                                    color = Color(86, 86, 86)
                                )

                            )
                        }

                       // Spacer(modifier = Modifier.height(tamEspacio))

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

                                println("botonSiguiente $botonSiguiente")
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
                    initialTime = LocalTime.NOON,
                    title = "Selecciona el horario de inicio del viaje",

                    ) {
                    pickedTimeInicio = it
                    selectedHoraInicio = pickedTimeInicio.toString()
                }
            }
        }
    }


    LaunchedEffect(dia){
        campoDia=false
    }
    LaunchedEffect(trayecto){
        campoTrayecto=false
    }

    LaunchedEffect(horaInicio){
        campoHoraI=false
    }
    LaunchedEffect(horallegada){
        campoHoraI=false
    }
    LaunchedEffect(horasalida){
        campoHoraI=false
    }


    if (botonSiguiente) {
        //Validar que haya llenado todos los campos
        if (diaCon == "" || trayectoCon == "" || selectedHoraInicio == "") {
            if (diaCon == "") {
                campoDia = true
            }
            if (trayectoCon == "") {
                campoTrayecto = true
            }
            if (selectedHoraInicio == "") {
                campoHoraI = true
            }
        } else {
            var tra=selectedTrayecto.toString()
            println("selectedTrayecto $tra")

            if(selectedTrayecto.toString()=="[1]"){ //UPIITA como origen
                navController.navigate(route = "registrar_destino_pasajero/$userId/$diaCon/$selectedHoraInicio")
            }

            if(selectedTrayecto.toString()=="[2]"){// //UPIITA como destino
                navController.navigate(route = "registrar_origen_pasajero/$userId/$diaCon/$selectedHoraInicio")
            }

            println("Campos completos")
        }

        botonSiguiente=false

    }

}


@Composable
fun DayButton(
    day: String,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    val selectedState = rememberUpdatedState(isSelected)

    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selectedState.value) {
                Color(116, 116, 116) // Cambiar a tu color cuando está seleccionado
            } else {
                Color(238, 236, 239) // Cambiar a tu color cuando no está seleccionado
            }
        ),
        modifier = Modifier
            .padding(4.dp)
            .height(50.dp)
            .width(220.dp),
        onClick = { onToggle() },
    ) {
        Text(
            text = day,
            style = TextStyle(
                color = if (selectedState.value) Color.White else Color(137, 13, 88),
                fontSize = 20.sp
            )
        )
    }


}