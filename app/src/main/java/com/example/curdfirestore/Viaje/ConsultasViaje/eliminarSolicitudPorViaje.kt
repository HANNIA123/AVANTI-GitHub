package com.example.curdfirestore.Viaje.ConsultasViaje

import com.google.firebase.firestore.FirebaseFirestore

fun eliminarSolicitudPorviajeId(viajeId: String) {
    try {
        val db = FirebaseFirestore.getInstance()
        val solicitudRef = db.collection("solicitud")
        solicitudRef.whereEqualTo("viaje_id", viajeId)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            println("Documento con horario_id $viajeId eliminado correctamente de Firestore.")
                        } else {
                            println("Error al intentar eliminar el documento de Firestore: ${task.exception}")
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                println("Error al intentar obtener los documentos de Firestore: $e")
            }
    } catch (e: Exception) {
        println("Error al intentar eliminar el documento de Firestore: $e")
    }
}
