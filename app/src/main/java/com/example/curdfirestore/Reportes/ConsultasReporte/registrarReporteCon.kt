package com.example.curdfirestore.Reportes.ConsultasReporte

import com.example.avanti.ReporteData
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

fun conRegistrarReporte(reporteData: ReporteData) {
    // Obtener una referencia a la base de datos de Firebase
    val db: FirebaseFirestore = Firebase.firestore

    // Crear una referencia a la colección "viajes"
    val viajesRef = db.collection("reporte")
    // Agregar el objeto Viaje a la colección con un ID generado automáticamente
    viajesRef.add(reporteData)
        .addOnSuccessListener { documentReference ->
            println("Viaje agregado correctamente a Firebase con ID: ${documentReference.id}")
           // editarCampoViajeSinRuta(documentId = viaje.viaje_id, campo = "viaje_id_iniciado", valor=documentReference.id )
        }
        .addOnFailureListener { e ->
            println("Error al agregar el viaje a Firebase: $e")
        }

}
