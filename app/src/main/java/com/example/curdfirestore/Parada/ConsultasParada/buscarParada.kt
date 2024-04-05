package com.example.curdfirestore.Parada.ConsultasParada

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.ParadaData
import com.example.avanti.Usuario.RetrofitClientParada
import com.example.avanti.ViajeDataReturn

@Composable
fun conBuscarParadasPas(
    navController: NavController,
    correo: String,
    horarioId: String,
    viajes:  List<ViajeDataReturn>
) {
    var paradas by remember { mutableStateOf<List<ParadaData>?>(null) }
    var text by remember { mutableStateOf("") }
    var busqueda by remember {
        mutableStateOf(true)
    }
    var show by remember {
        mutableStateOf(false)
    }
    //var listaParadas by remember { mutableStateOf<List<ViajeDataReturn>?>(null) }
    val listaParadas = mutableListOf<String>() // Reemplaza String con el tipo de elemento que deseas almacenar
    for (id in viajes){
        listaParadas.add(id.viaje_id)
    }
    val resultado = listaParadas.joinToString(",")
    LaunchedEffect(key1=true ) {
        try {
            val  resultadoParada = RetrofitClientParada.apiService.busquedaParadasPas(resultado)
            paradas=resultadoParada
            busqueda=true
        } catch (e: Exception) {
            busqueda=false
            text="Error al obtener parada: $e"
            println("Error al obtener viaje: $e")
        }
    }

    if (paradas!=null){
        //Obtenemos los datos del ultimo horario registrado y en esa cargamos la panatlla
        /*ObtenerHorario(navController = navController,
            correo =correo , viajes = viajes!!, paradas =paradas!!, horarioId )*/
    }
    if(!busqueda){
        show=true
        //VentanaNoFound(navController, correo,show,{show=false }, {})
    }
}
