package com.example.curdfirestore.Horario.ConsultasHorario

import android.annotation.SuppressLint
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
import com.example.avanti.Usuario.BASE_URL
import com.example.avanti.ViajeData
import com.example.avanti.ViajeDataReturn
import com.example.curdfirestore.Horario.ApiServiceHorario
import com.example.curdfirestore.Horario.RetrofitClientHorario
import com.example.curdfirestore.Parada.ConsultasParada.conBuscarParadasPas
import com.example.curdfirestore.Parada.Pantallas.ventanaNoEncontrado
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*Primero busca un viaje que coincida con los datos que porporciono el pasajero,
de acuerdo al día y tipo de trayecto (no se considera el horario para una mayor probabilidad de
encontrar paradas. Esta busqueda se hace en el servidor.
* */

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@Composable
fun conBuscarViajePas(
    navController: NavController,
    correo: String,
    horarioId: String,

    ) {
    //Obtener lista de viajes (Itinerario)
    var viajes by remember { mutableStateOf<List<ViajeData>?>(null) }
    var showViaje by rememberSaveable { mutableStateOf(false) }
    var fin by rememberSaveable { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    var busqueda by remember { mutableStateOf(false) }


    LaunchedEffect(key1 = true) {
        try {
            //val  resultadoViaje = RetrofitClient.apiService.busquedaViajesPas(horarioId)
            val response = RetrofitClientHorario.apiService.busquedaViajesPas(horarioId)
            if (response.isSuccessful) {
                println("Encontramos viajeee")
                viajes = response.body()
                busqueda=true
            } else {
                showViaje=true
                println("NOOO  viajeee")
                text = "No se encontró ningún viaje que coincida con tu búsqueda"

            }

        } catch (e: Exception) {
            text = "Error al obtener viaje: $e"
            println("Error al obtener viaje: $e")
        }
        finally {
            fin=true
        }
    }


    if(fin){
        if (viajes != null && busqueda == true) {
            println("Encontramos viajeee")
            //Se encontro un viaje, ahora buscar la parada
            conBuscarParadasPas(
                navController = navController,
                correo = correo,
                horarioId = horarioId,
                viajes = viajes!!
            )

        }
        else{
            showViaje=true
            ventanaNoEncontrado(
                show = showViaje,
                { showViaje = false },
                {},
                userId = correo,
                navController = navController
            )


        }


    }

}
