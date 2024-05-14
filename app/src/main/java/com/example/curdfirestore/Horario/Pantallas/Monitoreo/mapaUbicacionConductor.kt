package com.example.curdfirestore.Horario.Pantallas.Monitoreo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.avanti.ParadaData
import com.example.avanti.ViajeData
import com.example.curdfirestore.R
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun mapaUbicacionConductor( cameraPositionState: CameraPositionState,
                            cargando: ()-> Unit,
                            latLng: LatLng,
                            paradasOrdenadas: List<Pair<String, ParadaData>>,
                            infViaje: ViajeData?
                            ){
    GoogleMap(
        modifier = Modifier
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapLoaded = {
            cargando ()
        }
    ) {
        Marker(
            state = MarkerState(
                position = LatLng(
                    latLng.latitude,
                    latLng.longitude
                )
            ),
            title = "Tu ubicación",
            icon = BitmapDescriptorFactory.fromResource(R.drawable.autooficial),
        )

        paradasOrdenadas.forEach { parada ->
            val parLatLng = convertirStringALatLng(parada.second.par_ubicacion)
            if (parLatLng != null) {


                Marker(
                    state = MarkerState(
                        position = LatLng(
                            parLatLng.latitude,
                            parLatLng.longitude
                        )
                    ),
                    title = "Parada ${parada.second.par_nombre}",
                    //snippet = "Dirección: $direccionPar",
                    icon = BitmapDescriptorFactory.fromResource(R.drawable.paradas),
                )
            }


        }

        val origenLatLng = infViaje?.let { convertirStringALatLng(it.viaje_origen) }
        val destinoLatLng = infViaje?.let { convertirStringALatLng(it.viaje_destino) }

        if (origenLatLng != null) {
            Marker(
                state = MarkerState(
                    position = LatLng(
                        origenLatLng.latitude,
                        origenLatLng.longitude
                    )
                ),
                title = "Origen",
                //  snippet = "Dirección: $direccionOri",
                icon = BitmapDescriptorFactory.fromResource(R.drawable.origendestino),
            )
        }
        if (destinoLatLng != null) {
            Marker(
                state = MarkerState(
                    position = LatLng(
                        destinoLatLng.latitude,
                        destinoLatLng.longitude
                    )
                ),
                title = "Punto de llegada",
                //snippet = "Ubicación: $direccionDes",
                icon = BitmapDescriptorFactory.fromResource(R.drawable.origendestino),
            )
        }

    }
}