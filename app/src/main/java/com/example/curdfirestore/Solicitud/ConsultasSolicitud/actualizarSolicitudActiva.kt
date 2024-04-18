package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import com.google.firebase.firestore.FirebaseFirestore

fun conActualizarSolicitudByStatus(documentId: String, campo: String, valor: Any):Boolean {
    var newValor=if(valor=="vd") "si" else "no"

    var exito =false
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("solicitud").document(documentId).update(campo, newValor)
            .addOnSuccessListener {
                println("Documento con ID $documentId actualizado correctamente de Firestore.")
                exito=true
            }
            .addOnFailureListener { exception ->
                println("Error al intentar actualizar el documento de Firestore: $exception")
                exito=false
            }
    } catch (e: Exception) {
        println("Excepción al intentar actualizar el documento de Firestore: $e")
        exito=false
    }
    return exito
}