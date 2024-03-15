package com.example.curdfirestore.Parada.ConsultasParada

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun actualizarNumParadas(documentId: String, campo: String, valor: Any):Boolean {
    var exito by remember {
        mutableStateOf(false)
    }
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("viaje").document(documentId).update(campo, valor)
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