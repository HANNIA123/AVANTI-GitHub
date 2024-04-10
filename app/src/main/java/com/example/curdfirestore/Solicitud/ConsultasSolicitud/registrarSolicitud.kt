package com.example.curdfirestore.Solicitud.ConsultasSolicitud

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.SolicitudData
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.RetrofitClientSolicitud.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun conRegistrarSolicitud(
    solicitudData: SolicitudData,
) {
    var idSol by remember {
        mutableStateOf("")
    }
    var controlador by remember { mutableStateOf(false) }
    var resp by remember { mutableStateOf("") }

    val call: Call<RespuestaApiSolicitud> = apiService.enviarSolicitud(solicitudData)
    call.enqueue(object : Callback<RespuestaApiSolicitud> {
        override fun onResponse(call: Call<RespuestaApiSolicitud>, response: Response<RespuestaApiSolicitud>) {
            if (response.isSuccessful) {
                val respuesta = response.body()?.message ?: "Mensaje nulo"
                idSol = response.body()?.userId.toString()
                resp = respuesta
                println("Guardado $idSol")
                controlador=true

            } else {
                resp = "Entro al else"
            }
        }
        override fun onFailure(call: Call<RespuestaApiSolicitud>, t: Throwable) {
            TODO("Not yet implemented")
        }
    }
    )

}

