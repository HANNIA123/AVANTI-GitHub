package com.example.curdfirestore.Horario.Pantallas.Monitoreo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.SolicitudData
import com.example.curdfirestore.Horario.ConsultasHorario.actualizarHorarioPas
import com.example.curdfirestore.Horario.ConsultasHorario.conObtenerHorarioId
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParada
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParadaPorViaje
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerListaParadasRT
import com.example.curdfirestore.R
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesPorViaje
import com.example.curdfirestore.Usuario.Conductor.cabeceraConMenuCon
import com.example.curdfirestore.Usuario.Conductor.menuDesplegableCon
import com.example.curdfirestore.Usuario.Pasajero.cabeceraConMenuPas
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
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
fun verUbicacionMonitoreo(
    userId: String,
    viajeId: String,
    solicitudId: String,
    horarioId: String,
    navController: NavController
) {
    var validar by remember {
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


    var maxh by remember {
        mutableStateOf(0.dp)
    }

    BoxWithConstraints {
        maxh = this.maxHeight - 50.dp
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

    conObtenerSolicitudesPorViaje(viajeId, "Aceptada") { resultado ->
        solicitudes = resultado
    }



    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxh)

        ) {

            cabeceraConMenuPas(
                titulo = "Tu viaje ha comenzado",
                navController,
                userId,
                boton = { estaBoton ->
                    boton = estaBoton
                })


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
                    .height(maxh - 140.dp)
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
                }
            }


            //Boton de llegada
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(10.dp, 0.dp),
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


                    },
                    modifier = Modifier.width(180.dp)
                ) {
                    Text(
                        text = "Llegué a la parada",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
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




