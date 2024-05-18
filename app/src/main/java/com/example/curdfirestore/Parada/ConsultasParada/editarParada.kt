package com.example.curdfirestore.Parada.ConsultasParada

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.ParadaData
import com.example.avanti.Usuario.RespuestaApiParada
import com.example.avanti.Usuario.RetrofitClientParada
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun conActualizarParada(
    navController: NavController,
    userId: String,
    paradaId: String,
    paradaData: ParadaData,
    paradasViaje:String


) {
    var resp by remember { mutableStateOf("") }

    val call: Call<RespuestaApiParada> = RetrofitClientParada.apiService.actualizarParada(paradaId, paradaData)

    call.enqueue(object : Callback<RespuestaApiParada> {
        @SuppressLint("SuspiciousIndentation")
        override fun onResponse(call: Call<RespuestaApiParada>, response: Response<RespuestaApiParada>) {
            if (response.isSuccessful) {

                if (paradasViaje == "0") {
                    navController.navigate(route = "ver_mapa_viaje_sin/${paradaData.viaje_id}/$userId")
                    Toast.makeText(navController.context, "Parada actualizada ", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(navController.context, "Parada actualizado ", Toast.LENGTH_SHORT)
                        .show()

                    navController.navigate(route = "ver_mapa_viaje/${paradaData.viaje_id}/$userId")
                }


            } else {
                resp = "Entro al else"
            }
        }


        override fun onFailure(call: Call<RespuestaApiParada>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })

}

fun editarDocumentoParada(
    navController: NavController,
    userId: String,
    documentId: String,
    paradasViaje:String,
    viajeId:String,
    nuevosValores: Map<String, Any>) {

    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("parada").document(documentId).update(nuevosValores)
            .addOnSuccessListener {

                if (paradasViaje == "0") {
                    navController.navigate(route = "ver_mapa_viaje_sin/$viajeId/$userId")
                    Toast.makeText(navController.context, "Parada actualizada ", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(navController.context, "Parada actualizado ", Toast.LENGTH_SHORT)
                        .show()

                    navController.navigate(route = "ver_mapa_viaje/$viajeId/$userId")
                }

                println("Documento con ID $documentId actualizado correctamente en Firestore.")
            }
            .addOnFailureListener { exception ->
                println("Error al intentar actualizar el documento en Firestore: $exception")
            }
    } catch (e: Exception) {
        println("Excepción al intentar actualizar el documento en Firestore: $e")
    }
}
