package com.example.curdfirestore.Parada.ConsultasParada

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeRT
import com.google.firebase.firestore.FirebaseFirestore
@Composable
fun eliminarParada(documentId: String, navController: NavController, userId:String, viajeId:String) {
    val viaje= conObtenerViajeRT(viajeId = viajeId)
    var ejecutado by remember {
        mutableStateOf(false)
    }

    try {
        val db = FirebaseFirestore.getInstance()
        val documentReference = db.collection("parada").document(documentId)
        documentReference.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {

                viaje?.let{
                    if(viaje.viaje_paradas=="0"){
                        navController.navigate(route = "ver_mapa_viaje_sin/$viajeId/$userId")
                    }
                    else{

                        navController.navigate(route = "ver_mapa_viaje/$viajeId/$userId")
                    }
                }
                if(!ejecutado) {
                    Toast.makeText(navController.context, "Parada eliminada ", Toast.LENGTH_SHORT)
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