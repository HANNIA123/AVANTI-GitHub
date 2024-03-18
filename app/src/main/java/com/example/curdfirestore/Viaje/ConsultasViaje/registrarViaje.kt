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
import com.example.avanti.ViajeData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@Composable
fun conRegistrarViaje(
    navController: NavController,
    userId: String,
    viajeData: ViajeData,
    comPantalla:String

){

    var resp by remember { mutableStateOf("") }

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiServiceViaje::class.java)
    val call: Call<RespuestaApiViaje> = apiService.registrarViaje(viajeData)
    call.enqueue(object : Callback<RespuestaApiViaje> {
        override fun onResponse(call: Call<RespuestaApiViaje>, response: Response<RespuestaApiViaje>) {
            if (response.isSuccessful) {
                // Manejar la respuesta exitosa aquí
                val respuesta = response.body()?.message ?: "Mensaje nulo"
                val idViaje = response.body()?.viajeId.toString()
                resp = respuesta
println("Este es el id $idViaje")
                val regresa="inicioviaje"
                navController.navigate(route = "general_parada/$idViaje/$userId/$comPantalla/$regresa")

            } else {
                resp = "Entro al else"
            }
        }

        override fun onFailure(call: Call<RespuestaApiViaje>, t: Throwable) {
            // Manejar el fallo
            resp = "Error: ${t.message}"
        }
    })


}
