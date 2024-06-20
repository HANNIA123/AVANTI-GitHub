package com.example.curdfirestore.Horario.ConsultasHorario

import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

fun conEditarHorarioDocumento(documentId: String, nuevosValores: Map<String, Any>, userId:String, navController:NavController) {
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("horario").document(documentId).update(nuevosValores)
            .addOnSuccessListener {
                println("Documento con ID $documentId actualizado correctamente en Firestore.")
                navController.navigate("ver_paradas_pasajero/$userId/$documentId")
            }
            .addOnFailureListener { exception ->
                println("Error al intentar actualizar el documento en Firestore: $exception")
            }
    } catch (e: Exception) {
        println("Excepción al intentar actualizar el documento en Firestore: $e")
    }
}
