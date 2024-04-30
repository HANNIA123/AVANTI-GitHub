package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.ParadaData
import com.example.avanti.SolicitudData
import com.example.avanti.Usuario.RetrofitClientParada
import com.example.avanti.ViajeData
import com.example.avanti.ViajeDataReturn
import com.example.curdfirestore.Parada.Funciones.obtenerDistanciaParadas
import com.example.curdfirestore.Parada.Pantallas.ventanaNoEncontrado
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun conObtenerSolicitudesPorViaje(
    viajeId: String,
    status: String,
    onResultReady: (List<SolicitudData>?) -> Unit
) {
    var paradas by remember { mutableStateOf<List<SolicitudData>?>(null) }
    var text by remember { mutableStateOf("") }


    var fin by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        try {
            val resultadoSolicitud =
                RetrofitClientSolicitud.apiService.obtenerSolicitudesbyViaje(viajeId, status)
            paradas = resultadoSolicitud
        } catch (e: Exception) {
            text = "Error al obtener solicitudes: $e"
            println("Error al obtener solicitudes: $e")
        } finally {
            fin = true
            onResultReady(paradas) // Llamada a la función de devolución de llamada con el resultado

        }

    }

}

@Composable
fun conObtenerSolicitudesPorViajeRT(
    viajeId: String
): List<Pair<String, SolicitudData>>? {

    var fin by remember { mutableStateOf(false) }
    // Obtener lista de paradas
    var solicitudes by remember { mutableStateOf<List<Pair<String, SolicitudData>>?>(null) }

    LaunchedEffect(key1 = viajeId) {
        val db = Firebase.firestore

        val paradasRef = db.collection("solicitud").whereEqualTo("viaje_id", viajeId)

        paradasRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error al obtener solicitudes: $error")
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val listaParadas = snapshot.documents.map { document ->
                    val idDocumento = document.id
                    val paradaData = document.toObject(SolicitudData::class.java)
                    idDocumento to paradaData!!
                }
                solicitudes = listaParadas

            }
            fin = true
        }
    }

    return if (fin) {
        solicitudes
    } else {
        null
    }
}
