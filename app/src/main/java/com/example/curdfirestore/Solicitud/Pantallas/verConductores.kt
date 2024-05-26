package com.example.curdfirestore.Solicitud.Pantallas

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.navigation.NavController
import com.example.avanti.SolicitudData
import com.example.avanti.UserData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.cabeceraSin
import com.example.avanti.ui.theme.Aplicacion.lineaGris
import com.example.avanti.ui.theme.Aplicacion.obtenerNombreDiaEnEspanol
import com.example.curdfirestore.Reportes.Pantallas.dialogoBorrarConductor
import com.example.curdfirestore.Reportes.Pantallas.dialogoContactoConductor
import com.example.curdfirestore.Reportes.Pantallas.dialogoReportarConductor
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesPasajero
import com.example.curdfirestore.Usuario.Pasajero.menuPas
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import java.time.DayOfWeek
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@Composable
fun verConductores(
    navController: NavController,
    userid: String,
) {

    var diaActual by remember { mutableStateOf(LocalDate.now().dayOfWeek) }
    var listaSolicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }

    var usuarioCon by remember { mutableStateOf<UserData?>(null) }
    var dialogoInf by remember { mutableStateOf(false) }
    var dialogoContact by remember { mutableStateOf(false) } //Status de la solictud terminado
    var dialogoBorrar by rememberSaveable { mutableStateOf(false) }
    val ruta = "ver_conductores_pasajero/$userid"
    var conductor_id by remember {
        mutableStateOf("")
    }
    var id_solicitud by remember {
        mutableStateOf("")
    }
    var id_viaje by remember {
        mutableStateOf("")
    }
    var id_horario by remember {
        mutableStateOf("")
    }

    var maxh = 0.dp

    conObtenerSolicitudesPasajero(userId = userid) { solicitudes ->
        listaSolicitudes = solicitudes
    }

    var contador by remember {
        mutableStateOf(0)
    }

    BoxWithConstraints {
        maxh = this.maxHeight - 50.dp
    }

    Box {
        Scaffold(
            bottomBar = {
                BottomAppBar(modifier = Modifier.height(50.dp)) {
                    menuPas(navController = navController, userID = userid)
                }
            }
        ) {
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
                cabeceraSin("Conductores")
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    Spacer(modifier = Modifier.height(25.dp))


                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .border(2.dp, Color.White)
                        .background(Color.White)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center


                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(30.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            onClick = {
                                diaActual = diaActual.minus(1)
                                if (diaActual < DayOfWeek.MONDAY) {
                                    diaActual = DayOfWeek.SUNDAY
                                }
                            },
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowLeft,
                                contentDescription = "Anterior",
                                tint = Color(72, 12, 107)
                            )
                        }

                        Text(
                            text = obtenerNombreDiaEnEspanol(diaActual),
                            style = TextStyle(
                                color = Color(72, 12, 107),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = {
                                diaActual = diaActual.plus(1)
                                if (diaActual > DayOfWeek.SATURDAY) {
                                    diaActual = DayOfWeek.SUNDAY
                                }
                            },
                            modifier = Modifier.size(50.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowRight,
                                contentDescription = "Siguiente",
                                tint = Color(72, 12, 107)
                            )
                        }
                    }

                }
            }
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
                            Column {
                                //Columna con informacion de los pasajeros/conductores
                                if (listaSolicitudes != null) {
                                    val solicitudes = listaSolicitudes!!.filter {
                                        it.solicitud_status == "Aceptada"
                                    }

                                    if (solicitudes.isNotEmpty()) {
                                        val listaDias = mutableListOf<String>()

                                        solicitudes.forEachIndexed { index, solicitud ->

                                            val solId = solicitud.solicitud_id
                                            val conId = solicitud.conductor_id
                                            val viaId = solicitud.viaje_id
                                            val horId = solicitud.horario_id

                                            val viaje = conObtenerViajeId(viajeId = viaId)
                                            contador = 0

                                            viaje?.let {
                                                if (viaje.viaje_dia == obtenerNombreDiaEnEspanol(
                                                        diaActual
                                                    )
                                                ) {
                                                    listaDias.add("viaje")
                                                    val conductor =
                                                        conObtenerUsuarioId(correo = conId)
                                                    conductor?.let {
                                                        val nombreCompleto =
                                                            "${conductor.usu_nombre} ${conductor.usu_primer_apellido}"

                                                        Row(
                                                            modifier = Modifier.fillMaxWidth(),
                                                            verticalAlignment = Alignment.CenterVertically
                                                        ) {
                                                            // Contenido de la columna
                                                            Column(
                                                                modifier = Modifier
                                                                    .weight(1f)
                                                                    .padding(start = 8.dp)
                                                            ) {
                                                                Text(
                                                                    text = nombreCompleto,
                                                                    style = TextStyle(
                                                                        color = Color.Black,
                                                                        fontSize = 20.sp,
                                                                        textAlign = TextAlign.Start,
                                                                    ),
                                                                )
                                                            }

                                                            CoilImage(
                                                                url = conductor.usu_foto,
                                                                modifier = Modifier
                                                                    .size(90.dp)
                                                                    .clip(CircleShape)
                                                                    .align(Alignment.Bottom),
                                                            )
                                                        }

                                                        botonesVerPasajeros { buttonText ->
                                                            when (buttonText) {
                                                                "Contacto" -> {
                                                                    usuarioCon = conductor
                                                                    conductor_id = conId
                                                                    dialogoContact = true
                                                                }

                                                                "Reportar" -> {
                                                                    usuarioCon = conductor
                                                                    conductor_id = conId
                                                                    dialogoInf = true
                                                                }

                                                                "Borrar" -> {
                                                                    id_solicitud = solId
                                                                    id_viaje = viaId
                                                                    id_horario = horId
                                                                    conductor_id=conId
                                                                    dialogoBorrar = true

                                                                }
                                                            }
                                                        }
                                                        lineaGris()
                                                        Spacer(modifier = Modifier.height(20.dp))
                                                    }
                                                }
                                            }

                                            if (index == solicitudes.size - 1) {
                                                if (listaDias.isEmpty()) {
                                                    mensajeNoPasajeros()
                                                }
                                                // Código específico para la última iteración
                                            }
                                        }

                                    } else {
                                        mensajeNoPasajeros()
                                    }
                                } else {
                                    mensajeNoPasajeros()
                                }
                            }
                        }

                    } //cierre de la columna donde puse la info

                }

            }

        }


        if (dialogoContact) {
            dialogoContactoConductor(
                onDismiss = { dialogoContact = false },
                usuarioCon!!,
                conductor_id
            )
        }


        if (dialogoInf) {
            dialogoReportarConductor(
                onDismiss = { dialogoInf = false },
                usuarioCon!!,
                userid,
                conductor_id,
                navController,
                ruta
            )
        }

        if (dialogoBorrar) {
            dialogoBorrarConductor(
                onDismiss = { dialogoBorrar = false },
                userId = userid,
                viajeId = id_viaje,
                idsolicitud = id_solicitud,
                horarioId = id_horario,
                conductorId = conductor_id,
                navController = navController
            )
        }


    }

}


