package com.example.curdfirestore.Viaje.Funciones

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.database.database

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun UbicacionRealTime(context: Context, userId:String) {

    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)


    DisposableEffect(Unit) {
        if (!permissionState.hasPermission) {
            permissionState.launchPermissionRequest()
        }
        onDispose { }
    }

    LaunchedEffect(permissionState.permission) {
        if (permissionState.hasPermission && userId != null) {
            val referencia = Firebase.database.getReference("ubicacion").child(userId)

            val locationHelper = LocationHelper(context)
            locationHelper.observeLocationUpdates { latitude, longitude ->
               // println("Nuevas coordenada $latitude $longitude")
                // Creamos un mapa solo con las coordenadas
                val coordenadas: Map<String, Double> = mapOf("latitud" to latitude, "longitud" to longitude)
                // Guardamos las coordenadas dentro del nodo userId
                referencia.setValue(coordenadas)
            }
        }
    }
}


@SuppressLint("MissingPermission")
class LocationHelper(private val context: Context) {
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }
    private var locationCallback: LocationCallback? = null

    fun observeLocationUpdates(callback: (Double, Double) -> Unit) {
        val locationRequest = LocationRequest.create().apply {
            interval = 1000 // Intervalo de actualización en milisegundos
            fastestInterval = 500 // Intervalo más rápido en milisegundos
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY // Prioridad de la solicitud de ubicación
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.locations.forEach { location ->
                    callback(location.latitude, location.longitude)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback as LocationCallback, null)
    }

    fun stopLocationUpdates() {
        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
    }
}

