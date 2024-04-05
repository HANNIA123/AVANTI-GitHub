package com.example.curdfirestore.Horario.ConsultasHorario

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.ViajeDataReturn
import com.example.curdfirestore.Horario.RetrofitClientHorario
import com.example.curdfirestore.Parada.ConsultasParada.conBuscarParadasPas

/*Primero busca un viaje que coincida con los datos que porporciono el pasajero,
de acuerdo al día y tipo de trayecto (no se considera el horario para una mayor probabilidad de
encontrar paradas. Esta busqueda se hace en el servidor.
* */
@SuppressLint("RememberReturnType")
@Composable
fun conBuscarViajePas(
    navController: NavController,
    correo: String,
    horarioId: String,

    ) {
    //Obtener lista de viajes (Itinerario)
    var viajes by remember { mutableStateOf<List<ViajeDataReturn>?>(null) }
    var show by rememberSaveable { mutableStateOf(false) }

    var text by remember { mutableStateOf("") }
    var busqueda by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = true) {
        try {
            //val  resultadoViaje = RetrofitClient.apiService.busquedaViajesPas(horarioId)
            val response = RetrofitClientHorario.apiService.busquedaViajesPas(horarioId)
            if (response.isSuccessful) {
                viajes = response.body()
            } else {
                text = "No se encontró ningún viaje que coincida con tu búsqueda"
                busqueda = true
            }
            // Haz algo con el objeto Usuario

        } catch (e: Exception) {
            text = "Error al obtener viaje: $e"
            println("Error al obtener viaje: $e")
        }
    }



    if (viajes != null && busqueda == false) {
        println("Encontramos viajeee")
        //Se encontro un viaje, ahora buscar la parada
        conBuscarParadasPas(
            navController = navController,
            correo = correo,
            horarioId = horarioId,
            viajes = viajes!!
        )
        // BusquedaParadasPasajero(navController,correo, horarioId, viajes!!) //Pantalla de home
    }
    if (busqueda == true) {
        show = true
        println("no encontramos viaje")
        //VentanaNoFound(navController, correo,show,{show=false }, {}) //no encontro ninguna coincidencia

    }

}
