package com.example.curdfirestore.Usuario.Pasajero

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.curdfirestore.R

@Composable
fun menuPas(
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

            navController.navigate(route = "horario_inicio/$userID")
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

            navController.navigate(route = "cuenta_pasajero/$userID")
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
