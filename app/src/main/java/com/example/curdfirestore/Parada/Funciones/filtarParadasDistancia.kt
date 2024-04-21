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
import com.example.curdfirestore.Parada.Pantallas.ventanaNoEncontrado
import com.example.curdfirestore.Parada.Pantallas.verParadasCercanasPas
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng

//Pantalla desp ues de la busqueda de paradas que coincidan con el horario
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun obtenerDistanciaParadas(
    navController: NavController,
    correo: String,
    viajes:  List<ViajeData>,
    paradas:  List<ParadaData>,
    horarioId:String

) {
    val filterviajes by remember { mutableStateOf<List<ParadaData>?>(null) }

    var filterparadas by remember { mutableStateOf<List<ParadaData>?>(null) }
    var coordenadasDis by remember { mutableStateOf("") }

    val horario = conObtenerHorarioId(horarioId = horarioId)
    var validar by remember { mutableStateOf(false) }
    var validarcontenido by remember { mutableStateOf(false) }
    var show by rememberSaveable { mutableStateOf(false) }



    horario?.let {
        val listaActual = filterviajes?.toMutableList() ?: mutableListOf()

        //Coordenadas del pasajero
        var LatHorarioDes by remember { mutableStateOf(0.0) }
        var LonHorarioDes by remember { mutableStateOf(0.0) }

        var LatHorarioOri by remember { mutableStateOf(0.0) }
        var LonHorarioOri by remember { mutableStateOf(0.0) }



        var markerLatO by remember { mutableStateOf(0.0) }
        var markerLonO by remember { mutableStateOf(0.0) }
        val markerCoordenadasLatLngDes = convertirStringALatLng(horario!!.horario_destino)
        if (markerCoordenadasLatLngDes != null) {
            LatHorarioDes = markerCoordenadasLatLngDes.latitude
            LonHorarioDes = markerCoordenadasLatLngDes.longitude
        }


        val markerCoordenadasLatLngOri = convertirStringALatLng(horario!!.horario_origen)

        if (markerCoordenadasLatLngOri != null) {
            LatHorarioOri = markerCoordenadasLatLngOri.latitude
            LonHorarioOri = markerCoordenadasLatLngOri.longitude
            // Hacer algo con las coordenadas LatLng

        } else {
            // La conversión falló
            println("Error al convertir la cadena a LatLng")
        }


        var indiceViaje = 0
        while (indiceViaje < viajes.size) {
            var indiceParada = 0
            val viaje = viajes[indiceViaje]
            var lugaresDisp=0
            try{
                // Intenta convertir el String a Int
                lugaresDisp=viaje.viaje_num_lugares.toInt()
            }catch(e: NumberFormatException){
                println("No se pudo convertir el String a Int: ${e.message}")
            }

            if (viaje.viaje_status == "Disponible" && lugaresDisp>0) {  //Validar el numero de lugares
                if (viaje.viaje_trayecto == "0") {
                    coordenadasDis = horario.horario_destino
                } else {
                    coordenadasDis = horario.horario_origen
                }
                while (indiceParada < paradas.size) {
                    //Verificar

                    val parada = paradas[indiceParada]

                    val markerCoordenadasLatLngO =
                        convertirStringALatLng(parada.par_ubicacion)
                    if (markerCoordenadasLatLngO != null) {
                        markerLatO = markerCoordenadasLatLngO.latitude
                        markerLonO = markerCoordenadasLatLngO.longitude
                        // Hacer algo con las coordenadas LatLng
                    }

                    val distance = getDistance(coordenadasDis, parada.par_ubicacion)

                    if (distance <= 1000.0f) {
                        val nuevaP =
                            ParadaData(
                                user_id = parada.user_id,
                                viaje_id = parada.viaje_id,
                                par_nombre = parada.par_nombre,
                                par_hora = parada.par_hora,
                                par_ubicacion = parada.par_ubicacion,
                                par_id = parada.par_id
                            )
                        validarcontenido = true
                        listaActual.add(nuevaP)
                        println("Lista actual $listaActual")

                    }
                    println("El tipo de dato de miVariable es: ${distance::class.simpleName}")
                    indiceParada++
                }

                if (indiceViaje == viajes.size - 1) {
                    validar = true

                }
            }
            indiceViaje++

        }

        if(validar==true) {

            if (validarcontenido) {

                println("Encontramos paradaaaaa")
                verParadasCercanasPas(
                    navController = navController,
                    correo = correo,
                    horarioId = horarioId,
                    paradas = listaActual
                )

            } else {
                show=true
                ventanaNoEncontrado(
                    show = show,
                    { show = false },
                    {},
                    userId = correo,
                    navController = navController
                )
              //  VentanaLejos(navController, correo, show, { show = false }, {})
            }
            println("filtro: $listaActual")
        }
        else{
            show = true
            ventanaNoEncontrado(
                show = show,
                { show = false },
                {},
                userId = correo,
                navController = navController
            )
        }
    }

}

