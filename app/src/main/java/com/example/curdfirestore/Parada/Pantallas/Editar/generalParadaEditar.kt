package com.example.curdfirestore.Parada.Pantallas.Editar

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import androidx.navigation.compose.composable
import com.example.avanti.ui.theme.Aplicacion.convertirStringAHora
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerParadaId
import com.example.curdfirestore.Parada.Pantallas.dialogoInfViajeParada
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.example.curdfirestore.Viaje.Funciones.convertirStringATime
import com.example.curdfirestore.Viaje.Pantallas.FilaIconoTexto3
import com.example.curdfirestore.Viaje.Pantallas.cabeceraEditarAtras
import com.example.curdfirestore.Viaje.Pantallas.dialogoConfirmarEditarViaje
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun generalParadaEditar(
    navController: NavController,
    viajeId: String,
    userId: String,
    paradaId: String

) {

    var showEditar by remember {
        mutableStateOf(true)
    }

    var maxh by remember {
        mutableStateOf(0.dp)
    }
    val tamIcono = 55.dp
    BoxWithConstraints {
        maxh = this.maxHeight
    }
    val parada = conObtenerParadaId(paradaId = paradaId)
    val viaje = conObtenerViajeId(viajeId = viajeId)
    var infoViaje by remember {
        mutableStateOf(false)
    }
    var campoValidaHora by remember {
        mutableStateOf(false)
    }
    var campoHoraF by remember {
        mutableStateOf(false)
    }

    parada?.let {
        viaje?.let {

            var selectedHora by remember {
                mutableStateOf(parada.par_hora)
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
                mutableStateOf(parada.par_nombre)
            }
            var horaFin by remember {
                mutableStateOf(parada.par_hora)
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

                        val ruta = if (viaje.viaje_paradas == "0") {
                            "ver_mapa_viaje_sin/$viajeId/$userId"
                        } else {
                            "ver_mapa_viaje/$viajeId/$userId"
                        }

                        cabeceraEditarAtras(
                            "Editar parada",
                            navController,
                            ruta
                        )

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
                                                        .padding(10.dp),
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

                                    Spacer(modifier = Modifier.height(70.dp))
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
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
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
                    if (infoViaje) {

                        dialogoInfViajeParada(
                            onDismiss = { infoViaje = false },
                            viaje = viaje
                        )

                    }

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
                            initialTime = convertirStringATime(parada.par_hora),
                            title = "Selecciona el horario de llegada a la parada",

                            ) {
                            pickedTimeFin = it
                            selectedHora = pickedTimeFin.toString()
                        }
                    }
                }

                if (botonSiguiente) {
                    //Validar que haya llenado todos los campos

                    if (nombre == "") {
                        campoNombre = true
                    } else {


                        val horaIni = convertirStringAHora(viaje.viaje_hora_llegada)
                        val horaFin = convertirStringAHora(viaje.viaje_hora_partida)
                        val horaPar = convertirStringAHora(selectedHora)

                        if (
                            (horaFin.isBefore(horaPar) && horaIni.isAfter(horaPar)) || horaPar == horaIni || horaPar == horaFin) {
                            val parU = parada.par_ubicacion
                            navController.navigate("registrar_parada_barra_editar/$userId/$viajeId/$nombre/$selectedHora/$parU/$paradaId")


                        } else {
                            campoValidaHora = true

                        }


                    }
                    botonSiguiente = false
                }
            }

        }

    }

}


/*
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MyviajeParada() {
    val navController = rememberNavController()

    generalParada(navController = navController, "123", userId = "hannia", "muestra", "viaje")
}

*/