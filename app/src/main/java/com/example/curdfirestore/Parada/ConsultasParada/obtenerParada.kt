package com.example.curdfirestore.Parada.ConsultasParada

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.ParadaData

import com.example.avanti.Usuario.RetrofitClientParada
import com.example.avanti.Usuario.RetrofitClientViaje
import com.example.avanti.ViajeData

@Composable
fun conObtenerParadaId(paradaId: String): ParadaData? {

    var fin by remember {
        mutableStateOf(false)
    }
    var parada by remember { mutableStateOf<ParadaData?>(null) }
    LaunchedEffect(key1 = true) {
        try {
            val resultadoParada = RetrofitClientParada.apiService.obtenerParada(paradaId)
            parada = resultadoParada




            // Haz algo con el objeto Usuario
            println("Parada obtenida: $parada")

        } catch (e: Exception) {
            println("Error al obtener parada: $e")
        } finally {
            fin = true
        }
    }




    // Puedes retornar el usuario directamente
    return if (fin) {
        parada
    } else {
        null
    }

}



