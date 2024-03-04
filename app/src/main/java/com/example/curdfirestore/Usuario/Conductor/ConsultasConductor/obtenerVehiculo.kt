package com.example.curdfirestore.Usuario.Conductor.ConsultasConductor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.UserData
import com.example.avanti.Usuario.RetrofitClientUsuario
import com.example.avanti.VehicleData


@Composable
fun conObtenerVehiculoId(correo: String): VehicleData? {
    var fin by remember {
        mutableStateOf(false)
    }
    var vehiculo by remember { mutableStateOf<VehicleData?>(null) }
    LaunchedEffect(key1 = true) {
        try {
            val resultadoVehiculo = RetrofitClientUsuario.apiService.obtenerVehiculo(correo)
            vehiculo = resultadoVehiculo
            // Haz algo con el objeto Usuario

        } catch (e: Exception) {
            println("Error al obtener vehiculo: $e")
        } finally {
            fin = true
        }
    }

    // Puedes retornar el usuario directamente
    return if (fin) {
        vehiculo
    } else {
        null
    }
}
