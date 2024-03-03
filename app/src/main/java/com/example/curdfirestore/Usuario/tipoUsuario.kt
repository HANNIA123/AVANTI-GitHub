package com.example.avanti.Usuario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.UserData
import com.example.avanti.Usuario.Conductor.Pantallas.cuentaPantallaCon
import com.example.avanti.Usuario.Conductor.Pantallas.homePantallaConductor
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId


//funcion para que una vez que se logueo, pueda validar que tipo de usuario es
@Composable
fun obtenerTipoUsuario(
    navController: NavController,
    userId: String,
) {

    // Llamamos a la función obtenerUsuarioId y recibimos el usuario
    val usuario = conObtenerUsuarioId(userId)

    // Puedes hacer algo con el objeto de usuario aquí
    usuario?.let {

        if(usuario!!.usu_tipo=="Conductor"){
   homePantallaConductor(navController = navController, userid =userId )
        //cuentaPantallaCon(usuario = usuario, navController = navController, userID = userId)

        }
        else if(usuario!!.usu_tipo=="Pasajero"){

        }
        else{
            //solo probar que no intente entrar con administrador
        }

    }


}