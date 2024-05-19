package com.example.curdfirestore.Imprevistos.Funciones

import com.example.avanti.ImprevistoData
import com.example.avanti.ReporteData
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

fun conRegistrarImprevisto(imprevistoData: ImprevistoData) {

    val db: FirebaseFirestore = Firebase.firestore
    val imprevistoRef = db.collection("imprevisto")

    imprevistoRef.add(imprevistoData)
        .addOnSuccessListener { documentReference ->
            println("Imprevisto agregado correctamente a Firebase con ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            println("Error al agregar el imprevisto a Firebase: $e")
        }

}