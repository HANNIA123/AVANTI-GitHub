package com.example.curdfirestore.Viaje.Funciones

import androidx.compose.runtime.Composable
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerHistorialViajeRT
import com.example.curdfirestore.Viaje.ConsultasViaje.editarCampoHistorial
import java.time.temporal.ChronoUnit
/*
@Composable
fun bloqueoBotonInicioViaje(idInicioViaje: String, hisCreado: Boolean,
                            boton -> Unit
                            ): Boolean {
    var hisCreadoLocal = hisCreado // Variable local para usar dentro de la función

    if (idInicioViaje != "") {
        val historial = conObtenerHistorialViajeRT(idInicioViaje)
        historial?.let {
            if (historial.bloqueo_inicio_viaje) {
                val tiempoBloqueo = 20
                val horaActual = convertirStringATimeSec(obtenerHoraActualSec())
                val horaBloqueo = convertirStringATimeSec(historial.hora_bloqueo_viaje)

                val diferencia = ChronoUnit.SECONDS.between(horaBloqueo, horaActual)
                println("Diferencia: $diferencia")
                print("Actual: $horaActual  Bloqueo: $horaBloqueo")

                if (diferencia > tiempoBloqueo) {
                    editarCampoHistorial(
                        idInicioViaje,
                        "bloqueo_inicio_viaje",
                        false
                    )

                    println("La diferencia de tiempo es mayor a 1 minuto. Boton ACTIVO")
                    botonActivo = true

                    // Agregar aquí la lógica adicional según tu requerimiento
                } else {
                    botonActivo = false
                    println("La diferencia de tiempo es menor o igual a 1 minuto. INACTIVO")
                    // Agregar aquí la lógica adicional en caso de ser menor o igual a 1 minuto
                }

                hisCreadoLocal = true // Modificación de la variable local
            }
        }
    }

    return hisCreadoLocal // Devuelve la variable modificada o no modificada
}

// Ejemplo de uso
fun main() {
    val idInicioViaje = "ID_DEL_INICIO_DEL_VIAJE"
    var hisCreado = false // Variable inicial

    hisCreado = manejarBloqueoInicioViaje(idInicioViaje, hisCreado)

    println("El estado de hisCreado es: $hisCreado")
}
*/