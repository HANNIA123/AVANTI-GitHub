package com.example.curdfirestore.Horario.Pantallas

import android.annotation.SuppressLint

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import com.example.avanti.MarkerItiData
import com.example.avanti.ParadaData
import com.example.curdfirestore.Horario.ConsultasHorario.conObtenerHorarioId
import com.example.curdfirestore.R
import com.example.curdfirestore.Usuario.Pasajero.cabeceraConMenuPas
import com.example.curdfirestore.Usuario.Pasajero.menuDesplegablePas

import com.example.curdfirestore.Viaje.Funciones.calculateDistance
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.Viaje.Funciones.convertirTrayecto
import com.example.curdfirestore.Viaje.Funciones.getDirections
import com.example.curdfirestore.Viaje.Pantallas.MapViewContainer

import com.example.curdfirestore.lineaCargando

import com.example.curdfirestore.textoNegro

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap

import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun verMapaViajePasajeroSinPar(
    navController: NavController,
    correo: String,
    pantalla: String,
    horarioId: String

) {
    var maxh by remember {
        mutableStateOf(0.dp)
    }
    var boton by remember {
        mutableStateOf(false)
    }


    BoxWithConstraints {
        maxh = this.maxHeight
    }

    val horarioData= conObtenerHorarioId(horarioId = horarioId)

    val paradas by remember {
        mutableStateOf<List<ParadaData>>(emptyList())
    }
    //Para la ventana de carga
    var isLoading by remember { mutableStateOf(true) }

//Agregados
    val filterviajes by remember { mutableStateOf<List<MarkerItiData>?>(null) }

    val listaActual = filterviajes?.toMutableList() ?: mutableListOf()
    val paradasPorMarcador = mutableMapOf<String, MarkerItiData>()

    var infparadas by remember { mutableStateOf<MarkerItiData?>(null) }
    var show by rememberSaveable { mutableStateOf(false) }


    //Para el menú de opciones de viaje
    var expanded by remember { mutableStateOf(false) }

    var showEliminar by rememberSaveable { mutableStateOf(false) }

    if (isLoading) {
        lineaCargando(text = "Cargando mapa...")
    }
    horarioData?.let {

        //Convertir String a coordenadas  -- origen

        var markerLatO by remember { mutableStateOf(0.0) }
        var markerLonO by remember { mutableStateOf(0.0) }

        val markerCoordenadasLatLngO = convertirStringALatLng(horarioData.horario_origen)

        if (markerCoordenadasLatLngO != null) {
            markerLatO = markerCoordenadasLatLngO.latitude
            markerLonO = markerCoordenadasLatLngO.longitude
            }

//Destino
        var markerLatD by remember { mutableStateOf(0.0) }
        var markerLonD by remember { mutableStateOf(0.0) }
        val markerCoordenadasLatLngD = convertirStringALatLng(horarioData.horario_destino)

        if (markerCoordenadasLatLngD != null) {
            markerLatD = markerCoordenadasLatLngD.latitude
            markerLonD = markerCoordenadasLatLngD.longitude
         }
        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxh)
            ) {
                cabeceraConMenuPas(
                    titulo = "Ver horario",
                    navController,
                    correo,
                    boton = { estaBoton ->
                        boton = estaBoton
                    })

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(maxh - 145.dp)
                ) {
                    val origen = LatLng(markerLatO, markerLonO)
                    val destino = LatLng(markerLatD, markerLonD)

                    val nOrigen = MarkerItiData(
                        marker_ubicacion = origen,
                        marker_titulo = "Origen",
                        marker_id = horarioId
                    )
                    listaActual.add(nOrigen)
                    val nDestino = MarkerItiData(
                        marker_ubicacion = destino,
                        marker_titulo = "Destino",
                        marker_id = horarioId
                    )
                    listaActual.add(nDestino)

                    var markerPositions = listOf(
                        origen, // Marker 1
                        destino, // Marker 2

                    )
                    var paradasPositions = listOf<LatLng>()
                    var titlesPositions = listOf(
                        "Origen", "Destino"
                    )
                    var horaPositions = listOf(
                        horarioData.horario_destino,
                        horarioData.horario_origen,
                    )

                    var nparadas = paradas.sortedBy { it.par_hora }


                    for (parada in nparadas) {
                        var markerLat by remember { mutableStateOf(0.0) }
                        var markerLon by remember { mutableStateOf(0.0) }
                        val markerCoordenadasLatLng = convertirStringALatLng(parada.par_ubicacion)

                        if (markerCoordenadasLatLng != null) {
                            markerLat = markerCoordenadasLatLng.latitude
                            markerLon = markerCoordenadasLatLng.longitude
                            // Hacer algo con las coordenadas LatLng
                            // println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
                        }
                        val ubiParada = LatLng(markerLat, markerLon)
                        val nParada = MarkerItiData(
                            marker_ubicacion = ubiParada,
                            marker_hora = parada.par_hora,
                            marker_titulo = parada.par_nombre,
                            marker_id = parada.par_id
                        )

                        listaActual.add(nParada)
                        markerPositions = markerPositions + ubiParada
                        paradasPositions = paradasPositions + ubiParada
                        titlesPositions = titlesPositions + parada.par_nombre
                        horaPositions = horaPositions + parada.par_hora
                    }
                    val context = LocalContext.current
                    // Antes de cargar el mapa, muestra la ventana de carga



                    MapViewContainer { googleMap: GoogleMap ->
                        // Habilita los controles de zoom
                        googleMap.uiSettings.isZoomControlsEnabled = true
                        // Agrega los marcadores

                        for (newMarker in listaActual) {

                            var imageName: String
                            /*Diferente marker para el origen o detsino y las paradas*/

                            if (newMarker.marker_titulo == "Origen"
                                || newMarker.marker_titulo == "Destino"
                            ) {

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
                            val bitmapDescriptor = BitmapDescriptorFactory.fromResource(image)
                            val markerOptions = MarkerOptions().position(newMarker.marker_ubicacion)
                            markerOptions.icon(bitmapDescriptor)
                            val marker = googleMap.addMarker(markerOptions)
                            paradasPorMarcador[marker!!.id] = newMarker
                            // Agregar un evento de clic al marcador
                            googleMap.setOnMarkerClickListener { marker ->

                                // Aquí puedes manejar el evento de clic en el marcador
                                //Toast.makeText(context, "Has hecho clic en el marcador: ${marker.title}", Toast.LENGTH_SHORT).show()
                                val paradaSeleccionada = paradasPorMarcador[marker.id]
                                if (paradaSeleccionada != null) {
                                    infparadas = paradaSeleccionada
                                    show = true
                                }
                                true

                            }
                        }

                        // Obtiene y agrega la ruta entre los marcadores
                        GlobalScope.launch(Dispatchers.Main) {
                            val apiKey = "AIzaSyAZmpsa99qsen70ktUWCSDbmEChisRMdlQ"
                            // Calcula las distancias entre los puntos y ordénalos
                            val sortedPositions = paradasPositions.sortedBy { point ->
                                calculateDistance(
                                    "${origen.latitude},${origen.longitude}",
                                    "${point.latitude},${point.longitude}",
                                    apiKey
                                )
                            }

                            val waypointStrings =
                                sortedPositions.map { "${it.latitude},${it.longitude}" }
                            // Concatena las cadenas de texto con '|' como separador
                            val waypointsString = waypointStrings.joinToString("|")
                            // Llama a la función getDirections con la lista de waypoints
                            val directions = getDirections(
                                origin = "${origen.latitude},${origen.longitude}",
                                destination = "${destino.latitude},${destino.longitude}",
                                waypointsString, apiKey
                            )

                            // Agrega la ruta
                            val polylineOptions = PolylineOptions().addAll(directions)
                            googleMap.addPolyline(polylineOptions)


                            val cameraUpdate =
                                CameraUpdateFactory.newLatLngZoom(markerPositions[0], 14f)
                            // googleMap.moveCamera(cameraUpdate)
                            googleMap.animateCamera(cameraUpdate)
                            isLoading = false

                        }

                    }

                    if (show) {
                        ventanaMarkerItinerarioPas(
                            infparadas!!,
                            show,
                            null,
                            horarioData.horario_trayecto,
                            { show = false }
                        )
                    }



                    menuHorarioOpcionesSin(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        offset = (-48).dp,
                        onOption1Click = {
                            navController.navigate("ver_paradas_pasajero/$correo/$horarioId")

                        },
                        onOption2Click = {
                            showEliminar = true
                        },
                    )


                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color(126, 60, 127))
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp)
                        .padding(10.dp, 3.dp),
                    horizontalArrangement = Arrangement.SpaceBetween, // Espacio entre los elementos en la fila
                    verticalAlignment = Alignment.CenterVertically // Alineación vertical de los elementos en la fila
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,

                        ) {

                        val trayecto = convertirTrayecto(horarioData.horario_trayecto)
                        textoNegro(Texto = horarioData.horario_dia, tamTexto = 16f)
                        textoNegro(Texto = trayecto, tamTexto = 16f)
                        if(horarioData.horario_trayecto == "0"){
                            textoNegro(Texto = "Salida de UPIITA: " + horarioData.horario_hora + " hrs", tamTexto = 16f)
                        }else{
                            textoNegro(Texto = "Llegada a UPIITA: " + horarioData.horario_hora + " hrs", tamTexto = 16f)
                        }



                    }



                    IconButton(
                        onClick = {
                            expanded = true
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            painter = painterResource(id = R.drawable.opcionesviaje),
                            contentDescription = "Icono opciones",
                            tint = Color(137, 13, 88)
                        )
                    }
                }

            }
            if (boton) {
                menuDesplegablePas(
                    onDismiss = { boton = false },
                    navController,
                    userID = correo
                )
            }


            if (showEliminar) {
                dialogoConfirmarEliminarHorarioP(
                    onDismiss = { showEliminar = false },
                    horarioId, correo,
                    navController
                )
            }
        }


    }


}
