package com.example.curdfirestore.Viaje.ConsultasViaje

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.Usuario.ApiServiceViaje
import com.example.avanti.Usuario.BASE_URL
import com.example.avanti.Usuario.RespuestaApiViaje
import com.example.avanti.Usuario.RetrofitClientViaje.apiService
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun conEditarStatusViaje(
    navController: NavController,
    viajeId: String,
    userId:String,
    nuevoStatus: String
) {
   // var resp by remember { mutableStateOf("") }


    val call: Call<RespuestaApiViaje> = apiService.actualizarStatusViaje(viajeId, nuevoStatus)
    call.enqueue(object : Callback<RespuestaApiViaje> {
        override fun onResponse(call: Call<RespuestaApiViaje>, response: Response<RespuestaApiViaje>) {
            if (response.isSuccessful) {
                val pantalla="nomuetsra"
                // Manejar la respuesta exitosa aquí
                val respuesta = response.body()
                //resp = respuesta?.message ?: "Mensaje nulo"
                val viajeParada = respuesta?.viaje_paradas ?: "Campo viaje_parada no encontrado"

                if (viajeParada != "0") {
                    navController.navigate("ver_mapa_viaje/$viajeId/$userId/$pantalla")
                } else {
                    navController.navigate("ver_mapa_viaje_sin/$viajeId/$userId/$pantalla")
                }


                println("Campo viaje_parada: $viajeParada")
                // Continuar con la lógica de navegación u otras acciones
            } else {
               // resp = "Error al actualizar campo viaje_status"
                println("Error al actualizar campo viaje_status")
            }
        }

        override fun onFailure(call: Call<RespuestaApiViaje>, t: Throwable) {
            // Manejar el fallo
           // resp = "Error: ${t.message}"
            println("Error: ${t.message}")
        }
    })
}