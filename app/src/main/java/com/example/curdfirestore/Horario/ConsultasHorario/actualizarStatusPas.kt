package com.example.curdfirestore.Horario.ConsultasHorario

import androidx.navigation.NavController
import com.example.avanti.Usuario.RespuestaApiViaje
import com.example.avanti.Usuario.RetrofitClientViaje
import com.example.curdfirestore.Horario.RespuestaApiHorario
import com.example.curdfirestore.Horario.RetrofitClientHorario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun conEditarStatusHorario(
    navController: NavController,
    horarioId: String,
    userId:String,
    nuevoStatus: String
) {

    val call: Call<RespuestaApiHorario> = RetrofitClientHorario.apiService.actualizarStatusHorario(horarioId, nuevoStatus)
    call.enqueue(object : Callback<RespuestaApiHorario> {
        override fun onResponse(call: Call<RespuestaApiHorario>, response: Response<RespuestaApiHorario>) {
            if (response.isSuccessful) {
                val pantalla="itinerario"

                val respuesta = response.body()

                navController.navigate("ver_mapa_viaje_pas/$horarioId/$userId/$pantalla")

            } else {
                println("Error al actualizar campo horario_status")
            }
        }

        override fun onFailure(call: Call<RespuestaApiHorario>, t: Throwable) {
            println("Error: ${t.message}")
        }
    })
}