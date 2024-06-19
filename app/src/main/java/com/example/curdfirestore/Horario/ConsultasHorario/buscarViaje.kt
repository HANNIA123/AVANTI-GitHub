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
import com.example.avanti.ViajeData
import com.example.avanti.ui.theme.Aplicacion.convertirStringAHora
import com.example.curdfirestore.Horario.RetrofitClientHorario
import com.example.curdfirestore.Parada.ConsultasParada.conBuscarParadasPas
import com.example.curdfirestore.Parada.Pantallas.ventanaNoEncontrado


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
                busqueda = true
            } else {
                showViaje = true
                println("NOOO  viajeee")
                text = "No se encontró ningún viaje que coincida con tu búsqueda"

            }

        } catch (e: Exception) {
            text = "Error al obtener viaje: $e"
            println("Error al obtener viaje: $e")
        } finally {
            fin = true
        }
    }

    var final by remember {
        mutableStateOf(false)
    }
    var ejecutado by remember {
        mutableStateOf(false)
    }
    val nuevaListaDeViajes = mutableListOf<ViajeData>()
    if (fin) {
        println("fin es true")
        if (viajes != null) {
            println("VIAJEEEES $viajes")
            viajes!!.forEachIndexed { index, viaje ->

                val horario = conObtenerHorarioId(horarioId = horarioId)
                horario?.let { horario ->
                    println("HORARIO $horario")
                    val trayectoHorario= horario.horario_trayecto
                    val horaHorario = convertirStringAHora(horario.horario_hora)
                    println("TRAYECTO HORARIO ${horario.horario_trayecto}")
                    val horaTipoViaje =
                        if (trayectoHorario == "0") {

                            viaje.viaje_hora_partida
                        } else {

                            viaje.viaje_hora_llegada

                        }
                    val horaViaje = convertirStringAHora(horaTipoViaje)
                    val horariomas = horaHorario.plusMinutes(30)
                    val horariomenos = horaHorario.minusMinutes(30)

println("horaViaje $horaViaje y horaHorario $horaHorario  horamas: $horariomas horamenos $horariomenos")


                        if ((horaViaje.isBefore(horariomas) && horaViaje.isAfter(horariomenos)) || horaViaje == horariomenos || horaViaje == horariomas || horaViaje == horaHorario) {
                            nuevaListaDeViajes.add(viaje)
                            println("Encuentraa")
                        }


                    // Verifica si es la última iteración del bucle viajes
                    val isLastViaje = index == viajes!!.size - 1

                    if (isLastViaje) {
                        final = true
                    }

                }


            }


        } else {
            println("No encontro nada")
            showViaje = true


        }


        if (final) {
            if (nuevaListaDeViajes.isEmpty()) {
                println("lista vacia!!!! ")
                showViaje = true
            } else {
                conBuscarParadasPas(
                    navController = navController,
                    correo = correo,
                    horarioId = horarioId,
                    viajes = nuevaListaDeViajes
                )
            }
        }

        if (showViaje) {
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
