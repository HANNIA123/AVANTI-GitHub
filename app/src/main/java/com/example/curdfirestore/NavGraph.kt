package com.example.avanti.Usuario


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.avanti.Usuario.Conductor.Pantallas.cuentaPantallaCon
import com.example.curdfirestore.Usuario.Conductor.Pantallas.modificarPasswordCon
import com.example.curdfirestore.Usuario.Conductor.Pantallas.perfilConductor
import com.example.curdfirestore.Usuario.Conductor.Pantallas.viajesInicio
import com.example.curdfirestore.Usuario.Pasajero.Pantallas.cuentaPantallaPas
import com.example.curdfirestore.Usuario.Pasajero.Pantallas.horariosInicio
import com.example.curdfirestore.Usuario.Pasajero.Pantallas.modificarPasswordPas
import com.example.curdfirestore.Usuario.Pasajero.Pantallas.perfilPas
import com.example.curdfirestore.Usuario.resetPassword
import com.example.curdfirestore.Parada.Pantallas.generalParada
import com.example.curdfirestore.Parada.Pantallas.registrarParadaBarra
import com.example.curdfirestore.Viaje.Pantallas.generalViajeCon
import com.example.curdfirestore.Viaje.Pantallas.registrarDestinoConductor
import com.example.curdfirestore.Viaje.Pantallas.registrarOrigenConductor
import com.example.curdfirestore.Viaje.Pantallas.verItinerarioCon
import com.example.curdfirestore.Viaje.Pantallas.verMapaViajeConductor

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route
    ) {
        // main screen
        composable(
            route = Screens.Login.route
        ) {
            Login(navController = navController) {
                navController.navigate("home/$it")
                //-------------------FCM--------------------------------------------
                FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val token = task.result
                        println("TOKEN")
                        println(token)
                        println("ID")
                        println(it)
                        sendTokenToServer(it, token)
                    } else {
                        println("FCM -> Error al obtener el token: ${task.exception}")
                    }
                }

            }
        }
        composable("reset_password") {
            resetPassword(navController = navController)
        }

        composable(
            "home/{useid}"
        ) {
            val userId = it.arguments?.getString("useid") ?: ""
            obtenerTipoUsuario(navController = navController, userId = userId)
            // homePantallaConductor(navController = navController, userid = )

        }
        //Pantallas entrando con conductor
        composable(
            "cuenta_conductor/{userid}"
        ) {
            val userId = it.arguments?.getString("userid") ?: ""
            cuentaPantallaCon(navController = navController, userID = userId)

        }
        composable("viaje_inicio/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            viajesInicio(navController = navController, userId = userId)

        }

        composable("perfil_conductor/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            perfilConductor(navController = navController, userId = userId)

        }

        composable("modificar_password_conductor/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            modificarPasswordCon(navController = navController, userId = userId)

        }

        //Agregado por Hannia
        //04/03/2024
        composable("general_viaje_conductor/{userid}") {
/*
            val viajeID= "DxH2AhHYRVHMRcHjc6bC"
            val userID= it.arguments?.getString("userid")?:""
            val pantalla="pantalla"
            verMapaViajeConductor(
                navController = navController,
                correo = userID,

                pantalla = pantalla,
                viajeId = viajeID
            )
*/
            val userId = it.arguments?.getString("userid") ?: ""
            generalViajeCon(navController = navController, userId = userId)

        }


      //---------Viaje conductor--------------
        composable("registrar_origen_conductor/{userid}/{dia}/{horao}/{horad}/{lugares}/{tarifa}") {
            val userId = it.arguments?.getString("userid") ?: ""
            val dia = it.arguments?.getString("dia") ?: ""
            val horao = it.arguments?.getString("horao") ?: ""
            val horad = it.arguments?.getString("horad") ?: ""
            val lugares = it.arguments?.getString("lugares") ?: ""
            val tarifa = it.arguments?.getString("tarifa") ?: ""

            registrarOrigenConductor(navController = navController, userId, dia, horao, horad, lugares, tarifa)

        }
        composable("registrar_destino_conductor/{userid}/{dia}/{horao}/{horad}/{lugares}/{tarifa}") {
            val userId = it.arguments?.getString("userid") ?: ""
            val dia = it.arguments?.getString("dia") ?: ""
            val horao = it.arguments?.getString("horao") ?: ""
            val horad = it.arguments?.getString("horad") ?: ""
            val lugares = it.arguments?.getString("lugares") ?: ""
            val tarifa = it.arguments?.getString("tarifa") ?: ""

            registrarDestinoConductor(navController = navController, userId, dia, horao, horad, lugares, tarifa)

        }



