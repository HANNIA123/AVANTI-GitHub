package com.example.curdfirestore.Viaje.Pantallas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilaIconoTexto(
    icono: ImageVector, // Recibe el recurso de icono
    texto: String, // Recibe el texto a mostrar
    onClick: () -> Unit, // Recibe la acción a realizar al hacer clic en la fila
    mostrarTextoError: Boolean = false,
    mensaje:String// Opcional: indica si se debe mostrar el texto de error
) {
    val tamIcono = 48.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Altura deseada de la fila
            .border(
                width = 1.dp ,
                color = Color.LightGray
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            imageVector = icono,
            contentDescription = "Icono",
            modifier = Modifier
                .size(tamIcono)
                .padding(10.dp, 5.dp),
            tint = Color(137, 13, 86)
        )
        Text(
            text = texto,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .weight(1f)
                .padding(30.dp, 15.dp, 10.dp, 10.dp),
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.Black
            )
        )
    }

    if (mostrarTextoError) {
        Text(
            text = mensaje,
            style = TextStyle(
                color = Color(86, 86, 86)
            )

        )
    }
}

@Composable
fun FilaIconoTexto2(
    icono: Int, // Recibe el recurso de icono
    texto: String, // Recibe el texto a mostrar
    onClick: () -> Unit, // Recibe la acción a realizar al hacer clic en la fila
    mostrarTextoError: Boolean = false,
    mensaje:String// Opcional: indica si se debe mostrar el texto de error// Opcional: indica si se debe mostrar el texto de error
) {
    val tamIcono = 55.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp) // Altura deseada de la fila
            .border(
                width = 1.dp ,
                color = Color.LightGray
            )
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Icon(
            painter = painterResource(id = icono),
            contentDescription = "Icono",
            modifier = Modifier
                .size(tamIcono)
                .padding(10.dp, 5.dp),
            tint = Color(137, 13, 86)
        )
        Text(
            text = texto,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .weight(1f)
                .padding(30.dp, 15.dp, 10.dp, 10.dp),
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.Black
            )
        )
    }

    if (mostrarTextoError) {
        Text(
            text = mensaje,
            style = TextStyle(
                color = Color(86, 86, 86)
            )

            )
    }
}