package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import com.google.firebase.firestore.FirebaseFirestore

fun eliminarSolicitudPorCampo(valorCampo: String, campoBuscar:String) {
    try {
        val db = FirebaseFirestore.getInstance()
        val solicitudRef = db.collection("solicitud")
        solicitudRef.whereEqualTo(campoBuscar, valorCampo)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            println("Documento con horario_id $valorCampo eliminado correctamente de Firestore.")
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