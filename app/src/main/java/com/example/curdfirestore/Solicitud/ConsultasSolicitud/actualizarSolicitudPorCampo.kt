package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import com.google.firebase.firestore.FirebaseFirestore

fun actualizarCampoSolicitudPorBusqueda(campoBuscar: String, contenidoCampoBuscar: String,
                                     campoActualizar: String, valor: Any) {
    try {
        val db = FirebaseFirestore.getInstance()

        // Consultar todas las paradas donde el campo "idViaje" sea igual a "idViaje"
        db.collection("solicitud")
            .whereEqualTo(campoBuscar, contenidoCampoBuscar)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    // Actualizar el campo especificado para cada parada
                    db.collection("solicitud").document(document.id).update(campoActualizar, valor)
                        .addOnSuccessListener {
                            println("Documento solciitud con ID ${document.id} actualizado correctamente en Firestore.")
                        }
                        .addOnFailureListener { exception ->
                            println("Error al intentar actualizar el documento ${document.id} de Firestore: $exception")
                        }
                }
            }
            .addOnFailureListener { exception ->
                println("Error al intentar obtener las paradas de Firestore: $exception")
            }
    } catch (e: Exception) {
        println("Excepción al intentar actualizar las paradas de Firestore: $e")
    }
}
