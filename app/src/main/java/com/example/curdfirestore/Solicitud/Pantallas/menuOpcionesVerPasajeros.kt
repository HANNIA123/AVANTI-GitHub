package com.example.curdfirestore.Solicitud.Pantallas

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Botones dentro de ver conductores o pasajeros, quizás se pueda reutilizar en ambos
@Composable
fun botonesVerPasajeros(onButtonClick: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botón "Contacto"
        TextButton(
            onClick = { onButtonClick("Contacto") },
        ) {
            Icon(
                imageVector = Icons.Filled.Phone,
                contentDescription = "Contacto",
                modifier = Modifier.size(28.dp),
                tint = Color(137, 13, 88)
            )
            Text(
                text = "Contacto",
                style = TextStyle(
                    color = Color(137, 13, 88),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                )
            )
        }

        // Botón "Reportar"
        TextButton(
            onClick = { onButtonClick("Reportar") },
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Reportar",
                modifier = Modifier.size(28.dp),
                tint = Color(137, 13, 88)
            )
            Text(
                text = "Reportar",
                style = TextStyle(
                    color = Color(137, 13, 88),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                )
            )
        }

        // Botón "Borrar"
        TextButton(
            onClick = { onButtonClick("Borrar") },
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Borrar",
                modifier = Modifier.size(28.dp),
                tint = Color(137, 13, 88)
            )
            Text(
                text = "Borrar",
                style = TextStyle(
                    color = Color(137, 13, 88),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                )
            )
        }
    }
}
