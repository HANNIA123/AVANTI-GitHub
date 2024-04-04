package com.example.curdfirestore.Horario.ConsultasHorario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.HorarioData
import com.example.curdfirestore.Horario.RetrofitClientHorario

@Composable
fun conObtenerHorarioId(horarioId: String): HorarioData? {
    var fin by remember {
        mutableStateOf(false)
    }
    var horario by remember { mutableStateOf<HorarioData?>(null) }
    LaunchedEffect(key1 = true) {
        try {
            val resultadoViaje = RetrofitClientHorario.apiService.obtenerHorario(horarioId)
            horario = resultadoViaje


        } catch (e: Exception) {
            println("Error al obtener viaje: $e")
        } finally {
            fin = true
        }
    }

    // Puedes retornar el usuario directamente
    return if (fin) {
        horario
    } else {
        null
    }
}