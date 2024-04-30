package com.example.curdfirestore.Parada.Funciones

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.HorarioData
import com.example.avanti.ParadaData
import com.example.avanti.ViajeData
import com.example.avanti.ViajeDataReturn
import com.example.curdfirestore.Horario.ConsultasHorario.conObtenerHorarioId
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerListaParadas
import com.example.curdfirestore.Parada.Pantallas.ventanaNoEncontrado
import com.example.curdfirestore.Parada.Pantallas.verParadasCercanasPas
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeId
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import androidx.compose.runtime.collectAsState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.runBlocking

//Pantalla desp ues de la busqueda de paradas que coincidan con el horario
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun obtenerDistanciaParadas(
    navController: NavController,
    correo: String,
    viajes: List<ViajeData>,
    paradas: List<ParadaData>,
    horarioId: String

) {


    var filterparadas by remember { mutableStateOf<MutableList<ParadaData>>(mutableListOf()) }
    var coordenadasObtenerDis by remember { mutableStateOf(LatLng(0.0, 0.0)) }

    val horario = conObtenerHorarioId(horarioId = horarioId)
    var validar by remember { mutableStateOf(false) }
    var show by rememberSaveable { mutableStateOf(false) }
    horario?.let {
        val horarioOrigen = convertirStringALatLng(horario.horario_origen)
        val horarioDestino = convertirStringALatLng(horario.horario_destino)

        viajes.forEachIndexed { indexViaje, viaje ->
            val numLugares = (viaje.viaje_num_lugares).toInt()
            val statusViaje = viaje.viaje_status
            paradas.forEach { parada ->
                val paradaCoordenadas = convertirStringALatLng(parada.par_ubicacion)

                coordenadasObtenerDis = if (viaje.viaje_trayecto == "0") {

                    horarioDestino!!

                } else {
                    horarioOrigen!!
                }


                if (numLugares > 0 && statusViaje == "Disponible") {
                    val distancia =
                        getDistanceCorrecto(coordenadasObtenerDis!!, paradaCoordenadas!!)


                    if (distancia <= 1500.0f) {
                        println("entra al if de distancia")

                        val nuevaP =
                            ParadaData(
                                user_id = parada.user_id,
                                viaje_id = parada.viaje_id,
                                par_nombre = parada.par_nombre,
                                par_hora = parada.par_hora,
                                par_ubicacion = parada.par_ubicacion,
                                par_id = parada.par_id
                            )
                        filterparadas.add(nuevaP)

                        println("Filtrado $filterparadas")
                    }

                }

            }

            // Verifica si es la última iteración del bucle viajes
            val isLastViaje = indexViaje == viajes.size - 1

            if (isLastViaje) {
                validar = true
            }
        }

    }
    println("FINAL LISTA----- $filterparadas")

    if (validar) {


        if (filterparadas.isEmpty()) {
            show = true
            ventanaNoEncontrado(
                show = show,
                { show = false },
                {},
                userId = correo,
                navController = navController
            )

        } else {
            println("Encontramos paradaaaaa")
            verParadasCercanasPas(
                navController = navController,
                correo = correo,
                horarioId = horarioId,
                paradas = filterparadas!!
            )
        }

    }

}
