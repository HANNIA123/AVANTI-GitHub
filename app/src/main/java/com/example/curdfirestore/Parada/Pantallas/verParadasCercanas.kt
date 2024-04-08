package com.example.curdfirestore.Parada.Pantallas

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.HorarioData
import com.example.avanti.ParadaData
import com.example.avanti.ViajeData
import com.example.avanti.ViajeDataReturn
import com.example.avanti.ui.theme.Aplicacion.obtenerNombreDiaEnEspanol
import com.example.curdfirestore.Horario.ConsultasHorario.conBuscarViajePas
import com.example.curdfirestore.Horario.ConsultasHorario.conObtenerHorarioId
import com.example.curdfirestore.Horario.Pantallas.textInfHorario
import com.example.curdfirestore.Parada.ConsultasParada.conBuscarParadasPas
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerListaParadas
import com.example.curdfirestore.Parada.Funciones.obtenerDistanciaParadas
import com.example.curdfirestore.R
import com.example.curdfirestore.Solicitud.ventanaEnviarSolicitud
import com.example.curdfirestore.Usuario.Conductor.Pantallas.viajesInicio
import com.example.curdfirestore.Usuario.Conductor.cabeceraConMenuCon
import com.example.curdfirestore.Usuario.Pasajero.cabeceraConMenuPas
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.Viaje.Funciones.convertirTrayecto
import com.example.curdfirestore.Viaje.Pantallas.MapViewContainer
import com.example.curdfirestore.Viaje.Pantallas.verItinerarioCon
import com.example.curdfirestore.textInMarker
import com.example.curdfirestore.textoGris
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/*Este es el mapa donde se muestran las paradas cercanas de acuerdo al
* horario filtrado del pasajero, el marker abre una ventana (VentanaSolicitudesPasa)

* */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun verParadasCercanasPas(
    navController: NavController,
    correo: String,
    horarioId: String,
    paradas: List<ParadaData>

) {

    var maxh by remember {
        mutableStateOf(0.dp)
    }


    BoxWithConstraints {
        maxh = this.maxHeight
    }


    var infparadas by remember { mutableStateOf<ParadaData?>(null) }

    var show by rememberSaveable { mutableStateOf(false) }

    var boton by remember {
        mutableStateOf(false)
    }
    val horario = conObtenerHorarioId(horarioId = horarioId)


    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxh)
        ) {
            cabeceraConMenuPas(
                titulo = "Ver paradas",
                navController,
                correo,
                boton = { estaBoton ->
                    boton = estaBoton
                })

            horario?.let {
                        var markerLatO by remember { mutableStateOf(0.0) }
                        var markerLonO by remember { mutableStateOf(0.0) }

                        val markerCoordenadasLatLngO =
                            convertirStringALatLng(horario.horario_origen)
                        //Origen
                        markerCoordenadasLatLngO?.let {
                            markerLatO = it.latitude
                            markerLonO = it.longitude
                        }

//Destino
                        var markerLatD by remember { mutableStateOf(0.0) }
                        var markerLonD by remember { mutableStateOf(0.0) }

                        val markerCoordenadasLatLngD =
                            convertirStringALatLng(horario.horario_destino)
                        markerCoordenadasLatLngD?.let {
                            markerLatD = it.latitude
                            markerLonD = it.longitude
                        }


//Lista de los markers

                        val paradasPorMarcador = mutableMapOf<String, ParadaData>()
                        val origen = LatLng(markerLatO, markerLonO)
                        val destino = LatLng(markerLatD, markerLonD)
                        val altura = 200.dp

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(maxh - altura) //70 del menu
                        ) {

                            var paradasPositions = listOf<LatLng>()
                            // Variable para almacenar la última ubicación de la cámara
                            var ultimaUbicacionCamera: LatLng? = null

                            for (parada in paradas) {

                                var markerLat by remember { mutableStateOf(0.0) }
                                var markerLon by remember { mutableStateOf(0.0) }
                                val markerCoordenadasLatLng =
                                    convertirStringALatLng(parada.par_ubicacion)

                                markerCoordenadasLatLng?.let {
                                    markerLat = it.latitude
                                    markerLon = it.longitude
                                }

                                val ubiParada = LatLng(markerLat, markerLon)
                                paradasPositions = paradasPositions + ubiParada

                            }

                            //Mapa
                            var markerLat by remember { mutableStateOf(0.0) }
                            var markerLon by remember { mutableStateOf(0.0) }

                            var newParadas = paradas
                            if (horario.horario_trayecto == "0") {
                                val nParada = ParadaData(
                                    par_ubicacion = horario.horario_destino,
                                    par_nombre = "Destino",
                                    par_hora = horario.horario_hora
                                )
                                newParadas = newParadas + nParada
                            }
                            if (horario.horario_trayecto == "1") {
                                val nParada = ParadaData(
                                    par_ubicacion = horario.horario_origen,
                                    par_nombre = "Origen",
                                    par_hora = horario.horario_hora
                                )
                                newParadas = newParadas + nParada
                            }

                            val context = LocalContext.current
                            MapViewContainer { googleMap: GoogleMap ->
                                // Habilita los controles de zoom
                                googleMap.uiSettings.isZoomControlsEnabled = true
                                // Agrega los marcadores
                                for (parada in newParadas) {
                                    val markerCoordenadasLatLng =
                                        convertirStringALatLng(parada.par_ubicacion)
                                    if (markerCoordenadasLatLng != null) {
                                        markerLat = markerCoordenadasLatLng.latitude
                                        markerLon = markerCoordenadasLatLng.longitude
                                        // Hacer algo con las coordenadas LatLng
                                        // println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
                                    } else {
                                        println("Error al convertir la cadena a LatLng")
                                    }

                                    var imageName: String
                                    /*Diferente marker para el origen o detsino y las paradas*/

                                    if (parada.par_nombre == "Origen" || parada.par_nombre == "Destino") {

                                        imageName = "origendestino"
                                    } else {
                                        imageName = "paradapas"
                                    }
                                    val image =
                                        context.resources.getIdentifier(
                                            imageName,
                                            "drawable",
                                            context.packageName
                                        )
                                    val bitmapDescriptor =
                                        BitmapDescriptorFactory.fromResource(image)

                                    val ubiParada = LatLng(markerLat, markerLon)
                                    val markerOptions = MarkerOptions().position(ubiParada)
                                    markerOptions.icon(bitmapDescriptor)

                                    // markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.paradas))
                                    val marker = googleMap.addMarker(markerOptions)
                                    paradasPorMarcador[marker!!.id] = parada
                                    // Actualiza la última ubicación de la cámara con la primera parada
                                    ultimaUbicacionCamera = ubiParada

                                    // Agregar un evento de clic al marcador
                                    googleMap.setOnMarkerClickListener { marker ->
                                        // Aquí puedes manejar el evento de clic en el marcador
                                        val paradaSeleccionada = paradasPorMarcador[marker.id]
                                        if (paradaSeleccionada != null) {
                                            infparadas = paradaSeleccionada
                                            show = true
                                        }
                                        true
                                    }
                                }

                                ultimaUbicacionCamera?.let { ultimaUbicacion ->
                                    val cameraUpdate =
                                        CameraUpdateFactory.newLatLngZoom(
                                            ultimaUbicacion,
                                            16.0f
                                        )
                                    googleMap.animateCamera(cameraUpdate)
                                }

                            }


                        }

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Color(126, 60, 127))
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(altura)
                                .padding(10.dp, 0.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start,
                        ) {
                            textInfHorario(
                                horario = horario,
                                origen = origen,
                                destino = destino
                            )
                        }




            }
        }



        if (show) {
            ventanaEnviarSolicitud(
                navController,
                correo,
                infparadas!!,
                horario!!,
                horarioId,
                show,
                { show = false },
                {})


        }
    }

}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ItinerarioView() {

    //verItinerarioCon(navController = navController, userId = "hannia")
}

