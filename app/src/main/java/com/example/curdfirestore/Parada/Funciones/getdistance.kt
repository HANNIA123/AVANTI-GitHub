package com.example.curdfirestore.Parada.Funciones

import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.google.android.gms.maps.model.LatLng
import androidx.compose.runtime.collectAsState

fun getDistanceCorrecto(coordenadas1: LatLng, coordenadas2: LatLng):
        Float {
    val location1 = Location("")
    location1.latitude = coordenadas1.latitude
    location1.longitude = coordenadas1.longitude

    val location2 = Location("")
    location2.latitude = coordenadas2.latitude
    location2.longitude = coordenadas2.longitude

    return location1.distanceTo(location2)
}


@Composable
fun getDistance(coordenadas1: String, coordenadas2: String): State<Float?> {
    var distancia by remember { mutableStateOf<Float?>(null) }

    var Lat1 by remember { mutableStateOf(0.0) }
    var Lon1 by remember { mutableStateOf(0.0) }

    var Lat2 by remember { mutableStateOf(0.0) }
    var Lon2 by remember { mutableStateOf(0.0) }

    val markerCoordenadas1 = convertirStringALatLng(coordenadas1)
    if (markerCoordenadas1 != null) {
        Lat1 = markerCoordenadas1.latitude
        Lon1 = markerCoordenadas1.longitude
    }
    val markerCoordenadas2 = convertirStringALatLng(coordenadas2)
    if (markerCoordenadas2 != null) {
        Lat2 = markerCoordenadas2.latitude
        Lon2 = markerCoordenadas2.longitude
    }

    val lat1: Double = Lat1
    val lon1: Double = Lon1
    val lat2: Double = Lat2
    val lon2: Double = Lon2
    val location1 = Location("")
    location1.latitude = lat1
    location1.longitude = lon1

    val location2 = Location("")
    location2.latitude = lat2
    location2.longitude = lon2

    // Verifica si la distancia ya ha sido calculada
    if (distancia == null) {
        // Calcula la distancia
        distancia = location1.distanceTo(location2)
    }

    // Retorna la distancia calculada
    return remember { mutableStateOf(distancia) }
}

fun getDistanceParadaUbi(coordenadas1: LatLng, coordenadas2: LatLng): Float {


    val location1 = Location("")
    location1.latitude = coordenadas1.latitude
    location1.longitude = coordenadas1.longitude

    val location2 = Location("")
    location2.latitude = coordenadas2.latitude
    location2.longitude = coordenadas2.longitude

    return location1.distanceTo(location2)
}