//Formulario parada
        composable("general_parada/{viajeid}/{userid}/{compantalla}/{repantalla}") {
            val viajeId = it.arguments?.getString("viajeid") ?: ""
            val userId = it.arguments?.getString("userid") ?: ""
            val comPantalla = it.arguments?.getString("compantalla") ?: ""
            val pantallaRegresa = it.arguments?.getString("repantalla") ?: ""
            generalParada(navController, viajeId, userId, comPantalla,pantallaRegresa)
        }



        composable("registrar_parada_barra/{userid}/{viajeid}/{nombrep}/{horap}") {
            val viajeId = it.arguments?.getString("viajeid") ?: ""
            val userId = it.arguments?.getString("userid") ?: ""
            val nombreP = it.arguments?.getString("nombrep") ?: ""
            val horaP = it.arguments?.getString("horap") ?: ""
            registrarParadaBarra(navController,userId,viajeId, nombreP, horaP)
        }


        //Ver viaje, desde el itinerario o desde el registro
        composable( "ver_mapa_viaje/{viajeid}/{email}/{pantalla}"
        ) {
            val viajeID= it.arguments?.getString("viajeid")?:""
            val userID= it.arguments?.getString("email")?:""
            val pantalla=it.arguments?.getString("pantalla")?:""
            verMapaViajeConductor(
                navController = navController,
                correo = userID,

                pantalla = pantalla,
                viajeId = viajeID
            )


        }

        //Ver viaje sin paradas
        composable( "ver_mapa_viaje_sin/{viajeid}/{email}/{pantalla}"
        ) {
            val viajeID= it.arguments?.getString("viajeid")?:""
            val userID= it.arguments?.getString("email")?:""
            val pantalla=it.arguments?.getString("pantalla")?:""
            verMapaViajeConductor(
                navController = navController,
                correo = userID,

                pantalla = pantalla,
                viajeId = viajeID
            )


        }

        //Ver itinerario
        composable( "ver_itinerario_conductor/{userid}"
        ) {
            val userId= it.arguments?.getString("userid")?:""
       verItinerarioCon(navController = navController, userId = userId)


        }

        //--------------------Pantallas entrando con pasajero---------------------------
        composable(
            "cuenta_pasajero/{userid}"
        ) {
            val userId = it.arguments?.getString("userid") ?: ""
            cuentaPantallaPas(navController = navController, userID = userId)

        }

        composable("perfil_pasajero/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            perfilPas(navController = navController, userId = userId)

        }

        composable("modificar_password_pasajero/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            modificarPasswordPas(navController = navController, userId = userId)

        }

        composable("horario_inicio/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            horariosInicio(navController = navController, userId = userId)

        }



    }

}


//FCM
fun sendTokenToServer(userId: String, token: String?) {

    val usuarioRef = Firebase.firestore.collection("usuario").document(userId)

    // Actualiza el documento del usuario para incluir el token de FCM
    usuarioRef.update("usu_token", token)
        .addOnSuccessListener {
            // Manejar el éxito, si es necesario
        }
        .addOnFailureListener { e ->
            // Manejar el error
            Log.e("Firestore", "Error al actualizar el token: $e")
        }

}
