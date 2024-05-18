package com.example.curdfirestore.Parada.ConsultasParada

import android.widget.Toast
import androidx.navigation.NavController
import com.example.avanti.ParadaData
import com.google.firebase.firestore.FirebaseFirestore


fun actualizarCampoParada(documentId: String, campo: String, valor: Any) {
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("parada").document(documentId).update(campo, valor)
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


fun actualizarCampoParadaRuta(
    documentId: String,
    campo: String,
    valor: Any,
    viajeParadas: String,
    navController: NavController,
    viajeId: String,
    userId: String
) {
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("parada").document(documentId).update(campo, valor)
            .addOnSuccessListener {
                if (viajeParadas == "0") {
                    navController.navigate(route = "ver_mapa_viaje_sin/$viajeId/$userId")
                    Toast.makeText(navController.context, "Parada actualizada ", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(navController.context, "Parada actualizado ", Toast.LENGTH_SHORT)
                        .show()

                    navController.navigate(route = "ver_mapa_viaje/$viajeId/$userId")
                }


                println("Documento con ID $documentId actualizado correctamente de Firestore.")
            }
            .addOnFailureListener { exception ->
                println("Error al intentar actualizar el documento de Firestore: $exception")
            }
    } catch (e: Exception) {
        println("Excepción al intentar actualizar el documento de Firestore: $e")
    }
}

