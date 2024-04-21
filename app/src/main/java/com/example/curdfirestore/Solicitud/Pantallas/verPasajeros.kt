package com.example.curdfirestore.Solicitud.Pantallas

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
import com.example.avanti.SolicitudData
import com.example.avanti.UserData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.avanti.ui.theme.Aplicacion.lineaGris
import com.example.avanti.ui.theme.Aplicacion.obtenerNombreDiaEnEspanol
import com.example.avanti.verPasajerosData
import com.example.curdfirestore.Reportes.Pantallas.dialogoBorrarPasajero
import com.example.curdfirestore.Reportes.Pantallas.dialogoContactoPasajero
import com.example.curdfirestore.Reportes.Pantallas.dialogoReportarPasajero
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesConductor
import com.example.curdfirestore.Usuario.Conductor.menuCon
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import java.time.DayOfWeek
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun verPasajeros(
    navController: NavController,
    userid: String,
) {

    var verPasajero by remember { mutableStateOf<verPasajerosData?>(null) }
    var diaActual by remember { mutableStateOf(LocalDate.now().dayOfWeek) }
    var listaSolicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }

    var listDatos by remember { mutableStateOf<List<verPasajerosData>?>(null) }

    var usuario by remember { mutableStateOf<UserData?>(null) }
    var dialogoInf by remember { mutableStateOf(false) }
    var dialogoContact by remember { mutableStateOf(false) } //Status de la solictud terminado
    var dialogoBorrar by rememberSaveable { mutableStateOf(false) }

    var pasajero_id by remember {
        mutableStateOf("")
    }
    var id_solicitud by remember {
        mutableStateOf("")
    }

    var termino by remember { mutableStateOf(false) }
    var maxh = 0.dp


    conObtenerSolicitudesConductor(userId = userid) { resultado ->
        listaSolicitudes = resultado
    }

    if (listaSolicitudes != null) {
        println("entraa lista")
        val sol_aceptadas = listaSolicitudes!!.filter { it.solicitud_status == "Aceptada" }

        if (sol_aceptadas.isNotEmpty()) {
            val nuevosDatos = mutableListOf<verPasajerosData>()
            sol_aceptadas.forEach { solicitud ->
                val idSol = solicitud.solicitud_id
                val status = solicitud.solicitud_status
                val idViaje = solicitud.viaje_id
                val idUser = solicitud.pasajero_id
                val user = conObtenerUsuarioId(correo = solicitud.pasajero_id)
                val via = conObtenerViajeId(viajeId = solicitud.viaje_id)

                if (status == "Aceptada" && user != null && via != null) {
                    val datos = verPasajerosData(
                        idSol,
                        idUser,
                        idViaje,
                        "${user.usu_nombre} ${user.usu_primer_apellido}",
                        user.usu_foto,
                        via.viaje_hora_partida,
                        via.viaje_dia,
                        status
                    )
                    nuevosDatos.add(datos)
                    println("Nuevo dato agregado: $datos")
                }

                // Verifica si la lista está completa después de agregar cada dato
                val cantidadSolicitudes = sol_aceptadas.size
                val cantidadDatos = nuevosDatos.size
                if (cantidadDatos == cantidadSolicitudes) {
                    println("La lista está completa")
                    termino = true
                    // Salir del bucle forEach
                    return@forEach
                }
            }

            // Actualiza la lista original en lugar de crear una nueva
            listDatos = (listDatos ?: emptyList()) + nuevosDatos
        }
    }







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


                                if(termino) {

                                        val finales = listDatos!!.filter {
                                            it.dia_viaje == obtenerNombreDiaEnEspanol(diaActual)
                                        }

                                        if (finales.isNotEmpty()) {
                                            finales.forEach {
                                                id_solicitud = it.solicitud_id
                                                pasajero_id = it.usuario_id

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    // Contenido de la columna
                                                    Column(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(start = 8.dp) // Ajusta el espacio entre los textos en la columna
                                                    ) {
                                                        //For para poner por hora, similar a como se origanizan por dia
                                                        Text(
                                                            text = it.nombre_completo,
                                                            style = TextStyle(
                                                                color = Color.Black,
                                                                fontSize = 20.sp,
                                                                textAlign = TextAlign.Start,
                                                            ),
                                                        )
                                                    }

                                                    CoilImage(
                                                        url = it.URL_imagen,
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
            pasajero_id

        )
    }


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
            userid, id_solicitud,
            navController
        )
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