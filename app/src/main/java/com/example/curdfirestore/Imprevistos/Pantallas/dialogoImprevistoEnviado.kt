package com.example.curdfirestore.Imprevistos.Pantallas

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.curdfirestore.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val paddingIma=15.dp
val paddingTex=15.dp
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun dialogoImprevistoEnviado(
    onDismiss: () -> Unit,
    navController: NavController,
    userId:String,
    ruta: String
) {

    val fadeInAlpha = animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1000), label = ""
    ).value

    val scope = rememberCoroutineScope()

    scope.launch {
        delay(1000)
        navController.navigate(ruta)
        onDismiss()

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.2f)),
    ) {
        Dialog(
            onDismissRequest = { },
            // No permitir cerrar el diálogo al tocar fuera de él
            content = {
                Column(
                    modifier = Modifier

                        .padding(15.dp)
                        .background(Color.White)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Imprevisto enviado",
                        style = TextStyle(Color(137,  13, 88 )),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(paddingTex)
                    )
                    Image(
                        modifier = Modifier
                            .height(140.dp)
                            .width(120.dp)
                            .padding(paddingIma)
                            .align(Alignment.CenterHorizontally), // Centrar horizontalmente
                        painter = painterResource(id = R.drawable.solaceptada),
                        contentDescription = "Imagen pregunta",
                        contentScale = ContentScale.FillBounds,
                        alpha = fadeInAlpha
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

            },
        )
    }
}

