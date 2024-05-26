package com.example.curdfirestore.Imprevistos.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.avanti.ImprevistoData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaFormatoddmmyyyy
import com.example.avanti.ui.theme.Aplicacion.obtenerHoraActual
import com.example.curdfirestore.Imprevistos.Funciones.conRegistrarImprevisto
import com.example.curdfirestore.Imprevistos.Funciones.convertiraMotivoImprevisto
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesPorViajeRT
import com.example.curdfirestore.Viaje.Funciones.registrarNotificacionImprevistoViaje
import com.example.curdfirestore.textTituloInfSolcitud

@Composable
fun dialogoImprevisto(
    onDismiss: () -> Unit,
    userid: String,
    viajeid: String,
    navController: NavController,
    ruta: String
) {
    val tamEspacio = 15.dp
    val tamIcono = 45.dp
    var motivo by remember {
        mutableStateOf("Selecciona el motivo: ")
    }
    val campoMotivo by remember {
        mutableStateOf(false)
    }
    var showDialogMotivo by remember { mutableStateOf(false) }
    var botonNotificacion by remember { mutableStateOf(false) }
    var selectedMotivo by remember { mutableStateOf(emptySet<Int>()) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }

    var boton by remember { mutableStateOf(false) }
    var ejecutado by remember { mutableStateOf(false) }
    var confirmR by remember { mutableStateOf(false) }
    var motivoCon by remember {
        mutableStateOf("")
    }

    if (selectedMotivo.isNotEmpty()) {
        motivoCon = convertiraMotivoImprevisto(numMotivo = selectedMotivo)
        motivo = "Motivo: $motivoCon"

    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),

        ) {

        Dialog(
            onDismissRequest = {
                // onDismiss()
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
                        textTituloInfSolcitud("Reportar imprevisto")

                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                Color.LightGray
                            )
                            .clickable {
                                showDialogMotivo = true
                            },
                        verticalAlignment = Alignment.CenterVertically // Alineación vertical centrada
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Warning,
                            contentDescription = "Icono días",
                            modifier = Modifier
                                .size(tamIcono)
                                .padding(10.dp, 5.dp),
                            tint = Color(137, 13, 86)
                        )
                        Text(
                            text = motivo,
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(10.dp),
                            style = TextStyle(
                                fontSize = 17.sp,
                                color = Color.Black
                            )
                        )
                    }

                    if (campoMotivo) {
                        Text(
                            text = "*Por favor ingresa el motivo",
                            style = TextStyle(
                                color = Color(86, 86, 86)
                            )

                        )
                    }

                    Spacer(modifier = Modifier.height(tamEspacio))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
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
                                boton = true
                                confirmR = true
                                //showDialog = true

                            }) {

                            Text(
                                text = "Enviar imprevisto", style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            )
                        }

                        TextButton(
                            onClick = { onDismiss() },
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
    // Diálogo para la selección de motivos
    if (showDialogMotivo) {
        dialogSeleccionMotivoImpr(
            onDismiss = { showDialogMotivo = false },
            onDaysSelected = { selectedMotivo = it }
        )
    }

    if (confirmR) {
        dialogoImprevistoEnviado(onDismiss = { confirmR = false }, navController, userid, ruta)
    }
    val solicitudes = conObtenerSolicitudesPorViajeRT(viajeId = viajeid)
    val conductor = conObtenerUsuarioId(correo = userid)

    if (boton == true && ejecutado == false) {
        val imprevistoData = ImprevistoData(
            impr_u_que_reporta = userid,
            impr_viaje_id = viajeid,
            impr_motivo = motivo,
            impr_fecha = obtenerFechaFormatoddmmyyyy(),
            impr_hora = obtenerHoraActual().toString()
        )

        solicitudes?.let {
            conductor?.let {
                registrarNotificacionImprevistoViaje(
                    tipoNot = "iv",
                    solicitudes,
                    userid,
                    viajeid,
                    conductor = conductor
                )




                conRegistrarImprevisto(imprevistoData)
                ejecutado = true
            }
        }
        }


}