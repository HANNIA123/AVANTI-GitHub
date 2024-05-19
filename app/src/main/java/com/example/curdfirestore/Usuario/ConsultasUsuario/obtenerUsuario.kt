package com.example.avanti.Usuario.ConsultasUsuario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.avanti.SolicitudData
import com.example.avanti.UserData
import com.example.avanti.Usuario.RetrofitClientUsuario
import com.example.curdfirestore.lineaCargando
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await as await


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
fun conObtenerUsuarioNot(userId: String): State<UserData?> {
    val userState = remember { mutableStateOf<UserData?>(null) }

    LaunchedEffect(userId) {
        val db = Firebase.firestore
        val userRef = db.collection("usuario").document(userId)

        try {
            val snapshot = userRef.get().await()
            if (snapshot.exists()) {
                userState.value = snapshot.toObject(UserData::class.java)
                println("Usuario obtenido: ${userState.value}")
            } else {
                println("No existe el documento para el ID: $userId")
            }
        } catch (e: Exception) {
            println("Error al obtener usuario: $e")
        }
    }

    return userState
}