package com.example.curdfirestore.Solicitud.Pantallas

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.ParadaData
import com.example.avanti.UserData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ViajeData
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.avanti.ui.theme.Aplicacion.toLocalDate
import com.example.curdfirestore.R
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

    var listaSolicitudes = conObtenerSolicitudesConductor(userId = userId)
    var mhv by remember {
        mutableStateOf(0.dp)
    }


    var usuario by remember { mutableStateOf<UserData?>(null) }
    var viaje by remember { mutableStateOf<ViajeData?>(null) }
    var parada by remember { mutableStateOf<ParadaData?>(null) }


    var dialogoInf by remember { mutableStateOf(false) } //Status de la solictud terminado

    var confirmA by remember { mutableStateOf(false) } //Status de la solictud terminado

    var confirmR by remember { mutableStateOf(false) } //Status de la solictud terminado
    var presionado by remember { mutableStateOf(false) } //Status de la solictud terminado

    var aceptado by remember { mutableStateOf(false) }

    var rechazado by remember { mutableStateOf(false) }

    var noLugares by remember { mutableStateOf(false) }
    var idViaje by remember { mutableStateOf("") }
    var idSol by remember { mutableStateOf("") }
    var idParada by remember { mutableStateOf("") }
    var idPasajero by remember { mutableStateOf("") }
    BoxWithConstraints {
        mhv = this.maxHeight - 50.dp
    }
    Box() {

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
                            text = "Te mostramos las solicitudes que has recibido",
                            style = TextStyle(
                                color = Color(86, 86, 86),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Justify,
                            ),

                            )

                    }
                    Spacer(modifier = Modifier.height(10.dp))



                    listaSolicitudes?.let {
                        // Filtrar la lista por el nombre buscado
                        val solPendientes =
                            listaSolicitudes.filter { it.solicitud_status == "Pendiente" }

                        // Realizar acciones según la condición
                        if (listaSolicitudes.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(2.dp, Color.White)
                                    .background(Color.White)
                                    .fillMaxWidth()
                                    .padding(15.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                }
                                Image(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    painter = painterResource(id = R.drawable.buscar),
                                    contentDescription = "Imagen no viaje",
                                    contentScale = ContentScale.FillBounds
                                )

                                Text(
                                    text = "No hay  solicitudes",
                                    style = TextStyle(
                                        color = Color(86, 86, 86),
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Justify,
                                    )
                                )

                            }


                        } else {
                            val solicitudes =
                                solPendientes.sortedBy { it.solicitud_date.toLocalDate("dd/MM/yyyy") }

                            // val solicitudes = solPendientes.sortedByDescending { it.solicitud_date }
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
                                                Spacer(modifier = Modifier.width(20.dp)) // Agrega un espacio entre el texto y la columna

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
                                                            rechazado = false
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


                    }


                }

            }
            if (confirmA) {
                dialogoSolicitudAceptada(onDismiss = { confirmA = false }, navController, userId)

            }
            if(confirmR){
                dialogoSolicitudRechazada(onDismiss = { confirmR=false }, navController = navController, userId = userId)
            }
            if(noLugares){
                dialogoSolicitudRechazada(onDismiss = { confirmR=false }, navController = navController, userId = userId)
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

                println("Viaje Id: $idViaje")
                val viaje = conObtenerViajeId(viajeId = idViaje)
                var numLugares = 0

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
                        var newLugares = numLugares - 1
                        conActualizarLugares(idViaje, "viaje_num_lugares", newLugares.toString())
                        actualizarPasajerosDeSolicitud(idViaje, camposActualizar)


                        LaunchedEffect(Unit) {
                            conResponderSolicitud(idSol, "Aceptada") { respuestaExitosa ->
                                confirmA = respuestaExitosa
                            }
                        }

                    } else {
                        noLugares=true
                        //Ya no hay lugares disponibles
                        /*show1 = true
                VentanaSolicitudNoPermitida(navController, userId, show1, { show1 = false }, {})*/
                    }
                }
            } else {
                println("Solcitud Rechazada")
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

