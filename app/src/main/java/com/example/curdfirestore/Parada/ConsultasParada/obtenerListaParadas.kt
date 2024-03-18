package com.example.curdfirestore.Parada.ConsultasParada

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.ParadaData
import com.example.avanti.Usuario.RetrofitClientParada
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId

@Composable
fun conObtenerListaParadas(

    viajeId: String,


): List<ParadaData>? {

    var fin by remember {
        mutableStateOf(false)
    }
    //Obtener lista de paradas
    var paradas by remember { mutableStateOf<List<ParadaData>?>(null) }
    LaunchedEffect(key1 = true) {
        try {
            val resultadoParadas = RetrofitClientParada.apiService.obtenerListaParadas(viajeId)
            paradas = resultadoParadas
            // Haz algo con la lista de ViajeData
            println("Paradas obtenidas--------: $paradas")
        } catch (e: Exception) {

            println("Error al obtener parada: $e")
        }
        finally {
            fin=true
        }
    }
    return if (fin) {
        paradas
    } else {
        null
    }
}