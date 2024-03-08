package com.example.curdfirestore

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Recuadro titulo
@Composable
fun recuadroTitulos(titulo:String){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .border(2.dp, Color.White)

            .background(Color.White)
            .fillMaxWidth()


    ) {
        Text(
            text = titulo,
            style = TextStyle(
                color = Color(71, 12, 107),
                fontSize = 25.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold

            ),
            modifier= Modifier

                .padding(13.dp)



        )

    }
}
@Composable
fun textoHoraViaje(hora:String){
    Text(
        text = hora,
        style = TextStyle(
            color = Color.Black,
            fontSize = 17.sp,
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier
            .width(110.dp) // Ajusta el ancho del primer texto según tus necesidades
    )

}
@Composable
fun textoInformacionViaje(etiqueta: String, contenido: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("$etiqueta: ")
            }
            withStyle(style = SpanStyle(color = Color(86,86,86))) {
                append("$contenido")
            }

        },
        fontSize = 18.sp,
        textAlign = TextAlign.Justify
    )
}


@Composable
fun recuadroContenido(contenido:String){
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .border(2.dp, Color.White)

            .background(Color.White)
            .fillMaxWidth()


    ) {
        Text(
            text = contenido,
            style = TextStyle(
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,


            ),
            modifier= Modifier

                .padding(13.dp)



        )

    }
}

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
