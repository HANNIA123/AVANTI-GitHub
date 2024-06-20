package com.example.curdfirestore.Horario.Pantallas

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.avanti.MarkerItiData
import com.example.avanti.SolicitudData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeRT
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.textInMarker
import com.example.curdfirestore.textoNegrita

@SuppressLint("SuspiciousIndentation")
@Composable
fun ventanaMarkerItinerarioPas(
    marker: MarkerItiData,
    show: Boolean,
    solicitud: SolicitudData?,
    trayecto:String,
    onDismiss: () -> Unit,
    ) {

    if (show) {
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
                            if(trayecto=="0"){
                                addressP="UPIITA-IPN"
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
                            //TextInMarker(Label = "Nombre: ", Text = marker.marker_titulo)
                        } else if (marker.marker_titulo == "Destino") {
                            if(trayecto=="1"){
                                addressP="UPIITA-IPN"
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
                            //TextInMarker(Label = "Nombre: ", Text = marker.marker_titulo)
                        } else {
                            solicitud?.let {
                                Text(
                                    text = "Información sobre la parada",
                                    modifier = Modifier.padding(2.dp),
                                    style = TextStyle(
                                        Color(137, 13, 88),
                                        fontSize = 18.sp
                                    ),
                                    textAlign = TextAlign.Center

                                )
                                textInMarker(
                                    Label = "Nombre de la parada: ",
                                    Text = marker.marker_titulo
                                )

                                textInMarker(Label = "Ubicación: ", Text = addressP)


                                textInMarker(
                                    Label = "Hora de encuentro: ",
                                    Text = "${marker.marker_hora} hrs"
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Conductor",
                                    modifier = Modifier.padding(2.dp),
                                    style = TextStyle(
                                        Color(137, 13, 88),
                                        fontSize = 18.sp
                                    ),
                                    textAlign = TextAlign.Center

                                )

                                Spacer(modifier = Modifier.height(10.dp))


                                val viaje = conObtenerViajeRT(viajeId = solicitud.viaje_id)

                                val conductor = conObtenerUsuarioId(correo = solicitud.conductor_id)

                                viaje?.let {
                                    val status = viaje.viaje_status
                                    val (color, textoValidacion) = when (status) {
                                        "Disponible" -> Color(
                                            21,
                                            154,
                                            78
                                        ) to "Confirmado"


                                        else -> Color.Red to "Cancelado"

                                    }

                                    conductor?.let {

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically

                                    ) {
                                        CoilImage(
                                            url = conductor.usu_foto,
                                            modifier = Modifier
                                                .size(60.dp)
                                                .clip(CircleShape)
                                        )

                                        Spacer(modifier = Modifier.width(15.dp))
                                        Column {
                                            textoNegrita(
                                                texto = "${conductor.usu_nombre} ${conductor.usu_primer_apellido}",
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


                                }
                            }

                            }
                        }



                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center

                    ) {
                        TextButton(
                            onClick = {

                                onDismiss()

                            }) {
                            Text(
                                text = "CERRAR",
                                style = TextStyle(
                                    Color(137, 67, 242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }
                    }

                }
            }
        }
    }

}