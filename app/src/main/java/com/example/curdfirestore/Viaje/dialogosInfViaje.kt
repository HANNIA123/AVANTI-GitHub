package com.example.curdfirestore.Viaje

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@Composable
fun dialogSeleccionDia(
    onDismiss: () -> Unit,
    onDaysSelected: (Set<Int>) -> Unit
) {
    var selectedDay by remember { mutableStateOf(0) }


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
                        text = "Selecciona un día de la semana",
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

                    // Lista de días de la semana
                    val daysOfWeek =
                        listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")

                    // Botones para seleccionar/deseleccionar días
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(230.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        items(daysOfWeek.size) { index ->
                            val isSelected = index + 1 == selectedDay
                            DayButton(
                                day = daysOfWeek[index],
                                isSelected = isSelected,
                                onToggle = {
                                    selectedDay = if (isSelected) 0 else index + 1
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))
                    // Botón de confirmación
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(137, 13, 86)
                            ),

                            onClick = {
                                onDaysSelected(if (selectedDay != 0) setOf(selectedDay) else emptySet())
                                onDismiss()
                            }

                        ) {

                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "Confirmar",
                                style = TextStyle(
                                    Color.White

                                ),
                                fontSize = 20.sp

                            )
                        }
                    }
                }
            },

            )

    }
}

@Composable
fun dialogoSeleccionTrayecto(
    onDismiss: () -> Unit,
    onDaysSelected: (Set<Int>) -> Unit
) {
    var selectedTrayecto by remember { mutableStateOf(0) }


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
                        text = "Selecciona el trayecto",
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

                    // Lista de días de la semana
                    val daysOfWeek =
                        listOf("UPIITA como origen",
                            "UPIITA como destino")

                    // Botones para seleccionar/deseleccionar días
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(230.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        items(daysOfWeek.size) { index ->
                            val isSelected = index + 1 == selectedTrayecto
                            DayButton(
                                day = daysOfWeek[index],
                                isSelected = isSelected,
                                onToggle = {
                                    selectedTrayecto = if (isSelected) 0 else index + 1
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))
                    // Botón de confirmación
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(137, 13, 86)
                            ),

                            onClick = {
                                onDaysSelected(if (selectedTrayecto != 0) setOf(selectedTrayecto) else emptySet())
                                onDismiss()
                            }

                        ) {

                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "Confirmar",
                                style = TextStyle(
                                    Color.White

                                ),
                                fontSize = 20.sp

                            )
                        }
                    }
                }
            },

            )

    }
}