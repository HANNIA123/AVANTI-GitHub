package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.avanti.SolicitudData

@Composable
fun conObtenerSolicitudesPasajero(
    userId: String,
    onResultReady: (List<SolicitudData>?) -> Unit // Función de devolución de llamada para el resultado
) {
    println("id del pasajero $userId")
    var fin by rememberSaveable { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    var solicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }

    LaunchedEffect(key1 = true) {
        val response = RetrofitClientSolicitud.apiService.obtenerSolicitudesPas(userId)
        try {
            if (response.isSuccessful) {
                solicitudes = response.body()
            } else {
                text = "No se encontró ninguna solicitud que coincida con tu búsqueda"
            }
        } catch (e: Exception) {
            text = "Error al obtener Solicitud: $e"
        } finally {
            println(text)
            fin = true
            onResultReady(solicitudes) // Llamada a la función de devolución de llamada con el resultado
        }
    }
}
