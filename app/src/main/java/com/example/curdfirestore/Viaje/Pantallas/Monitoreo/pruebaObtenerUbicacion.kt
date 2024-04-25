package com.example.curdfirestore.Viaje.Pantallas.Monitoreo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi

import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.database.database

//cameraPosition = CameraPosition(LatLng(animatedLatitude.toDouble(), animatedLongitude.toDouble()), 16f,0f,0f)
/*cameraPositionState = CameraPositionState(
    CameraPosition(
        LatLng(
            latLng.latitude,
            latLng.longitude
        ), 16f, 0f, 0f
    )
)
*/

/*
  state = MarkerState(
                                            position = LatLng(
                                                latLng.latitude,
                                                latLng.longitude
                                            )
 */