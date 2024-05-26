package com.example.curdfirestore.Usuario.Conductor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.curdfirestore.R


@Composable
fun menuCon(
    navController: NavController,
    userID: String
) {

    Column {
        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .background(Color(126, 60, 127)))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
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



}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun menuDesplegableCon(
    onDismiss: () -> Unit,
    navController: NavController,
    userID: String,
) {

    val maxWidth =
        LocalConfiguration.current.screenWidthDp.dp / 2 // La mitad del ancho de la pantalla

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { // Este bloque se ejecutará al hacer clic fuera de la columna
                onDismiss()
            }
            .background(Color.Black.copy(alpha = 0.5f))

    ) {

        Column(

            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .width(maxWidth)
                .background(Color.White)
                .fillMaxHeight()

        ) {

            Spacer(modifier = Modifier.height(40.dp))


            TextButton(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    navController.navigate(route = "home/$userID")
                }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp),

                    tint = Color(137, 13, 88),
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Inicio",
                    textAlign = TextAlign.Left,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(137, 13, 88),

                        )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))


            TextButton(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    println("boroon presionado")
                    navController.navigate(route = "viaje_inicio/$userID")
//                        onDismiss()
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.carro),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp),
                    tint = Color(137, 13, 88),
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "Viaje",
                    textAlign = TextAlign.Left,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(137, 13, 88),

                        )
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            TextButton(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    navController.navigate(route = "cuenta_conductor/$userID")
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.btuser),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp),
                    tint = Color(137, 13, 88),
                )
                Spacer(modifier = Modifier.width(15.dp))
                Text(
                    text = "Cuenta",
                    textAlign = TextAlign.Left,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color(137, 13, 88),

                        )
                )
            }


        }
    }
}

@Composable
fun cabeceraConMenuCon(titulo:String
,
                       navController: NavController,
                       userID: String,
                       boton: (Boolean) -> Unit

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
                onClick = {
                    boton (true)
                          },
                modifier = Modifier
                    .padding(end = 16.dp) // Ajusta el espacio entre el icono y el texto
            ) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Abrir menú",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
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
@Composable
fun cabeceraConMenuConNot(titulo:String
                       ,
                       navController: NavController,

                       boton: (Boolean) -> Unit,
                          ruta:String

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
                onClick = {
                    boton (true)
                },
                modifier = Modifier
                    .padding(end = 16.dp) // Ajusta el espacio entre el icono y el texto
            ) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = "Abrir menú",
                    tint = Color.White,
                    modifier = Modifier.size(30.dp)
                )
            }
            Text(
                text = titulo,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(start = 16.dp) // Ajusta el espacio entre el icono y el texto
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.notificacion),
                    contentDescription = "not",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            navController.navigate(ruta)
                        }
                )

            }

        }
    }

}
