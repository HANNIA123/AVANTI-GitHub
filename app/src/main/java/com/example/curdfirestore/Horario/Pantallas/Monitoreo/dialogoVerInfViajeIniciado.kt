package com.example.curdfirestore.Horario.Pantallas.Monitoreo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Warning
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.avanti.SolicitudData
import com.example.avanti.UserData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.lineaGris
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerParadaRT
import com.example.curdfirestore.R
import com.example.curdfirestore.Reportes.Pantallas.dialogoReportarConductor
import com.example.curdfirestore.Usuario.Conductor.ConsultasConductor.conObtenerVehiculoId
import com.example.curdfirestore.textInfPasajeros
import com.example.curdfirestore.textInfViaje
import com.example.curdfirestore.textoNegrita


@Composable
fun dialogoVerInfViajeIniciado(
    onDismiss: () -> Unit,
    solicitud: Pair<String, SolicitudData>,
    paradaId: String,
    navController: NavController

) {
    var expContacto by remember { mutableStateOf(false) }
    var botonReportar by remember { mutableStateOf(false) }
    var conductor_id by remember {
        mutableStateOf("")
    }
    var usuarioCon by remember { mutableStateOf<UserData?>(null) }
    val ruta = "ver_progreso_viaje/${solicitud.second.pasajero_id}/${solicitud.second.viaje_id}/${solicitud.first}/${solicitud.second.horario_id}/${solicitud.second.parada_id}"
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
                    Spacer(modifier = Modifier.height(10.dp))

                    val conductor = conObtenerUsuarioId(correo = solicitud.second.conductor_id)
                    val vehiculo = conObtenerVehiculoId(correo = solicitud.second.conductor_id)

                    Column(
                        modifier = Modifier.fillMaxWidth(),

                        ) {
                        conductor?.let {
                            vehiculo?.let {
                                val conId = solicitud.second.conductor_id
                                val parada = conObtenerParadaRT(paradaId = paradaId)
                                parada?.let {


                                    val nombreCampo =
                                        solicitud.second.solicitud_validacion_conductor

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


                                    Row(
                                        modifier = Modifier.fillMaxWidth(),

                                        ) {
                                        CoilImage(
                                            url = conductor.usu_foto,
                                            modifier = Modifier
                                                .size(110.dp)
                                                .clip(CircleShape)
                                        )

                                        Spacer(modifier = Modifier.width(10.dp))

                                        Column(
                                            modifier = Modifier.weight(1f), // La columna ocupará todo el espacio restante
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.CenterHorizontally


                                        ) {
                                            val nombre =
                                                "${conductor.usu_nombre} ${conductor.usu_primer_apellido} "
                                            textoNegrita(nombre, 18.0f, Color.Black)

                                            Spacer(modifier = Modifier.height(10.dp))
                                            Row(
                                                modifier = Modifier.clickable {
                                                    expContacto = !expContacto
                                                    // Función para contacto
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.Call,
                                                    contentDescription = "Contacto",
                                                    modifier = Modifier.size(20.dp),
                                                    tint = Color.Black,
                                                )
                                                Spacer(modifier = Modifier.width(15.dp))



                                                Text(
                                                    text = "Contacto",
                                                    style = TextStyle(
                                                        color = Color(86, 86, 86),
                                                        fontSize = 16.sp,

                                                        ),
                                                    modifier = Modifier.align(Alignment.CenterVertically)
                                                )


                                            }
                                            if (expContacto) {
                                                Box(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = conductor.usu_telefono,
                                                        style = TextStyle(
                                                            color = Color(86, 86, 86),
                                                            fontSize = 16.sp,

                                                            ),
                                                        modifier = Modifier.offset(x=10.dp)

                                                    )
                                                }
                                            }


                                            Spacer(modifier = Modifier.height(25.dp))
                                            Row(
                                                modifier = Modifier
                                                    .align(Alignment.CenterHorizontally) // Alineación horizontal centrada
                                                    .clickable {
                                                        usuarioCon = conductor
                                                        conductor_id = conId
                                                        botonReportar = true
                                                    }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.Warning,
                                                    contentDescription = "Reportar",
                                                    modifier = Modifier.size(20.dp),
                                                    tint = Color(180, 13, 13),
                                                )

                                                Spacer(modifier = Modifier.width(10.dp))

                                                Text(
                                                    text = "Reportar",
                                                    style = TextStyle(
                                                        color = Color(180, 13, 13),
                                                        fontSize = 16.sp,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                    modifier = Modifier.align(Alignment.CenterVertically)
                                                )
                                            }


                                        }
                                    }



                                    Spacer(modifier = Modifier.height(10.dp))
                                    lineaGris()
                                    Spacer(modifier = Modifier.height(10.dp))
                                    val tam = 16.0f
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column {
                                            textInfViaje(
                                                Label = "Vehículo: ",
                                                Text = "${vehiculo.vehi_marca} ${vehiculo.vehi_color}",
                                                color = Color.Black,
                                                tam = tam
                                            )

                                            textInfViaje(
                                                Label = "Placas: ",
                                                Text = "${vehiculo.vehi_placas}",
                                                color = Color.Black,
                                                tam = tam
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    lineaGris()
                                    Spacer(modifier = Modifier.height(10.dp))

                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center

                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {

                                            Spacer(modifier = Modifier.height(10.dp))
                                            Icon(
                                                painter = painterResource(id = R.drawable.huella),
                                                contentDescription = "huella",
                                                tint = color,
                                                modifier = Modifier.size(40.dp)

                                            )

                                            textInfPasajeros(texto = textoValidacion, color)
                                        }
                                    }

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
    if (botonReportar) {
        dialogoReportarConductor(
            onDismiss = { botonReportar = false },
            usuarioCon!!,
            solicitud.second.pasajero_id,
            conductor_id,
            navController,
            ruta
        )
    }

}