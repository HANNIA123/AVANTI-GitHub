package com.example.curdfirestore.Viaje.ConsultasViaje

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.avanti.Usuario.ApiServiceViaje
import com.example.avanti.Usuario.BASE_URL
import com.example.avanti.Usuario.RetrofitClientViaje
import com.example.avanti.ViajeData
import com.example.avanti.ViajeDataReturn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun conObtenerItinerarioCon(
    userId:String
):List<ViajeDataReturn>?
{
    var fin by rememberSaveable { mutableStateOf(false) }
    var show by rememberSaveable { mutableStateOf(false) }
    var control by remember { mutableStateOf("try") }
    var viajes by remember { mutableStateOf<List<ViajeDataReturn>?>(null) }


    LaunchedEffect(key1 = true) {
        try {
            val resultadoViajes = RetrofitClientViaje.apiService.obtenerItinerarioCon(userId)
            viajes = resultadoViajes
        } catch (e: Exception) {
            println("Error al obtener parada: $e")
            control="catch"
            show=true
        }
        finally {
            fin=true
        }
    }

    return if (fin) {
        viajes
    } else {
        null
    }

}