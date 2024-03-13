package com.example.curdfirestore.Viaje

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


//Para los dialogos
@Composable
fun convertirADia(numDia: Set<Int>):String{
    var dia by remember {
        mutableStateOf("")
    }

    dia = when (numDia) {
        setOf(1) -> "Lunes"
        setOf(2) -> "Martes"
        setOf(3) -> "Miércoles"
        setOf(4) -> "Jueves"
        setOf(5) -> "Viernes"
        setOf(6) -> "Sábado"
        setOf(7) -> "Domingo"
        else -> ""
    }
    return dia

}

//Solo para el dialogo!
@Composable
fun convertirATrayecto(numTrayecto: Set<Int>):String{
    var trayecto by remember {
        mutableStateOf("")
    }

    trayecto = when (numTrayecto) {
        setOf(1) -> "UPIITA como origen"
        setOf(2) -> "UPIITA como destino"

        else -> ""
    }
    return trayecto

}