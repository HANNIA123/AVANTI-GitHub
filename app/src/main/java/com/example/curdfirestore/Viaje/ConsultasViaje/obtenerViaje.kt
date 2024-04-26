package com.example.curdfirestore.Viaje.ConsultasViaje

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.ParadaData

import com.example.avanti.Usuario.RetrofitClientViaje

import com.example.avanti.ViajeData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun conObtenerViajeId(viajeId: String): ViajeData? {
    var fin by remember {
        mutableStateOf(false)
    }
    var viaje by remember { mutableStateOf<ViajeData?>(null) }
    LaunchedEffect(key1 = true) {
        try {
            val resultadoViaje = RetrofitClientViaje.apiService.obtenerViaje(viajeId)
            viaje = resultadoViaje


        } catch (e: Exception) {
            println("Error al obtener viaje: $e")
        } finally {
            fin = true
        }
    }

    // Puedes retornar el usuario directamente
    return if (fin) {
        viaje
    } else {
        null
    }
}
@Composable
fun conObtenerViajeRT(
    viajeId: String
): ViajeData? {

    var fin by remember { mutableStateOf(false) }
    // Obtener objeto ViajeData
    var viaje by remember { mutableStateOf<ViajeData?>(null) }

    LaunchedEffect(key1 = viajeId) {
        val db = Firebase.firestore

        val viajeRef = db.collection("viaje").document(viajeId)

        viajeRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error al obtener viaje: $error")
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                viaje = snapshot.toObject(ViajeData::class.java)
                println("Viaje obtenido: $viaje")
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
