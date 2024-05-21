package com.example.curdfirestore.Viaje.ConsultasViaje

import com.google.firebase.firestore.FirebaseFirestore

fun aumentarLugaresDeViaje(viajeId: String) {
    try {
        val db = FirebaseFirestore.getInstance()
        val viajeRef = db.collection("viaje").document(viajeId)

        viajeRef.get().addOnSuccessListener { viajeDoc ->
            val lugaresActuales = viajeDoc.getString("viaje_num_lugares")?.toIntOrNull() ?: 0
val newLugares=lugaresActuales + 1
            viajeRef.update("viaje_num_lugares", newLugares.toString())
                .addOnSuccessListener {
                    println("Campo viaje_lugares actualizado correctamente para el viaje con ID: $viajeId.")
                }
                .addOnFailureListener { e ->
                    println("Error al intentar actualizar el campo viaje_lugares: $e")
                }
        }.addOnFailureListener { e ->
            println("Error al obtener el documento de viaje: $e")
        }
    } catch (e: Exception) {
        println("Error al intentar actualizar el campo viaje_lugares: $e")
    }
}
