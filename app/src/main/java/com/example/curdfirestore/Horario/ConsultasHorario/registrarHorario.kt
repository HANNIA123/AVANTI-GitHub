package com.example.curdfirestore.Horario.ConsultasHorario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.HorarioData
import com.example.avanti.Usuario.BASE_URL
import com.example.avanti.Usuario.RespuestaApiViaje
import com.example.curdfirestore.Horario.ApiServiceHorario
import com.example.curdfirestore.Horario.RespuestaApiHorario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun conRegistrarHorario(
    navController: NavController,
    userId: String,
    horarioData: HorarioData,
    comPantalla: String
) {
    var resp by remember { mutableStateOf("") }
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
    val apiService = retrofit.create(ApiServiceHorario::class.java)
    val call: Call<RespuestaApiHorario> = apiService.registrarHorario(horarioData)
    call.enqueue(object : Callback<RespuestaApiHorario> {
        override fun onResponse(
            call: Call<RespuestaApiHorario>,
            response: Response<RespuestaApiHorario>
        ) {
            if (response.isSuccessful) {
                val respuesta = response.body()?.message ?: "Mensaje nulo"
                val idHorario = response.body()?.horarioId.toString()
                resp = respuesta
                navController.navigate("ver_paradas_pasajero/$userId/$idHorario")

            } else {
                resp = "Entro al else"
            }
        }


        override fun onFailure(call: Call<RespuestaApiHorario>, t: Throwable) {
            // Manejar el fallo
            resp = "Error: ${t.message}"
        }
    }
    )
}