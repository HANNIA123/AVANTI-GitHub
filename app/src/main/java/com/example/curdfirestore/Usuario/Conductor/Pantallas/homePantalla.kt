package com.example.avanti.Usuario.Conductor.Pantallas

//Pantalla donde el conductor podrá iniciar un viaje

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.curdfirestore.R
import com.example.avanti.ui.theme.Aplicacion.encabezado
import com.example.avanti.ui.theme.Aplicacion.menuCon
import com.example.avanti.ui.theme.Aplicacion.obtenerDiaDeLaSemanaActual
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaHoyCompleto



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun homePantallaConductor(
    navController: NavController,
    userid: String,
    ) {
    // Obtén el Painter desde la ruta específica
    val painter: Painter = painterResource(R.drawable.hecho)
    var viajeVacio = true //variable para prueba
    var diaActual = obtenerDiaDeLaSemanaActual()
    var fechaHoy= obtenerFechaHoyCompleto()

    BoxWithConstraints {
        maxh = this.maxHeight - 55.dp
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
                .height(maxh)
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            encabezado()
            Column(
                modifier = Modifier
                    .padding(20.dp, 0.dp)

            ) {

                Text(
                    text = fechaHoy,
                    style = TextStyle(
                        color = Color(71, 12, 107),
                        fontSize = 28.sp,
                        textAlign = TextAlign.Start

                    )
                )
Spacer(modifier = Modifier.height(30.dp))
                // Muestra la imagen usando el componente Image
                Image(
                    painter = painter,
                    contentDescription = "Viajes vacios",
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Aquí aparecerán los viajes que estén proximos a comenzar",
                    style = TextStyle(
                        color = Color(71, 12, 107),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start

                    )
                )

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

