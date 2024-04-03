package com.example.curdfirestore.Horario.Pantallas

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.avanti.ui.theme.Aplicacion.obtenerNombreDiaEnEspanol
import com.example.curdfirestore.Horario.ConsultasHorario.conObtenerItinerarioPas
import com.example.curdfirestore.R
import com.example.curdfirestore.Usuario.Conductor.menuCon
import com.example.curdfirestore.Usuario.Pasajero.menuPas
import com.example.curdfirestore.Viaje.Funciones.convertirTrayecto
import com.example.curdfirestore.textoHoraItinerario
import com.example.curdfirestore.textoInformacionViaje
import java.time.DayOfWeek
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun verItinerarioPas(
    navController: NavController,
    userId: String
) {
    var diaActual by remember { mutableStateOf(LocalDate.now().dayOfWeek) }
    var horarios = conObtenerItinerarioPas(userId = userId)
    var mhv by remember {
        mutableStateOf(0.dp)
    }

    BoxWithConstraints {
        mhv = this.maxHeight - 50.dp
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(50.dp)) {
                menuPas(navController = navController, userID = userId)
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
                Spacer(modifier = Modifier.height(15.dp))

                Spacer(modifier = Modifier.height(10.dp))
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
                                tint= Color(72, 12, 107)
                            )
                        }

                        Text(
                            text = obtenerNombreDiaEnEspanol(diaActual),
                            style = TextStyle(
                                color =  Color(72, 12, 107),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = { diaActual=diaActual.plus(1)
                                if (diaActual > DayOfWeek.SATURDAY) {
                                    diaActual = DayOfWeek.SUNDAY
                                }
                            },
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowRight,
                                contentDescription = "Siguiente",
                                tint= Color(72, 12, 107)
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
                        text = "Te mostramos los horarios registrados para este día.",
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

                        horarios?.let {
                            val pantalla = "itinerario"
                            // Filtrar la lista por el nombre buscado
                            val horariosPorDia =
                                horarios.filter { it.horario_dia == obtenerNombreDiaEnEspanol(diaActual) }


                            // Realizar acciones según la condición
                            if (horariosPorDia.isEmpty()) {
                                Image(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    painter = painterResource(id = R.drawable.buscar),
                                    contentDescription = "Imagen no viaje",
                                    contentScale = ContentScale.FillBounds
                                )

                                Text(
                                    text = "No hay horarios registrados para hoy.",
                                    style = TextStyle(
                                        color = Color(86, 86, 86),
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Justify,
                                    )
                                )


                            }
                            else{

                                val horariosOrdenados = horariosPorDia.sortedBy { it.horario_hora}

                                horariosOrdenados.forEach {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {


                                        textoHoraItinerario(hora = "${it.horario_hora} hrs")

                                        Spacer(modifier = Modifier.width(20.dp)) // Agrega un espacio entre el texto y la columna

                                        Column(
                                            modifier = Modifier
                                                .weight(1f) // Utiliza el peso para que la columna ocupe el espacio restante
                                                .padding(start = 0.dp)
                                        ) {
                                            var trayectoTexto = convertirTrayecto(it.horario_trayecto)

                                            textoInformacionViaje(
                                                etiqueta = "Trayecto",
                                                contenido = trayectoTexto
                                            )

                                            textoInformacionViaje(
                                                etiqueta = "Status",
                                                contenido = it.horario_status
                                            )

                                        }
                                        Spacer(modifier = Modifier.width(10.dp)) // Agrega un espacio entre el texto y la columna

                                        IconButton(onClick = {
                                            //navController.navigate("ver_mapa_viaje_pas/${it.horario_id}/$userId/$pantalla")

                                            if (it.horario_solicitud == "No") {
                                                navController.navigate("ver_mapa_viaje_pas_sin/${it.horario_id}/$userId/$pantalla")
                                            } else {
                                                navController.navigate("ver_mapa_viaje_pas/${it.horario_id}/$userId/$pantalla")
                                            }

                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.KeyboardArrowRight,
                                                contentDescription = "Ver horario",
                                                modifier = Modifier.size(50.dp),
                                                tint = Color(137, 13, 86)
                                            )
                                        }


                                    }
                                    Spacer(modifier = Modifier.height(15.dp)) // Agrega un espacio entre el texto y la columna


                                }
                            }

                            //fin columana por viaje

                        }

                        //Esta es la fila para cada viaje

                    }
                }

            }

        }
    }

}