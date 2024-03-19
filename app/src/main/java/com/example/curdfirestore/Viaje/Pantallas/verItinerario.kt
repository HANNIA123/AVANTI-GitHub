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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
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
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaHoyCompleto
import com.example.curdfirestore.Usuario.Conductor.menuCon
import com.example.curdfirestore.recuadroTitulos
import com.example.curdfirestore.textoHoraItinerario
import com.example.curdfirestore.textoHoraViaje
import com.example.curdfirestore.textoInformacionViaje
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun verItinerarioCon(
    navController: NavController,
    userId:String
){

var mhv by remember {
    mutableStateOf(0.dp)
}
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    BoxWithConstraints {
 mhv = this.maxHeight - 50.dp
    }
    Scaffold(
    bottomBar = {
        BottomAppBar(modifier = Modifier.height(45.dp)) {
            menuCon(navController = navController, userID =userId )
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
                //recuadroTitulos(titulo = obtenerFechaHoyCompleto())
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(2.dp, Color.White)
                        .background(Color.White)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center


                ) {
                    WeeklyCalendar(selectedDate = selectedDate) { date ->
                        selectedDate = date
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
                    Column() {

                        Text(
                            text = obtenerFechaHoyCompleto(),
                            style = TextStyle(
                                color = Color(72,12,107),
                                fontSize = 24.sp,
                                textAlign = TextAlign.Start,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier
                        )

                        Text(
                            text = "Te mostramos los viajes registrados para este día",
                            style = TextStyle(
                                color = Color(86, 86, 86),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Justify,
                            ),

                            )
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //Esta es la fila para cada viaje
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                ,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            textoHoraItinerario(hora = "14:30 hrs")

                            Spacer(modifier = Modifier.width(20.dp)) // Agrega un espacio entre el texto y la columna

                            Column(
                                modifier = Modifier
                                    .weight(1f) // Utiliza el peso para que la columna ocupe el espacio restante
                                    .padding(start = 0.dp)
                            ) {
                                textoInformacionViaje(
                                    etiqueta = "Trayecto",
                                    contenido = "UPIITA como origen"
                                )
                                textoInformacionViaje(etiqueta = "Pasajeros", contenido = "3")
                                textoInformacionViaje(etiqueta = "Status", contenido = "Disponible")

                            }
                            Spacer(modifier = Modifier.width(10.dp)) // Agrega un espacio entre el texto y la columna

                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = "Ver viaje",
                                    modifier = Modifier.size(50.dp),
                                    tint = Color(137, 13, 86)
                                )
                            }
                        }
                        //fin columana por viaje

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

