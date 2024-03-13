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

import com.example.curdfirestore.Viaje.convertirADia
import com.example.curdfirestore.Viaje.convertirATrayecto
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime


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


    var tamEspacio = 18.dp
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





    var expanded by remember { mutableStateOf(false) }
    BoxWithConstraints {
        maxh = this.maxHeight
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

    if(selectedTarifa!=""){
tarifa="Tarifa: $$selectedTarifa "
    }
    if(selectedLugares!=""){
        lugares="Lugares: $selectedLugares "
    }
if(selectedHoraInicio!=""){
    horaInicio="Inicio del viaje: $selectedHoraInicio hrs "
}
    if(selectedHoraFin!=""){
        horaFin="Fin del viaje: $selectedHoraFin hrs"
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

            cabeceraConBotonAtras(titulo = "Regsitro de viaje", navController = navController)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxh - 80.dp),
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

                        Spacer(modifier = Modifier.height(tamEspacio))

                    }
                }



                Column(
                    modifier = Modifier
                        .padding(40.dp,20.dp)
                        .align(Alignment.BottomCenter)

                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(
                                137,
                                13,
                                88
                            )
                        ),
                        onClick = {

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



        // Diálogo para la selección de días
        if (showDialogTrayecto) {
            dialogoSeleccionTrayecto(
                onDismiss = { showDialogTrayecto = false },
                onDaysSelected = { selectedTrayecto = it }
            )
        }

        if (selectedTrayecto.isNotEmpty()) {
            var trayectoCon= convertirATrayecto(numTrayecto = selectedTrayecto)
            trayecto= "Trayecto: $trayectoCon"


        }
        // Mostrar días seleccionados
        if (selectedDays.isNotEmpty()) {
           var diaCon= convertirADia(numDia = selectedDays)
            dia= "Día del viaje: $diaCon"

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
                onDismiss = {  showDialogTarifa = false },
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
                    selectedHoraInicio=pickedTimeInicio.toString()
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
                    selectedHoraFin=pickedTimeFin.toString()
                }
            }
        }



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


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Myviaje() {
    val navController = rememberNavController()

    generalViajeCon(navController = navController, userId = "hannia")
}

