package com.example.curdfirestore.Horario.Pantallas.Monitoreo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.SolicitudData
import com.example.avanti.ui.theme.Aplicacion.lineaGrisModificada
import com.example.curdfirestore.Horario.ConsultasHorario.actualizarHorarioPas
import com.example.curdfirestore.Horario.ConsultasHorario.conObtenerHorarioId
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParada
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParadaPorViaje
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerListaParadasRT
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerParadaId
import com.example.curdfirestore.R
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitud
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesPorViaje
import com.example.curdfirestore.Usuario.Conductor.Pantallas.dialogoNoIniciarViaje
import com.example.curdfirestore.Usuario.Conductor.cabeceraConMenuCon
import com.example.curdfirestore.Usuario.Conductor.menuDesplegableCon
import com.example.curdfirestore.Usuario.Pasajero.cabeceraConMenuPas
import com.example.curdfirestore.Usuario.Pasajero.menuDesplegablePas
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeRT
import com.example.curdfirestore.Viaje.ConsultasViaje.editarCampoViajeSinRuta
import com.example.curdfirestore.Viaje.Funciones.UbicacionRealTime
import com.example.curdfirestore.Viaje.Funciones.accionesComienzoViaje
import com.example.curdfirestore.Viaje.Funciones.accionesTerminoViaje
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.Viaje.Funciones.registrarNotificacionViaje
import com.example.curdfirestore.Viaje.Funciones.registrarNotificacionViajePas
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
fun verUbicacionMonitoreo(
    userId: String,
    viajeId: String,
    solicitudId: String,
    horarioId: String,
    paradaId: String,
    navController: NavController
) {
    var botonNotificacionParada by remember {
        mutableStateOf(false)
    }
    var validar by remember {
        mutableStateOf(false)
    }
    var verInformacionViaje by remember {
        mutableStateOf(false)
    }
    val referencia = Firebase.database.getReference("ubicacion").child(viajeId)
    var latLng by remember { mutableStateOf(LatLng(0.0, 0.0)) } // Coordenadas de San Francisco

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


    val listaParadasCom = conObtenerListaParadasRT(viajeId = viajeId)
    val infViaje = conObtenerViajeRT(viajeId = viajeId)

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
        maxw = this.maxWidth
    }

    //Paradas en el mapa y control de ellas
    var boton by remember {
        mutableStateOf(false)
    }

    var cargando by remember {
        mutableStateOf(false)
    }


    val infHorario = conObtenerHorarioId(horarioId = horarioId)
    var solicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }

    val solicitud = conObtenerSolicitud(horarioId = horarioId)

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

            cabeceraConMenuPas(
                titulo = "Tu viaje ha comenzado",
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
                }

            }

            val scope = rememberCoroutineScope()
            var currentLatLng by remember { mutableStateOf(latLng) }


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


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxh - 175.dp)
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
                            delay(8000)
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
                        title = "Conductor",

                        icon = BitmapDescriptorFactory.fromResource(R.drawable.autooficial),
                    )



                    infHorario?.let {
                        val origenLatLng = convertirStringALatLng(infHorario.horario_origen)
                        val destinoLatLng = convertirStringALatLng(infHorario.horario_destino)
                        if (origenLatLng != null) {
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        origenLatLng.latitude,
                                        origenLatLng.longitude
                                    )
                                ),
                                title = "Punto de origen",

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

                                icon = BitmapDescriptorFactory.fromResource(R.drawable.origendestino),
                            )
                        }
                    }
                }


                //boton flotante ver imprevistos
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
                            backgroundColor = Color(180, 13, 13)
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


            //Boton de llegada
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.White),
                horizontalArrangement = Arrangement.SpaceBetween, // Distribuye los elementos de forma equitativa en el eje horizontal
                verticalAlignment = Alignment.CenterVertically // Alin

            ) {

                val parada = conObtenerParadaId(paradaId = paradaId)
                parada?.let {
                    if (parada.par_llegada_pas != "si") {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    137,
                                    13,
                                    88
                                )
                            ),
                            onClick = {
                                botonNotificacionParada = true
                                actualizarCampoParada(paradaId, "par_llegada_pas", "si")

                            },
                            modifier = Modifier
                                .height(54.dp)
                                .padding(5.dp)
                                .weight(0.6f) // Ocupa el 80% del ancho disponible
                        ) {
                            Text(
                                text = "Llegué a la parada",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            )
                        }
                    } else {
                        //Verificar la validación, si ya es exitosa, entonces...
                        Box (modifier = Modifier.padding(5.dp)){
                            Text(
                                text = "Viaje en progreso...",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color(
                                        137,
                                        13,
                                        88
                                    ),
                                    )
                            )
                        }

                    }

                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {
                        verInformacionViaje = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(137, 13, 88)
                    ),
                    modifier = Modifier
                        .height(54.dp)
                        .padding(5.dp)

                ) {

                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.White
                    )

                }


                if (botonNotificacionParada) {
                    solicitud?.let {
                        registrarNotificacionViajePas(
                            tipoNot = "llp",
                            solicitud,
                            userId,
                            viajeId
                        )
                    }
                    botonNotificacionParada = false
                }


            }


        }
    }

    if (boton) {
        menuDesplegablePas(
            onDismiss = { boton = false },
            navController,
            userID = userId
        )
    }

    if (verInformacionViaje) {
        solicitud?.let {
            dialogoVerInfViajeIniciado(
                onDismiss = { verInformacionViaje = false },
                solicitud
            )
        }

    }
    if (!cargando) {
        lineaCargando(text = "Cargando Mapa....")
    }

}




