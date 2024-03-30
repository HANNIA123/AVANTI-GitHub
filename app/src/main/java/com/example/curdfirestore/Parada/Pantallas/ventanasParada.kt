package com.example.curdfirestore.Parada.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.avanti.ParadaData
import com.example.avanti.ViajeData
import com.example.curdfirestore.Parada.ConsultasParada.actualizarNumParadas
import com.example.curdfirestore.Parada.ConsultasParada.conRegistrarParada
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId


@Composable
fun ventanaAgregarOrigenParada(
    onDismiss: () -> Unit,
    viaje: ViajeData,
    viajeId:String,
    userId:String

){

    var showPar by remember {
        mutableStateOf(true)
    }

    var ejecutado by remember {
        mutableStateOf(false)
    }

    var seleccion by remember {
        mutableStateOf(false)
    }

    var textVentana by remember { mutableStateOf("") }
    var textGuardar by remember { mutableStateOf("") }
    var ubicacionP by remember { mutableStateOf("") }
    var horarioP by remember { mutableStateOf("") }


    if (viaje!!.viaje_trayecto == "0") {
        textVentana = "¿Deseas agregar tu ubicación de destino como una parada?"
        textGuardar = "Destino de un viaje"
        ubicacionP=viaje!!.viaje_destino
        horarioP=viaje!!.viaje_hora_llegada


    } else {
        textVentana = "¿Deseas agregar tu ubicación de origen como una parada?"
        textGuardar = "Origen de un viaje"
        ubicacionP=viaje!!.viaje_origen
        horarioP=viaje!!.viaje_hora_partida
    }




    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        ) {


        Dialog(
            onDismissRequest = {
                onDismiss()

            }, // Cierra el diálogo al tocar fuera de él
            content = {


                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(15.dp)
                ) {

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = textVentana,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        style = TextStyle(
                            color = Color(104, 104, 104),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Justify,
                        )
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Icon(
                            modifier = Modifier
                                .size(70.dp)
                                .padding(5.dp),
                            painter = painterResource(id = R.drawable.ubica),
                            contentDescription = "Icono Viajes",
                            tint = Color(137, 13, 88)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Segundo botón para "No"
                            TextButton(onClick = {
                                onDismiss()
                            }) {
                                Text(text = "NO",
                                    style = TextStyle(
                                        Color(137,67,242),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                )
                            }
                            // Primer botón para "Sí"
                            TextButton(onClick = {
                                seleccion=true
                                //agregar(true)
                                //onDismiss()
                            }) {
                                Text(text = "SI",
                                    style = TextStyle(
                                        Color(137,67,242),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
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
                                    onDismiss()

                                }
                            }




                        }
                    }
                }




            }
            )
    }
}


@Composable
fun myDiaologExitosa(
    navController: NavController,
    email: String,
    idViaje: String,
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (show) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),

            ) {

            Dialog(onDismissRequest = { onDismiss() }) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(15.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Viaje registrado exitosamente",
                        modifier = Modifier
                            .padding(2.dp),
                        style = TextStyle(
                            color = Color(104, 104, 104),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Justify,
                        )
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))

                        Icon(
                            modifier = Modifier
                                .size(80.dp)
                                .padding(5.dp),
                            painter = painterResource(id = R.drawable.cheque),
                            contentDescription = "Icono Viajes",
                            tint = Color(137, 13, 88)
                        )

                        // Primer botón para "Ver viaje"
                        TextButton(onClick = {

                            navController.navigate("ver_mapa_viaje/$idViaje/$email")
                        }) {
                            Text(text = "Ver viaje",
                                style = TextStyle(
                                    Color(137,67,242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        // Segundo botón (agregado) - Puedes personalizar el texto y la acción según tus necesidades
                        TextButton(onClick = {
                            // Acción para el segundo botón
                            var conpantalla = "nomuestra"
                            var regresa= "inicioviaje"
                            navController.navigate("general_parada/$idViaje/$email/$conpantalla/$regresa")
                        }) {


                            Text(text = "Nueva parada",
                                style = TextStyle(
                                    Color(137,67,242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

