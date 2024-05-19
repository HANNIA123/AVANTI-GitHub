package com.example.curdfirestore.Viaje.ConsultasViaje

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun eliminarViaje(documentId: String, navController: NavController, userId:String) {
    var ejecutado by remember{
        mutableStateOf(false)
    }
    try {
        val db = FirebaseFirestore.getInstance()
        val documentReference = db.collection("viaje").document(documentId)
        documentReference.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navController.navigate("ver_itinerario_conductor/$userId")
                // Mostrar Toast indicando que el documento se ha eliminado correctamente
                if(!ejecutado) {
                    Toast.makeText(navController.context, "Viaje eliminado ", Toast.LENGTH_SHORT)
                        .show()
                    ejecutado=true
                }

            } else {
                println("Error al intentar eliminar el documento de Firestore: ${task.exception}")
            }
        }
    } catch (e: Exception) {
        println("Error al intentar eliminar el documento de Firestore: $e")
    }
}

fun eliminarViajeSinRuta(documentId: String) {
    try {
        val db = FirebaseFirestore.getInstance()
        val documentReference = db.collection("viaje").document(documentId)
        documentReference.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {

                println("Documento con ID $documentId eliminado correctamente de Firestore.")
            } else {
                println("Error al intentar eliminar el documento de Firestore: ${task.exception}")
            }
        }
    } catch (e: Exception) {
        println("Error al intentar eliminar el documento de Firestore: $e")
    }
}