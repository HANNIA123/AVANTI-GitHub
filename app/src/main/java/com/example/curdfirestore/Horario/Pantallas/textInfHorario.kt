package com.example.curdfirestore.Horario.Pantallas

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.avanti.HorarioData
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddress
import com.example.curdfirestore.textoGris
import com.google.android.gms.maps.model.LatLng

@Composable
fun textInfHorario(
    horario:HorarioData,
    origen: LatLng,
    destino:LatLng
){
    Text(
        text = "Información sobre tu viaje",
        style = TextStyle(
            Color(137, 13, 88),
            fontSize = 18.sp
        ),
        textAlign = TextAlign.Center

    )
    val tamT = 16f
    textoGris(Texto = "Día: ${horario.horario_dia}", tamTexto = tamT)
    if (horario.horario_trayecto == "0") {
        val address = convertCoordinatesToAddress(destino)
        textoGris("Origen: UPIITA", tamT)
        textoGris("Destino: $address", tamT)
        textoGris("Horario de salida: ${horario.horario_hora} hrs", tamT)

    } else {
        val address = convertCoordinatesToAddress(origen)
        textoGris(Texto = "Origen: $address", tamTexto = tamT)
        textoGris("Destino: UPIITA", tamT)
        textoGris(
            Texto = "Horario de llegada: ${horario.horario_hora} hrs",
            tamTexto = tamT
        )

    }


}