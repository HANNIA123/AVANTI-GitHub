package com.example.curdfirestore.Viaje.Pantallas.Monitoreo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.NoticacionData
import com.example.avanti.SolicitudData
import com.example.avanti.ui.theme.Aplicacion.lineaGrisModificada
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaFormatoddmmyyyy
import com.example.avanti.ui.theme.Aplicacion.obtenerHoraActual
import com.example.curdfirestore.Horario.ConsultasHorario.actualizarHorarioPas
import com.example.curdfirestore.Notificaciones.Consultas.conRegistrarNotificacion
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParada
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParadaPorViaje
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerListaParadasRT
import com.example.curdfirestore.R
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.actualizarCampoSolicitud
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesPorViaje
import com.example.curdfirestore.Usuario.Conductor.cabeceraConMenuCon
import com.example.curdfirestore.Usuario.Conductor.menuDesplegableCon
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeRT
import com.example.curdfirestore.Viaje.ConsultasViaje.editarCampoViajeSinRuta
import com.example.curdfirestore.Viaje.Funciones.UbicacionRealTime
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.lineaCargando
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun obtenerCoordenadas(
    userId: String,
    viajeId: String,
    navController: NavController
) {
    var maxh by remember {
        mutableStateOf(0.dp)
    }
    var maxw by remember {
        mutableStateOf(0.dp)
    }

    BoxWithConstraints {
        maxh = this.maxHeight
    }
    BoxWithConstraints {
        maxw=this.maxWidth
    }

    val context = LocalContext.current
    val newUserId = userId.substringBefore('@')
    var validar by remember {
        mutableStateOf(false)
    }
    var empezarUbicacion by remember {
        mutableStateOf(false)
    }

    if(empezarUbicacion) {
        UbicacionRealTime(context, viajeId)
    }
        val referencia = Firebase.database.getReference("ubicacion").child(viajeId)


    var latLng by remember { mutableStateOf(LatLng(0.0, 0.0)) } // Coordenadas de San Francisco
    var markerOptions by remember {
        mutableStateOf(
            MarkerOptions().position(latLng).title("San Francisco")
        )
    }


    //Consulta a la BD
    var datosAnteriores: Map<String, Any>? = null

    referencia.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Obtén los nuevos datos
            val nuevosDatos: Map<String, Any>? = dataSnapshot.value as? Map<String, Any>

            // Compara los nuevos datos con los datos anteriores
            if (nuevosDatos != null && nuevosDatos != datosAnteriores) {
                // Los datos han cambiado, actúa en consecuencia
                val latitud = nuevosDatos["latitud"] as Double
                val longitud = nuevosDatos["longitud"] as Double
                latLng = LatLng(latitud, longitud)

                markerOptions = MarkerOptions().position(latLng).title("San Francisco")
                // Haz lo que necesites con latitud y longitud
                validar = true

                // Actualiza los datos anteriores
                datosAnteriores = nuevosDatos

            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Manejar errores, si es necesario
            println("Error al obtener los datos: ${databaseError.message}")
        }
    })

    //Paradas en el mapa y control de ellas
    var boton by remember {
        mutableStateOf(false)
    }
    var botonNotificacion by remember {
        mutableStateOf(false)
    }
    var ejecutado by remember {
        mutableStateOf(false)
    }
    var notEnviada by remember {
        mutableStateOf(false)
    }

    var cargando by remember {
        mutableStateOf(false)
    }

    var idParadaActual by remember {
        mutableStateOf("")
    }
    var numParadaActual by remember {
        mutableStateOf(0)
    }

    val listaParadasCom = conObtenerListaParadasRT(viajeId = viajeId)
    val infViaje = conObtenerViajeRT(viajeId = viajeId)
    //conObtenerViajeId(viajeId = viajeId)
    var solicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }

    conObtenerSolicitudesPorViaje(viajeId, "Aceptada") { resultado ->
        solicitudes = resultado
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxh)
                .verticalScroll(rememberScrollState())

        ) {

            cabeceraConMenuCon(
                titulo = "Tu viaje",
                navController,
                userId,
                boton = { estaBoton ->
                    boton = estaBoton
                })

            infViaje?.let {
                listaParadasCom?.let {

                    val paradasOrdenadas = listaParadasCom.sortedBy { it.second.par_hora }
                    val totalParadas = paradasOrdenadas.size
                    val listParadasRecorridas =
                        paradasOrdenadas.filter { it.second.par_recorrido == "si" }

                    val viajeComenzado = paradasOrdenadas.filter {
                        it.second.para_viaje_comenzado == "si"
                    }
                    var num by remember {
                        mutableStateOf(listParadasRecorridas.size)
                    }

                    val animationDurationMillis = 2000
                    val scope = rememberCoroutineScope()
                    var currentLatLng by remember { mutableStateOf(latLng) }

                    val animatedLatitude by animateDpAsState(
                        targetValue = currentLatLng.latitude.toDouble().dp,
                        animationSpec = tween(durationMillis = animationDurationMillis), label = ""
                    )
                    val animatedLongitude by animateDpAsState(
                        targetValue = currentLatLng.longitude.toDouble().dp,
                        animationSpec = tween(durationMillis = animationDurationMillis), label = ""
                    )

                    LaunchedEffect(Unit) {
                        while (true) {
                            val newLatitude = latLng.latitude
                            val newLongitude = latLng.longitude
                            scope.launch {
                                currentLatLng = LatLng(newLatitude, newLongitude)
                            }
                            delay(3000)
                        }
                    }



                    val lineH = 3.dp
                    // Variable para almacenar el valor máximo de width

                    val lineaW = maxw / (totalParadas + 2)

                    Column(
                        modifier = Modifier
                            .height(45.dp)
                            .background(Color.White)
                            .padding(10.dp)
                    ) {


                        Text(
                            "Progreso del viaje...", fontSize = 12.sp,
                            color = Color(165, 165, 165)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row() {


                            for (i in 0..(totalParadas + 2)) {
                                val color: Color
                                if (viajeComenzado.isEmpty()) {
                                    println("No ha comenzado")
                                    color = Color(222, 222, 222)
                                } else {
                                    val numeros = listParadasRecorridas.size
                                    if (i <= numeros) {
                                        color = Color.Blue
                                    } else {
                                        color = Color(222, 222, 222)
                                    }
                                }
                                lineaGrisModificada(
                                    width = lineaW,
                                    height = lineH,
                                    color = color
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                            }


                        }
                    }



                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(maxh -195.dp)
                    ) {
                        val cameraPosition = remember {
                            CameraPosition(
                                latLng,
                                16f,
                                0f,
                                0f
                            )
                        }
                        val cameraPositionState = remember { CameraPositionState(cameraPosition) }

                        val zoomLevel = rememberSaveable { mutableStateOf(cameraPosition.zoom) }

                        LaunchedEffect(Unit) {
                            while (true) {
                                if (latLng.latitude != 0.0 && latLng.longitude != 0.0) {
                                    // Solo actualiza la posición de la cámara si latLng no es (0.0, 0.0)
                                    cameraPositionState.position = CameraPosition(
                                        latLng,
                                        zoomLevel.value,
                                        cameraPosition.tilt,
                                        cameraPosition.bearing
                                    )
                                    delay(6000)
                                } else {
                                    delay(500)
                                }
                            }
                        }


                        GoogleMap(
                            modifier = Modifier
                                .fillMaxSize(),
                            cameraPositionState = cameraPositionState,
                            onMapLoaded = {
                                cargando = true
                            },
                            /*cameraPositionState = CameraPositionState(CameraPosition(LatLng(animatedLatitude.value.toDouble(),
                                        animatedLongitude.value.toDouble()
                                    ), 16f, 0f,0f))
                                  */
                        ) {
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        latLng.latitude,
                                        latLng.longitude
                                    )
                                ),
                                title = "Tu ubicación",
                                snippet = "Ubicación: ",
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.autooficial),
                            )

                            paradasOrdenadas.forEach { parada ->
                                val parLatLng = convertirStringALatLng(parada.second.par_ubicacion)
                                if (parLatLng != null) {
                                    Marker(
                                        state = MarkerState(
                                            position = LatLng(
                                                parLatLng.latitude,
                                                parLatLng.longitude
                                            )
                                        ),
                                        title = "Parada ${parada.second.par_nombre}",
                                        snippet = "Ubicación: ",
                                        icon = BitmapDescriptorFactory.fromResource(R.drawable.paradas),
                                    )
                                }


                            }

                                val origenLatLng = convertirStringALatLng(infViaje.viaje_origen)
                                val destinoLatLng = convertirStringALatLng(infViaje.viaje_destino)
                                if (origenLatLng != null) {
                                    Marker(
                                        state = MarkerState(
                                            position = LatLng(
                                                origenLatLng.latitude,
                                                origenLatLng.longitude
                                            )
                                        ),
                                        title = "Punto de origen",
                                        snippet = "Ubicación: ",
                                        icon = BitmapDescriptorFactory.fromResource(R.drawable.origendestino),
                                    )
                                }
                                if (destinoLatLng != null) {
                                    Marker(
                                        state = MarkerState(
                                            position = LatLng(
                                                destinoLatLng.latitude,
                                                destinoLatLng.longitude
                                            )
                                        ),
                                        title = "Punto de llegada",
                                        snippet = "Ubicación: ",
                                        icon = BitmapDescriptorFactory.fromResource(R.drawable.origendestino),
                                    )
                                }

                        }

                        //boton flotante
                        Box(
                            contentAlignment = Alignment.BottomCenter,

                            modifier = Modifier
                                .size(57.dp)
                                .offset(x = 15.dp, y = 12.dp),
                        ) {
                            Button(
                                onClick = {
                                    //Reportar imprevisto
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(137, 13, 88)
                                ),
                                shape = CircleShape, // Cambiado a CircleShape para hacer el botón redondo
                                contentPadding = ButtonDefaults.ContentPadding,
                                elevation = ButtonDefaults.elevation(defaultElevation = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier.size(48.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    androidx.compose.material.Icon(
                                        Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        }


                    }


                    val textoBoton = if (viajeComenzado.isEmpty()) {
                        "Comenzar viaje"
                    } else if (numParadaActual < totalParadas - 1) {
                        "Llegué a la parada"
                    } else {
                        "Finalizar viaje"
                    }

                    //Boton de llegada
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(5.dp, 0.dp),
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
                                empezarUbicacion=true
                                num = listParadasRecorridas.size
                                numParadaActual = listParadasRecorridas.size
                                if (viajeComenzado.isEmpty()) {
                                    //Enviar notificaciones de comienzo de viaje

                                    //El conductor comenzo el viaje, enviar notificacion al pasajero
                                    editarCampoViajeSinRuta(
                                        documentId = viajeId,
                                        campo = "viaje_iniciado",
                                        valor = "si"
                                    )
                                    solicitudes?.forEach {
                                        actualizarHorarioPas(
                                            it.horario_id,
                                            "horario_iniciado",
                                            "si"
                                        )
                                        actualizarCampoSolicitud(it.solicitud_id, "solicitud_viaje_iniciado", "si")

                                    }


                                    actualizarCampoParadaPorViaje(
                                        viajeId,
                                        "para_viaje_comenzado",
                                        "si"
                                    )
                                    editarCampoViajeSinRuta(viajeId, "viaje_iniciado", "si")
                                    botonNotificacion=true

                                } else {
                                    println("Parada actual----.--- $numParadaActual y total paradas $totalParadas")
                                    if (numParadaActual < totalParadas) {
                                        idParadaActual = paradasOrdenadas[numParadaActual].first
                                        println("Recorrido")
                                        actualizarCampoParada(idParadaActual, "par_recorrido", "si")
                                    } else {
                                        // Restablecer el status del viaje y las paradas
                                        editarCampoViajeSinRuta(viajeId, "viaje_iniciado", "no")
                                        actualizarCampoParadaPorViaje(
                                            viajeId,
                                            "par_recorrido",
                                            "no"
                                        )
                                        actualizarCampoParadaPorViaje(
                                            viajeId,
                                            "para_viaje_comenzado",
                                            "no"
                                        )
                                        solicitudes?.forEach {
                                            actualizarHorarioPas(
                                                it.horario_id,
                                                "horario_iniciado",
                                                "no"
                                            )
                                            actualizarCampoSolicitud(it.solicitud_id, "solicitud_viaje_iniciado", "no")

                                        }

                                        navController.navigate("homeconductor/$userId")

                                    }

                                }

                            },
                            modifier = Modifier
                                .width(380.dp)
                                .padding(5.dp)
                        ) {
                            Text(
                                text = textoBoton,
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            )
                        }

                        if(botonNotificacion){
                            if(solicitudes!=null){
                                for ((index, solicitud) in solicitudes!!.withIndex()) {
                                    actualizarHorarioPas(solicitud.horario_id, "horario_solicitud", "No")

                                    val notificacionData = NoticacionData(
                                        notificacion_tipo = "vc",
                                        notificacion_usu_origen = userId,
                                        notificacion_usu_destino = solicitud.pasajero_id,
                                        notificacion_id_viaje = viajeId,
                                        notificacion_id_solicitud = solicitud.solicitud_id,
                                        notificacion_fecha = obtenerFechaFormatoddmmyyyy(),
                                        notificacion_hora = obtenerHoraActual(),

                                        )
                                    if (!ejecutado) {
                                        LaunchedEffect(Unit) {
                                            conRegistrarNotificacion(notificacionData) { respuestaExitosa ->
                                                notEnviada = respuestaExitosa
                                            }
                                        }

                                    }
                                    if (index == solicitudes!!.size - 1) {
                                        ejecutado = true
                                    }
                                }

                            }
                            botonNotificacion=false
                        }

                    }

                }
            }
        }
    }

    if (boton) {
        menuDesplegableCon(
            onDismiss = { boton = false },
            navController,
            userID = userId
        )
    }

    if (!cargando) {
        lineaCargando(text = "Cargando Mapa....")
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MyScaffoldContentPreviewMapa() {
    val navController = rememberNavController()
    obtenerCoordenadas(userId = "hannia", viajeId = "viajeId", navController = navController)

}

