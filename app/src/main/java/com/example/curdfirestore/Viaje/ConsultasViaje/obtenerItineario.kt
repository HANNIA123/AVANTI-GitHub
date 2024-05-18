package com.example.curdfirestore.Viaje.ConsultasViaje

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.avanti.Usuario.RetrofitClientViaje
import com.example.avanti.ViajeData
import com.example.avanti.ViajeDataReturn
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


@Composable
fun conObtenerItinerarioCon(
    userId: String,
    onResultReady: (List<ViajeDataReturn>?) -> Unit // Función de devolución de llamada para el resultado
) {

    var fin by rememberSaveable { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    var viajes by remember { mutableStateOf<List<ViajeDataReturn>?>(null) }

    LaunchedEffect(key1 = true) {
        try {
            val resultadoViajes = RetrofitClientViaje.apiService.obtenerItinerarioCon(userId)
            viajes = resultadoViajes
        } catch (e: Exception) {
            text = "Error al obtener viaje: $e"
            println(text)
        } finally {
            fin = true
            onResultReady(viajes) // Llamada a la función de devolución de llamada con el resultado
        }
    }
}
@Composable
fun conObtenerItinerarioConSus(
    userId: String,
    fin:() -> Unit// Función de devolución de llamada para el resultado
): List<ViajeDataReturn>? {

    var finD by rememberSaveable { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    var viajes by remember { mutableStateOf<List<ViajeDataReturn>?>(null) }

    LaunchedEffect(key1 = true) {
        try {
            val resultadoViajes = RetrofitClientViaje.apiService.obtenerItinerarioCon(userId)
            viajes = resultadoViajes
        } catch (e: Exception) {
            text = "Error al obtener viaje: $e"
        } finally {
            println(text)
            fin()
            finD=true

        }
    }
    return if (finD) {
        viajes
    } else {
        null
    }
}


@Composable
fun conObtenerItinerarioConRT(
    userId: String
): List<Pair<String, ViajeData>>? {

    var fin by remember { mutableStateOf(false) }
    // Obtener lista de paradas
    var viajes by remember { mutableStateOf<List<Pair<String, ViajeData>>?>(null) }

    LaunchedEffect(key1 = userId) {
        val db = Firebase.firestore

        val paradasRef = db.collection("viaje").whereEqualTo("usu_id", userId)

        paradasRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error al obtener itinerario: $error")
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val listaViajes = snapshot.documents.map { document ->
                    val idDocumento = document.id
                    val paradaData = document.toObject(ViajeData::class.java)
                    idDocumento to paradaData!!
                }
                viajes = listaViajes

            }
            fin = true
        }
    }

    return if (fin) {
        viajes
    } else {
        null
    }
}
