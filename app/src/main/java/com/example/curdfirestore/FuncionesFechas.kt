package com.example.avanti.ui.theme.Aplicacion

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Calendar
import java.util.Date
import java.util.Locale


fun obtenerDiaDeLaSemanaActual(): String {
    val calendar = Calendar.getInstance()
    val diasSemana = arrayOf("Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado")
    val diaDeLaSemana = calendar.get(Calendar.DAY_OF_WEEK)
    return diasSemana[diaDeLaSemana - 1]
}


@RequiresApi(Build.VERSION_CODES.O)
fun obtenerNombreDiaEnEspanol(dia: DayOfWeek): String {
    return when (dia) {
        DayOfWeek.MONDAY -> "Lunes"
        DayOfWeek.TUESDAY -> "Martes"
        DayOfWeek.WEDNESDAY -> "Miércoles"
        DayOfWeek.THURSDAY -> "Jueves"
        DayOfWeek.FRIDAY -> "Viernes"
        DayOfWeek.SATURDAY -> "Sábado"
        DayOfWeek.SUNDAY -> "Domingo"
    }
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
@RequiresApi(Build.VERSION_CODES.O)
fun obtenerMesAnio(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("es", "ES"))
    return currentDate.format(formatter)
}
fun obtenerFechaFormatoddmmyyyy(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fechaActual = Date()
    return dateFormat.format(fechaActual)
}