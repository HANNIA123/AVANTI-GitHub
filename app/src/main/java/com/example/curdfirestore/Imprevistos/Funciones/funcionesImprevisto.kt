package com.example.curdfirestore.Imprevistos.Funciones

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun convertiraMotivoImprevisto(numMotivo: Set<Int>):String{
    var motivo by remember {
        mutableStateOf("")
    }

    motivo = when (numMotivo) {
        setOf(1) -> "Problemas mecánicos"
        setOf(2) -> "Problemas eléctricos"
        setOf(3) -> "Congestionamiento vehícular"
        setOf(4) -> "Problemas con los neumáticos"
        setOf(5) -> "Otros"
        else -> ""
    }
    return motivo

}