package com.example.curdfirestore.Viaje.Funciones

import androidx.navigation.NavController
import com.example.avanti.HistorialViajesData
import com.example.avanti.ParadaData
import com.example.avanti.SolicitudData
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaFormatoddmmyyyy
import com.example.avanti.ui.theme.Aplicacion.obtenerHoraActual
import com.example.curdfirestore.Horario.ConsultasHorario.actualizarHorarioPas
import com.example.curdfirestore.MainActivity
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParada
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParadaPorViaje
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.actualizarCampoSolicitud
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.actualizarCampoSolicitudPorBusqueda
import com.example.curdfirestore.Viaje.ConsultasViaje.editarCampoViajeSinRuta
import com.example.curdfirestore.Viaje.ConsultasViaje.editarDocumentoHistorial
import com.example.curdfirestore.Viaje.ConsultasViaje.registrarHistorialViaje
import kotlinx.coroutines.runBlocking


fun accionesComienzoViaje(viajeId: String, solicitudes: List<SolicitudData>?, conductorId: String, hisCreado:Boolean, idInicioViaje:String) {
    //Enviar notificaciones de comienzo de viaje

    //El conductor comenzo el viaje, enviar notificacion al pasajero
    editarCampoViajeSinRuta(
        documentId = viajeId,
        campo = "viaje_iniciado",
        valor = "si"
    )
    solicitudes?.forEach {
        actualizarHorarioPas(
            it.horario_id,
            "horario_iniciado",
            "si"
        )
        actualizarCampoSolicitud(
            it.solicitud_id,
            "solicitud_viaje_iniciado",
            "si"
        )

    }
    actualizarCampoParadaPorViaje(
        viajeId,
        "para_viaje_comenzado",
        "si"
    )
    editarCampoViajeSinRuta(viajeId, "viaje_iniciado", "si")


    if(!hisCreado) {
        val viajeIniciado = HistorialViajesData(
            conductor_id = conductorId,
            hora_inicio_viaje = obtenerHoraActual(),
            validacion_conductor_inicio = true,
            bloqueo_inicio_viaje = false,
            fecha_inicio_viaje = obtenerFechaFormatoddmmyyyy(),
            viaje_iniciado = true,
            viaje_id = viajeId
        )
        registrarHistorialViaje(viajeIniciado)
    }else{
        val nuevosValores = mapOf(
            "validacion_conductor_inicio" to true,
            "bloqueo_inicio_viaje" to false,
            "hora_inicio_viaje" to obtenerHoraActual(),
            "fecha_inicio_viaje" to obtenerFechaFormatoddmmyyyy(),
            "viaje_iniciado" to true

        )

        editarDocumentoHistorial(idInicioViaje, nuevosValores)

    }

}


fun accionesTerminoViaje(
    viajeId: String, solicitudes: List<SolicitudData>?,
    navController: NavController, userId: String, paradaId: String,
    paradas: List<Pair<String, ParadaData>>?
) {
    runBlocking {
        paradas?.forEach { parad ->
            actualizarCampoSolicitudPorBusqueda(
                "parada_id",
                parad.first,
                "solicitud_validacion_conductor",
                "pendiente"

            )
            actualizarCampoSolicitudPorBusqueda(
                "parada_id",
                parad.first,
                "solicitud_validacion_pasajero",
                "pendiente"

            )

        }


        editarCampoViajeSinRuta(viajeId, "viaje_iniciado", "no")
        actualizarCampoParadaPorViaje(viajeId, "par_recorrido", "no")
        actualizarCampoParadaPorViaje(viajeId, "para_viaje_comenzado", "no")
        actualizarCampoParadaPorViaje(viajeId, "par_llegada_pas", "no")

        actualizarCampoParada(paradaId, "par_llegada_pas", "no")
        editarCampoViajeSinRuta(viajeId, "viaje_id_iniciado", "")


        solicitudes?.forEach {
            actualizarHorarioPas(it.horario_id, "horario_iniciado", "no")
            actualizarCampoSolicitud(it.solicitud_id, "solicitud_viaje_iniciado", "no")
        }

        actualizarCampoSolicitudPorBusqueda(
            "parada_id",
            paradaId,
            "solicitud_validacion_conductor",
            "pendiente"
        )
        actualizarCampoSolicitudPorBusqueda(
            "parada_id",
            paradaId,
            "solicitud_validacion_pasajero",
            "pendiente"
        )
    }


    navController.navigate("homeconductor/$userId")
}