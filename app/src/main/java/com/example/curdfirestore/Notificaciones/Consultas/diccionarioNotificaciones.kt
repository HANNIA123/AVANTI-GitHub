package com.example.curdfirestore.Notificaciones.Consultas

import androidx.compose.runtime.Composable

@Composable
fun TextoNotificacionVer(tipoNot:String):String{
    var texto=""
    if(tipoNot=="sr")
    {
        texto="te ha enviado una solicitud"
    }
    else if(tipoNot=="cv")
    {
        texto="ha cancelado un viaje"
    }
    else if(tipoNot=="vi"){
        texto="ha validado su identidad"
    }
    else if(tipoNot=="sa"){
        texto="ha aceptado tu solicitud"
    }
    else if(tipoNot=="llc"){
        texto="ha llegado al punto de encuentro"
    }
    else if(tipoNot=="iv"){
        texto="ha reportado un imprevisto"
    }

    else if(tipoNot=="vd"){
        texto="ha activado el viaje nuevamente"
    }
    else if(tipoNot=="ve"){
        texto="ha eliminado un viaje"
    }
    else{
        texto="Notificacion recibida"
    }

    return texto

}


fun TextoNotificacionEnviar(tipoNot:String): Pair<String, String> {
    var title=""
    var texto=""
    if(tipoNot=="sr")
    {
        title="Solicitud recibida"
        texto="te ha enviado una solicitud."
    }
    else if(tipoNot=="cv")
    {
        title="Cancelación de viaje"
        texto="ha cancelado un viaje."
    }
    else if(tipoNot=="vi"){
        texto="ha validado su identidad."
    }
    else if(tipoNot=="sa"){
        title="Solicitud aceptada"
        texto="ha aceptado tu solicitud."
    }
    else if(tipoNot=="llc"){
        texto="ha llegado al punto de encuentro."
    }
    else if(tipoNot=="iv"){
        texto="ha reportado un imprevisto."
    }

    else if(tipoNot=="vd"){
        title="Viaje disponible"
        texto="ha activado el viaje nuevamente."
    }
    else if(tipoNot=="ve"){
        title="Viaje eliminado"
        texto="ha eliminado un viaje."
    }
    else{
        texto="Notificacion recibida."
    }
    return Pair(title, texto)

}

fun ObtenerNavGraphCon(tipoNot: String):String{
    var ruta=""

    when (tipoNot) {
        "sr" ->ruta= "ver_solicitudes_conductor"
        "cv" -> ruta="ver_itinerario_conductor"
        "vd" -> ruta="ver_itinerario_conductor"
        "vi" -> ruta="validacion huella"
        else -> println("Número de día no válido")
    }
    return ruta
}

fun ObtenerNavGraphPas(tipoNot: String):String{
    var ruta=""

    when (tipoNot) {
        "sa" ->ruta= "vdetalles_viajesconfirmados" //cambiar a detalles viaje
        "cv" -> ruta="ver_itinerario_pasajero_con"
        "vi" -> ruta="validacion huella"
        "llc" -> ruta="Llegada_conductor"
        "iv" -> ruta="imprevisto_viaje" //¿donde ve los imprevistos?
        else -> println("Número de día no válido")
    }
    return ruta
}