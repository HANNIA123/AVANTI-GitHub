package com.example.avanti.Usuario.ConsultasUsuario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.UserData
import com.example.avanti.Usuario.RetrofitClientUsuario
import com.example.avanti.ViajeData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore


//unicamente lauched effect para las consultas, funciones unicamente.
//inicuar con la palabra "con" las funciones de consulta, con de consulta
@Composable
fun conObtenerUsuarioId(correo: String): UserData? {
    var noReady by remember {
        mutableStateOf(true)
    }
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



@Composable
fun conObtenerUsuarioRT(
    usuarioId: String
): UserData? {

    var fin by remember { mutableStateOf(false) }
    // Obtener objeto ViajeData
    var usuario by remember { mutableStateOf<UserData?>(null) }

    LaunchedEffect(key1 = usuarioId) {
        val db = Firebase.firestore

        val viajeRef = db.collection("usuario").document(usuarioId)

        viajeRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error al obtener usuario: $error")
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                usuario = snapshot.toObject(UserData::class.java)
                println("user obtenido: $usuario")
            }
            fin = true
        }
    }

    return if (fin) {
        usuario
    } else {
        null
    }
}
