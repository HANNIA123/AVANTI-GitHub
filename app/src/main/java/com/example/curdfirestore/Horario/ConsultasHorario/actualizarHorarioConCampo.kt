package com.example.curdfirestore.Horario.ConsultasHorario

import com.google.firebase.firestore.FirebaseFirestore

fun actualizarCampoHorarioPorCampo(valorCampo: String, campoBuscar:String, campoActualizar: String, valorActualizar: Any) {
    try {
        val db = FirebaseFirestore.getInstance()

        // Consultar todas las paradas donde el campo "idViaje" sea igual a "idViaje"
        db.collection("parada")
            .whereEqualTo(campoBuscar, valorCampo)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    // Actualizar el campo especificado para cada parada
                    db.collection("parada").document(document.id).update(campoActualizar, valorActualizar)
                        .addOnSuccessListener {
                            println("Documento con ID ${document.id} actualizado correctamente en Firestore.")
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
