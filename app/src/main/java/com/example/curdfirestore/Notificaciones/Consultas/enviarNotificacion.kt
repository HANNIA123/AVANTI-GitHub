package com.example.curdfirestore.Notificaciones.Consultas

import com.example.avanti.Usuario.BASE_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Context
import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings



fun enviarNotificacion(
    nombre: String,
    p_apellido: String,
    token: String,
    tipo: String,
    userId: String,
    onSuccess: () -> Unit,
    onError: (String) -> Unit
) {
    val (title, texto) = TextoNotificacionEnviar(tipo)
    var titulo = ""
    var cuerpo = ""

    if(tipo=="sa"){
        titulo = "$title"
        cuerpo = "$nombre $p_apellido $texto"
    }else if(tipo=="sr"){
        titulo = "$title"
        cuerpo = "$nombre $p_apellido $texto"
    }else if(tipo=="cv"){
        titulo = "$title"
        cuerpo = "$nombre $p_apellido $texto"
    }else if(tipo=="vd"){
        titulo = "$title"
        cuerpo = "$nombre $p_apellido $texto"
    }else if(tipo=="ve"){
        titulo = "$title"
        cuerpo = "$nombre $p_apellido $texto"
    }else if(tipo=="llp"){
        titulo = "$title"
        cuerpo = "$nombre $p_apellido $texto"
    }else if(tipo=="vi"){
        titulo = "$title"
        cuerpo = "$nombre $p_apellido $texto"
    }
    else{
        println("NO ENTRAAAA")
    }

    println("TITULO $titulo")
    println("TOKEN $token")
    println("TOKEN $tipo")
    println("USER ID $userId")

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) // Reemplaza con la URL de tu servidor
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiServiceNotificacion::class.java)

    val call = apiService.enviarNotificacionServer(token, titulo, cuerpo, userId)

    call.enqueue(object : Callback<Void> {
        override fun onResponse(call: Call<Void>, response: Response<Void>) {
            if (response.isSuccessful) {
                println("Notificación enviada exitosamente")
            } else {
                onError.invoke("Error al enviar la notificación ENVIAR NOTIFICACION.KT - Código: ${response.code()}")
            }
        }

        override fun onFailure(call: Call<Void>, t: Throwable) {
            onError.invoke("Error al enviar la notificación: $t")
        }
    })
}


fun requestNotificationPermission(context: Context) {
    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
    intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
    context.startActivity(intent)
}


fun showNotificationPermissionDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("Solicitar permisos de notificación")
        .setMessage("Para recibir notificaciones, necesitamos permisos de notificación. ¿Deseas conceder permisos ahora?")
        .setPositiveButton("Sí") { _, _ ->
            requestNotificationPermission(context)
        }
        .setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}
