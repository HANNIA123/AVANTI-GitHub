package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.ParadaData
import com.example.avanti.SolicitudData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


@Composable
fun conObtenerSolicitud(horarioId: String): SolicitudData? {
    var fin by remember {
        mutableStateOf(false)
    }
    var solicitud by remember { mutableStateOf<SolicitudData?>(null) }
    LaunchedEffect(key1 = true) {
        try {
            val resultadoSolicitud = RetrofitClientSolicitud.apiService.obtenerSolicitudbyHorario(horarioId)
            solicitud = resultadoSolicitud

            println("Solicitud solicitud: $solicitud")
        } catch (e: Exception) {
            println("Error al obtener la solicitud: $e")
        } finally {
            fin = true
        }
    }



    return if (fin) {
        solicitud
    } else {
        null
    }
}

@Composable
fun conObtenerSolicitudByHorarioRT(
    horarioId: String
): SolicitudData? {

    var fin by remember { mutableStateOf(false) }
    // Obtener objeto ViajeData
    var solicitud by remember { mutableStateOf<SolicitudData?>(null) }

    LaunchedEffect(key1 = horarioId) {
        val db = Firebase.firestore

        val viajeRef = db.collection("solicitud").whereEqualTo("horario_id", horarioId)

     viajeRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error al obtener solicitud: $error")
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                solicitud = snapshot.documents[0].toObject(SolicitudData::class.java)
            }

            fin = true
        }


    }

    return if (fin) {
        solicitud
    } else {
        null
    }
}
