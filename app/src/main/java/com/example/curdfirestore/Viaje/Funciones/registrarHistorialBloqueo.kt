package com.example.curdfirestore.Viaje.Funciones

import android.os.Handler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerHistorialViajeRT
import com.example.curdfirestore.Viaje.ConsultasViaje.editarCampoHistorial
import java.time.temporal.ChronoUnit

@Composable
fun registrarHistorialBloqueo(idInicioViaje: String,
                              botonActivo : () -> Unit,
                              botonNoActivo : () -> Unit,


                              ){

    val handler = Handler()
    val delayMillis = 2000L // 2 segundos en milisegundos, para recargar

    val isRunning = remember { mutableStateOf(true) } // Variable de control para detener la ejecución

        val historial = conObtenerHistorialViajeRT(idInicioViaje)
        val runnableCode = object : Runnable {
            override fun run() {

                historial?.let {

                    // Coloca tu código aquí
                    if (historial.bloqueo_inicio_viaje) {

                        val tiempoBloqueo = 60
                        val horaActual =
                            convertirStringATimeSec(obtenerHoraActualSec())
                        val horaBloqueo =
                            convertirStringATimeSec(historial.hora_bloqueo_viaje)

                        val diferencia =
                            ChronoUnit.SECONDS.between(horaBloqueo, horaActual)
                        println("Diferenciaaa $diferencia")
                        print("Actua: $horaActual  Bloqueo $horaBloqueo")

                        if (diferencia > tiempoBloqueo) {
                            editarCampoHistorial(
                                idInicioViaje,
                                "bloqueo_inicio_viaje",
                                false
                            )


                            println("La diferencia de tiempo es mayor a 1 minuto. Boton ACTIVO")
                            botonActivo()
                            isRunning.value = false // Detener la ejecución después de ejecutar botonActivo()


                            // Agregar aquí la lógica adicional según tu requerimiento
                        } else {
                            botonNoActivo()
                            println("La diferencia de tiempo es menor o igual a 1 minuto. INACTIVO")
                            // Agregar aquí la lógica adicional en caso de ser menor o igual a 1 minuto
                        }

                        // Reiniciar la variable "recargar" para futuras recargas
                    }

                    // Vuelve a programar la ejecución del código después de 2 segundos
                    if (isRunning.value) {

                        handler.postDelayed(this, delayMillis)
                    }



                }
            }

        }
    if (isRunning.value) {
        handler.postDelayed(runnableCode, delayMillis)
    }

}