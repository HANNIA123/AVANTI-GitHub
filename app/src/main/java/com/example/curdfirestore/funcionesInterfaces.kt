package com.example.avanti.ui.theme.Aplicacion

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.curdfirestore.R
@Composable
fun cabecera(titulo: String, navController: NavController, ruta:String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    )
    {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
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
            Text(
                text = titulo,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(  painter = painterResource(id = R.drawable.notificacion),
                contentDescription = "not", tint = Color.White,
                modifier = Modifier.size(45.dp).clickable {
navController.navigate(ruta)
                }
            )
            Spacer(modifier = Modifier.width(15.dp))
            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.logoavanti),
                contentDescription = "Logo de Avanti",
                contentScale = ContentScale.FillBounds
            )
        }


    }
}

@Composable
fun cabeceraSin(titulo: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    )
    {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
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
            Text(
                text = titulo,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.weight(1f)
            )

            Image(
                modifier = Modifier.size(60.dp),
                painter = painterResource(id = R.drawable.logoavanti),
                contentDescription = "Logo de Avanti",
                contentScale = ContentScale.FillBounds
            )
        }


    }
}




@Composable
fun cabeceraConBotonAtras(
    titulo: String,
    navController: NavController,

    ) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    )
    {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
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

                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .padding(end = 16.dp) // Ajusta el espacio entre el icono y el texto
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "volver",
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
fun cabeceraAtrasRuta(
    titulo: String,
    navController: NavController, ruta: String

) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    )
    {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
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

                onClick = { navController.navigate(ruta)},
                modifier = Modifier
                    .padding(end = 16.dp) // Ajusta el espacio entre el icono y el texto
            ) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "volver",
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
fun CoilImage(url: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter(model = Uri.parse(url)),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun lineaGris() {
    Box(
        modifier = Modifier
            .width(350.dp)
            .height(1.dp)
            //.align(Alignment.CenterHorizontally)
            .background(Color(222, 222, 222))

    )
}
@Composable
fun lineaGrisCompleta() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .height(1.dp)
            //.align(Alignment.CenterHorizontally)
            .background(Color(222, 222, 222))

    )
}

@Composable
fun lineaGrisModificada(width: Dp, height: Dp, color: Color) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .background(color)
        //.align(Alignment.CenterHorizontally)
        //  .background(Color(222, 222, 222))

    )
}


@Composable
fun nombreCompleto(nombre: String, apellidop: String, apellidom: String): String {
    val fn: String = nombre + " " + apellidop + " " + apellidom
    return fn
}