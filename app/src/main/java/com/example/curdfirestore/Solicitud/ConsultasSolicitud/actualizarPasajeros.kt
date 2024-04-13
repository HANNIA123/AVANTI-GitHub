package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import com.google.firebase.firestore.FirebaseFirestore

fun actualizarPasajerosDeSolicitud(documentId: String, campos: Map<String, Any>) {
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("viaje").document(documentId).update(campos)
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
