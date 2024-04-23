package com.example.curdfirestore.Viaje.ConsultasViaje

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.Usuario.RespuestaApiViaje
import com.example.avanti.Usuario.RetrofitClientViaje.apiService
import com.example.avanti.ViajeData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun conActualizarViaje(
    navController: NavController,
    userId: String,
    viajeId: String,
    viajeData: ViajeData

) {
    var resp by remember { mutableStateOf("") }

    val call: Call<RespuestaApiViaje> = apiService.actualizarViaje(viajeId, viajeData)

    call.enqueue(object : Callback<RespuestaApiViaje> {
        override fun onResponse(call: Call<RespuestaApiViaje>, response: Response<RespuestaApiViaje>) {
            if (response.isSuccessful) {
                val respuesta = response.body()?.message ?: "Mensaje nulo"
                resp = respuesta
                if(viajeData.viaje_paradas=="0"){
                    navController.navigate(route = "ver_mapa_viaje_sin/$viajeId/$userId")
                    Toast.makeText(navController.context, "Viaje actualizado ", Toast.LENGTH_SHORT).show()

                }
                else{
                    navController.navigate(route = "ver_mapa_viaje/$viajeId/$userId")
                }


            } else {
                resp = "Entro al else"
            }
        }

        override fun onFailure(call: Call<RespuestaApiViaje>, t: Throwable) {

            t.printStackTrace()
        }
    })

}
