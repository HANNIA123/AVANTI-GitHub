package com.example.avanti

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
    var usu_token:String=""
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
    var viaje_iniciado:String=""
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

