package com.example.curdfirestore.Reportes.Pantallas


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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.avanti.ParadaData
import com.example.avanti.ReporteData
import com.example.avanti.UserData
import com.example.avanti.ViajeData
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.lineaGris
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaFormatoddmmyyyy
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerParadaId
import com.example.curdfirestore.Reportes.ConsultasReporte.conRegistrarReporte
import com.example.curdfirestore.Reportes.Funciones.convertiraMotivo
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.Viaje.Funciones.convertirADia
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.Viaje.Pantallas.DayButton
import com.example.curdfirestore.Viaje.Pantallas.dialogSeleccionDia
import com.example.curdfirestore.textTituloInfSolcitud
import com.example.curdfirestore.textoInformacionSolicitud
import com.example.curdfirestore.textoInformacionViaje

@Composable
fun dialogoReportarPasajero(
    onDismiss: () -> Unit,
    usuario: UserData,
    userid: String,
    navController: NavController,
) {
    var tamEspacio = 15.dp
    var tamIcono = 55.dp
    var motivo by remember {
        mutableStateOf("Motivo del reporte: ")
    }
    var campoMotivo by remember {
        mutableStateOf(false)
    }
    var showDialogMotivo by remember { mutableStateOf(false) }
    var selectedMotivo by remember { mutableStateOf(emptySet<Int>()) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var boton by remember { mutableStateOf(false) }
    var ejecutado by remember { mutableStateOf(false) }
    var confirmR by remember { mutableStateOf(false) }
    val descripciontext: String = descripcion.text
    var motivoCon by remember {
        mutableStateOf("")
    }

    if (selectedMotivo.isNotEmpty()) {
        motivoCon = convertiraMotivo(numMotivo = selectedMotivo)
        motivo = "Motivo: $motivoCon"

    }

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
                        textTituloInfSolcitud("Reporte")

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
                    //textTituloInfSolcitud("Motivo")

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                1.dp,
                                Color.LightGray
                            )
                            .clickable {
                                showDialogMotivo = true
                                // show = true
                            }
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
                                .fillMaxWidth()
                                .padding(30.dp, 15.dp, 10.dp, 10.dp),
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.Black

                            )
                        )

                    }

                    if(campoMotivo) {
                        Text(
                            text = "*Por favor ingresa el motivo",
                            style = TextStyle(
                                color = Color(86, 86, 86)
                            )

                        )
                    }



                    Spacer(modifier = Modifier.height(tamEspacio))

                    textTituloInfSolcitud("Detalles")

                    BasicTextField(
                        value = descripcion.text,
                        onValueChange = { descripcion = TextFieldValue(it) },
                        textStyle = TextStyle(
                            color = Color(104, 104, 104),
                            fontSize = 18.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            //.fillMaxHeight()
                            .height(100.dp)
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RectangleShape
                            )
                            .padding(8.dp) // Opcional: puedes ajustar el relleno según tus preferencias
                    )
                    Spacer(modifier = Modifier.height(tamEspacio))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Button(
                            colors= ButtonDefaults.buttonColors(backgroundColor = Color(137, 13, 88) ),
                            onClick = {
                                boton=true
                                confirmR=true
                                //showDialog = true

                            }) {

                            Text(
                                text = "Enviar reporte", style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            )
                        }



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
    // Diálogo para la selección de días
    if (showDialogMotivo) {
        dialogSeleccionMotivo(
            onDismiss = { showDialogMotivo = false },
            onDaysSelected = { selectedMotivo = it }
        )
    }

    if (confirmR) {
        dialogoReporteEnviado(onDismiss = { confirmR = false }, navController, userid)
    }


    if (boton==true && ejecutado==false){
        val fecha_now = obtenerFechaFormatoddmmyyyy()
        val reporteData = ReporteData(
            repor_u_que_reporta = userid,
            repor_u_reportado =  usuario.usu_id,
            repor_motivo = motivo,
            repor_detalles = descripciontext,
            repor_fecha = fecha_now
        )

        conRegistrarReporte(navController, reporteData)

        ejecutado=true
    }


}