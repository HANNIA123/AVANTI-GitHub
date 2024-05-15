package com.example.curdfirestore.Usuario.Conductor.Pantallas

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.Usuario.Conductor.Pantallas.maxh
import com.example.avanti.ViajeDataReturn
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaHoyCompleto
import com.example.avanti.ui.theme.Aplicacion.obtenerNombreDiaEnEspanol
import com.example.avanti.ui.theme.Aplicacion.restarTreintaMinutosAHoraActual
import com.example.avanti.ui.theme.Aplicacion.sumarTreintaMinutosAHoraActual
import com.example.curdfirestore.Usuario.Conductor.menuCon
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerItinerarioCon
import com.example.curdfirestore.Viaje.Funciones.convertirTrayecto
import com.example.curdfirestore.Viaje.Funciones.solicitarPermiso
import com.example.curdfirestore.Viaje.Pantallas.mensajeNoViajes
import com.example.curdfirestore.recuadroTitulos
import com.example.curdfirestore.textoHoraViaje
import com.example.curdfirestore.textoInformacionViaje
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun homeNoIniciado(
    navController: NavController,
    userid: String
) {

    BoxWithConstraints {
        maxh = this.maxHeight - 50.dp
    }

    val diaActual by remember { mutableStateOf(LocalDate.now().dayOfWeek) }
    var botonNoIniciar by remember {
        mutableStateOf(false)
    }
    var texto by remember {
        mutableStateOf("")
    }
    var viajes by remember { mutableStateOf<List<ViajeDataReturn>?>(null) }
    conObtenerItinerarioCon(userId = userid) { resultado ->
        viajes = resultado
    }







    Box {
        Scaffold(
            bottomBar = {
                BottomAppBar(modifier = Modifier.height(50.dp)) {
                    menuCon(navController = navController, userID = userid)
                }
            }
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
                cabecera("Inicio de viaje")

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    recuadroTitulos(titulo = obtenerFechaHoyCompleto())
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .border(2.dp, Color.White)
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        Column() {
                            Text(
                                text = "Próximos viajes",
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                            )

                            Text(
                                text = "Solamente te mostramos los viajes " +
                                        "programados para los próximos 30 minutos",
                                style = TextStyle(
                                    color = Color(86, 86, 86),
                                    fontSize = 18.sp,
                                    textAlign = TextAlign.Justify,
                                ),

                                )

                            Spacer(modifier = Modifier.height(10.dp))

                            if (viajes != null) {

                                val horaMinima = restarTreintaMinutosAHoraActual()
                                val horaMaxima = sumarTreintaMinutosAHoraActual()


                                val viajesFiltrados = viajes!!.filter {
                                    it.viaje_dia == obtenerNombreDiaEnEspanol(diaActual)

                                }


                                /* val viajesFiltrados = viajes!!.filter {
                                it.viaje_dia == obtenerNombreDiaEnEspanol(diaActual) &&
                                        convertirStringAHora(it.viaje_hora_partida).isAfter(horaMinima)  && convertirStringAHora(it.viaje_hora_partida).isBefore(horaMaxima)

                            }*/

                                if (viajesFiltrados.isNotEmpty()) {
                                    val viajesProximos =
                                        viajesFiltrados.sortedBy { it.viaje_hora_partida }

                                    viajesProximos.forEach { viaje ->
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(10.dp))
                                                .border(2.dp, Color.White)
                                                .background(Color.White)
                                                .fillMaxWidth()
                                                .padding(15.dp)
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(16.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    textoHoraViaje(hora = "${viaje.viaje_hora_partida} hrs")
                                                    Column(
                                                        modifier = Modifier
                                                            .padding(start = 8.dp) // Ajusta el espacio entre los textos en la columna
                                                    ) {
                                                        val trayecto =
                                                            convertirTrayecto(viaje.viaje_trayecto)
                                                        textoInformacionViaje(
                                                            etiqueta = "Trayecto",
                                                            contenido = trayecto
                                                        )
                                                        textoInformacionViaje(
                                                            etiqueta = "Pasajeros confirmados",
                                                            contenido = viaje.viaje_num_pasajeros_con
                                                        )

                                                    }
                                                }
                                                Button(
                                                    colors = ButtonDefaults.buttonColors(
                                                        backgroundColor = Color(
                                                            137,
                                                            13,
                                                            88
                                                        )
                                                    ),
                                                    onClick = {

                                                        if (viaje.viaje_num_pasajeros_con == "0" || viaje.viaje_num_pasajeros_con == "" || viaje.viaje_num_pasajeros == "0") {
                                                            texto =
                                                                "No puedes inciar este viaje porque no tienen ningún pasajero confirmado"
                                                            botonNoIniciar = true
                                                        } else {

                                                            navController.navigate("empezar_viaje/$userid/${viaje.viaje_id}")
                                                        }

                                                    },
                                                    modifier = Modifier.width(180.dp)
                                                ) {
                                                    Text(
                                                        text = "Ver viaje", style = TextStyle(
                                                            fontSize = 18.sp,
                                                            color = Color.White
                                                        )
                                                    )
                                                }
                                            }

                                        }
                                        Spacer(modifier = Modifier.height(15.dp))


                                    }


                                } else {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(10.dp))
                                            .border(2.dp, Color.White)
                                            .background(Color.White)
                                            .fillMaxWidth()
                                            .padding(15.dp)
                                    ) {
                                        mensajeNoViajes()
                                    }
                                }


                            } else {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(2.dp, Color.White)
                                        .background(Color.White)
                                        .fillMaxWidth()
                                        .padding(15.dp)
                                ) {
                                    mensajeNoViajes()
                                }
                            }

                        }

                    }

                }

            }

        }
        if (botonNoIniciar) {
            dialogoNoIniciarViaje(
                onDismiss = { botonNoIniciar = false },
                texto
            )
        }
    }


}


