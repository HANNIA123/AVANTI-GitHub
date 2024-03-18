package com.example.avanti.Usuario.Conductor.Pantallas

//Pantalla donde el conductor podrá iniciar un viaje

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.curdfirestore.R
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaHoyCompleto
import com.example.curdfirestore.Usuario.Conductor.menuCon

import com.example.curdfirestore.recuadroTitulos
import com.example.curdfirestore.textoHoraViaje
import com.example.curdfirestore.textoInformacionViaje


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
                            textoHoraViaje(hora = "14:30 hrs")
                            Column(
                                modifier = Modifier
                                    .padding(start = 8.dp) // Ajusta el espacio entre los textos en la columna
                            ) {
                                textoInformacionViaje(
                                    etiqueta = "Trayecto",
                                    contenido = "UPIITA como origen"
                                )
                                textoInformacionViaje(etiqueta = "Pasajeros", contenido = "3")

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
                                //Accion del boton
                            },
                            modifier = Modifier.width(180.dp)
                        ) {
                            Text(
                                text = "Iniciar viaje", style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            )
                        }
                    }

                }

            }


        }

    }
}

@Preview(showBackground = true)
@Composable
fun MyScaffoldContentPreview() {
    val navController = rememberNavController()
    homePantallaConductor(navController = navController, userid = "hannia")
}

