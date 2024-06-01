package com.example.avanti

import com.google.android.gms.maps.model.LatLng


data class TokenData(
    var token: String? = "",
    var token_hora_bloqueo: String = "",
    val token_bloqueo: Boolean = false,
    val token_intentos: Int = 0,
    val token_fecha: String = ""

)


data class UserData(
    var usu_correo: String? = "",
    var usu_id: String = "",
    var usu_nombre: String = "",
    var usu_primer_apellido: String = "",
    var usu_segundo_apellido: String = "",
    var usu_boleta: String = "",
    var usu_telefono: String = "",
    var usu_nombre_usuario: String = "",
    var usu_tipo: String = "",
    var usu_foto: String = "",
    var usu_token: String = "",
    var usu_status: String = "",
    var usu_token_reg: String = "",


    )

data class VehicleData(
    var vehi_color: String = "",
    var vehi_marca: String = "",
    var vehi_modelo: String = "",
    var vehi_placas: String = "",
    var vehi_poliza: String = ""
)

data class ViajeData(
    var usu_id: String = "",
    var viaje_dia: String = "",
    var viaje_origen: String = "",
    var viaje_destino: String = "",
    var viaje_hora_partida: String = "",
    var viaje_hora_llegada: String = "",
    var viaje_trayecto: String = "",
    var viaje_num_lugares: String = "",
    var viaje_status: String = "",
    var viaje_paradas: String = "",
    var viaje_iniciado: String = "",
    var viaje_tarifa: String = "",
    var viaje_num_pasajeros: String = "",
    var viaje_num_pasajeros_con: String = "",
    var viaje_id: String = "",
    var viaje_id_iniciado: String = "",


    )

data class ViajeDataReturn(
    var viaje_id: String,
    var usu_id: String = "",
    var viaje_dia: String = "",
    var viaje_origen: String = "",
    var viaje_destino: String = "",
    var viaje_hora_partida: String = "",
    var viaje_hora_llegada: String = "",
    var viaje_trayecto: String = "",
    var viaje_num_lugares: String = "",
    var viaje_status: String = "",
    var viaje_paradas: String = "",
    var viaje_iniciado: String = "",
    var viaje_tarifa: String = "",
    var viaje_num_pasajeros: String = "",
    var viaje_num_pasajeros_con: String = "",
    var viaje_id_iniciado: String = "",
)


data class HorarioData(
    var usu_id: String = "",
    var horario_dia: String = "",
    var horario_origen: String = "",
    var horario_destino: String = "",
    var horario_hora: String = "",
    var horario_id: String = "",
    var horario_trayecto: String = "",
    var horario_solicitud: String = "",
    var horario_status: String = "",
    var horario_iniciado: String = ""
)

data class HorarioDataReturn(
    var usu_id: String = "",
    var horario_dia: String = "",
    var horario_origen: String = "",
    var horario_destino: String = "",
    var horario_hora: String = "",
    var horario_id: String = "",
    var horario_trayecto: String = "",
    var horario_solicitud: String = "",
    var horario_status: String = "",
    var horario_iniciado: String = ""
)

data class SolicitudData(
    var viaje_id: String = "",
    var horario_id: String = "",
    var conductor_id: String = "",
    var pasajero_id: String = "",
    var pasajero_token: String = "",
    var parada_id: String = "",
    var horario_trayecto: String = "",
    var solicitud_status: String = "",
    var solicitud_date: String = "",
    var solicitud_id: String = "",
    var solicitud_activa_pas: String = "",
    var solicitud_activa_con: String = "",
    var solicitud_viaje_iniciado: String = "",
    var solicitud_validacion_pasajero: String = "",
    var solicitud_validacion_conductor: String = ""

)


data class ParadaData(
    var par_id: String = "",
    var viaje_id: String = "",
    var par_nombre: String = "",
    var par_hora: String = "",
    var par_ubicacion: String = "",
    var user_id: String = "",
    var par_recorrido: String = "",
    var para_viaje_comenzado: String = "",
    var par_llegada_pas: String = ""


)

/*Comienza nuevo codigo 16/12/2023*/
data class MarkerItiData(
    var marker_ubicacion: LatLng,
    var marker_titulo: String = "",
    var marker_hora: String = "",
    var marker_id: String = "" //id de la parada o el viaje
)

data class NoticacionData(
    var notificacion_id: String = "",
    var notificacion_tipo: String = "",
    var notificacion_usu_origen: String = "",
    var notificacion_usu_destino: String = "",
    var notificacion_id_viaje: String = "",
    var notificacion_id_solicitud: String = "", //Solo cuando es solicitud aceptada o recibida
    var notificacion_fecha: String = "",
    var notificacion_token: String = "",
    var notificacion_hora: String = "",

    )

data class ReporteData(
    var repor_u_reportado: String = "",
    var repor_u_que_reporta: String = "",
    var repor_motivo: String = "",
    var repor_detalles: String = "",
    var repor_fecha: String = "",
    var repor_hora: String = "",
)

data class HistorialViajesData(
    var validacion_conductor_inicio: Boolean = false,
    var viaje_id: String = "",
    val viaje_iniciado: Boolean = false,
    val fecha_inicio_viaje: String = "",
    val fecha_bloqueo_viaje: String = "",
    var conductor_id: String = "",
    var pasajeros_id: List<String> = emptyList(),
    var horario_id: String = "",
    var hora_inicio_viaje: String = "",
    var hora_fin_viaje: String = "",
    var validacion_pasajeros: List<Boolean> = emptyList(),
    var ids_pasajeros: List<String> = emptyList(),
    var validacion_conductor_paradas: List<Boolean> = emptyList(),
    var bloqueo_inicio_viaje: Boolean = false,
    var hora_bloqueo_viaje: String = "",
    var id_viaje_iniciado: String = ""
)

data class ImprevistoData(
    var impr_u_que_reporta: String = "",
    var impr_viaje_id: String = "",
    var impr_motivo: String = "",
    var impr_fecha: String = "",
    var impr_hora: String = "",
)
