package com.example.avanti.Usuario.Conductor.Pantallas

//Pantalla donde el conductor podrá iniciar un viaje

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.curdfirestore.Usuario.Conductor.Pantallas.homeNoIniciado
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerItinerarioConSus
import com.example.curdfirestore.Viaje.Funciones.solicitarPermiso
import com.example.curdfirestore.Viaje.Pantallas.Monitoreo.obtenerCoordenadas


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun homePantallaConductor(
    navController: NavController,
    userid: String,

    ) {
    solicitarPermiso()

    var terminaConsulta by remember {
        mutableStateOf(false)
    }
    val viajes = conObtenerItinerarioConSus(userId = userid,
        fin = { terminaConsulta = true }
    )

    var valida by remember {
        mutableStateOf(false)
    }
    if (terminaConsulta) {
        viajes?.let {

            val viajesIniciados = viajes.filter { it.viaje_iniciado == "si" }
            if (viajesIniciados.isNotEmpty()) {
                val primerViajeIniciado = viajesIniciados.firstOrNull()
                if (primerViajeIniciado != null) {
                    obtenerCoordenadas(
                        userId = userid,
                        viajeId = primerViajeIniciado.viaje_id,
                        navController = navController
                    )
                    //  navController.navigate("empezar_viaje/$userid/${primerViajeIniciado.viaje_id}")
                }

            } else {
                valida = true
            }
        }
        if (viajes == null) {
            valida = true
        }
        if (valida) {
            homeNoIniciado(navController, userid)
        }
    }

}

/*
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MyScaffoldContentPreview() {
    val navController = rememberNavController()
    homePantallaConductor(navController = navController, userid = "hannia")
}

*/