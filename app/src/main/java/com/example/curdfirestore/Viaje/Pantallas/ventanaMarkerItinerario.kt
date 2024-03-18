package com.example.curdfirestore.Viaje.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.avanti.MarkerItiData
import com.example.avanti.SolicitudData
import com.example.avanti.UserData
import com.example.avanti.Usuario.BASE_URL
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.textInMarker
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Composable
fun ventanaMarkerItinerario(
    navController: NavController,
    viajeId: String,
    email: String,
    marker: MarkerItiData,
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    var boton by remember { mutableStateOf(false) }

    var ejecutado by remember { mutableStateOf(false) }
    if (show) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(10.dp)
            ) {


                val addressP = convertCoordinatesToAddress(marker.marker_ubicacion)


                if (marker.marker_titulo == "Origen") {
                    Text(
                        text = "Tu punto de partida",
                        modifier = Modifier.padding(2.dp),
                        style = TextStyle(
                            Color(137, 13, 88),
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Center

                    )
                    //TextInMarker(Label = "Nombre: ", Text = marker.marker_titulo)
                } else if (marker.marker_titulo == "Destino") {
                    Text(
                        text = "Tu punto de llegada",
                        modifier = Modifier.padding(2.dp),
                        style = TextStyle(
                            Color(137, 13, 88),
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Center

                    )
                    //TextInMarker(Label = "Nombre: ", Text = marker.marker_titulo)
                } else {
                    Text(
                        text = "Información sobre la parada",
                        modifier = Modifier.padding(2.dp),
                        style = TextStyle(
                            Color(137, 13, 88),
                            fontSize = 18.sp
                        ),
                        textAlign = TextAlign.Center

                    )
                    textInMarker(Label = "Nombre de la parada: ", Text = marker.marker_titulo)

                }

                textInMarker(Label = "Ubicación: ", Text = addressP)
                textInMarker(Label = "Horario: ", Text = "${marker.marker_hora} hrs")


                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
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
        }
    }


}

