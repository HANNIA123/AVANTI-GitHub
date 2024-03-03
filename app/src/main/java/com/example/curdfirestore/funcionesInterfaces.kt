package com.example.avanti.ui.theme.Aplicacion

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.curdfirestore.R

@Composable
fun encabezado(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        contentAlignment = Alignment.TopEnd
    )
    {
        Image(
            modifier = Modifier
                .width(100.dp),
            painter = painterResource(id = R.drawable.elipselado),
            contentDescription = "Fondo inicial",
            contentScale = ContentScale.FillWidth

        )
        Image(
            modifier = Modifier
                .width(75.dp)
                .padding(5.dp, 5.dp, 10.dp, 5.dp),
            painter = painterResource(id = R.drawable.logoavanti),
            contentDescription = "Logo",
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun TituloPantalla(Titulo: String,
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
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center

                )
            )

        }
        encabezado()
    }
}

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

            navController.navigate(route = "cuenta_conductor/$userID")
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
@Composable
fun CoilImage(url: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter(model = Uri.parse(url)),
        contentDescription = null,
        modifier = modifier
    )
}

