package com.example.curdfirestore.Parada.ConsultasParada

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.ParadaData
import com.example.avanti.Usuario.RetrofitClientParada
import com.example.avanti.ViajeData
import com.example.avanti.ViajeDataReturn
import com.example.curdfirestore.Parada.Funciones.obtenerDistanciaParadas
import com.example.curdfirestore.Parada.Pantallas.ventanaNoEncontrado
import com.example.curdfirestore.Parada.Pantallas.verParadasCercanasPas

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun conBuscarParadasPas(
    navController: NavController,
    correo: String,
    horarioId: String,
    viajes:  List<ViajeData>
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
        obtenerDistanciaParadas(
            navController = navController,
            correo = correo,
            viajes = viajes,
            paradas = paradas!!,
            horarioId = horarioId
        )

    }
    if(!busqueda){
        show=true
        ventanaNoEncontrado(
            show = show,
            onDismiss = {show=false },
            onConfirm = { /*TODO*/ },
            userId = correo,
            navController = navController
        )

    }

}


