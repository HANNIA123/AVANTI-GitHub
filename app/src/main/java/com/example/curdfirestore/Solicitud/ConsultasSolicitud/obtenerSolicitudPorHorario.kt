package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.SolicitudData

@Composable
fun conObtenerSolicitudesPorHorario(
    horarioId: String,
    status: String,
    onResultReady: (List<SolicitudData>?) -> Unit
) {
    var solicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }
    var text by remember { mutableStateOf("") }


    var fin by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        try {
            val resultadoSolicitud =
                RetrofitClientSolicitud.apiService.obtenerSolicitudesbyHorario(horarioId, status)
            solicitudes = resultadoSolicitud
        } catch (e: Exception) {
            text = "Error al obtener solicitudes: $e"
            println("Error al obtener solicitudes: $e")
        } finally {
            fin = true
            onResultReady(solicitudes) // Llamada a la función de devolución de llamada con el resultado

        }

    }

}
