package com.example.curdfirestore.Notificaciones.Consultas

import androidx.compose.runtime.Composable

@Composable
fun enviarNotificacion(
    nombre: String,
    p_apellido: String,
    token: String,
    tipo: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    /*
    val (title, texto) = TextoNotificacionEnviar(tipo)
    var titulo = ""
    var cuerpo = ""

    if(tipo=="sa"){
        println("ENTRA EN TIPO SA")
        titulo = "$title"
        cuerpo = "$nombre $p_apellido $texto"
    }else{
        println("NO ENTRAAAA")
    }


    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) // Reemplaza con la URL de tu servidor
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    val call = apiService.enviarNotificacionServer(token, titulo, cuerpo)

    call.enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                println("Notificación enviada exitosamente")
            } else {
                onError.invoke("Error al enviar la notificación - Código: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            onError.invoke("Error al enviar la notificación: $t")
        }
    })
*/
}