package com.example.curdfirestore.Viaje.ConsultasViaje

import com.google.firebase.firestore.FirebaseFirestore

fun editarCampoHistorial(documentId: String, campo: String, valor: Any) {
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("viajesHistorial").document(documentId).update(campo, valor)
            .addOnSuccessListener {
                println("Documento con ID $documentId actualizado correctamente de Firestore.")
            }
            .addOnFailureListener { exception ->
                println("Error al intentar actualizar el documento de Firestore: $exception")
            }
    } catch (e: Exception) {
        println("Excepción al intentar actualizar el documento de Firestore: $e")
    }
}
fun editarDocumentoHistorial(documentId: String, nuevosValores: Map<String, Any>) {
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("viajesHistorial").document(documentId).update(nuevosValores)
            .addOnSuccessListener {
                println("Documento con ID $documentId actualizado correctamente en Firestore.")
            }
            .addOnFailureListener { exception ->
                println("Error al intentar actualizar el documento en Firestore: $exception")
            }
    } catch (e: Exception) {
        println("Excepción al intentar actualizar el documento en Firestore: $e")
    }
}
