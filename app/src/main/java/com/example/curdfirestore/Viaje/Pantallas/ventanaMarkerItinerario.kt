package com.example.curdfirestore.Viaje.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.avanti.MarkerItiData
import com.example.avanti.SolicitudData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioRT
import com.example.avanti.ViajeData
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.curdfirestore.Horario.ConsultasHorario.conObtenerHorarioId
import com.example.curdfirestore.Parada.ConsultasParada.eliminarParada
import com.example.curdfirestore.Parada.Pantallas.dialogoConfirmarEliminarParada
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.textInMarker
import com.example.curdfirestore.textoNegrita

@Composable
fun ventanaMarkerItinerario(
    navController: NavController,
    viajeId: String,
    viaje: ViajeData,
    email: String,
    marker: MarkerItiData,
    solicitudes: List<Pair<String, SolicitudData>>?,
    show: Boolean,
    trayecto: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    if (show) {


        var showConfirmarEliminar by remember {
            mutableStateOf(false)
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)),

            ) {
            Dialog(onDismissRequest = { onDismiss() }) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(10.dp)
                ) {


                    var addressP = convertCoordinatesToAddress(marker.marker_ubicacion)


                    if (marker.marker_titulo == "Origen") {
                        if (trayecto == "0") {
                            addressP = "UPIITA-IPN"
                        }

                        Text(
                            text = "Tu punto de partida",
                            modifier = Modifier.padding(2.dp),
                            style = TextStyle(
                                Color(137, 13, 88),
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Center

                        )
                        textInMarker(Label = "Ubicación: ", Text = addressP)
                        textInMarker(
                            Label = "Horario de salida: ",
                            Text = "${marker.marker_hora} hrs"
                        )

                        //TextInMarker(Label = "Nombre: ", Text = marker.marker_titulo)
                    } else if (marker.marker_titulo == "Destino") {
                        if (trayecto == "1") {
                            addressP = "UPIITA-IPN"
                        }
                        Text(
                            text = "Tu punto de llegada",
                            modifier = Modifier.padding(2.dp),
                            style = TextStyle(
                                Color(137, 13, 88),
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Center

                        )

                        textInMarker(Label = "Ubicación: ", Text = addressP)
                        textInMarker(
                            Label = "Horario de llegada: ",
                            Text = "${marker.marker_hora} hrs"
                        )


                        //TextInMarker(Label = "Nombre: ", Text = marker.marker_titulo)
                    } else {

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center


                        ) {
                            Text(
                                text = "Información sobre la parada",
                                modifier = Modifier.padding(2.dp),
                                style = TextStyle(
                                    Color(137, 13, 88),
                                    fontSize = 18.sp
                                ),
                                textAlign = TextAlign.Center

                            )

                        }
                        Spacer(modifier = Modifier.height(5.dp))


                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {

                                Column(
                                    modifier = Modifier
                                        .weight(0.70f),
                                ) {


                                    textInMarker(
                                        Label = "Nombre de la parada: ",
                                        Text = marker.marker_titulo
                                    )
                                    textInMarker(
                                        Label = "Hora de encuentro: ",
                                        Text = "${marker.marker_hora} hrs"
                                    )


                                    textInMarker(Label = "Ubicación: ", Text = addressP)

                                }

                                Column(
                                    modifier = Modifier
                                        .weight(0.25f),

                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.End// Hace que esta columna ocupe el 20% del ancho del Row
                                ) {
                                    IconButton(
                                        onClick = {
                                            showConfirmarEliminar = true
                                            //eliminarParada(marker.marker_id, navController, email)


                                        }) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = "Borrar",
                                            tint = Color(137, 13, 86),
                                            modifier = Modifier.size(50.dp)

                                        )
                                    }
                                }
                            }

                            if (viaje.viaje_num_pasajeros != "0") {

                                Spacer(modifier = Modifier.height(10.dp))
                                val solicitudeFilter = solicitudes?.filter { solicitud ->
                                    solicitud.second.parada_id == marker.marker_id
                                }

                                if (solicitudeFilter!!.isNotEmpty()) {

                                    Text(
                                        text = "Pasajeros: ",
                                        modifier = Modifier.padding(2.dp),
                                        style = TextStyle(
                                            Color(137, 13, 88),
                                            fontSize = 18.sp
                                        ),
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(10.dp))
                                    solicitudeFilter.forEach { solicitud ->
                                        if (solicitud.second.parada_id == marker.marker_id) {

                                        val idUser = solicitud.second.pasajero_id
                                        val idHorario = solicitud.second.horario_id

                                        val horario = conObtenerHorarioId(horarioId = idHorario)

                                        horario?.let {
                                            val status = horario.horario_status
                                            val (color, textoValidacion) = when (status) {
                                                "Disponible" -> Color(
                                                    21,
                                                    154,
                                                    78
                                                ) to "Confirmado"


                                                else -> Color.Red to "Cancelado"

                                            }


                                            Spacer(modifier = Modifier.height(10.dp))

                                            val pasajero =
                                                conObtenerUsuarioId(correo = idUser)

                                            pasajero?.let {

                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth(),
                                                    verticalAlignment = Alignment.CenterVertically

                                                ) {
                                                    val foto = pasajero.usu_foto
                                                    val nombre =
                                                        "${pasajero.usu_nombre} ${pasajero.usu_primer_apellido}"

                                                    CoilImage(
                                                        url = foto,
                                                        modifier = Modifier
                                                            .size(60.dp)
                                                            .clip(CircleShape)
                                                    )

                                                    Spacer(modifier = Modifier.width(15.dp))
                                                    Column {


                                                        textoNegrita(
                                                            texto = nombre,
                                                            tam = 15.0f,
                                                            color = Color.Black
                                                        )
                                                        textoNegrita(
                                                            texto = textoValidacion,
                                                            tam = 14.0f,
                                                            color = color
                                                        )
                                                    }
                                                }

                                                Spacer(modifier = Modifier.height(5.dp))
                                            }


                                        }

                                    }
                                    }
                                }


                            }
                        }


                    }


                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        137,
                                        13,
                                        88
                                    )
                                ),
                                onClick = {

                                    if (marker.marker_titulo == "Origen" || marker.marker_titulo == "Destino") {
                                        navController.navigate("general_viaje_conductor_editar/$email/$viajeId")
                                    } else {
                                        navController.navigate("general_parada_editar/$viajeId/$email/${marker.marker_id}")


                                    }
                                    // Cerrar el diálogo cuando se hace clic en "Cerrar"
                                    onDismiss()
                                }) {
                                Text(
                                    text = "Editar", style = TextStyle(
                                        fontSize = 15.sp,
                                        color = Color.White
                                    )
                                )
                            }



                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        233,
                                        168,
                                        219
                                    )
                                ),
                                onClick = {
                                    // Cerrar el diálogo cuando se hace clic en "Cerrar"
                                    onDismiss()
                                }) {
                                Text(
                                    text = "Cerrar", style = TextStyle(
                                        fontSize = 15.sp,
                                        color = Color.White
                                    )
                                )
                            }


                        }

                    }
                }

                println("El id de parada es: ------ ${marker.marker_id}")

                if (showConfirmarEliminar) {
                    dialogoConfirmarEliminarParada(
                        onDismiss = { showConfirmarEliminar = false },
                        viajeId,
                        email,
                        marker.marker_id,
                        navController
                    )

                }
            }
        }
    }
}

