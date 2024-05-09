package com.example.curdfirestore.Notificaciones.Consultas

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.curdfirestore.Act_Notificaciones_Con
import com.example.curdfirestore.Act_Notificaciones_Pas
import com.example.curdfirestore.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        println("Notificación recibida: ${remoteMessage.data}")

        // Acceder a los datos de la notificación
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body

        val userId = remoteMessage.data["userId"] ?: ""

        println("Título: $title, Cuerpo: $body, userId: $userId")

        showNotification(title, body, userId)
    }



    private fun showNotification(title: String?, body: String?, userId: String) {


        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default_channel",
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }


        val intentA = Intent(this, Act_Notificaciones_Con::class.java).apply {
            putExtra("userId", userId)
            putExtra("notificationClicked", true)
            println("Configurando PendingIntent A")
        }

        val pendingIntentA = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 0, intentA, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(this, 0, intentA, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT)
        }


        val intentB = Intent(this, Act_Notificaciones_Pas::class.java).apply {
            putExtra("userId", userId)
            putExtra("notificationClicked", true)
            println("Configurando PendingIntent B")
        }

        val pendingIntentB = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(this, 1, intentB, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(this, 1, intentB, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_UPDATE_CURRENT)
        }


        println("USER IDDDDDDDDDDDDD $userId")
        //----------------------------------------------------------------------------------


        val usuarioDocument = FirebaseFirestore.getInstance().collection("usuario").document(userId)


        usuarioDocument
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val tipoUsuario = document.getString("usu_tipo")

                    // Ahora puedes usar usuToken en tu notificación
                    println("TIPO DE USUARIO: $tipoUsuario")

                    val pendingIntent: PendingIntent = if (tipoUsuario=="Conductor") {
                        println("PendingIntent AAAAAAAAAAAAAAAA")
                        pendingIntentA
                    } else {
                        println("PendingIntent BBBBBBBBBBBBBBBB")
                        pendingIntentB
                    }

                    // Construir la notificación
                    val notificationBuilder = NotificationCompat.Builder(this, "default_channel")
                        .setSmallIcon(R.drawable.logoa)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)

                    // Mostrar la notificación
                    notificationManager.notify(0, notificationBuilder.build())
                }
            }
            .addOnFailureListener { e ->
                // Manejar errores aquí
                println("Error al obtener el usu_tipo: $e")
            }

    }


    companion object {
        private const val TAG = "MyFirebaseMessaging"
    }
}
