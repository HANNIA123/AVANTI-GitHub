package com.example.curdfirestore.Horario.ConsultasHorario

import com.google.firebase.firestore.FirebaseFirestore

fun actualizarHorarioPas(documentId: String, campo: String, valor: Any) {
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("horario").document(documentId).update(campo, valor)
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