package com.example.curdfirestore.Viaje.Funciones

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.avanti.SolicitudData
import com.example.curdfirestore.Horario.ConsultasHorario.actualizarHorarioPas
import com.example.curdfirestore.MainActivity
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParada
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParadaPorViaje
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.actualizarCampoSolicitud
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.actualizarCampoSolicitudPorBusqueda
import com.example.curdfirestore.Viaje.ConsultasViaje.editarCampoViajeSinRuta
import com.example.curdfirestore.Viaje.Pantallas.Huella.autenticaHuella
import kotlinx.coroutines.runBlocking


fun accionesComienzoViaje(viajeId: String, solicitudes: List<SolicitudData>?) {


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

}



fun accionesTerminoViaje(
    viajeId: String, solicitudes: List<SolicitudData>?,
    navController: NavController, userId: String, paradaId: String
) {
    runBlocking {
        editarCampoViajeSinRuta(viajeId, "viaje_iniciado", "no")
        actualizarCampoParadaPorViaje(viajeId, "par_recorrido", "no")
        actualizarCampoParadaPorViaje(viajeId, "para_viaje_comenzado", "no")
        actualizarCampoParada(paradaId, "par_llegada_pas", "no")

        solicitudes?.forEach {
            actualizarHorarioPas(it.horario_id, "horario_iniciado", "no")
            actualizarCampoSolicitud(it.solicitud_id, "solicitud_viaje_iniciado", "no")
        }

        actualizarCampoSolicitudPorBusqueda("parada_id", paradaId, "solicitud_validacion_conductor", "pendiente")
        actualizarCampoSolicitudPorBusqueda("parada_id", paradaId, "solicitud_validacion_pasajero", "pendiente")
    }


    navController.navigate("homeconductor/$userId")
}