package com.example.curdfirestore.Solicitud.Pantallas

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.SolicitudData
import com.example.avanti.UserData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.avanti.ui.theme.Aplicacion.lineaGris
import com.example.avanti.ui.theme.Aplicacion.obtenerNombreDiaEnEspanol
import com.example.avanti.verPasajerosData
import com.example.curdfirestore.Horario.Pantallas.dialogoConfirmarEliminarHorarioSE
import com.example.curdfirestore.R
import com.example.curdfirestore.Reportes.Pantallas.dialogoBorrarPasajero
import com.example.curdfirestore.Reportes.Pantallas.dialogoContactoPasajero
import com.example.curdfirestore.Reportes.Pantallas.dialogoReportarPasajero
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesConductor
import com.example.curdfirestore.Usuario.Conductor.menuCon
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.example.curdfirestore.Viaje.Funciones.convertirADia
import com.example.curdfirestore.textoHora
import java.time.DayOfWeek
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun verPasajeros(
    navController: NavController,
    userid: String,
) {

    var verPasajero by remember { mutableStateOf<verPasajerosData?>(null) }
    var diaActual by remember { mutableStateOf(LocalDate.now().dayOfWeek) }
    var solicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }
    var dialogoInf by remember { mutableStateOf(false) }
    var dialogoContact by remember { mutableStateOf(false) } //Status de la solictud terminado
    var dialogoBorrar by rememberSaveable { mutableStateOf(false) }
    var maxh = 0.dp


    conObtenerSolicitudesConductor(userId = userid) { resultado ->
        solicitudes = resultado
    }

    println("SOLICITUDES $solicitudes")


    BoxWithConstraints {
        maxh = this.maxHeight - 50.dp
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
            cabecera("Pasajeros")

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
            //Contenido
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
            ) {

                Spacer(modifier = Modifier.height(15.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(2.dp, Color.White)
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(15.dp)
                ) {
                    /*Columna para ordenar por día*/
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column {
                            //Columna con informacion de los pasajeros/conductores

                            if (solicitudes != null) {
                                val sol_aceptadas =
                                    solicitudes!!.filter { it.solicitud_status == "Aceptada" }
                                if (sol_aceptadas.isNotEmpty()) {
                                    sol_aceptadas.forEach {
                                        var id_solicitud = it.solicitud_id
                                        val usuario = conObtenerUsuarioId(correo = it.pasajero_id)
                                        val pasajero_id = it.pasajero_id
                                        val viaje = conObtenerViajeId(viajeId = it.viaje_id)
                                        val id_viaje = it.viaje_id
                                        if (usuario != null && viaje != null) {
                                            verPasajero = verPasajerosData(
                                                solicitud_id = id_solicitud,
                                                usuario_id = pasajero_id,
                                                viaje_id = id_viaje,
                                                nombre_completo = "${usuario.usu_nombre} ${usuario.usu_primer_apellido} ${usuario.usu_segundo_apellido}",
                                                URL_imagen = usuario.usu_foto,
                                                dia_viaje = viaje.viaje_dia,
                                                hora_viaje = viaje.viaje_hora_partida
                                            )
                                            if (verPasajero?.dia_viaje == obtenerNombreDiaEnEspanol(
                                                    diaActual
                                                )
                                            ) {
                                                println("VERPASAJERODATA $verPasajero")

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    // Contenido de la columna
                                                    Column(
                                                        modifier = Modifier
                                                            .weight(1f) // Para que la columna ocupe todo el espacio disponible
                                                            .padding(start = 8.dp) // Ajusta el espacio entre los textos en la columna
                                                    ) {
                                                        //For para poner por hora, similar a como se origanizan por dia
                                                        Text(
                                                            text = "${verPasajero?.nombre_completo}",
                                                            style = TextStyle(
                                                                color = Color.Black,
                                                                fontSize = 20.sp,
                                                                textAlign = TextAlign.Start,
                                                            ),
                                                        )
                                                    }

                                                    CoilImage(
                                                        url = "${verPasajero?.URL_imagen}",
                                                        modifier = Modifier
                                                            .size(90.dp)
                                                            .clip(CircleShape)
                                                            .align(Alignment.Bottom),
                                                    )

                                                }

                                                botonesVerPasajeros { buttonText ->
                                                    when (buttonText) {
                                                        "Contacto" -> {
                                                            dialogoContact = true
                                                        }

                                                        "Reportar" -> {
                                                            dialogoInf = true
                                                        }

                                                        "Borrar" -> {
                                                            dialogoBorrar = true

                                                        }
                                                    }
                                                }
                                                lineaGris()
                                                Spacer(modifier = Modifier.height(20.dp))
                                            }


                                        } else {
                                            Text(
                                                text = "No hay pasajeros registrados para hoy.",
                                                style = TextStyle(
                                                    color = Color(86, 86, 86),
                                                    fontSize = 18.sp,
                                                    textAlign = TextAlign.Justify,
                                                )
                                            )

                                        }




                                    }

                                }


                            }

                        }


                    } //cierre de la columna donde puse la info

                }

                //cieere for }
            }

        }

    }

        if (dialogoContact) {
            dialogoContactoPasajero(
                onDismiss = { dialogoContact = false },
                usuario!!
            )
        }

        println("USUARIO $usuario")
        if (dialogoInf) {
            dialogoReportarPasajero(
                onDismiss = { dialogoInf = false },
                usuario!!,
                userid,
                pasajero_id,
                navController
            )
        }

        if (dialogoBorrar) {
            dialogoBorrarPasajero(
                onDismiss = { dialogoBorrar = false },
                userid, it.solicitud_id,
                navController
            )
        }


}

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewSolicitudes() {
    val navController = rememberNavController()
    verPasajeros(
        navController = navController,
        userid = "hannia"
    )
}