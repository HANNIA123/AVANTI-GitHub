package com.example.curdfirestore.Parada.Pantallas

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
import androidx.compose.material.Icon
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.avanti.ViajeData
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.textoInformacionSolicitud

@Composable
fun dialogoInfViajeParada(
    onDismiss: () -> Unit,
    viaje: ViajeData,
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),

        ) {
        Dialog(
            onDismissRequest = {
                onDismiss()

            }, // Cierra el diálogo al tocar fuera de él
            content = {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(15.dp)

                ) {
                    Box(contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.trayecto),
                            contentDescription = "ubicacion",
                            modifier = Modifier.size(80.dp),
                            tint = Color(137, 13, 86)
                        )
                    }
             
                    val dirOrigen = convertirStringALatLng(viaje.viaje_origen)?.let { it1 ->
                        convertCoordinatesToAddress(coordenadas = it1)
                    }
                    val dirDestino = convertirStringALatLng(viaje.viaje_destino)?.let { it1 ->
                        convertCoordinatesToAddress(coordenadas = it1)
                    }


                    textoInformacionSolicitud(
                        etiqueta = "Hora de inicio",
                        contenido = "${viaje.viaje_hora_partida} hrs"
                    )
                    if (dirOrigen != null) {
                        textoInformacionSolicitud(
                            etiqueta = "Punto de incio",
                            contenido = dirOrigen
                        )
                    }
                    textoInformacionSolicitud(
                        etiqueta = "Hora de llegada",
                        contenido = "${viaje.viaje_hora_llegada} hrs"
                    )
                    if (dirDestino != null) {
                        textoInformacionSolicitud(
                            etiqueta = "Punto de llegada",
                            contenido = dirDestino
                        )
                    }



                    Spacer(modifier = Modifier.height(20.dp))


                    Row(modifier = Modifier.align(Alignment.End)) {
                        TextButton(onClick = {

                            onDismiss()
                        }
                        ) {
                            Box(contentAlignment = Alignment.BottomCenter,
                                modifier = Modifier.fillMaxWidth()) {
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
        )
    }
}
