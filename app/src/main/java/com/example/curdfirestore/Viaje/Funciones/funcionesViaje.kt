package com.example.curdfirestore.Viaje.Funciones

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import java.util.Locale


//Para los dialogos
@Composable
fun convertirADia(numDia: Set<Int>):String{
    var dia by remember {
        mutableStateOf("")
    }

    dia = when (numDia) {
        setOf(1) -> "Lunes"
        setOf(2) -> "Martes"
        setOf(3) -> "Miércoles"
        setOf(4) -> "Jueves"
        setOf(5) -> "Viernes"
        setOf(6) -> "Sábado"
        setOf(7) -> "Domingo"
        else -> ""
    }
    return dia

}

//Solo para el dialogo!
@Composable
fun convertirATrayecto(numTrayecto: Set<Int>):String{
    var trayecto by remember {
        mutableStateOf("")
    }

    trayecto = when (numTrayecto) {
        setOf(1) -> "UPIITA como origen"
        setOf(2) -> "UPIITA como destino"

        else -> ""
    }
    return trayecto

}


//Para el registro de viajes
@Composable

fun obtenerUbicacionInicial(
    navController: NavController,
    userId: String,
    onUbicacionObtenida: (String) -> Unit) {
    var show by remember {
        mutableStateOf(false)
    }
    var showPermissionDeniedMessage by remember { mutableStateOf(false) }

    var snackbarVisible by remember { mutableStateOf(false) }

    // Comprueba y solicita permisos de ubicación
    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    // Comprueba y solicita permisos de ubicación
    // Comprueba y solicita permisos de ubicación
    DisposableEffect(context) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as ComponentActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                123
            )
            // show = true
        }

        onDispose { /* Cleanup */ }
    }

    // ...

    // Obtiene la última ubicación conocida
    fusedLocationClient.lastLocation
        .addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val lastKnownLocation = task.result
                val nuevaUbicacion = "${lastKnownLocation.latitude},${lastKnownLocation.longitude}"
                println("Mi ubicación: Latitude: ${lastKnownLocation.latitude}, Longitude: ${lastKnownLocation.longitude}")
                Log.d(ContentValues.TAG, "Latitude: ${lastKnownLocation.latitude}, Longitude: ${lastKnownLocation.longitude}")
                // Llama a la función de devolución de llamada con la nueva ubicación
                onUbicacionObtenida(nuevaUbicacion)
            } else {
                // Cambia el estado de la variable solo cuando el permiso es rechazado
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    showPermissionDeniedMessage = true
                }
                Log.w(ContentValues.TAG, "Failed to get location.")
            }
        }


// Muestra un Snackbar cuando el permiso es rechazado
    if (showPermissionDeniedMessage) {
        LaunchedEffect(showPermissionDeniedMessage) {
            show=true
            showPermissionDeniedMessage = false
        }
    }


}

fun convertirStringALatLng(coordenadas: String): LatLng? {
    try {
        // Supongamos que las coordenadas están separadas por una coma
        val partes = coordenadas.split(",")

        if (partes.size == 2) {
            val latitud = partes[0].trim().toDouble()
            val longitud = partes[1].trim().toDouble()

            return LatLng(latitud, longitud)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return null
}


@Composable
fun convertCoordinatesToAddress(coordenadas:LatLng): String {
    val latitud: Double = coordenadas.latitude
    val longitud: Double = coordenadas.longitude
    val context = LocalContext.current
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address> = geocoder.getFromLocation(latitud, longitud, 1)!!

    return if (addresses.isNotEmpty()) {
        val address = addresses[0]
        val addressLines = address.getAddressLine(0).split(", ")
        val firstTwoLines = addressLines.take(2).joinToString(", ")
        firstTwoLines

    } else {
        "No se encontraron direcciones para las coordenadas dadas"
    }
}

