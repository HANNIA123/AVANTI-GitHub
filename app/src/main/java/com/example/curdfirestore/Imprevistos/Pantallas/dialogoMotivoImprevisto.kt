package com.example.curdfirestore.Imprevistos.Pantallas

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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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

@Composable
fun dialogSeleccionMotivoImpr(
    onDismiss: () -> Unit,
    onDaysSelected: (Set<Int>) -> Unit
) {
    var selectedMotivo by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),

        ) {

        Dialog(
            onDismissRequest = {
                //onDismiss()
            },
            content = {
                // Contenido del diálogo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(15.dp)

                ) {
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = "Selecciona el motivo",
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black

                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // Lista de motivos de reporte
                    val daysOfWeek =
                        listOf(
                            "Problemas mecánicos",
                            "Problemas eléctricos",
                            "Congestionamiento vehícular",
                            "Problemas con los neumáticos",
                            "Otros"
                        )

                    // Botones para seleccionar/deseleccionar motivo
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(500.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        items(daysOfWeek.size) { index ->
                            val isSelected = index + 1 == selectedMotivo
                            ReporteButton(
                                day = daysOfWeek[index],
                                isSelected = isSelected,

                                onToggle = {
                                    selectedMotivo = if (isSelected) 0 else index + 1
                                }

                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))


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

                }
            },

            )
    }
}

@Composable
fun ReporteButton(
    day: String,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    val selectedState = rememberUpdatedState(isSelected)

    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selectedState.value) {
                Color(116, 116, 116) // Cambiar a tu color cuando está seleccionado
            } else {
                Color(238, 236, 239) // Cambiar a tu color cuando no está seleccionado
            }
        ),
        modifier = Modifier
            .padding(4.dp)
            .width(220.dp),
        onClick = { onToggle() },
    ) {
        Text(
            text = day,
            style = TextStyle(
                color = if (selectedState.value) Color.White else Color(137, 13, 88),
                fontSize = 17.sp,
                textAlign = TextAlign.Center
            )
        )
    }

}