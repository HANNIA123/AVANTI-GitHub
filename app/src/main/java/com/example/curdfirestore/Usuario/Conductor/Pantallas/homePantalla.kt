package com.example.avanti.Usuario.Conductor.Pantallas

//Pantalla donde el conductor podrá iniciar un viaje

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.ViajeDataReturn
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaHoyCompleto
import com.example.avanti.ui.theme.Aplicacion.obtenerHoraActualConRestaDeMinutos
import com.example.avanti.ui.theme.Aplicacion.obtenerHoraActualConSumaDeMinutos
import com.example.avanti.ui.theme.Aplicacion.obtenerNombreDiaEnEspanol
import com.example.curdfirestore.Usuario.Conductor.menuCon
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerItinerarioCon
import com.example.curdfirestore.Viaje.Funciones.convertirTrayecto
import com.example.curdfirestore.Viaje.Pantallas.Monitoreo.obtenerCoordenadas
import com.example.curdfirestore.Viaje.Pantallas.mensajeNoViajes

import com.example.curdfirestore.recuadroTitulos
import com.example.curdfirestore.textoHoraViaje
import com.example.curdfirestore.textoInformacionViaje
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun homePantallaConductor(
    navController: NavController,
    userid: String,
) {

    BoxWithConstraints {
        maxh = this.maxHeight - 50.dp
    }

    val diaActual by remember { mutableStateOf(LocalDate.now().dayOfWeek) }


    var viajes by remember { mutableStateOf<List<ViajeDataReturn>?>(null) }
    conObtenerItinerarioCon(userId = userid) { resultado ->
        viajes = resultado
    }

    if(viajes!=null) {
        //verificar que no haya ningun viaje iniciado
        val viajesIniciados= viajes!!.filter {it.viaje_iniciado=="si"}

        if(viajesIniciados.isNotEmpty()) {
            val primerViajeIniciado = viajesIniciados.firstOrNull()

            obtenerCoordenadas(userId = userid, primerViajeIniciado!!.viaje_id, navController)
        }
else {
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
//Contenido
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
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))


                        val horaMinima = obtenerHoraActualConRestaDeMinutos(30)
                        val horaMaxima = obtenerHoraActualConSumaDeMinutos(30)
                        val viajesFiltrados = viajes!!.filter {
                            it.viaje_dia == obtenerNombreDiaEnEspanol(
                                diaActual
                            ) && it.viaje_hora_partida >= horaMinima && it.viaje_hora_partida <= horaMaxima
                        }


                        //Mostrar solo los viajes del dia y los que estan por empezar

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
                                                navController.navigate( "empezar_viaje/$userid/${viaje.viaje_id}")
                                                //Iniaciar Viaje
                                                     /* conEditarCampoViaje(
                                                          navController = navController,
                                                          ruta = "empezar_viaje/$userid/${viaje.viaje_id}",
                                                          viajeId = viaje.viaje_id,
                                                          camposEditar = "viaje_iniciado",
                                                          nuevoValor = "si"

                                                      )*/
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


                    }


                }

            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MyScaffoldContentPreview() {
    val navController = rememberNavController()
    homePantallaConductor(navController = navController, userid = "hannia")
}

