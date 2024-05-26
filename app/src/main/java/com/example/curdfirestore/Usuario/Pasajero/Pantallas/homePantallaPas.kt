package com.example.curdfirestore.Usuario.Pasajero.Pantallas

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.SolicitudData

import com.example.avanti.Usuario.Conductor.Pantallas.maxh
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.curdfirestore.R
import com.example.curdfirestore.Horario.ConsultasHorario.conObtenerItinerarioPasRT
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesPorHorario
import com.example.curdfirestore.Usuario.Pasajero.menuPas
import com.example.curdfirestore.Viaje.Funciones.solicitarPermiso


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun homePantallaPasajero(
    navController: NavController,
    userid: String,

) {

    solicitarPermiso()
    // Obtén el Painter desde la ruta específica
    val painter: Painter = painterResource(R.drawable.hecho)
    var solicitud by remember { mutableStateOf<List<SolicitudData>?>(null) }


    val horarios= conObtenerItinerarioPasRT(userId = userid)


    BoxWithConstraints {
        maxh = this.maxHeight - 55.dp
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(50.dp)) {
                menuPas(navController = navController, userID = userid)
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


            cabecera("Inicio de viaje", navController, "ver_notificaciones_pasajero/$userid")

            Column(
                modifier = Modifier
                    .padding(20.dp, 0.dp)

            ) {


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
                    text = "Aquí aparecerán los viajes que estén próximos a comenzar",
                    style = TextStyle(
                        color = Color(71, 12, 107),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start

                    )
                )

            }
          horarios?.let {
                val horarioIniciado =
                    horarios.filter { it.second.horario_iniciado == "si" }.firstOrNull()
                if (horarioIniciado != null) {

                    conObtenerSolicitudesPorHorario(
                        horarioIniciado.first,
                        "Aceptada"
                    ) { resultado ->
                        solicitud = resultado
                    }
                    if (solicitud != null) {
                        val unaSolicitud = solicitud!!.first()
                        //Mandar a la pantalla para ver el viaje

                        navController.navigate("ver_progreso_viaje/$userid/${unaSolicitud.viaje_id}/${unaSolicitud.solicitud_id}/${unaSolicitud.horario_id}/${unaSolicitud.parada_id}")

                    }

                }
            }

        }
    }
}