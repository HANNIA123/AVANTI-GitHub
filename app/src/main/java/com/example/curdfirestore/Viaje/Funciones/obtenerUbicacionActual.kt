package com.example.curdfirestore.Viaje.Funciones

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun obtenerUbicacion(
    context: Context,
    onUbicacionObtenida: (String) -> Unit
) {

    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    DisposableEffect(Unit) {
        if (!permissionState.hasPermission) {
            permissionState.launchPermissionRequest()
        }
        onDispose { }
    }

    LaunchedEffect(permissionState.permission) {
        if (permissionState.hasPermission) {
            val locationHelper = LocationHelper(context)
            locationHelper.observeLocationUpdates { latitude, longitude ->
                val nuevaUbicacion = "$latitude,$longitude"
                onUbicacionObtenida(nuevaUbicacion)
            }

        }

    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun solicitarPermiso(

) {
    // Comprueba y solicita permisos de ubicación
    val context = LocalContext.current
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    DisposableEffect(Unit) {
        if (!permissionState.hasPermission) {
            permissionState.launchPermissionRequest()
        }
        onDispose { }
    }

}