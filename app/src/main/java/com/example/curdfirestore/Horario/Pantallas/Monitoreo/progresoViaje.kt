package com.example.curdfirestore.Horario.Pantallas.Monitoreo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.avanti.ParadaData
import com.example.avanti.ui.theme.Aplicacion.lineaGrisModificada

@Composable
fun barraProgresoViaje(
    totalParadas: Int,
    viajeComenzado: List<Pair<String, ParadaData>>,
    maxw: Dp,
    listParadasRecorridas:  List<Pair<String, ParadaData>>
) {
    val lineH = 3.dp
    val lineaW = maxw / (totalParadas + 2)
    Column(
        modifier = Modifier
            .height(45.dp)
            .background(Color.White)
            .padding(10.dp)
    ) {
        Text(
            "Progreso del viaje...",
            fontSize = 12.sp,
            color = Color(165, 165, 165)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            for (i in 0..(totalParadas + 2)) {
                val color: Color
                if (viajeComenzado.isEmpty()) {
                    color = Color(222, 222, 222)
                } else {
                    val numeros = listParadasRecorridas.size
                    color = if (i <= numeros) {
                        Color.Blue
                    } else {
                        Color(222, 222, 222)
                    }
                }
                lineaGrisModificada(
                    width = lineaW,
                    height = lineH,
                    color = color
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}
