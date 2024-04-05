package com.example.curdfirestore.Horario.ConsultasHorario

import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

fun eliminarHorario(documentId: String, navController: NavController, userId:String) {
    try {
        val db = FirebaseFirestore.getInstance()
        val documentReference = db.collection("horario").document(documentId)
        documentReference.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate("ver_itinerario_conductor/$userId")
                // Mostrar Toast indicando que el documento se ha eliminado correctamente
                Toast.makeText(navController.context, "Horario eliminado ", Toast.LENGTH_SHORT).show()

                println("Documento con ID $documentId eliminado correctamente de Firestore.")
            } else {
                println("Error al intentar eliminar el documento de Firestore: ${task.exception}")
            }
        }
    } catch (e: Exception) {
        println("Error al intentar eliminar el documento de Firestore: $e")
    }
}