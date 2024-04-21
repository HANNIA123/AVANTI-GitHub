package com.example.curdfirestore.Horario.ConsultasHorario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.avanti.HorarioDataReturn
import com.example.curdfirestore.Horario.RetrofitClientHorario

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