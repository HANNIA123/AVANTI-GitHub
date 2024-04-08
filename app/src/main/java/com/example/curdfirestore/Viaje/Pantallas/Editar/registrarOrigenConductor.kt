package com.example.curdfirestore.Viaje.Pantallas.Editar


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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.ViajeData


import com.example.curdfirestore.NivelAplicacion.SearchBar
import com.example.curdfirestore.NivelAplicacion.searchPlaces
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.ConsultasViaje.conActualizarViaje
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.example.curdfirestore.Viaje.ConsultasViaje.conRegistrarViaje

import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.Viaje.Funciones.obtenerUbicacionInicial
import com.example.curdfirestore.Viaje.Pantallas.cabeceraConBotonCerrarViaje
import com.example.curdfirestore.Viaje.Pantallas.cabeceraEditarAtras
import com.example.curdfirestore.Viaje.Pantallas.mapaMarker
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun registrarOrigenConductorEditar(
    navController: NavController,
    userid: String,
    dia: String,
    horao: String,
    horad: String,
    lugares: String,
    tarifa: String,
    origen: String,
    viajeId: String
) {
    var maxh by remember {
        mutableStateOf(0.dp)
    }


    BoxWithConstraints {
        maxh = this.maxHeight
    }


    var showPar by remember {
        mutableStateOf(false)
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

    var ubicacionpasar by remember { //agregado
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(maxh)
    ) {

        val ruta="general_viaje_conductor_editar/$userid/$viajeId"
        cabeceraEditarAtras(
            "Registrar destino",
            navController,
            ruta
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxh - 70.dp),
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
                    if (primeraVez == 0) {
                        ubicacion = origen

                    } else {
                        ubicacion = ubiMarker

                    }
                    ubicacionpasar = ubicacion //aqui

                    if (ubicacion != "") {
                        val markerCoordenadasLatLng = convertirStringALatLng(ubicacion)
                        var miUbic by remember {
                            mutableStateOf(LatLng(0.0, 0.0))
                        }
                        println("Coordenadas Location----- $ubicacion")

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

                        var direccion by remember {
                            mutableStateOf("")
                        }
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
                            ubicacionpasar = "$pasarlatitud,$pasarlongitud" //Esto

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
                                    title = "Origen",
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
                                        title = "Origen",
                                        snippet = "Ubicación: $direccion",
                                        icon = BitmapDescriptorFactory.fromResource(R.drawable.marcador),
                                    )
                                }
                            }
                        }

                    }


                    val TextoBarra = "¿De dónde sales?"
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

                    ubiMarker = mapaMarker(ubicacionMarker = "$newUbi")
                    TipoBusqueda = "marker"
                    ubicacionpasar = ubiMarker //Esto

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
                        .padding(bottom = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(137, 13, 88),
                    ),
                    onClick = {
                        boton = true
                        showPar = true
                    }) {
                    Text(
                        text = "Siguiente",
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }

            }

        }

    }

    if (boton == true && ejecutado == false) {

        val conViaje= conObtenerViajeId(viajeId = viajeId)

        conViaje?.let {
            val comPantalla = "muestra"

            val destino = "19.5114059,-99.1265259" //Coordenadas de UPIITA
            val viajeData = ViajeData(
                usu_id = userid,
                viaje_dia = dia,
                viaje_hora_partida = horao,
                viaje_origen = ubicacionpasar,
                viaje_hora_llegada = horad,
                viaje_destino = destino,
                viaje_trayecto = "1",
                viaje_status = conViaje.viaje_status,
                viaje_num_lugares = lugares,
                viaje_paradas = "0",
                viaje_iniciado = "no",
                viaje_tarifa = tarifa,
                viaje_num_pasajeros = "0",
                viaje_num_pasajeros_con = "0",
                viaje_id = ""
            )


            conActualizarViaje(
                navController = navController,
                userId = userid,
                viajeId = viajeId,
                viajeData = viajeData
            )

//conRegistrarViaje(navController, userid,viajeData, comPantalla)


            //GuardarViaje(navController, userid, viajeData,comPantalla)
            ejecutado = true

        }

    }
}










