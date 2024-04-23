package com.example.curdfirestore.Viaje.ConsultasViaje

import androidx.navigation.NavController
import com.example.avanti.Usuario.RespuestaApiViaje
import com.example.avanti.Usuario.RetrofitClientViaje
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun conEditarCampoViaje(
    navController: NavController,
    ruta:String,
    viajeId: String,
    camposEditar:String,
    nuevoValor: String
) {
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("viaje").document(viajeId).update(camposEditar, nuevoValor)
            .addOnSuccessListener {
                println("Documento con ID $viajeId actualizado correctamente de Firestore.")
                navController.navigate(ruta)
            }
            .addOnFailureListener { exception ->
                println("Error al intentar actualizar el documento de Firestore: $exception")
            }
    } catch (e: Exception) {
        println("Excepción al intentar actualizar el documento de Firestore: $e")
    }

}