/*
@Composable
fun obtenerDistanciaParadas(
    viajes:  List<ViajeData>,
    paradas:  List<ParadaData>,
    horario: HorarioData,
) : List<ParadaData>?{

    val filterviajes by remember { mutableStateOf<List<ParadaData>?>(null) }
    var coordenadasDis by remember { mutableStateOf("") }


    var validar by remember { mutableStateOf(false) }
    var validarcontenido by remember { mutableStateOf(false) }
    var fin by rememberSaveable { mutableStateOf(false) }


    val listaActual = filterviajes?.toMutableList() ?: mutableListOf()



        //Coordenadas del pasajero
        var LatHorarioDes by remember { mutableStateOf(0.0) }
        var LonHorarioDes by remember { mutableStateOf(0.0) }

        var LatHorarioOri by remember { mutableStateOf(0.0) }
        var LonHorarioOri by remember { mutableStateOf(0.0) }


        var markerLatO by remember { mutableStateOf(0.0) }
        var markerLonO by remember { mutableStateOf(0.0) }
        val markerCoordenadasLatLngDes = convertirStringALatLng(horario!!.horario_destino)
        if (markerCoordenadasLatLngDes != null) {
            LatHorarioDes = markerCoordenadasLatLngDes.latitude
            LonHorarioDes = markerCoordenadasLatLngDes.longitude
        }


        val markerCoordenadasLatLngOri = convertirStringALatLng(horario!!.horario_origen)

        if (markerCoordenadasLatLngOri != null) {
            LatHorarioOri = markerCoordenadasLatLngOri.latitude
            LonHorarioOri = markerCoordenadasLatLngOri.longitude
            // Hacer algo con las coordenadas LatLng

        } else {
            // La conversión falló
            println("Error al convertir la cadena a LatLng")
        }


        var indiceViaje = 0
        while (indiceViaje < viajes.size) {
            var indiceParada = 0
            val viaje = viajes[indiceViaje]
            var lugaresDisp = 0
            try {
                // Intenta convertir el String a Int
                lugaresDisp = viaje.viaje_num_lugares.toInt()
            } catch (e: NumberFormatException) {
                println("No se pudo convertir el String a Int: ${e.message}")
            }

            if (viaje.viaje_status == "Disponible" && lugaresDisp > 0) {  //Validar el numero de lugares
                if (viaje.viaje_trayecto == "0") {
                    coordenadasDis = horario.horario_destino
                } else {
                    coordenadasDis = horario.horario_origen
                }
                while (indiceParada < paradas.size) {
                    //Verificar

                    val parada = paradas[indiceParada]

                    val markerCoordenadasLatLngO =
                        convertirStringALatLng(parada.par_ubicacion)
                    if (markerCoordenadasLatLngO != null) {
                        markerLatO = markerCoordenadasLatLngO.latitude
                        markerLonO = markerCoordenadasLatLngO.longitude
                        // Hacer algo con las coordenadas LatLng
                    }

                    val distance = getDistance(coordenadasDis, parada.par_ubicacion)

                    if (distance <= 1000.0f) {
                        val nuevaP =
                            ParadaData(
                                user_id = parada.user_id,
                                viaje_id = parada.viaje_id,
                                par_nombre = parada.par_nombre,
                                par_hora = parada.par_hora,
                                par_ubicacion = parada.par_ubicacion,
                                par_id = parada.par_id
                            )
                        validarcontenido = true
                        listaActual.add(nuevaP)
                        println("Lista actual $listaActual")

                    }
                    println("El tipo de dato de miVariable es: ${distance::class.simpleName}")
                    indiceParada++
                }

                if (indiceViaje == viajes.size - 1) {
                    validar = true

                }
            }
            indiceViaje++

        }



    println("Sin datos $listaActual ----------------")

    return listaActual


}
*/