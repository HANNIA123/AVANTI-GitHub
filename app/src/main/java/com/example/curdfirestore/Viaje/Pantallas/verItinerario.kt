package com.example.curdfirestore.Viaje.Pantallas

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.ViajeDataReturn
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.avanti.ui.theme.Aplicacion.lineaGris
import com.example.avanti.ui.theme.Aplicacion.lineaGrisCompleta
import com.example.avanti.ui.theme.Aplicacion.obtenerNombreDiaEnEspanol
import com.example.curdfirestore.Usuario.Conductor.menuCon
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerItinerarioCon
import com.example.curdfirestore.Viaje.Funciones.convertirTrayecto
import com.example.curdfirestore.textoHoraItinerario
import com.example.curdfirestore.textoInformacionViaje
import java.time.DayOfWeek
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun verItinerarioCon(
    navController: NavController,
    userId: String
) {
    var mhv by remember {
        mutableStateOf(0.dp)
    }
    var diaActual by remember { mutableStateOf(LocalDate.now().dayOfWeek) }

    var viajes by remember { mutableStateOf<List<ViajeDataReturn>?>(null) }
    conObtenerItinerarioCon(userId = userId) { resultado ->
        viajes = resultado
    }


    BoxWithConstraints {
        mhv = this.maxHeight - 50.dp
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(50.dp)) {
                menuCon(navController = navController, userID = userId)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(239, 239, 239))
                .height(mhv)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            cabecera(titulo = "Itinerario")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {
                Spacer(modifier = Modifier.height(25.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(2.dp, Color.White)
                        .background(Color.White)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center

                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(30.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {
                                diaActual = diaActual.minus(1)
                                if (diaActual < DayOfWeek.MONDAY) {
                                    diaActual = DayOfWeek.SUNDAY
                                }
                            },
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "Anterior",
                                tint = Color(72, 12, 107)
                            )
                        }

                        Text(
                            text = obtenerNombreDiaEnEspanol(diaActual),
                            style = TextStyle(
                                color = Color(72, 12, 107),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = {
                                diaActual = diaActual.plus(1)
                                if (diaActual > DayOfWeek.SATURDAY) {
                                    diaActual = DayOfWeek.SUNDAY
                                }
                            },
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowRight,
                                contentDescription = "Siguiente",
                                tint = Color(72, 12, 107)
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(2.dp, Color.White)
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {

                    Text(
                        text = "Te mostramos los viajes registrados para este día",
                        style = TextStyle(
                            color = Color(86, 86, 86),
                            fontSize = 18.sp,
                            textAlign = TextAlign.Justify,
                        ),

                        )

                }
                Spacer(modifier = Modifier.height(10.dp))

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

                        if (viajes != null) {
                            val viajesPorDia =
                                viajes!!.filter {
                                    it.viaje_dia == obtenerNombreDiaEnEspanol(
                                        diaActual
                                    )
                                }

                            if (viajesPorDia.isEmpty()) {
                                mensajeNoViajes()

                            } else {
                                val viajesOrdenados =
                                    viajesPorDia.sortedBy { it.viaje_hora_partida }

                                viajesOrdenados.forEach {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth().padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {

                                        textoHoraItinerario(hora = "${it.viaje_hora_partida} hrs")

                                        Spacer(modifier = Modifier.width(20.dp)) // Agrega un espacio entre el texto y la columna

                                        Column(
                                            modifier = Modifier
                                                .weight(1f) // Utiliza el peso para que la columna ocupe el espacio restante
                                                .padding(start = 0.dp)
                                        ) {
                                            val trayectoTexto = convertirTrayecto(it.viaje_trayecto)

                                            textoInformacionViaje(
                                                etiqueta = "Trayecto",
                                                contenido = trayectoTexto
                                            )

                                            textoInformacionViaje(
                                                etiqueta = "Pasajeros",
                                                contenido = it.viaje_num_pasajeros
                                            )
                                            textoInformacionViaje(
                                                etiqueta = "Status",
                                                contenido = it.viaje_status
                                            )

                                        }
                                        Spacer(modifier = Modifier.width(10.dp)) // Agrega un espacio entre el texto y la columna

                                        IconButton(onClick = {
                                            // navController.navigate("ver_mapa_viaje/${it.viaje_id}/$userId/$pantalla")
                                            if (it.viaje_paradas != "0") {
                                                navController.navigate("ver_mapa_viaje/${it.viaje_id}/$userId")
                                            } else {
                                                navController.navigate("ver_mapa_viaje_sin/${it.viaje_id}/$userId")
                                            }

                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.KeyboardArrowRight,
                                                contentDescription = "Ver viaje",
                                                modifier = Modifier.size(50.dp),
                                                tint = Color(137, 13, 86)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(5.dp)) // Agrega un espacio entre el texto y la columna

                                    lineaGrisCompleta()
                                    Spacer(modifier = Modifier.height(5.dp)) // Agrega un espacio entre el texto y la columna

                                }
                            }

                            //fin columana por viaje

                        } else {
                            mensajeNoViajes()
                        }

                        //Esta es la fila para cada viaje

                    }
                }

            }

        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ItinerarioView() {
    val navController = rememberNavController()
    verItinerarioCon(navController = navController, userId = "hannia")
}

