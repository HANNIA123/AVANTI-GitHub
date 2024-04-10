package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.SolicitudData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun conObtenerSolicitudesConductor(
    userId: String,
) : List<SolicitudData>?{
    var fin by rememberSaveable { mutableStateOf(false) }
    var show by rememberSaveable { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    //Obtener lista de viajes (Itinerario)
    var resultado by remember { mutableStateOf(false) }
    var solicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }
    LaunchedEffect(key1 = true) {
        val response = RetrofitClientSolicitud.apiService.obtenerSolicitudesCon(userId)
        try {
            if (response.isSuccessful) {
                solicitudes=response.body()
            } else {
                text="No se encontró ningún solicitud que coincida con tu búsqueda"

            }
        } catch (e: Exception) {
            text = "Error al obtener Solicitud: $e"

        }
        finally {
            println(text)
            fin=true
        }
    }

    return if (fin) {

        solicitudes
    } else {
        null
    }

}