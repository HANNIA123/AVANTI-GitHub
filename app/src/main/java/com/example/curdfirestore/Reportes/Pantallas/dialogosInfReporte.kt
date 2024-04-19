package com.example.curdfirestore.Reportes.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.avanti.HorarioData
import com.example.avanti.ReporteData
import com.example.curdfirestore.Horario.ConsultasHorario.conRegistrarHorario
import com.example.curdfirestore.Viaje.Pantallas.DayButton

@Composable
fun dialogSeleccionMotivo(
    onDismiss: () -> Unit,
    onDaysSelected: (Set<Int>) -> Unit
) {
    var selectedMotivo by remember { mutableStateOf(0) }
    var boton by remember { mutableStateOf(false) }
    var ejecutado by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),

        ) {


        Dialog(
            onDismissRequest = {
                onDismiss()

                //expanded = false
            }, // Cierra el diálogo al tocar fuera de él
            content = {
                // Contenido del diálogo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(15.dp)


                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Selecciona el motivo",
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black

                        )
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    // Lista de motivos de reporte
                    val daysOfWeek =
                        listOf(
                            "Acoso",
                            "Cancelación de viaje",
                            "No asistió a punto de encuentro",
                            "Malas prácticas de conducción",
                            "Otros"
                        )

                    // Botones para seleccionar/deseleccionar motivo
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(230.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        items(daysOfWeek.size) { index ->
                            val isSelected = index + 1 == selectedMotivo
                            DayButton(
                                day = daysOfWeek[index],
                                isSelected = isSelected,
                                onToggle = {
                                    selectedMotivo = if (isSelected) 0 else index + 1
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))
                    // Botón de confirmación

                    Row(modifier = Modifier.align(Alignment.End)) {
                        TextButton(onClick = { onDismiss() }) {
                            Text(
                                text = "CANCELAR",
                                style = TextStyle(
                                    Color(137, 67, 242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        TextButton(
                            onClick = {

                                onDaysSelected(if (selectedMotivo != 0) setOf(selectedMotivo) else emptySet())
                                onDismiss()

                            }) {
                            Text(
                                text = "ACEPTAR",
                                style = TextStyle(
                                    Color(137, 67, 242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }
                    }


                    ///////////////
                }
            },

            )

    }


}