package com.example.curdfirestore.Horario.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.HorarioData
import com.example.avanti.ViajeData
import com.example.curdfirestore.Horario.ConsultasHorario.conRegistrarHorario
import com.example.curdfirestore.NivelAplicacion.SearchBar
import com.example.curdfirestore.NivelAplicacion.searchPlaces
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.ConsultasViaje.conRegistrarViaje
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.Viaje.Funciones.obtenerUbicacionInicial
import com.example.curdfirestore.Viaje.Pantallas.cabeceraConBotonCerrarViaje
import com.example.curdfirestore.Viaje.Pantallas.mapaMarkerDestino
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun registrarDestinoPasajero(
    navController: NavController,
    userid: String,
    dia: String,
    horao: String,

) {


    println("userid $userid")
    println("dia $dia")
    println("horao $horao")


    var maxh by remember {
        mutableStateOf(0.dp)
    }

    BoxWithConstraints {
        maxh = this.maxHeight
    }
    var ubicacion by remember {
        mutableStateOf("")
    }

    var latitud by remember {
        mutableStateOf("")
    }
    var longitud by remember {
        mutableStateOf("")
    }
    var pasarlatitud by remember {
        mutableStateOf("")
    }
    var pasarlongitud by remember {
        mutableStateOf("")
    }
    var valorMapa: String by remember { mutableStateOf("barra") } //El que regresa
    var TipoBusqueda: String by remember { mutableStateOf("barra") } //El que paso
    var ubiMarker by remember { mutableStateOf("19.3898164,-99.11023") }
    var ejecutado by remember { mutableStateOf(false) }
    var boton by remember { mutableStateOf(false) }
    var primeraVez by remember {
        mutableStateOf(0)
    }
    var ubicacionpasar by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(maxh)
    ) {
        cabeceraConBotonCerrarHorario("Registrar destino", navController, userid)
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxh - 70.dp)
            ) {
                if (valorMapa == "barra") {
                    var searchResults = remember { mutableStateOf(emptyList<Place>()) }
                    val context = LocalContext.current
                    var searchQuery = remember { mutableStateOf("") }
                    var selectedPlace by remember { mutableStateOf<Place?>(null) }
                    LaunchedEffect(searchQuery.value) {
                        // Lanzar un bloque coroutine para ejecutar la búsqueda de lugares
                        try {
                            val results = withContext(Dispatchers.IO) {
                                searchPlaces(searchQuery.value, context)
                            }
                            searchResults.value = results
                        } catch (e: Exception) {
                            // Manejar cualquier error que pueda ocurrir durante la búsqueda
                            e.printStackTrace()
                        }
                    }
                    println("YA cambiando a barra")
                    println("Variable primer $primeraVez ---------")
                    if (primeraVez == 0) {
                        obtenerUbicacionInicial(
                            navController = navController,
                            userId = userid,
                            onUbicacionObtenida =
                            { nuevaUbicacion ->
                                ubicacion = nuevaUbicacion
                            }
                        )
                    } else {
                        ubicacion = ubiMarker
                    }

                    ubicacionpasar=ubicacion
                    if (ubicacion != "") {
                        val markerCoordenadasLatLng = convertirStringALatLng(ubicacion)
                        var miUbic by remember {
                            mutableStateOf(LatLng(0.0, 0.0))
                        }

                        if (markerCoordenadasLatLng != null) {
                            var markerLat = markerCoordenadasLatLng.latitude
                            var markerLon = markerCoordenadasLatLng.longitude
                            miUbic = LatLng(markerLat, markerLon)
                            // Hacer algo con las coordenadas LatLng
                            println("Latitud: ${markerCoordenadasLatLng.latitude}, Longitud: ${markerCoordenadasLatLng.longitude}")
                        } else {
                            // La conversión falló
                            println("Error al convertir la cadena a LatLng")
                        }


                        //miUbic = LatLng(markerLat, markerLon)

                        var direccion by remember {
                            mutableStateOf("")
                        }
                        //var miUbic = LatLng(19.389816, -99.110234)
                        var markerState = rememberMarkerState(position = miUbic)
                        direccion = convertCoordinatesToAddress(coordenadas = miUbic)
                        var cameraPositionState = rememberCameraPositionState {
                            position = CameraPosition.fromLatLngZoom(markerState.position, 17f)
                        }

                        latitud = markerState.position.latitude.toString()
                        longitud = markerState.position.longitude.toString()

                        selectedPlace?.let { place ->
                            markerState = rememberMarkerState(
                                position = LatLng(
                                    place.latLng.latitude,
                                    place.latLng.longitude
                                )
                            )
                            pasarlatitud = place.latLng.latitude.toString()
                            pasarlongitud = place.latLng.longitude.toString()
                            ubicacionpasar = "$pasarlatitud,$pasarlongitud"

                            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                LatLng(
                                    place.latLng.latitude,
                                    place.latLng.longitude
                                ), 17f
                            )
                            direccion = convertCoordinatesToAddress(
                                LatLng(
                                    place.latLng.latitude,
                                    place.latLng.longitude
                                )
                            )
                        }
                        GoogleMap(
                            modifier = Modifier
                                .fillMaxSize(),
                            cameraPositionState = cameraPositionState
                        ) {
                            if (selectedPlace == null) {
                                Marker(
                                    state = markerState,
                                    title = "Destino",
                                    snippet = "Ubicación: $direccion",
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.marcador),
                                )
                            } else {
                                selectedPlace?.let { place ->
                                    Marker(
                                        state = MarkerState(
                                            position = LatLng(
                                                place.latLng.latitude,
                                                place.latLng.longitude
                                            )
                                        ),
                                        title = "Destino",
                                        snippet = "Ubicación: $direccion",
                                        icon = BitmapDescriptorFactory.fromResource(R.drawable.marcador),
                                    )
                                }
                            }
                        }
                    }
                    val TextoBarra = "¿A dónde vas?"
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                        SearchBar(searchQuery, searchResults.value, { newQuery ->
                            searchQuery.value = newQuery
                        }, { place ->
                            selectedPlace = place
                        },
                            onMapButtonClick = { valorRetornadoDelMapa ->
                                valorMapa = valorRetornadoDelMapa
                            },
                            TipoBusqueda,
                            TextoBarra
                        )
                    }

                } else {
                    var newUbi by remember {
                        mutableStateOf("")
                    }
                    if (pasarlatitud == "" && pasarlongitud == "") {
                        newUbi = ubicacion
                    } else {
                        newUbi = "$pasarlatitud,$pasarlongitud"
                    }

                    ubiMarker = mapaMarkerDestino(ubicacionMarker = "$newUbi")
                    TipoBusqueda = "marker"
                    ubicacionpasar=ubiMarker //Esto

                }
                if (valorMapa == "marker") {
                    //Boton para regresar a la barra
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {

                        Row(
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .border(
                                    1.dp,
                                    Color.LightGray
                                )
                                .padding(8.dp)
                                .clickable
                                {
                                    valorMapa = "barra"
                                    primeraVez = primeraVez + 1
                                    TipoBusqueda = "barra"
                                }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = null,
                                tint = Color(104, 104, 104),
                                modifier = Modifier.padding(5.dp)

                            )
                            Text(
                                text = "Buscar por dirección",
                                style = TextStyle(
                                    color = Color(104, 104, 104),
                                    fontSize = 14.sp
                                ),
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                }

                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp)
                    ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(137, 13, 88),
                    ),
                    onClick = {
                        var ubicacionF = "$pasarlatitud,$pasarlongitud"
                        boton = true
                    }) {
                    Text(text = "Siguiente",
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }





            }
        }

    }
    if (boton == true && ejecutado == false) {
        var comPantalla="muestra"
        val origen = "19.5114059,-99.1265259" //Coordenadas de UPIITA
        val horarioData = HorarioData(
            usu_id = userid,
            horario_dia = dia,
            horario_hora= horao,
            horario_origen = origen,
            horario_destino = ubicacionpasar,
            horario_trayecto = "0",
            horario_status = "Disponible",
            horario_solicitud = "No",
        )

        conRegistrarHorario(navController, userid, horarioData, comPantalla)

        ejecutado = true
    }

}






