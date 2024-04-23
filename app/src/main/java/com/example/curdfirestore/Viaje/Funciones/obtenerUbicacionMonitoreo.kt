package com.example.curdfirestore.Viaje.Funciones

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerListaParadas
import com.example.curdfirestore.R
import com.example.curdfirestore.Usuario.Conductor.cabeceraConMenuCon
import com.example.curdfirestore.Usuario.Conductor.menuDesplegableCon
import com.example.curdfirestore.Viaje.ConsultasViaje.conEditarCampoViaje
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.example.curdfirestore.Viaje.Pantallas.MapViewContainer
import com.example.curdfirestore.Viaje.Pantallas.Monitoreo.UbicacionRealTime
import com.google.android.gms.maps.GoogleMap
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun obtenerCoordenadas(
    userId: String,
    viajeId: String,
    navController: NavController
) {

    var maxh by remember {
        mutableStateOf(0.dp)
    }

    BoxWithConstraints {
        maxh = this.maxHeight - 50.dp
    }


    val context = LocalContext.current
    val newUserId = userId.substringBefore('@')

    UbicacionRealTime(context, newUserId)
    val referencia = Firebase.database.getReference("ubicacion").child(newUserId)


    var latLng by remember { mutableStateOf(LatLng(0.0, 0.0)) } // Coordenadas de San Francisco
    var markerOptions by remember {
        mutableStateOf(
            MarkerOptions().position(latLng).title("San Francisco")
        )
    }


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
    var boton by remember {
        mutableStateOf(false)
    }

    var numParadas by remember {
        mutableStateOf(0)
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxh)

        ) {

            val listaParadas = conObtenerListaParadas(viajeId = viajeId)
            val infViaje = conObtenerViajeId(viajeId = viajeId)
            cabeceraConMenuCon(
                titulo = "Viaje en progreso",
                navController,
                userId,
                boton = { estaBoton ->
                    boton = estaBoton
                })


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(maxh - 140.dp)
            ) {

                ///
                MapViewContainer { googleMap: GoogleMap ->
                    // Habilita los controles de zoom
                    googleMap.uiSettings.isZoomControlsEnabled = true
                    // Agrega los marcadores

                    // Agrega el marcador en una ubicación específica
                    val marker = MarkerOptions().position(LatLng(latLng.latitude, latLng.longitude)).title("Título del marcador")
                    googleMap.addMarker(marker)


                    }



                ///
/*
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    cameraPositionState = CameraPositionState(
                        CameraPosition(
                            LatLng(
                                latLng.latitude,
                                latLng.longitude
                            ), 16f, 0f, 0f
                        )
                    )
                ) {
                    Marker(
                        state = MarkerState(
                            position = LatLng(
                                latLng.latitude,
                                latLng.longitude
                            )
                        ),
                        title = "Tu ubicación",
                        snippet = "Ubicación: ",
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.autooficial),
                    )


                    listaParadas?.let {
                        numParadas=listaParadas.size
                        val paradas =
                            listaParadas.sortedBy { it.par_hora }

                        paradas.forEach { parada ->
                            val parLatLng = convertirStringALatLng(parada.par_ubicacion)
                            if (parLatLng != null) {

                                Marker(
                                    state = MarkerState(
                                        position = LatLng(
                                            parLatLng.latitude,
                                            parLatLng.longitude
                                        )
                                    ),
                                    title = "Parada ${parada.par_nombre}",
                                    snippet = "Ubicación: ",
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.paradas),
                                )
                            }


                        }
                    }

                    infViaje?.let {
                       val origenLatLng= convertirStringALatLng(infViaje.viaje_origen)

                        val destinoLatLng= convertirStringALatLng(infViaje.viaje_destino)
                        if(origenLatLng!=null){
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        origenLatLng.latitude,
                                        origenLatLng.longitude
                                    )
                                ),
                                title = "Punto de origen",
                                snippet = "Ubicación: ",
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.origendestino),
                            )
                        }
                        if(destinoLatLng!=null){
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        destinoLatLng.latitude,
                                        destinoLatLng.longitude
                                    )
                                ),
                                title = "Punto de llegada",
                                snippet = "Ubicación: ",
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.origendestino),
                            )
                        }

                    }
                }
*/

            }

            //Boton de llegada
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(10.dp, 0.dp),
            ) {

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            137,
                            13,
                            88
                        )
                    ),
                    onClick = {
                        //Iniaciar Viaje

                    },
                    modifier = Modifier.width(180.dp)
                ) {
                    Text(
                        text = "Iniciar viaje", style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    )
                }

            }


        }
    }

    if (boton) {
        menuDesplegableCon(
            onDismiss = { boton = false },
            navController,
            userID = userId
        )
    }


}




