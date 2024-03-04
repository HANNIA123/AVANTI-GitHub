package com.example.curdfirestore

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfTextos(Title: String, Inf: String ){
    Text(
        text = Title,
        modifier = Modifier
            .offset(x = 30.dp)
            .padding(2.dp),
        style = TextStyle(
            color = Color.Black,
            fontSize = 18.sp
        )
    )
    Text(
        text = Inf,
        modifier = Modifier
            .offset(x = 30.dp)
            .padding(2.dp),
        style = TextStyle(
            color = Color(93, 93, 93),
            fontSize = 18.sp,
            textAlign = TextAlign.Start,

        )
    )

}
