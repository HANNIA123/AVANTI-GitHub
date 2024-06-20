package com.example.curdfirestore.Solicitud.Pantallas

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.avanti.ParadaData
import com.example.avanti.UserData
import com.example.avanti.ViajeData
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.lineaGris
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerParadaId
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.Viaje.Pantallas.DayButton
import com.example.curdfirestore.textTituloInfSolcitud
import com.example.curdfirestore.textoInformacionSolicitud
import com.example.curdfirestore.textoInformacionViaje

@Composable
fun dialogoInformacionSolicitud(
    onDismiss: () -> Unit,
    usuario: UserData,
    idViaje: String,
    idParada: String

) {
    val viaje = conObtenerViajeId(viajeId = idViaje)
    val parada = conObtenerParadaId(paradaId = idParada)
    viaje?.let {
        parada?.let {
            val dirOrigen =
                convertirStringALatLng(viaje.viaje_origen)?.let { convertCoordinatesToAddress(it) }
            val dirDestino =
                convertirStringALatLng(viaje.viaje_destino)?.let { convertCoordinatesToAddress(it) }
            val dirParada =
                convertirStringALatLng(parada.par_ubicacion)?.let { convertCoordinatesToAddress(it) }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),

                ) {


                Dialog(
                    onDismissRequest = {
                        onDismiss()
                        //expanded = false
                    }, // Cierra el diálogo al tocar fuera de él
                    content = {
                        // Contenido del diálogo
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(15.dp)


                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                CoilImage(
                                    url = usuario.usu_foto, modifier = Modifier
                                        .size(90.dp)
                                        .clip(CircleShape)
                                        .align(Alignment.CenterHorizontally), // Centrar horizontalmente
                                )
                                val nombreMostrar =
                                    "${usuario.usu_nombre} ${usuario.usu_primer_apellido}"

                                Text(
                                    text = nombreMostrar,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold

                                    )
                                )


                            }
                            Spacer(modifier = Modifier.height(10.dp))
lineaGris()
                            Spacer(modifier = Modifier.height(5.dp))
                            //Informacion del viaje
                            textTituloInfSolcitud("Información del viaje")
                            textoInformacionSolicitud(
                                etiqueta = "Día",
                                contenido = viaje.viaje_dia
                            )
                            if (viaje.viaje_trayecto == "0")// origen UPIITA{
                            {
                                textoInformacionSolicitud(etiqueta = "Origen", contenido = "UPIITA")
                                textoInformacionSolicitud(
                                    etiqueta = "Destino",
                                    contenido = dirDestino!!
                                )
                            } else {
                                textoInformacionSolicitud(
                                    etiqueta = "Origen",
                                    contenido = dirOrigen!!
                                )
                                textoInformacionSolicitud(
                                    etiqueta = "Destino",
                                    contenido = "UPIITA"
                                )

                            }
                            textoInformacionSolicitud(
                                etiqueta = "Hora de inicio",
                                contenido = "${viaje.viaje_hora_partida} hrs"
                            )
                            textoInformacionSolicitud(
                                etiqueta = "Hora de llegada",
                                contenido = "${viaje.viaje_hora_llegada} hrs"
                            )
                            textoInformacionSolicitud(
                                etiqueta = "Lugares disponibles",
                                contenido = viaje.viaje_num_lugares
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            lineaGris()
                            Spacer(modifier = Modifier.height(5.dp))
                            textTituloInfSolcitud("Información de la solicitud")
                            //Informacion de la parada
                            textoInformacionSolicitud(
                                etiqueta = "Parada solicitada",
                                contenido = parada.par_nombre
                            )
                            textoInformacionSolicitud(
                                etiqueta = "Ubicación",
                                contenido = dirParada!!
                            )
                            textoInformacionSolicitud(
                                etiqueta = "Hora de encuentro",
                                contenido = "${parada.par_hora} hrs"
                            )
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TextButton(
                                    onClick = {  onDismiss() },
                                    modifier = Modifier.height(48.dp)
                                ) {
                                    Text(
                                        text = "CERRAR",
                                        style = TextStyle(
                                            color = Color(137, 67, 242),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    )
                                }
                            }

                        }


                    },

                    )

            }

        }
    }


}