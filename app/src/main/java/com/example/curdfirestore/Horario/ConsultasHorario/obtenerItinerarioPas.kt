package com.example.curdfirestore.Horario.ConsultasHorario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.avanti.HorarioData
import com.example.avanti.HorarioDataReturn
import com.example.avanti.ParadaData
import com.example.avanti.ViajeData
import com.example.curdfirestore.Horario.RetrofitClientHorario
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun conObtenerItinerarioPas(
    userId:String,
    onResultReady: (List<HorarioDataReturn>?) -> Unit
) {
    var fin by rememberSaveable { mutableStateOf(false) }
    var horarios by remember { mutableStateOf<List<HorarioDataReturn>?>(null) }

    LaunchedEffect(key1 = true) {
        try {
            val resultadoHorarios = RetrofitClientHorario.apiService.obtenerItinerarioPas(userId)
            horarios = resultadoHorarios
        } catch (e: Exception) {
            println("Error al obtener itinerario: $e")
        } finally {
            fin = true
            onResultReady(horarios)
        }
    }

}


@Composable
fun conObtenerItinerarioPasRT(
    userId: String
): List<Pair<String, HorarioData>>? {

    var fin by remember { mutableStateOf(false) }
    // Obtener lista de paradas
    var viajes by remember { mutableStateOf<List<Pair<String, HorarioData>>?>(null) }

    LaunchedEffect(key1 = userId) {
        val db = Firebase.firestore

        val paradasRef = db.collection("horario").whereEqualTo("usu_id", userId)

        paradasRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error al obtener paradas: $error")
                return@addSnapshotListener
            }

            if (snapshot != null) {
                val listaViajes = snapshot.documents.map { document ->
                    val idDocumento = document.id
                    val paradaData = document.toObject(HorarioData::class.java)
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
