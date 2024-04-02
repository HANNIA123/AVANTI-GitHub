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
import com.example.avanti.Usuario.RespuestaApiViaje
import com.example.avanti.Usuario.RetrofitClientParada
import com.example.avanti.Usuario.RetrofitClientViaje
import com.example.avanti.ViajeData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun conActualizarParada(
    navController: NavController,
    userId: String,
    paradaId: String,
    paradaData: ParadaData

) {
    var resp by remember { mutableStateOf("") }

    val call: Call<RespuestaApiParada> = RetrofitClientParada.apiService.actualizarParada(paradaId, paradaData)

    call.enqueue(object : Callback<RespuestaApiParada> {
        @SuppressLint("SuspiciousIndentation")
        override fun onResponse(call: Call<RespuestaApiParada>, response: Response<RespuestaApiParada>) {
            if (response.isSuccessful) {
                val respuesta = response.body()?.message ?: "Mensaje nulo"
                resp = respuesta
                    navController.navigate(route = "ver_mapa_viaje/${paradaData.viaje_id}/$userId")
                Toast.makeText(navController.context, "Para actualizada ", Toast.LENGTH_SHORT).show()

            } else {
                resp = "Entro al else"
            }
        }


        override fun onFailure(call: Call<RespuestaApiParada>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })

}
