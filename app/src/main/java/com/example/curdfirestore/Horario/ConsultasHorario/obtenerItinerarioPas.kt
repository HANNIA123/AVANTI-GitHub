package com.example.curdfirestore.Horario.ConsultasHorario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.HorarioDataReturn
import com.example.avanti.ViajeDataReturn
import com.example.curdfirestore.Horario.RetrofitClientHorario
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun conObtenerItinerarioPas(
    userId:String
):List<HorarioDataReturn>? {
    var fin by rememberSaveable { mutableStateOf(false) }
    var show by rememberSaveable { mutableStateOf(false) }
    var control by remember { mutableStateOf("try") }
    var horarios by remember { mutableStateOf<List<HorarioDataReturn>?>(null) }

    LaunchedEffect(key1 = true) {
        try {
            val resultadoHorarios = RetrofitClientHorario.apiService.obtenerItinerarioPas(userId)
            horarios = resultadoHorarios
        } catch (e: Exception) {
            println("Error al obtener itinerario: $e")
            control="catch"
            show=true
        } finally {
            fin=true
        }
    }

    return if (fin) {
        horarios
    } else {
        null
    }

}