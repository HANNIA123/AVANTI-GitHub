package com.example.curdfirestore.Viaje.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun menuViajeOpciones(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    offset: Dp,
    txtBoton:String,
    onOption1Click: () -> Unit,
    onOption2Click: () -> Unit,
    onOption3Click: () -> Unit,
    onOption4Click: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = Modifier
            .padding(top = 8.dp)
            .background(Color.White),
        offset = DpOffset((-8).dp, offset)
    ) {

        DropdownMenuItem(onClick = {
            onOption1Click()
            onDismissRequest()
        }) {
            Text(
                text = "Nueva parada",
                style = TextStyle(color = Color(137, 13, 88))
            )
        }
        DropdownMenuItem(onClick = {
            onOption2Click()
            onDismissRequest()
        }) {
            Text(
                text = txtBoton,
                style = TextStyle(color = Color(137, 13, 88))
            )
        }
        DropdownMenuItem(onClick = {
            onOption3Click()
            onDismissRequest()
        }) {
            Text(
                text = "Editar",
                style = TextStyle(color = Color(137, 13, 88))
            )
        }
        DropdownMenuItem(onClick = {
            onOption4Click()
            onDismissRequest()
        }) {
            Text(
                text = "Eliminar",
                style = TextStyle(color = Color(137, 13, 88))
            )
        }
    }
}