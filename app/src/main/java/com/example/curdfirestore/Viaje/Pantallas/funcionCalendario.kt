package com.example.curdfirestore.Viaje.Pantallas
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avanti.ui.theme.Aplicacion.obtenerMesAnio
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

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
                style = TextStyle(
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
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 16.dp, start = 15.dp, end=15.dp).fillMaxWidth()
        ) {
            val daysOfWeek = listOf("Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom")
            for (day in daysOfWeek) {
                Text(
                    text = day,
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Row for dates of the week starting from Monday of the current week
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(start = 14.dp, end=18.dp).fillMaxWidth()
        ) {
            val currentWeekMonday = LocalDate.now().with(DayOfWeek.MONDAY)
            for (i in 0 until 7) {
                val date = currentWeekMonday.plusDays(i.toLong())
                DateItem(
                    date = date,
                    isSelected = date == selectedDate,
                    isCurrentDay = date == LocalDate.now(),
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
    val textColor = if (isSelected) Color(72,12,107) else if (isCurrentDay) Color.Blue else Color.Black

    Text(
        text = date.dayOfMonth.toString(),
        modifier = Modifier
            .padding(8.dp)
            .clickable { onDateSelected() },
        color = textColor,
        fontSize = 20.sp
    )
}


