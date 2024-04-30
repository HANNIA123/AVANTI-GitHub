package com.example.curdfirestore.Reportes.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.avanti.UserData
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.lineaGris


@Composable
fun dialogoContactoConductor(
    onDismiss: () -> Unit,
    usuarioCon: UserData,
    conductor_id: String,
) {
    val tamEspacio = 15.dp
    val tamIcono = 55.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),

        ) {


        Dialog(
            onDismissRequest = {
                onDismiss()
                //expanded = false
            }, // Cierra el diálogo al tocar fuera de él
            content = {

                // Contenido del diálogo
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(15.dp)


                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //textTituloInfSolcitud("Reporte")

                        CoilImage(
                            url = usuarioCon.usu_foto,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .align(Alignment.CenterHorizontally), // Centrar horizontalmente
                        )
                        val nombreMostrar =
                            "${usuarioCon.usu_nombre} ${usuarioCon.usu_primer_apellido}"

                        Text(
                            text = nombreMostrar,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold

                            )
                        )


                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    lineaGris()
                    Spacer(modifier = Modifier.height(5.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Phone,
                            contentDescription = "Icono telefono",
                            modifier = Modifier
                                .size(tamIcono)
                                .padding(10.dp, 5.dp),
                            tint = Color(137, 13, 86)
                        )
                        Text(
                            text = usuarioCon.usu_telefono,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold

                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(tamEspacio))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        TextButton(
                            onClick = { onDismiss() },
                            modifier = Modifier.height(48.dp)
                        ) {
                            Text(
                                text = "CERRAR",
                                style = TextStyle(
                                    color = Color(137, 67, 242),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            )
                        }
                    }

                }


            },

            )

    }



}