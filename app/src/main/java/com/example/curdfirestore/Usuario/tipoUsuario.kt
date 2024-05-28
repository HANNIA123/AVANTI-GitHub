package com.example.avanti.Usuario

import android.os.Build

import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable

import androidx.navigation.NavController
import com.example.avanti.Usuario.Conductor.Pantallas.homePantallaConductor
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.curdfirestore.AuthViewModel

import com.example.curdfirestore.Usuario.Pasajero.Pantallas.homePantallaPasajero



//funcion para que una vez que se logueo, pueda validar que tipo de usuario es
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun obtenerTipoUsuario(
    navController: NavController,
    userId: String,
    authViewModel: AuthViewModel
) {

    // Llamamos a la función obtenerUsuarioId y recibimos el usuario
    val usuario = conObtenerUsuarioId(userId)

    // Puedes hacer algo con el objeto de usuario aquí
    usuario?.let {


        when (usuario.usu_tipo) {
            "Conductor" -> {
                // obtenerCoordenadas(userId)
                homePantallaConductor(navController = navController, userid = userId)


            }

            "Pasajero" -> {
                homePantallaPasajero(navController = navController, userid = userId)
            }

            else -> {


            }
        }


    }


}