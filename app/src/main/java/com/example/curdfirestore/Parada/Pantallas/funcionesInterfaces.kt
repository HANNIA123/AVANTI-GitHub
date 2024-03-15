package com.example.curdfirestore.Parada.Pantallas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun mapaMarkerParada(ubicacionMarker: String): String {
    var latitud by remember {
        mutableStateOf("")
    }
    var direccion by remember {
        mutableStateOf("")
    }
    var longitud by remember {
        mutableStateOf("")
    }
    var markerLat by remember { mutableStateOf(0.0) }
    var markerLon by remember { mutableStateOf(0.0) }
    val markerCoordenadasLatLng = convertirStringALatLng(ubicacionMarker)
    if (markerCoordenadasLatLng != null) {
        markerLat = markerCoordenadasLatLng.latitude
        markerLon = markerCoordenadasLatLng.longitude
        // Hacer algo con las coordenadas LatLng
        println("Latitud: ${markerCoordenadasLatLng.latitude}, Longitud: ${markerCoordenadasLatLng.longitude}")
    } else {
        // La conversión falló
        println("Error al convertir la cadena a LatLng")
    }

    val miUbic = LatLng(markerLat, markerLon)
    direccion = convertCoordinatesToAddress(coordenadas = miUbic)
    var markerState = rememberMarkerState(position = miUbic)
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerState.position, 17f)
    }

    latitud = markerState.position.latitude.toString()
    longitud = markerState.position.longitude.toString()



    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {

        Marker(
            title = "Parada",
            state = markerState,
            icon = BitmapDescriptorFactory.fromResource(R.drawable.marcador),
            snippet = "Ubicación: $direccion",
            draggable = true
        )
    }

    println("Coordenadas del marker $latitud,$longitud --------")
    return "$latitud,$longitud"
}

