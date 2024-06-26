package com.example.curdfirestore.Horario.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.curdfirestore.R

@Composable
fun cabeceraConBotonCerrarHorario(titulo:String,
                                navController: NavController,
                                userid:String

){
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    )
    {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            painter = painterResource(id = R.drawable.fondorec),
            contentDescription = "Fondo inicial",
            contentScale = ContentScale.FillBounds
        )
        Row(
            modifier = Modifier
                .padding(18.dp, 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(

                onClick = { navController.navigate("general_horario_pasajero/$userid")},
                modifier = Modifier
                    .padding(end = 16.dp) // Ajusta el espacio entre el icono y el texto
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "volver",
                    tint = Color.White,
                    modifier = Modifier.size(35.dp)
                )
            }

            Text(
                text = titulo,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.weight(1f)
            )
        }


    }


}