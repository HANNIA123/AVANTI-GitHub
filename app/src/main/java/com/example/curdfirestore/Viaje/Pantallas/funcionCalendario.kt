package com.example.curdfirestore.Viaje.Pantallas

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avanti.ui.theme.Aplicacion.obtenerMesAnio
import java.time.LocalDate
import java.time.format.TextStyle
import java.time.DayOfWeek

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeeklyCalendar(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Row for navigation buttons and month/year text
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { onWeekChangeClicked(selectedDate, -1, onDateSelected) },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowLeft,
                    contentDescription = "Anterior"
                )
            }

            Text(
                text = obtenerMesAnio(),
                style = androidx.compose.ui.text.TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = { onWeekChangeClicked(selectedDate, 1, onDateSelected) },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = "Siguiente"
                )
            }
        }

        // Row for days of the week
        // Row for days of the week

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 16.dp, start = 15.dp, end=15.dp).fillMaxWidth()
        ) {
            val daysOfWeek = listOf("Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb")
            for (day in daysOfWeek) {


                Text(
                    text = day,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Row for dates of the week starting from Sunday
        Row(   horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(start = 15.dp, end=15.dp).fillMaxWidth()
        ) {
            val firstDayOfWeek = selectedDate.with(DayOfWeek.SUNDAY)
            for (i in 0 until 7) {
                val date = firstDayOfWeek.plusDays(i.toLong())
                DateItem(
                    date = date,
                    isSelected = date == selectedDate,
                    isCurrentDay = date == LocalDate.now(), // Agrega esta condición para detectar el día actual
                    onDateSelected = { onDateSelected(date) }
                )
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun onWeekChangeClicked(selectedDate: LocalDate, weekOffset: Int, onDateSelected: (LocalDate) -> Unit) {
    val newDate = selectedDate.plusWeeks(weekOffset.toLong())
    onDateSelected(newDate)
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateItem(date: LocalDate, isSelected: Boolean,
             isCurrentDay: Boolean,
             onDateSelected: () -> Unit) {
    val textColor = if (isSelected) Color.Blue else if (isCurrentDay) Color.Gray else Color.Black

    Text(
        text = date.dayOfMonth.toString(),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onDateSelected() },
        color = if (isSelected) Color.Blue else Color.Black,
        fontSize = 20.sp
    )
}
