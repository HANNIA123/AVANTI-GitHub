package com.example.avanti.Usuario.ConsultasUsuario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.SolicitudData
import com.example.avanti.UserData
import com.example.avanti.Usuario.RetrofitClientUsuario
import com.example.curdfirestore.lineaCargando
import com.example.avanti.ViajeData
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await


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
    usuarioId: String,
    botonFin: () -> Unit
): UserData? {

    var fin by remember { mutableStateOf(false) }
    // Obtener objeto ViajeData
    var viaje by remember { mutableStateOf<UserData?>(null) }

    LaunchedEffect(key1 = usuarioId) {
        val db = Firebase.firestore

        val viajeRef = db.collection("usuario").document(usuarioId)

        viajeRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error al obtener viaje: $error")
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                viaje = snapshot.toObject(UserData::class.java)

            }
            fin = true

        }
    }
    if(fin){
        botonFin()
        println("Aquiiiii")
    }
    print("El boton $fin")
    return if (fin) {
        viaje
    } else {
        null
    }
}

suspend fun validarUsuarioExistente(
    usuarioId: String
): Pair<Boolean, UserData?> {
    return try {
        val db = FirebaseFirestore.getInstance()
        val documentoRef = db.collection("usuario").document(usuarioId)
        val snapshot = documentoRef.get().await()

        if (snapshot.exists()) {
            val usuario = snapshot.toObject(UserData::class.java)
            Pair(true, usuario)
        } else {
            Pair(false, null)
        }
    } catch (e: FirebaseFirestoreException) {
        println("Error al verificar usuario: $e")
        Pair(false, null)
    }
}






