package com.example.curdfirestore.Usuario.Conductor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.ui.theme.Aplicacion.encabezado
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaHoyCompleto
import com.example.curdfirestore.R


@Composable
fun menuCon(
    navController: NavController,
    userID:String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        IconButton(onClick = {

            navController.navigate(route = "home/$userID")
        }) {
            Icon(
                modifier = Modifier
                    .size(35.dp),
                imageVector = Icons.Filled.Home,
                contentDescription = "Icono Home- inicio de viajes",
                tint = Color(137, 13, 88),

                )
        }
        IconButton(onClick = {

            navController.navigate(route = "viaje_inicio/$userID")
        }) {
            Icon(
                modifier = Modifier
                    .size(35.dp),

                painter = painterResource(id = R.drawable.car),
                contentDescription = "Icono Viajes",
                tint = Color(137, 13, 88)
            )
        }
        IconButton(onClick = {

            navController.navigate(route = "cuenta_conductor/$userID")
        }) {

            Icon(
                modifier = Modifier
                    .size(35.dp),
                painter = painterResource(id = R.drawable.btuser),
                contentDescription = "Icono Usuario",
                tint = Color(137, 13, 88),

                )
        }
    }
}

//
@Composable
fun tituloPantallaInicio(){
    var fechaHoy= obtenerFechaHoyCompleto()

    Row {
        Row (
            modifier = Modifier
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){



            Text(
                text = fechaHoy,
                style = TextStyle(
                    color = Color(71, 12, 107),
                    fontSize = 28.sp,
                    textAlign = TextAlign.Start

                )
            )

        }
        encabezado()
    }
}