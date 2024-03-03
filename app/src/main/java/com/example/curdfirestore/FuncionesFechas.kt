package com.example.avanti.ui.theme.Aplicacion

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun obtenerDiaDeLaSemanaActual(): String {
    val calendar = Calendar.getInstance()
    val diasSemana = arrayOf("Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado")
    val diaDeLaSemana = calendar.get(Calendar.DAY_OF_WEEK)
    return diasSemana[diaDeLaSemana - 1]
}

fun obtenerFechaHoyCompleto(): String {
    // Obtiene la fecha actual
    val fechaActual = Calendar.getInstance().time

    // Define el formato deseado
    val formato = SimpleDateFormat("EEEE dd 'de' MMMM", Locale("es", "ES"))

    // Formatea la fecha en el formato deseado
    val fechaFormateada = formato.format(fechaActual)

    // Convierte la primera letra a mayúscula
    return fechaFormateada.replaceFirstChar { it.uppercase() }
}