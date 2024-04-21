package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

fun eliminarSolicitudById(documentId: String, navController: NavController, userId:String, ruta:String) {
    try {
        val db = FirebaseFirestore.getInstance()
        val documentReference = db.collection("solicitud").document(documentId)
        documentReference.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate("$ruta/$userId")
                // Mostrar Toast indicando que el documento se ha eliminado correctamente
                Toast.makeText(navController.context, "solicitud eliminado ", Toast.LENGTH_SHORT).show()

                println("Documento con ID $documentId eliminado correctamente de Firestore.")
            } else {
                println("Error al intentar eliminar el documento de Firestore: ${task.exception}")
            }
        }
    } catch (e: Exception) {
        println("Error al intentar eliminar el documento de Firestore: $e")
    }
}

