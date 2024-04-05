package com.example.curdfirestore.Parada.Pantallas

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.HorarioData
import com.example.avanti.ParadaData
import com.example.avanti.ViajeDataReturn
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.Viaje.Pantallas.MapViewContainer
import com.example.curdfirestore.textInMarker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

/*Este es el mapa donde se muestran las paradas cercanas de acuerdo al
* horario filtrado del pasajero, el marker abre una ventana (VentanaSolicitudesPasa)

* */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun verParadasCercanasPas(
    navController: NavController,
    correo: String,
    viajeData: List<ViajeDataReturn>,
    paradas: List<ParadaData>,
    horarioId: String,
    horario: HorarioData
) {
    var maxh= 0.dp

    //Convertir String a coordenadas  -- origen
    var previa by rememberSaveable { mutableStateOf(true) }


    if (previa) {
       /* VentanaPreviaParadas(
            previa,
            { previa = false },
            {})
*/

    }

    var markerLatO by remember { mutableStateOf(0.0) }
    var markerLonO by remember { mutableStateOf(0.0) }
    var infparadas by remember { mutableStateOf<ParadaData?>(null) }

    val markerCoordenadasLatLngO = convertirStringALatLng(horario.horario_origen)

    if (markerCoordenadasLatLngO != null) {
        markerLatO = markerCoordenadasLatLngO.latitude
        markerLonO = markerCoordenadasLatLngO.longitude
        // Hacer algo con las coordenadas LatLng
        println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
    } else {
        // La conversión falló
        println("Error al convertir la cadena a LatLng")
    }

//Destino
    var markerLatD by remember { mutableStateOf(0.0) }
    var markerLonD by remember { mutableStateOf(0.0) }

    val markerCoordenadasLatLngD = convertirStringALatLng(horario.horario_destino)

    if (markerCoordenadasLatLngD != null) {
        markerLatD = markerCoordenadasLatLngD.latitude
        markerLonD = markerCoordenadasLatLngD.longitude
        // Hacer algo con las coordenadas LatLng
        //  println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
    } else {
        // La conversión falló
        println("Error al convertir la cadena a LatLng")
    }

//Lista de los markers
    val latLngs = mutableListOf<LatLng>()
    val paradasPorMarcador = mutableMapOf<String, ParadaData>()

    BoxWithConstraints {
        maxh = this.maxHeight
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxh)
        ) {
            val origen = LatLng(markerLatO, markerLonO)
            val destino = LatLng(markerLatD, markerLonD)

            var markerPositions = listOf(
                origen, // Marker 1
                destino, // Marker 2

            )

            var paradasPositions = listOf<LatLng>()
            // Variable para almacenar la última ubicación de la cámara
            var ultimaUbicacionCamera: LatLng? = null

            for (parada in paradas) {

                var markerLat by remember { mutableStateOf(0.0) }
                var markerLon by remember { mutableStateOf(0.0) }

                val markerCoordenadasLatLng = convertirStringALatLng(parada.par_ubicacion)

                if (markerCoordenadasLatLng != null) {
                    markerLat = markerCoordenadasLatLng.latitude
                    markerLon = markerCoordenadasLatLng.longitude
                    // Hacer algo con las coordenadas LatLng
                    // println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
                } else {
                    // La conversión falló
                    println("Error al convertir la cadena a LatLng")
                }
                val ubiParada = LatLng(markerLat, markerLon)
                //  markerPositions = markerPositions + ubiParada
                paradasPositions = paradasPositions + ubiParada

            }
            val miUbic = LatLng(markerLatO, markerLonO)
            val markerState = rememberMarkerState(position = miUbic)
            var cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(markerState.position, 17f)
            }
            var paradasClickeadas by remember { mutableStateOf(emptyList<ParadaData>()) }
            //Mapa
            var show by rememberSaveable { mutableStateOf(false) }
            var markerLat by remember { mutableStateOf(0.0) }
            var markerLon by remember { mutableStateOf(0.0) }

            var newParadas=paradas
            if (horario.horario_trayecto=="0"){
                val nParada= ParadaData(
                    par_ubicacion = horario.horario_destino,
                    par_nombre = "Destino",
                    par_hora = horario.horario_hora
                )
                newParadas=newParadas+nParada
            }
            if (horario.horario_trayecto=="1"){
                val nParada= ParadaData(
                    par_ubicacion = horario.horario_origen,
                    par_nombre = "Origen",
                    par_hora = horario.horario_hora
                )
                newParadas=newParadas+nParada
            }

            val context = LocalContext.current
            MapViewContainer { googleMap: GoogleMap ->
                // Habilita los controles de zoom
                // googleMap.uiSettings.isZoomControlsEnabled = true
                // Agrega los marcadores
                for (parada in newParadas) {
                    val markerCoordenadasLatLng = convertirStringALatLng(parada.par_ubicacion)
                    if (markerCoordenadasLatLng != null) {
                        markerLat = markerCoordenadasLatLng.latitude
                        markerLon = markerCoordenadasLatLng.longitude
                        // Hacer algo con las coordenadas LatLng
                        // println("Latitud: ${markerCoordenadasLatLngO.latitude}, Longitud: ${markerCoordenadasLatLngO.longitude}")
                    } else {
                        println("Error al convertir la cadena a LatLng")
                    }

                    var imageName:String
                    /*Diferente marker para el origen o detsino y las paradas*/

                    if (parada.par_nombre=="Origen" || parada.par_nombre=="Destino"){

                        imageName="origendestino"
                    }
                    else{
                        imageName="paradapas"
                    }
                    val image = context.resources.getIdentifier(imageName, "drawable", context.packageName)
                    val bitmapDescriptor = BitmapDescriptorFactory.fromResource(image)

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
                        // Por ejemplo, puedes mostrar un mensaje Toast con el título del marcador
                        //Toast.makeText(context, "Has hecho clic en el marcador: ${marker.title}", Toast.LENGTH_SHORT).show()
                        val paradaSeleccionada = paradasPorMarcador[marker.id]
                        if (paradaSeleccionada != null) {
                            infparadas = paradaSeleccionada
                            show = true
                        }


                        true

                    }


                }

                ultimaUbicacionCamera?.let { ultimaUbicacion ->
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(ultimaUbicacion, 13.0f)
                    googleMap.animateCamera(cameraUpdate)
                }

            }
            if (show) {
               /* VentanaMarker(
                    navController,
                    correo,
                    infparadas!!,
                    horario!!,
                    horarioId,
                    show,
                    { show = false },
                    {})

                */
            }

            Row(verticalAlignment = Alignment.Top) {
//Boton
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(15.dp)
                        .offset(x = 20.dp, y = 25.dp)

                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("home_viaje_pasajero/$correo")
                        },
                        modifier = Modifier
                            .size(25.dp) // Tamaño del botón
                            .background(Color(137, 13, 88), shape = CircleShape)

                    ) {
                        Icon(
                            modifier = Modifier
                                .size(23.dp),
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Icono Cerrar",
                            tint = Color.White

                        )


                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp, 15.dp, 30.dp, 5.dp)
                        .border(1.dp, Color.LightGray)
                        .background(Color.White),

                    ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.Center
                    ) {
                        val latLng = LatLng(37.7749, -122.4194)
                        var newadress = ""
                        Text(
                            text = "Información sobre tu viaje",
                            modifier = Modifier.padding(2.dp),
                            style = TextStyle(
                                Color(137, 13, 88),
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Center

                        )


                        textInMarker(Label = "Dia: ", Text = horario.horario_dia)

                        if (horario.horario_trayecto == "0") {
                            val address = convertCoordinatesToAddress(destino)
                            newadress = address
                            // println("Direccón origen $address") // Imprime la dirección en la consola

                            textInMarker(Label = "Origen: ", Text = "UPIITA ")
                            textInMarker(Label = "Destino: ", Text = "$address")
                            textInMarker(
                                Label = "Horario de salida: ",
                                Text = "${horario.horario_hora} hrs"
                            )

                        } else {
                            val address = convertCoordinatesToAddress(origen)
                            newadress = address
                            // println("Direccón origen $address") // Imprime la dirección en la consola
                            textInMarker(Label = "Origen: ", Text = "$address")
                            textInMarker(Label = "Destino: ", Text = "UPIITA")
                            textInMarker(
                                Label = "Horario de llegada: ",
                                Text = "${horario.horario_hora} hrs"
                            )

                        }


                    }


                }

            }

        }
    }

}
