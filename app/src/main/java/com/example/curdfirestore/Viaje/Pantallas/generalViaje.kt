package com.example.curdfirestore.Viaje.Pantallas

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


import androidx.compose.material3.ExperimentalMaterial3Api

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

import com.example.avanti.Usuario.Conductor.Pantallas.maxh
import com.example.avanti.ui.theme.Aplicacion.cabeceraConBotonAtras

import com.example.curdfirestore.R

import com.example.curdfirestore.Viaje.Funciones.convertirADia
import com.example.curdfirestore.Viaje.Funciones.convertirATrayecto
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime


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
fun generalViajeCon(
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
    var pickedTimeFin by remember {
        mutableStateOf(LocalTime.now())
    }
    var isDialogOpenInicio by remember { mutableStateOf(false) }
    var isDialogOpenFin by remember { mutableStateOf(false) }

    val timeDialogStateInicio = rememberMaterialDialogState(isDialogOpenInicio)
    val timeDialogStateFin = rememberMaterialDialogState(isDialogOpenFin)


    var tamEspacio = 15.dp
    var tamIcono = 55.dp

    var showDialogDia by remember { mutableStateOf(false) }
    var selectedDays by remember { mutableStateOf(emptySet<Int>()) }

    var showDialogTrayecto by remember { mutableStateOf(false) }
    var selectedTrayecto by remember { mutableStateOf(emptySet<Int>()) }


    var showDialogTarifa by remember { mutableStateOf(false) }
    var selectedTarifa by remember {
        mutableStateOf("")
    }

    var showDialogLugares by remember {
        mutableStateOf(false)
    }
    var selectedLugares by remember {
        mutableStateOf("")
    }


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

    var tarifa by remember {
        mutableStateOf("Tarifa del viaje")
    }

    var lugares by remember {
        mutableStateOf("Lugares disponibles")
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

    var campoHoraF by remember {
        mutableStateOf(false)
    }

    var campoLugares by remember {
        mutableStateOf(false)
    }

    var campoTarifa by remember {
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

            cabeceraConBotonAtras(titulo = "Registro de viaje", navController = navController)

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
                        if(campoHoraI) {
                            Text(
                                text = "*Por favor ingresa el horario de inicio",
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

                        if(campoHoraF) {
                            Text(
                                text = "*Por favor ingresa el horario de finalización",
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

                        if(campoLugares) {
                            Text(
                                text = "*Por favor ingresa los lugares",
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
                        if(campoTarifa) {
                            Text(
                                text = "*Por favor ingresa la tarifa, puede ser 0",
                                style = TextStyle(
                                    color = Color(86, 86, 86)
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
                        text = "Aceptar",
                    )
                    negativeButton(text = "Cancelar")
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
                        text = "Aceptar",
                    )
                    negativeButton(text = "Cancelar")
                }
            ) {

                timepicker(
                    initialTime = LocalTime.NOON,
                    title = "Selecciona el horario de fin del viaje",

                    ) {
                    pickedTimeFin = it
                    selectedHoraFin = pickedTimeFin.toString()
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

    LaunchedEffect(horaFin){
        campoHoraF=false
    }
    LaunchedEffect(lugares){
        campoLugares=false
    }
    LaunchedEffect(tarifa){
        campoTarifa=false
    }




    if (botonSiguiente) {

        //Validar que haya llenado todos los campos


        if (diaCon == "" || trayectoCon == "" || selectedHoraInicio == "" || selectedHoraFin == "" || selectedLugares == "" || selectedTarifa == "") {
            if (diaCon == "") {
                campoDia = true
            }
            if (trayectoCon == "") {
                campoTrayecto = true
            }
            if (selectedHoraInicio == "") {
                campoHoraI = true
            }
            if (selectedHoraFin == "") {
                campoHoraF = true
            }
            if (selectedLugares == "") {
                campoLugares = true
            }
            if (selectedTarifa == "") {
                campoTarifa = true
            }
        } else {
            var tra=selectedTrayecto.toString()
            println("selectedTrayecto $tra")
            //Definir si elegirá origen o destino
            if(selectedTrayecto.toString()=="[1]"){ //UPIITA como origen
//Pantalla de seleccionar destino
             //   composable("registrar_origen_conductor/{userid}/{dia}/{horao}/{horad}/{lugares}/{tarifa}")

                navController.navigate(route = "registrar_destino_conductor/$userId/$diaCon/$selectedHoraInicio/$selectedHoraFin/$selectedLugares/$selectedTarifa")
            }
            if(selectedTrayecto.toString()=="[2]"){// //UPIITA como destino
                navController.navigate(route = "registrar_origen_conductor/$userId/$diaCon/$selectedHoraInicio/$selectedHoraFin/$selectedLugares/$selectedTarifa")


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

/*
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Myviaje() {
    val navController = rememberNavController()

    generalViajeCon(navController = navController, userId = "hannia")
}

*/