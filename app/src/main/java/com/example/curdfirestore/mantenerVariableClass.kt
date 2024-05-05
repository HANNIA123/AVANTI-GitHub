package com.example.curdfirestore

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.compose.runtime.Composable
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ServerValue
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.concurrent.schedule


import java.util.*
import kotlin.concurrent.schedule

fun iniciarContador(usuarioId: String) {

    val referencia = Firebase.database.getReference(usuarioId).child("InicioViaje")

    referencia.setValue(false) // Establecemos el valor inicial como false

    // Programamos la actualización del valor después de un minuto
    Timer().schedule(60000) {
        referencia.setValue(true)
    }
}
fun iniciarContador1(usuarioId: String, context: Context) {
    val referencia = Firebase.database.getReference(usuarioId).child("InicioViaje")
    referencia.setValue(false) // Establecemos el valor inicial como false

    // Creamos un intent para la tarea que queremos realizar después de un minuto
    val intent = Intent(context, ActualizarValorReceiver::class.java)
    intent.putExtra("usuarioId", usuarioId)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    // Programamos la actualización del valor después de un minuto
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val tiempoActual = SystemClock.elapsedRealtime()
    val tiempoDespuesDeUnMinuto = tiempoActual + 60000 // 1 minuto en milisegundos
    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, tiempoDespuesDeUnMinuto, pendingIntent)
}

// Clase para recibir la notificación del AlarmManager
class ActualizarValorReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val usuarioId = intent?.getStringExtra("usuarioId")
        if (context != null && usuarioId != null) {
            val referencia = Firebase.database.getReference(usuarioId).child("InicioViaje")
            referencia.setValue(true)
        }
    }
}