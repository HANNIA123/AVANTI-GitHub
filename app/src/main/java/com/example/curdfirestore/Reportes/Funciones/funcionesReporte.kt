package com.example.curdfirestore.Reportes.Funciones

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun convertiraMotivo(numMotivo: Set<Int>):String{
    var motivo by remember {
        mutableStateOf("")
    }

    motivo = when (numMotivo) {
        setOf(1) -> "Acoso"
        setOf(2) -> "Cancelación de viaje"
        setOf(3) -> "No asistió a punto de encuentro"
        setOf(4) -> "Malas prácticas de conducción"
        setOf(5) -> "Otros"
        else -> ""
    }
    return motivo

}

@Composable
fun convertiraMotivoPas(numMotivo: Set<Int>):String{
    var motivo by remember {
        mutableStateOf("")
    }

    motivo = when (numMotivo) {
        setOf(1) -> "Acoso"
        setOf(2) -> "Cancelación de viaje"
        setOf(3) -> "No asistió a punto de encuentro"
        setOf(4) -> "Llegó tarde al punto de encuentro"
        setOf(5) -> "Otros"
        else -> ""
    }
    return motivo
}