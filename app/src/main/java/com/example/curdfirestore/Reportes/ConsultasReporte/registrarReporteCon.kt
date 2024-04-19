package com.example.curdfirestore.Reportes.ConsultasReporte

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.ReporteData
import com.example.avanti.Usuario.BASE_URL
import com.example.curdfirestore.Reportes.ApiServiceReporte
import com.example.curdfirestore.Reportes.RespuestaApiReporte
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Composable
fun conRegistrarReporte(
    navController: NavController,
    reporteData: ReporteData
){
    var resp by remember { mutableStateOf("") }

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
    val apiService = retrofit.create(ApiServiceReporte::class.java)
    val call: Call<RespuestaApiReporte> = apiService.enviarReporte(reporteData)

    call.enqueue(object : Callback<RespuestaApiReporte> {
        override fun onResponse(call: Call<RespuestaApiReporte>, response: Response<RespuestaApiReporte>) {
            if (response.isSuccessful) {
                // Manejar la respuesta exitosa aquí
                val respuesta = response.body()?.message ?: "Mensaje nulo"

                resp=respuesta

            } else {
                resp="Entro al else"
            }
        }
        override fun onFailure(call: Call<RespuestaApiReporte>, t: Throwable) {
            TODO("Not yet implemented")
        }
    }
    )

}

