package com.example.avanti

import com.google.android.gms.maps.model.LatLng


data class UserData(
    var usu_correo: String? ="",
    var usu_id: String = "",
    var usu_nombre: String = "",
    var usu_primer_apellido: String = "",
    var usu_segundo_apellido: String = "",
    var usu_boleta: String = "",
    var usu_telefono: String = "",
    var usu_nombre_usuario: String = "",
    var usu_tipo: String = "",
    var usu_foto:String="",
    var usu_token:String="",
    var usu_status: String=""
)
data class VehicleData(
    var vehi_color: String="",
    var vehi_marca: String="",
    var vehi_modelo: String="",
    var vehi_placas: String="",
    var vehi_poliza: String=""
)

data class ViajeData(
    var usu_id: String="",
    var viaje_dia: String="",
    var viaje_origen: String="",
    var viaje_destino: String="",
    var viaje_hora_partida: String="",
    var viaje_hora_llegada: String="",
    var viaje_trayecto:String="",
    var viaje_num_lugares:String="",
    var viaje_status:String="",
    var viaje_paradas:String="",
    var viaje_iniciado:String="",
    var viaje_tarifa:String="",
    var viaje_num_pasajeros:String="",
    var viaje_num_pasajeros_con:String="",
    var viaje_id: String,



    )

data class ViajeDataReturn(
    var viaje_id: String,
    var usu_id: String="",
    var viaje_dia: String="",
    var viaje_origen: String="",
    var viaje_destino: String="",
    var viaje_hora_partida: String="",
    var viaje_hora_llegada: String="",
    var viaje_trayecto:String="",
    var viaje_num_lugares:String="",
    var viaje_status:String="",
    var viaje_paradas:String="",
    var viaje_iniciado:String="",
    var viaje_tarifa:String="",
    var viaje_num_pasajeros:String="",
    var viaje_num_pasajeros_con:String=""
)


data class HorarioData(
    var usu_id: String="",
    var horario_dia: String="",
    var horario_origen: String="",
    var horario_destino: String="",
    var horario_hora: String="",
    var horario_id:String="",
    var horario_trayecto: String="",
    var horario_solicitud:String="",
    var horario_status: String="" //AGREGADO 23/12/23
)

data class HorarioDataReturn(
    var usu_id: String="",
    var horario_dia: String="",
    var horario_origen: String="",
    var horario_destino: String="",
    var horario_hora: String="",
    var horario_id:String="",
    var horario_trayecto: String="",
    var horario_solicitud:String="",
    var horario_status: String="" //AGREGADO 23/12/23
)

data class SolicitudData(
    var viaje_id: String="",
    var horario_id: String="",
    var conductor_id: String="",
    var pasajero_id:String="",
    var parada_id:String="",
    var horario_trayecto: String="",
    var solicitud_status:String="",
    var solicitud_date:String="",
    var solicitud_id:String="",
    var solicitud_activa_pas:String="",
    var solicitud_activa_cond:String=""
)



data class ParadaData(
    var par_id: String="",
    var viaje_id: String="",
    var par_nombre: String="",
    var par_hora: String="",
    var par_ubicacion: String="",
    var user_id:String ="" //Agregado 12/12/2023!!!

    )

/*Comienza nuevo codigo 16/12/2023*/
data class MarkerItiData(
    var marker_ubicacion: LatLng,
    var marker_titulo: String="",
    var marker_hora: String="",
    var marker_id:String="" //id de la parada o el viaje
)

data class NoticacionData(
    var notificacion_id: String="",
    var notificacion_tipo: String="",
    var notificacion_usu_origen: String="",
    var notificacion_usu_destino: String="",
    var notificacion_id_viaje: String="",
    var notificacion_id_solicitud: String="", //Solo cuando es solicitud aceptada o recibida
    var notificacion_fecha:String="",
    var notificacion_token: String="",
    var notificacion_hora:String="",

    )

data class verPasajerosData(
    var solicitud_id: String="",
    var usuario_id: String="",
    var viaje_id: String="",
    var nombre_completo: String="",
    var URL_imagen: String="",
    var hora_viaje: String="",
    var dia_viaje: String="",
)

data class ReporteData(
    var repor_u_reportado: String="",
    var repor_u_que_reporta: String="",
    var repor_motivo: String="",
    var repor_detalles: String ="",
    var repor_fecha: String="",
)