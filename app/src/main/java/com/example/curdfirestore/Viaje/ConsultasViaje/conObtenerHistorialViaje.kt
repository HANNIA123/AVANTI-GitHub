package com.example.curdfirestore.Viaje.ConsultasViaje

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.HistorialViajesData
import com.example.avanti.ViajeData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun conObtenerHistorialViajeRT(
    viajeId: String
): HistorialViajesData? {

    var fin by remember { mutableStateOf(false) }
    // Obtener objeto ViajeData
    var viaje by remember { mutableStateOf<HistorialViajesData?>(null) }

    LaunchedEffect(key1 = viajeId) {
        val db = Firebase.firestore

        val viajeRef = db.collection("viajesHistorial").document(viajeId)

        viajeRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error al obtener historiaaal: $error")
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                viaje = snapshot.toObject(HistorialViajesData::class.java)
            }
            fin = true
        }
    }

    return if (fin) {
        viaje
    } else {
        null
    }
}
