package com.example.avanti.Usuario.ConsultasUsuario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.UserData
import com.example.avanti.Usuario.RetrofitClientUsuario


//unicamente lauched effect para las consultas, funciones unicamente
@Composable
fun conObtenerUsuarioId(correo: String): UserData? {
    var fin by remember {
        mutableStateOf(false)
    }
    var usuario by remember { mutableStateOf<UserData?>(null) }

    LaunchedEffect(key1 = true) {
        try {
            val resultadoUsuario = RetrofitClientUsuario.apiService.obtenerUsuario(correo)
            usuario = resultadoUsuario
            // Haz algo con el objeto Usuario
            println("Usuario obtenido: $usuario")
        } catch (e: Exception) {
            println("Error al obtener usuario: $e")
        } finally {
            fin = true
        }
    }

    // Puedes retornar el usuario directamente
    return if (fin) {
        usuario
    } else {
        null
    }
}

