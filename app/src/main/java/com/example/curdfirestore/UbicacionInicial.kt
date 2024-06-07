package com.example.curdfirestore

import android.Manifest
import android.content.pm.PackageManager

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import android.Manifest.permission.ACCESS_FINE_LOCATION

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Looper
import androidx.compose.material.Scaffold

import com.google.android.gms.location.LocationRequest
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen1(): String {

    val context = LocalContext.current
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    // Comprueba y solicita permisos de ubicación
    DisposableEffect(context) {
        if (ContextCompat.checkSelfPermission(
                context,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as ComponentActivity,
                arrayOf(
                    ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                123
            )
        }

        onDispose { /* Cleanup */ }
    }

    fusedLocationClient.lastLocation
        .addOnCompleteListener { task ->
            if (task.isSuccessful && task.result != null) {
                val lastKnownLocation = task.result
                // Utiliza lastKnownLocation
            } else {
                Log.w(TAG, "Failed to get location.")
            }
        }



    var ubicacion by remember {
        mutableStateOf("")
    }
    // Obtiene la última ubicación conocida
    DisposableEffect(context) {
        fusedLocationClient.lastLocation
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val lastKnownLocation = task.result
                    // Utiliza lastKnownLocation como desees
                    ubicacion = "${lastKnownLocation.latitude},${lastKnownLocation.longitude}"
                    println("Mi ubicacion: Latitude: ${lastKnownLocation.latitude}, Longitude: ${lastKnownLocation.longitude}")
                    Log.d(
                        TAG,
                        "Latitude: ${lastKnownLocation.latitude}, Longitude: ${lastKnownLocation.longitude}"
                    )
                } else {
                    Log.w(TAG, "Failed to get location.")
                }
            }
        onDispose { /* Cleanup */ }
    }

    return ubicacion


}



