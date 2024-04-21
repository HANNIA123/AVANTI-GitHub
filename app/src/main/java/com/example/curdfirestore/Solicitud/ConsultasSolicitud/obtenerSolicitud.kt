package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.SolicitudData


@Composable
fun conObtenerSolicitud(horarioId: String): SolicitudData? {
    var fin by remember {
        mutableStateOf(false)
    }
    var solicitud by remember { mutableStateOf<SolicitudData?>(null) }
    LaunchedEffect(key1 = true) {
        try {
            val resultadoSolicitud = RetrofitClientSolicitud.apiService.obtenerSolicitudbyHorario(horarioId)
            solicitud = resultadoSolicitud

            println("Solicitud solicitud: $solicitud")
        } catch (e: Exception) {
            println("Error al obtener la solicitud: $e")
        } finally {
            fin = true
        }
    }



    return if (fin) {
        solicitud
    } else {
        null
    }
}
