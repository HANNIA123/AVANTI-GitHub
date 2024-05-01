package com.example.curdfirestore.Horario.Pantallas.Monitoreo

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.avanti.SolicitudData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.curdfirestore.R
import com.example.curdfirestore.Usuario.Conductor.ConsultasConductor.conObtenerVehiculoId
import com.example.curdfirestore.textInfPasajeros
import com.example.curdfirestore.textTituloInfSolcitud
import com.example.curdfirestore.textoHora

@Composable
fun dialogoVerInfViajeIniciado(
    onDismiss: () -> Unit,
    solicitud: SolicitudData

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
                // Contenido del diálogo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(15.dp)

                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    textTituloInfSolcitud("Información del conductor")
                    val conductor = conObtenerUsuarioId(correo = solicitud.conductor_id)
                    val vehiculo = conObtenerVehiculoId(correo = solicitud.conductor_id)

                    Column(
                        modifier = Modifier.fillMaxWidth(),

                        ) {
                        conductor?.let {
                            vehiculo?.let {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center

                                ) {
                                    CoilImage(
                                        url = conductor.usu_foto,
                                        modifier = Modifier
                                            .size(90.dp)
                                            .clip(CircleShape)

                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))

                                val nombreCampo =
                                    solicitud.solicitud_validacion_conductor
                                val (color, textoValidacion) = when (nombreCampo) {
                                    "si" -> Color(
                                        21,
                                        154,
                                        78
                                    ) to "Validación exitosa"

                                    "no" -> Color.Red to "Validación fallida"
                                    else -> Color(
                                        86,
                                        86,
                                        86
                                    ) to "Validación pendiente"
                                }
                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                    text = "${conductor.usu_nombre} ${conductor.usu_primer_apellido}",
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold

                                    )
                                )

                                textInfPasajeros(
                                    texto = "Contacto: ${conductor.usu_telefono}",
                                    Color.Black
                                )


                                textInfPasajeros(
                                    texto = "Vehículo: ${vehiculo.vehi_marca} ${vehiculo.vehi_color}",
                                    Color.Black
                                )
                                textInfPasajeros(
                                    texto = "Placas: ${vehiculo.vehi_placas}",
                                    Color.Black
                                )

                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center

                                ) {
                                    textInfPasajeros(texto = textoValidacion, color)
                                }
                            }
                        }

                    }



                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center

                    ) {
                        TextButton(
                            onClick = {

                                onDismiss()

                            }) {
                            Text(
                                text = "ACEPTAR",
                                style = TextStyle(
                                    Color(137, 67, 242),
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