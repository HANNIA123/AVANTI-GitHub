package com.example.curdfirestore.Solicitud.Pantallas

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.NoticacionData
import com.example.avanti.SolicitudData
import com.example.avanti.UserData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaFormatoddmmyyyy
import com.example.avanti.ui.theme.Aplicacion.obtenerHoraActual
import com.example.avanti.ui.theme.Aplicacion.toLocalDate
import com.example.curdfirestore.Notificaciones.Consultas.conRegistrarNotificacion
import com.example.curdfirestore.Notificaciones.Consultas.enviarNotificacion
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.actualizarPasajerosDeSolicitud
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conActualizarLugares
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesConductor
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conResponderSolicitud
import com.example.curdfirestore.Usuario.Conductor.menuCon
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun verSolicitudesCon(
    navController: NavController,
    userId: String
) {

    var mhv by remember {
        mutableStateOf(0.dp)
    }

    var dialogoInf by remember { mutableStateOf(false) } //Status de la solictud terminado
    var confirmA by remember { mutableStateOf(false) } //Status de la solictud terminado
    var confirmR by remember { mutableStateOf(false) } //Status de la solictud terminado
    var presionado by remember { mutableStateOf(false) } //Status de la solictud terminado
    var aceptado by remember { mutableStateOf(false) }
    var rechazado by remember { mutableStateOf(false) }
    var noLugares by remember { mutableStateOf(false) }
    var confirmN by remember { mutableStateOf(false) }

    var idViaje by remember { mutableStateOf("") }
    var idSol by remember { mutableStateOf("") }
    var idParada by remember { mutableStateOf("") }
    var idPasajero by remember { mutableStateOf("") }

    var usuario by remember { mutableStateOf<UserData?>(null) }
    var usuarioCon by remember { mutableStateOf<UserData?>(null) }
    var listaSolicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }

    usuarioCon = conObtenerUsuarioId(correo = userId)
    conObtenerSolicitudesConductor(userId = userId) { resultado ->
        // Asignar el resultado a la variable 'solicitudes'
        listaSolicitudes = resultado
    }

    BoxWithConstraints {
        mhv = this.maxHeight - 50.dp
    }

    Box {
        Scaffold(
            bottomBar = {
                BottomAppBar(modifier = Modifier.height(50.dp)) {
                    menuCon(navController = navController, userID = userId)
                }
            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(239, 239, 239))
                    .height(mhv)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                cabecera(titulo = "Solicitudes")
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

                        Text(
                            text = "Solicitudes recibidas, elige alguna para ver los detalles. ",
                            style = TextStyle(
                                color = Color(86, 86, 86),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Justify,
                            ),
                            )

                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    if (listaSolicitudes != null) {
                        val solPendientes =
                            listaSolicitudes!!.filter { it.solicitud_status == "Pendiente" }

                        if (solPendientes.isEmpty()) {
                            mensajeNosolciitudes()

                        } else {
                            val solicitudes =
                                solPendientes.sortedBy { it.solicitud_date.toLocalDate("dd/MM/yyyy") }

                            solicitudes.forEach {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(2.dp, Color.White)
                                        .background(Color.White)
                                        .fillMaxWidth()
                                        .padding(15.dp)
                                        .clickable { dialogoInf = true }
                                ) {
                                    val fechaSol = it.solicitud_date
                                    idViaje = it.viaje_id
                                    idSol = it.solicitud_id
                                    idPasajero = it.pasajero_id
                                    idParada = it.parada_id
                                    usuario = conObtenerUsuarioId(correo = idPasajero)

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {

                                            usuario?.let {

                                                val nombreMostrar =
                                                    "${usuario!!.usu_nombre} ${usuario!!.usu_primer_apellido}"

                                                CoilImage(
                                                    url = usuario!!.usu_foto, modifier = Modifier
                                                        .size(70.dp)
                                                        .clip(CircleShape)
                                                )
                                                Spacer(modifier = Modifier.width(20.dp))

                                                Column {

                                                    Text(
                                                        text = nombreMostrar,
                                                        style = TextStyle(Color.Black),
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.Bold
                                                    )

                                                    Text(
                                                        text = fechaSol,
                                                        style = TextStyle(Color(86, 86, 86)),
                                                        fontSize = 14.sp
                                                    )

                                                   Spacer(modifier = Modifier.height(10.dp))



                                                    botonesSolicitud(
                                                        onAceptarClick = {
                                                            presionado = true
                                                            aceptado = true

                                                        },
                                                        onRechazarClick = {
                                                            presionado = true
                                                            rechazado = true
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }

                                }


                                Spacer(modifier = Modifier.height(20.dp))

                            }
                        }


                    } else {
                        mensajeNosolciitudes()
                    }

                }

            }
            if (confirmA) {
                dialogoSolicitudAceptada(onDismiss = { confirmA = false }, navController, userId)

            }
            if (confirmR) {
                dialogoSolicitudRechazada(
                    onDismiss = { confirmR = false },
                    navController = navController,
                    userId = userId
                )
            }
            if (noLugares) {
                dialogoSolicitudNoPermitida(
                    onDismiss = {
                        noLugares = false
                        presionado = false
                        aceptado = false
                    },
                    idSol = idSol,
                    idUser = userId,
                    navController
                )
            }

            if (dialogoInf) {
                dialogoInformacionSolicitud(
                    onDismiss = { dialogoInf = false },
                    usuario!!,
                    idViaje,
                    idParada
                )

            }
        }

        //Acciones de la solciitud
        if (presionado) {
            if (aceptado) {
                val viaje = conObtenerViajeId(viajeId = idViaje)
                val numLugares: Int

                if (viaje != null) {
                    val nl = viaje.viaje_num_lugares
                    val np = ((viaje.viaje_num_pasajeros).toInt()) + 1 //Pasajeros en ese viaje
                    val npc =
                        ((viaje.viaje_num_pasajeros_con).toInt()) + 1 //Pasajeros en ese viaje y activos

                    val camposActualizar = mapOf(
                        "viaje_num_pasajeros" to np.toString(),
                        "viaje_num_pasajeros_con" to npc.toString(),
                    )
                    numLugares = nl.toInt()
                    if (numLugares > 0) {
                        //Enviar esta notificacion al conductor
                        val notificacionData = NoticacionData(
                            notificacion_tipo = "sa",
                            notificacion_usu_origen = userId,
                            notificacion_usu_destino = idPasajero,
                            notificacion_id_viaje = idViaje,
                            notificacion_fecha = obtenerFechaFormatoddmmyyyy(),
                            notificacion_hora = obtenerHoraActual().toString()
                        )
                        val newLugares = numLugares - 1
                        conActualizarLugares(idViaje, "viaje_num_lugares", newLugares.toString())
                        actualizarPasajerosDeSolicitud(idViaje, camposActualizar)

                        LaunchedEffect(Unit) {
                            conResponderSolicitud(idSol, "Aceptada") { respuestaExitosa ->
                                confirmA = respuestaExitosa
                            }
                        }
                        LaunchedEffect(Unit) {
                            if(!confirmN) {
                                conRegistrarNotificacion(notificacionData) { respuestaExitosa ->
                                    confirmN = respuestaExitosa
                                }
                            }
                        }

                        //---------------------------ENVIAR NOTIFICACIÓN-------------------------------------
                        enviarNotificacion(usuarioCon!!.usu_nombre, usuarioCon!!.usu_primer_apellido,
                            usuario!!.usu_token, "sa", idPasajero,
                            onSuccess = {
                                println("Notificación enviada exitosamente")
                            },
                            onError = { errorMessage ->
                                println(errorMessage)
                            }
                        )
                        //---------------------------ENVIAR NOTIFICACIÓN-------------------------------------


                    } else {
                        noLugares = true
                        //Ya no hay lugares disponibles
                    }
                }
            } else {
                LaunchedEffect(Unit) {
                    conResponderSolicitud(idSol, "Rechazada") { respuestaExitosa ->
                        confirmR = respuestaExitosa
                    }
                }
            }

        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun solitudesView() {
    val navController = rememberNavController()
    verSolicitudesCon(navController = navController, userId = "hannia")
}

