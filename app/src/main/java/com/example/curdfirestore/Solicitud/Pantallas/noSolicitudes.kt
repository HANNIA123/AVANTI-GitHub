package com.example.curdfirestore.Solicitud.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.curdfirestore.R

@Composable
fun mensajeNosolciitudes(){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(2.dp, Color.White)
            .background(Color.White)
            .fillMaxWidth()
            .padding(15.dp)
    ) {
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
            text = "En este momento, no tienes solicitudes registradas",
            style = TextStyle(
                color = Color(86, 86, 86),
                fontSize = 18.sp,
                textAlign = TextAlign.Justify,
            )
        )

    }
}