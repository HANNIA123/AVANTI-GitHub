package com.example.curdfirestore.Horario.Pantallas.Editar

import android.annotation.SuppressLint
import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults

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
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.example.avanti.ui.theme.Aplicacion.cabeceraConBotonAtras
import com.example.curdfirestore.Horario.ConsultasHorario.conObtenerHorarioId

import com.example.curdfirestore.R

import com.example.curdfirestore.Viaje.Funciones.convertirADia
import com.example.curdfirestore.Viaje.Funciones.convertirATrayecto
import com.example.curdfirestore.Viaje.Funciones.convertirStringATime
import com.example.curdfirestore.Viaje.Funciones.convertirTrayecto
import com.example.curdfirestore.Viaje.Funciones.trayectoANum
import com.example.curdfirestore.Viaje.Pantallas.FilaIconoTexto2
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
fun generalViajePasEditarSin(
    navController: NavController,
    userId: String,
    horarioId: String
) {
    val horarioPas = conObtenerHorarioId(horarioId = horarioId)

    var maxh by remember {
        mutableStateOf(0.dp)
    }


    BoxWithConstraints {
        maxh = this.maxHeight
    }

    val altura = maxh - 130.dp
    val tamEspacio = ((altura - 430.dp) / 4) - 20.dp


    horarioPas?.let {

        val trayectoI = trayectoANum(horarioPas.horario_trayecto)
        var selectedHoraInicio by remember {
            mutableStateOf(horarioPas.horario_hora)
        }
        var selectedHoraFin by remember {
            mutableStateOf(horarioPas.horario_hora)
        }
        val horaPas = convertirStringATime(horarioPas.horario_hora)

        var pickedTimeInicio by remember {
            mutableStateOf(horaPas)
        }

        var isDialogOpenInicio by remember { mutableStateOf(false) }
        val timeDialogStateInicio = rememberMaterialDialogState(isDialogOpenInicio)


        var tamIcono = 55.dp

        var showDialogDia by remember { mutableStateOf(false) }
        var selectedDays by remember { mutableStateOf(emptySet<Int>()) }

        var showDialogTrayecto by remember { mutableStateOf(false) }
        var selectedTrayecto by remember { mutableStateOf(setOf(trayectoI)) }

        var botonSiguiente by remember {
            mutableStateOf(false)
        }
        BoxWithConstraints {
            maxh = this.maxHeight
        }
        var diaCon by remember {
            mutableStateOf(horarioPas.horario_dia)
        }
        val trayectoStr = convertirTrayecto(horarioPas.horario_trayecto)

        var trayectoCon by remember {
            mutableStateOf(trayectoStr)
        }

        var dia by remember {
            mutableStateOf("Día del viaje: ${horarioPas.horario_dia}")
        }
        var trayecto by remember {
            mutableStateOf("Trayecto: $trayectoStr")
        }

        var horaInicio by remember {
            mutableStateOf("Llegada: ${horarioPas.horario_hora}")
        }
        var horaFin by remember {
            mutableStateOf("Salida: ${horarioPas.horario_hora}")
        }
        var horallegada by remember {
            mutableStateOf("Llegada: ${horarioPas.horario_hora}")
        }
        var horasalida by remember {
            mutableStateOf("Salida: ${horarioPas.horario_hora}")
        }

        if (selectedHoraInicio != "") {
            //horaInicio = "Inicio del viaje: $selectedHoraInicio hrs "
            if (selectedTrayecto.toString() == "[1]") {
                horasalida = "Salida: $selectedHoraInicio hrs "
            } else if (selectedTrayecto.toString() == "[2]") {
                horallegada = "Llegada: $selectedHoraInicio hrs "
            } else {
                horaInicio = "Salida: $selectedHoraInicio hrs "
            }
        }
        if (selectedHoraFin != "") {
            horaFin = "Salida: $selectedHoraFin hrs"
        }


        if (selectedTrayecto.isNotEmpty()) {
            trayectoCon = convertirATrayecto(numTrayecto = selectedTrayecto)
            trayecto = trayectoCon
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
        val colorTxt = Color(57, 58, 57)

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
cabeceraConBotonAtras(titulo = "Editar viaje", navController = navController)
            /*    cabeceraEditarAtras(
                    titulo = "Editar viaje",
                    navController = navController,
                    ruta = "ver_mapa_viaje_pas_sin/$horarioId/$userId/nada"
                )
*/

                Column(
                    modifier = Modifier
                        .padding(25.dp)
                        .background(Color.White)
                        .fillMaxSize()

                )
                {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxHeight()
                    ) {

                        Spacer(modifier = Modifier.height(15.dp))

                        Text(
                            text = "Los viajes registrados se agregarán a tu itinerario y se repetirán semanalmente.",
                            style = TextStyle(
                                color = colorTxt,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Justify,
                            ),

                            )

                        Spacer(modifier = Modifier.height(15.dp))

                        Box(
                            contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.calendarhor),
                                contentDescription = "Calendario",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                tint = Color(137, 13, 86)
                            )

                        }
                        Spacer(modifier = Modifier.height(18.dp))

                        FilaIconoTexto2(
                            icono = R.drawable.calendar,
                            texto = dia,
                            onClick = { showDialogDia = true },
                            mensaje = "*Por favor ingresa el día"
                        )

                        Spacer(modifier = Modifier.height(tamEspacio))


                        FilaIconoTexto2(
                            icono = R.drawable.trayecto,
                            texto = trayecto,
                            onClick = { showDialogTrayecto = true },
                            mensaje = "*Por favor ingresa el trayecto"
                        )


                        Spacer(modifier = Modifier.height(tamEspacio))
                        val tt = when {
                            selectedTrayecto.toString() == "[1]" -> horasalida
                            selectedTrayecto.toString() == "[2]" -> horallegada
                            else -> horaInicio
                        }




                        FilaIconoTexto2(
                            icono = R.drawable.clock,
                            texto = tt,
                            onClick = {
                                timeDialogStateInicio.show()
                                isDialogOpenInicio = true
                            },
                            mensaje = "Por favor ingresa el horario "
                        )



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

            val textoAux = when {
                selectedTrayecto.toString() == "[1]" -> "¿Cual es tu horario de salida de UPIITA?"
                selectedTrayecto.toString() == "[2]" -> "¿Cúal es tu horario de llegada a la UPIITA?"
                else -> "Este horario depende del trayecto seleccionado"
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
                        initialTime = convertirStringATime(horarioPas.horario_hora),
                        title = textoAux

                    ) {
                        pickedTimeInicio = it
                        selectedHoraInicio = pickedTimeInicio.toString()
                    }
                }
            }
        }
        LaunchedEffect(dia) {
            campoDia = false
        }
        LaunchedEffect(trayecto) {
            campoTrayecto = false
        }

        LaunchedEffect(horaInicio) {
            campoHoraI = false
        }
        LaunchedEffect(horallegada) {
            campoHoraI = false
        }
        LaunchedEffect(horasalida) {
            campoHoraI = false
        }


        if (botonSiguiente) {
            val uDestino= horarioPas.horario_destino
            val uOrige=horarioPas.horario_origen
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
                var tra = selectedTrayecto.toString()
                println("selectedTrayecto $tra")

                if (selectedTrayecto.toString() == "[1]") { //UPIITA como origen
                    navController.navigate(route = "registrar_destino_pasajero_editar/$userId/$diaCon/$selectedHoraInicio/$horarioId/$uDestino")
                }

                if (selectedTrayecto.toString() == "[2]") {// //UPIITA como destino
                    navController.navigate(route = "registrar_origen_pasajero_editar/$userId/$diaCon/$selectedHoraInicio/$horarioId/$uOrige")
                }

                println("Campos completos")
            }

            botonSiguiente = false

        }
    }


}

