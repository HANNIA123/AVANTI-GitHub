package com.example.curdfirestore.Viaje.Pantallas

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.curdfirestore.R


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
                        text = "Selecciona el día de la semana en que realizarás este viaje",
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
                        listOf(
                            "Lunes",
                            "Martes",
                            "Miércoles",
                            "Jueves",
                            "Viernes",
                            "Sábado",
                            "Domingo"
                        )

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

                    Row(modifier = Modifier.align(Alignment.End)){
                        TextButton(onClick = { onDismiss() }) {
                            Text(text = "CANCELAR",
                                style = TextStyle(
                                    Color(137,67,242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        TextButton(
                            onClick = {

                                    onDaysSelected(if (selectedDay != 0) setOf(selectedDay) else emptySet())
                                    onDismiss()

                            }) {
                            Text(text = "ACEPTAR",
                                style = TextStyle(
                                    Color(137,67,242),
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

               // expanded = false
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
                        text = "Selecciona el trayecto, recuerda que UPIITA debe ser tu punto de partida o de llegada.",
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Black

                        )
                    )
                    Spacer(modifier = Modifier.height(30.dp))

                    // Lista de días de la semana
                    val daysOfWeek =
                        listOf(
                            "UPIITA como origen",
                            "UPIITA como destino"
                        )

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


                    Row(modifier = Modifier.align(Alignment.End)){



                        TextButton(onClick = { onDismiss() }) {
                            Text(text = "CANCELAR",
                                style = TextStyle(
                                    Color(137,67,242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        TextButton(
                            onClick = {
                                onDaysSelected(if (selectedTrayecto != 0) setOf(selectedTrayecto) else emptySet())
                                onDismiss()
                            }) {
                            Text(text = "ACEPTAR",
                                style = TextStyle(
                                    Color(137,67,242),
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
fun dialogoSeleccionLugares(
    onDismiss: () -> Unit,
    numLugares: (String) -> Unit
) {

    var numero by remember {
        mutableStateOf(1)
    }

    var mensaje by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(numero) {
        mensaje = false
    }


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
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .height(280.dp)
                        .background(Color.White)
                ) {
                    Text(
                        text = "Ingresa los lugares disponibles para este viaje",
                        style = TextStyle(
                            fontSize = 18.sp,
                            ),
                        modifier = Modifier.padding(15.dp)
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .height(150.dp)

                    ) {
                        Text(
                            text = numero.toString(),
                            style = TextStyle(
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        FloatingActionButton(
                            onClick = {
                                if (numero < 5) {

                                    numero++
                                } else {
                                    mensaje = true
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(15.dp)
                        )

                        {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Sumar",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        FloatingActionButton(
                            onClick = {
                                if (numero > 1) {
                                    numero--
                                } else {
                                    numero = 1
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(15.dp)
                        )

                        {
                            Icon(
                                painter = painterResource(id = R.drawable.menos),
                                contentDescription = "restar",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)

                            )
                        }
                        if (mensaje) {
                            Column(
                                modifier = Modifier
                                    .align(
                                        Alignment.BottomStart
                                    )

                            ) {
                                Text(
                                    text = "*No puedes agregar más de 5 lugares",
                                    style = TextStyle(
                                        color = Color(86, 86, 86)
                                    )

                                )
                            }

                        }

                    }

                    Row(modifier = Modifier.align(Alignment.End)
                    //.height(40.dp)
                             ){
                        TextButton(onClick = { onDismiss() }) {
                            Text(text = "CANCELAR",
                                    style = TextStyle(
                                        Color(137,67,242),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                            )
                        }

                        Spacer(modifier = Modifier.width(15.dp))

                        TextButton(onClick = { numLugares(numero.toString())
                        onDismiss()
                        }
                        ) {
                            Text(text = "ACEPTAR",
                                style = TextStyle(
                                    Color(137,67,242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }
                    }
                }
            }
        )
    }
}



@Composable
fun dialogoTarifa(
    onDismiss: () -> Unit,
    newTarifia: (String) -> Unit
) {
    var tarifa by remember {
        mutableStateOf("0")
    }

    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(tarifa))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),

        ) {
        Dialog(
            onDismissRequest = {
                onDismiss()

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
                        text = "Puedes agregar un costo para este viaje",
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
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "$",
                            style = TextStyle(
                                fontSize = 30.sp, // Ajusta el tamaño de texto según tus necesidades
                                color = Color.Black,

                            ),
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(5.dp)
                        )

                        TextField(
                            value = textFieldValue,
                            onValueChange = {
                                // Validar que el texto ingresado sea un número
                                if (it.text.matches(Regex("[0-9]*([.][0-9]*)?"))) {
                                    textFieldValue = it
                                    tarifa = it.text
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            textStyle = TextStyle(
                                fontSize = 30.sp,
                                color = Color.Black,

                            ),
                            modifier = Modifier
                                .weight(1f)
                                .padding(5.dp)
                        )
                    }


                    Spacer(modifier = Modifier.height(25.dp))
                    Row(modifier = Modifier.align(Alignment.End)){
                        TextButton(onClick = { onDismiss() }) {
                            Text(text = "CANCELAR",
                                style = TextStyle(
                                    Color(137,67,242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        TextButton(
                            onClick = {
                                //onDaysSelected(if (selectedTrayecto != 0) setOf(selectedTrayecto) else emptySet())
                                newTarifia(tarifa)
                                onDismiss()
                            }) {
                            Text(text = "ACEPTAR",
                                style = TextStyle(
                                    Color(137,67,242),
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

