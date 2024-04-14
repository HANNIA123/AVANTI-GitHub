package com.example.curdfirestore.Horario.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curdfirestore.R

@Composable
fun mensajeNoHorarios() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    }
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        painter = painterResource(id = R.drawable.buscar),
        contentDescription = "Imagen no viaje",
        contentScale = ContentScale.FillBounds
    )
    Spacer(modifier = Modifier.height(15.dp))

    Text(
        text = "No hay ningún horario registrados para hoy",
        style = TextStyle(
            color = Color(86, 86, 86),
            fontSize = 18.sp,
            textAlign = TextAlign.Justify,
        )
    )


}