package com.example.curdfirestore.Viaje.Pantallas.Monitoreo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.ParadaData
import com.example.avanti.SolicitudData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.cabeceraAtrasRuta
import com.example.avanti.ui.theme.Aplicacion.cabeceraConBotonAtras
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesConductor
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesPorViajeRT
import com.example.curdfirestore.Usuario.Conductor.menuCon
import com.example.curdfirestore.textInfPasajeros
import com.example.curdfirestore.textTituloInfSolcitud

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun verInformacionViajeComenzada(
    navController: NavController,
    userid: String,
    viajeId:String,
    paradasOrdenadas: List<Pair<String, ParadaData>>
) {

    var maxh by remember {
        mutableStateOf(0.dp)
    }

    var listaSolicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }
    conObtenerSolicitudesConductor(userId = userid) { solicitudes ->
        listaSolicitudes = solicitudes
    }


    BoxWithConstraints {
        maxh = this.maxHeight
    }

    Box {

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

            cabeceraAtrasRuta(
                titulo = "Detalles",
                navController = navController,
                ruta = "empezar_viaje/$userid/$viajeId"
            )



            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(2.dp, Color.White)
                    .background(Color.White)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center


            ) {


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


                            paradasOrdenadas.forEach {
                                textTituloInfSolcitud("${it.second.par_nombre}  -  ${it.second.par_hora} hrs")
                                Spacer(modifier = Modifier.height(5.dp))
                                val paradaId = it.first

                                val solicitudes =
                                    conObtenerSolicitudesPorViajeRT(viajeId = it.second.viaje_id)
                                solicitudes?.forEach { solicitud ->

                                    if (solicitud.second.parada_id == paradaId && solicitud.second.solicitud_activa_pas != "no") {
                                        val pasajero =
                                            conObtenerUsuarioId(correo = solicitud.second.pasajero_id)

                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            pasajero?.let {
                                                CoilImage(
                                                    url = pasajero.usu_foto,
                                                    modifier = Modifier
                                                        .size(60.dp)
                                                        .clip(CircleShape)
                                                        .align(Alignment.Bottom),
                                                )

                                                Spacer(modifier = Modifier.width(10.dp))

                                                Column {

                                                    val nombreCampo =
                                                        solicitud.second.solicitud_validacion_pasajero
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
                                                    textInfPasajeros(
                                                        texto = "${pasajero.usu_nombre} ${pasajero.usu_primer_apellido}",
                                                        Color.Black
                                                    )
                                                    textInfPasajeros(
                                                        texto = "Contacto: ${pasajero.usu_telefono}",
                                                        Color.Black
                                                    )
                                                    textInfPasajeros(texto = textoValidacion, color)

                                                }

                                            }
                                        }
                                    } else {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Sin pasajeros confirmados",
                                                style = TextStyle(
                                                    color = Color.Black,
                                                    fontSize = 16.sp,
                                                    textAlign = TextAlign.Start
                                                )
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                            }


                        }

                    } //cierre de la columna donde puse la info

                }

            }

        }


    }


}


