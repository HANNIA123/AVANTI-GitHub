package com.example.curdfirestore.Viaje.Funciones

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat

import androidx.core.content.ContextCompat

import com.example.curdfirestore.R

import com.example.curdfirestore.Viaje.Pantallas.Monitoreo.UbicacionRealTime
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.checkerframework.checker.nullness.qual.NonNull


@Composable
fun obtenerCoordenadas(
    userId: String
){

    val context = LocalContext.current
    val newUserId = userId.substringBefore('@')

    UbicacionRealTime(context, newUserId)
    val referencia = Firebase.database.getReference("ubicacion").child(newUserId)



    var maxh by remember {
        mutableStateOf(0.dp)
    }
    BoxWithConstraints {
        maxh = this.maxHeight
    }

    var latLng by remember { mutableStateOf(LatLng(0.0, 0.0)) } // Coordenadas de San Francisco
    var markerOptions by remember { mutableStateOf(MarkerOptions().position(latLng).title("San Francisco")) }



    var datosAnteriores: Map<String, Any>? = null

    referencia.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Obtén los nuevos datos
            val nuevosDatos: Map<String, Any>? = dataSnapshot.value as? Map<String, Any>

            // Compara los nuevos datos con los datos anteriores
            if (nuevosDatos != null && nuevosDatos != datosAnteriores) {
                // Los datos han cambiado, actúa en consecuencia
                val latitud = nuevosDatos["latitud"] as Double
                val longitud = nuevosDatos["longitud"] as Double
                latLng = LatLng(latitud, longitud)

                markerOptions = MarkerOptions().position(latLng).title("San Francisco")
                // Haz lo que necesites con latitud y longitud



                // Actualiza los datos anteriores
                datosAnteriores = nuevosDatos

            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Manejar errores, si es necesario
            println("Error al obtener los datos: ${databaseError.message}")
        }
    })



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(maxh - 140.dp)
    ) {

        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = CameraPositionState(CameraPosition(LatLng(latLng.latitude, latLng.longitude), 19f, 0f, 0f))
        ) {
            Marker(
                state = MarkerState(
                    position = LatLng(
                        latLng.latitude,
                        latLng.longitude
                    )
                ),
                title = "Origen",
                snippet = "Ubicación: ",
                icon = BitmapDescriptorFactory.fromResource(R.drawable.autooficial),
            )
        }

    }


}



val REQUEST_LOCATION_PERMISSION = 1001
fun solicitarPermisos(context: Context) {
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
            context as Activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            123
        )
    }
}
fun solicitarPermisosNew(context: Context) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }
}

fun obtenerUbicacion(context: Context, userId: String) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    GlobalScope.launch {
        try {
            // Solicitar permisos de ubicación
            solicitarPermisos(context)

            // Obtener la última ubicación conocida
            val location = fusedLocationClient.lastLocation.await()

            // Procesar la ubicación obtenida
            if (location != null) {
                val nuevaUbicacion = "${location.latitude},${location.longitude}"
                println("Recargandoooo : Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                Log.d(ContentValues.TAG, "Latitude: ${location.latitude}, Longitude: ${location.longitude}")


                val referencia = Firebase.database.getReference("ubicacion")

                val mapaUbicacion: Map<String, Double> = mapOf("latitud" to location.latitude, "longitud" to location.longitude)
                referencia.setValue(mapaUbicacion)



                // Llama a la función de devolución de llamada con la nueva ubicación
                // onUbicacionObtenida(nuevaUbicacion)
            } else {
                Log.w(ContentValues.TAG, "Failed to get location.")
            }
        } catch (e: SecurityException) {
            // Manejar excepción de permisos denegados
            Log.e(ContentValues.TAG, "Security exception: ${e.message}")
        } catch (e: Exception) {
            // Manejar otras excepciones
            Log.e(ContentValues.TAG, "Exception: ${e.message}")
        }
    }
}

/*const val REQUEST_CHECK_SETTINGS = 1001 // Puedes elegir cualquier valor que desees aquí

fun obtenerUbicacion(context: Context, userId: String) {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    // Configurar la solicitud de ubicación
    val locationRequest = LocationRequest.create()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY) // Establecer alta precisión
        .setInterval(5000) // Intervalo de actualización de ubicación en milisegundos (5 segundos)
        .setFastestInterval(1000) // Intervalo más rápido en el que se actualizará la ubicación en milisegundos (1 segundo)

    // Configurar las opciones de solicitud de ubicación
    val locationSettingsRequestBuilder = LocationSettingsRequest.Builder()
        .addLocationRequest(locationRequest)

    // Crear una tarea para manejar la solicitud de ubicación
    val locationSettingsTask = LocationServices.getSettingsClient(context).checkLocationSettings(locationSettingsRequestBuilder.build())

    locationSettingsTask.addOnSuccessListener {
        // Solicitar permisos de ubicación si no están otorgados
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }

        // Obtener la última ubicación conocida
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val nuevaUbicacion = "${location.latitude},${location.longitude}"
                println("Recargandoooo : Latitude: ${location.latitude}, Longitude: ${location.longitude}")
                Log.d(ContentValues.TAG, "Latitude: ${location.latitude}, Longitude: ${location.longitude}")

                val referencia = FirebaseDatabase.getInstance().getReference("ubicacion")
                val mapaUbicacion: Map<String, Double> = mapOf("latitud" to location.latitude, "longitud" to location.longitude)
                referencia.setValue(mapaUbicacion)

                // Llama a la función de devolución de llamada con la nueva ubicación
                // onUbicacionObtenida(nuevaUbicacion)
            } ?: Log.w(ContentValues.TAG, "Failed to get location.")
        }
    }


}



*/
/*
@Composable

fun obtenerUbicacionMonitoreo(
userId: String
) {

    val database = Firebase.database
    val referencia = database.getReference("ubicacion")

    var show by remember {
        mutableStateOf(false)
    }
    var showPermissionDeniedMessage by remember { mutableStateOf(false) }


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

// Ordenar los usuarios por nombre


                val nuevaUbicacion = "${lastKnownLocation.latitude},${lastKnownLocation.longitude}"
                println("Mi ubicación: Latitude: ${lastKnownLocation.latitude}, Longitude: ${lastKnownLocation.longitude}")
                Log.d(
                    ContentValues.TAG,
                    "Latitude: ${lastKnownLocation.latitude}, Longitude: ${lastKnownLocation.longitude}"
                )
                val mapaVacio: Map<String, Double> = mapOf(
                    "latitud" to lastKnownLocation.latitude,
                    "longitud" to lastKnownLocation.longitude
                )
                referencia.setValue(mapaVacio)

                // Llama a la función de devolución de llamada con la nueva ubicación
                // onUbicacionObtenida(nuevaUbicacion)
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
            show = true
            showPermissionDeniedMessage = false
        }
    }


}
*/
