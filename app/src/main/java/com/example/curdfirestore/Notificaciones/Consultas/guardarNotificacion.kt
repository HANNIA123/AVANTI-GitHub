package com.example.curdfirestore.Notificaciones.Consultas

import com.example.avanti.NoticacionData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun GuardarNotificacion(
    noticacionData: NoticacionData
){
    /*
    var resp =""

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
    val apiService = retrofit.create(ApiService::class.java)
    val call: Call<RespuestaApi> = apiService.enviarNotificacion(noticacionData)
    call.enqueue(object : Callback<RespuestaApi> {
        override fun onResponse(call: Call<RespuestaApi>, response: Response<RespuestaApi>) {
            if (response.isSuccessful) {
                // Manejar la respuesta exitosa aquí
                val respuesta = response.body()?.message ?: "Mensaje nulo"
                resp=respuesta

            } else {
                resp="Entro al else"
            }
        }
        override fun onFailure(call: Call<RespuestaApi>, t: Throwable) {
            TODO("Not yet implemented")
        }
    }
    )
*/
}
