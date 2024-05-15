package com.example.curdfirestore.Viaje.ConsultasViaje

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.avanti.Usuario.RetrofitClientViaje
import com.example.avanti.ViajeDataReturn

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
        } finally {
            println(text)
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
