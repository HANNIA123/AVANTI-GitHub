package com.example.avanti.ui.theme.Aplicacion

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.curdfirestore.R

@Composable
fun cabecera(titulo:String){
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
fun tituloAtras(Titulo: String,
                   navController: NavController
){
    Row {
        Row (
            modifier = Modifier
                .padding(5.dp, 10.dp, 0.dp, 0.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){

            Box {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        modifier= Modifier
                            .height(57.dp)
                            .width(57.dp)
                            .align(Alignment.Center),

                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Icono atras",
                        tint= Color(137, 13, 88),

                        )
                }

            }
            Text(
                text= Titulo,  style = TextStyle(
                    color= Color(71, 12, 107),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold



                )
            )

        }
        cabecera(Titulo)
    }
}
@Composable
fun tituloNoAtras(Titulo: String,
                navController: NavController
){
    Row {
        Row (
            modifier = Modifier
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ){



            Text(
                text= Titulo,  style = TextStyle(
                    color= Color(71, 12, 107),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold



                )
            )

        }
        cabecera(Titulo)
    }
}



@Composable
fun cabeceraConBotonAtras(titulo:String,
                          navController: NavController,

){
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

                onClick = { navController.popBackStack()},
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
fun lineaGris(){
    Box(
        modifier = Modifier
            .width(350.dp)
            .height(1.dp)
            //.align(Alignment.CenterHorizontally)
            .background(Color(222, 222, 222))

    )
}


@Composable
fun nombreCompleto(nombre: String, apellidop: String, apellidom: String):String {
    var fn: String
    fn= nombre+" "+ apellidop+ " "+ apellidom
    return  fn
}