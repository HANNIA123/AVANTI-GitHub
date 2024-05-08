package com.example.avanti.Usuario


import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.avanti.Usuario.Conductor.Pantallas.cuentaPantallaCon
import com.example.avanti.Usuario.Conductor.Pantallas.homePantallaConductor
import com.example.curdfirestore.AuthViewModel
import com.example.curdfirestore.ContadorViewModel
import com.example.curdfirestore.Horario.ConsultasHorario.conBuscarViajePas
import com.example.curdfirestore.Horario.Pantallas.Monitoreo.verUbicacionMonitoreo
import com.example.curdfirestore.Horario.Pantallas.generalViajePas
import com.example.curdfirestore.Horario.Pantallas.registrarDestinoPasajero
import com.example.curdfirestore.Horario.Pantallas.registrarOrigenPasajero
import com.example.curdfirestore.Parada.Pantallas.Editar.generalParadaEditar
import com.example.curdfirestore.Parada.Pantallas.Editar.registrarParadaBarraEditar
import com.example.curdfirestore.Horario.Pantallas.verItinerarioPas
import com.example.curdfirestore.Horario.Pantallas.verMapaViajePasajero
import com.example.curdfirestore.Horario.Pantallas.verMapaViajePasajeroSinPar
import com.example.curdfirestore.MainActivity
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
import com.example.curdfirestore.Solicitud.Pantallas.verConductores
import com.example.curdfirestore.Solicitud.Pantallas.verPasajeros
import com.example.curdfirestore.Solicitud.Pantallas.verSolicitudesCon
import com.example.curdfirestore.Usuario.Pasajero.Pantallas.homePantallaPasajero
import com.example.curdfirestore.Viaje.Pantallas.Monitoreo.obtenerCoordenadas
import com.example.curdfirestore.Viaje.Pantallas.Editar.generalViajeConEditar
import com.example.curdfirestore.Viaje.Pantallas.Editar.registrarDestinoConductorEditar
import com.example.curdfirestore.Viaje.Pantallas.Editar.registrarOrigenConductorEditar
import com.example.curdfirestore.Viaje.Pantallas.generalViajeCon
import com.example.curdfirestore.Viaje.Pantallas.registrarDestinoConductor
import com.example.curdfirestore.Viaje.Pantallas.registrarOrigenConductor
import com.example.curdfirestore.Viaje.Pantallas.verItinerarioCon
import com.example.curdfirestore.Viaje.Pantallas.verMapaViajeConductor
import com.example.curdfirestore.Viaje.Pantallas.verMapaViajeConductorSinPar

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

@SuppressLint("ComposableDestinationInComposeScope", "SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: ContadorViewModel,
    authViewModel: AuthViewModel

) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route,

    ) {
        // main screen
        composable(
            route = Screens.Login.route
        ) {


            if (authViewModel.isLoggedIn.value) {
                println("autenticado")
                val userId = authViewModel.currentUser!!.email

                obtenerTipoUsuario(navController = navController, userId = userId.toString(), viewModel, authViewModel)
                // homePantallaConductor(navController = navController, userid = )

            } else {
                println("NOO autenticado")
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


        }
        composable("reset_password") {
            resetPassword(navController = navController)
        }

        composable(
            "home/{useid}"
        ) {
            val userId = it.arguments?.getString("useid") ?: ""
            obtenerTipoUsuario(navController = navController, userId = userId, viewModel, authViewModel)
            // homePantallaConductor(navController = navController, userid = )

        }
        composable(
            "homeconductor/{useid}"
        ) {
            val userId = it.arguments?.getString("useid") ?: ""
             homePantallaConductor(navController = navController, userid = userId, viewModel )

        }

        //Pantallas entrando con conductor
        composable(
            "cuenta_conductor/{userid}"
        ) {
            val userId = it.arguments?.getString("userid") ?: ""
            cuentaPantallaCon(navController = navController, userID = userId, authViewModel = authViewModel)

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

            registrarOrigenConductor(
                navController = navController,
                userId,
                dia,
                horao,
                horad,
                lugares,
                tarifa
            )

        }
        composable("registrar_destino_conductor/{userid}/{dia}/{horao}/{horad}/{lugares}/{tarifa}") {
            val userId = it.arguments?.getString("userid") ?: ""
            val dia = it.arguments?.getString("dia") ?: ""
            val horao = it.arguments?.getString("horao") ?: ""
            val horad = it.arguments?.getString("horad") ?: ""
            val lugares = it.arguments?.getString("lugares") ?: ""
            val tarifa = it.arguments?.getString("tarifa") ?: ""

            registrarDestinoConductor(
                navController = navController,
                userId,
                dia,
                horao,
                horad,
                lugares,
                tarifa
            )

        }


//Formulario parada
        composable("general_parada/{viajeid}/{userid}/{compantalla}/{repantalla}") {
            val viajeId = it.arguments?.getString("viajeid") ?: ""
            val userId = it.arguments?.getString("userid") ?: ""
            val comPantalla = it.arguments?.getString("compantalla") ?: ""
            val pantallaRegresa = it.arguments?.getString("repantalla") ?: ""
            generalParada(navController, viajeId, userId, comPantalla, pantallaRegresa)
        }



        composable("registrar_parada_barra/{userid}/{viajeid}/{nombrep}/{horap}") {
            val viajeId = it.arguments?.getString("viajeid") ?: ""
            val userId = it.arguments?.getString("userid") ?: ""
            val nombreP = it.arguments?.getString("nombrep") ?: ""
            val horaP = it.arguments?.getString("horap") ?: ""
            registrarParadaBarra(navController, userId, viajeId, nombreP, horaP)
        }



        composable(
            "ver_mapa_viaje/{viajeid}/{email}"
        ) {
            val viajeID = it.arguments?.getString("viajeid") ?: ""
            val userID = it.arguments?.getString("email") ?: ""

            verMapaViajeConductor(
                navController = navController,
                correo = userID,

                viajeId = viajeID
            )


        }

        //Ver viaje sin paradas

        composable(
            "ver_mapa_viaje_sin/{viajeid}/{email}"
        ) {
            val viajeID = it.arguments?.getString("viajeid") ?: ""
            val userID = it.arguments?.getString("email") ?: ""

            verMapaViajeConductorSinPar(
                navController = navController,
                correo = userID,

                viajeId = viajeID
            )


        }

        //Ver itinerario
        composable(
            "ver_itinerario_conductor/{userid}"
        ) {
            val userId = it.arguments?.getString("userid") ?: ""
            verItinerarioCon(navController = navController, userId = userId)

        }

        //Editar viaje
        composable("general_viaje_conductor_editar/{userid}/{viajeid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            val viajeId = it.arguments?.getString("viajeid") ?: ""
            generalViajeConEditar(
                navController = navController,
                userId = userId,
                viajeId = viajeId
            )
        }
        composable("registrar_origen_conductor_editar/{userid}/{dia}/{horao}/{horad}/{lugares}/{tarifa}/{origen}/{viajeid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            val dia = it.arguments?.getString("dia") ?: ""
            val horao = it.arguments?.getString("horao") ?: ""
            val horad = it.arguments?.getString("horad") ?: ""
            val lugares = it.arguments?.getString("lugares") ?: ""
            val tarifa = it.arguments?.getString("tarifa") ?: ""
            val origen = it.arguments?.getString("origen") ?: ""

            val viajeId = it.arguments?.getString("viajeid") ?: ""
            registrarOrigenConductorEditar(
                navController = navController,
                userId,
                dia,
                horao,
                horad,
                lugares,
                tarifa,
                origen,
                viajeId
            )

        }
        composable("registrar_destino_conductor_editar/{userid}/{dia}/{horao}/{horad}/{lugares}/{tarifa}/{destino}/{viajeid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            val dia = it.arguments?.getString("dia") ?: ""
            val horao = it.arguments?.getString("horao") ?: ""
            val horad = it.arguments?.getString("horad") ?: ""
            val lugares = it.arguments?.getString("lugares") ?: ""
            val tarifa = it.arguments?.getString("tarifa") ?: ""
            val destino = it.arguments?.getString("destino") ?: ""
            val viajeId = it.arguments?.getString("viajeid") ?: ""
            registrarDestinoConductorEditar(
                navController = navController,
                userId,
                dia,
                horao,
                horad,
                lugares,
                tarifa,
                destino,
                viajeId
            )

        }
        composable("general_parada_editar/{viajeid}/{userid}/{paradaid}") {
            val viajeId = it.arguments?.getString("viajeid") ?: ""
            val userId = it.arguments?.getString("userid") ?: ""
            val paradaid = it.arguments?.getString("paradaid") ?: ""
            println("Variables rutaaa $viajeId $userId $paradaid")
            generalParadaEditar(
                navController = navController,
                viajeId = viajeId,
                userId = userId,
                paradaId = paradaid
            )
        }


        composable("registrar_parada_barra_editar/{userid}/{viajeid}/{nombrep}/{horap}/{ubicacionp}/{paradaid}") {
            val viajeId = it.arguments?.getString("viajeid") ?: ""
            val userId = it.arguments?.getString("userid") ?: ""
            val nombreP = it.arguments?.getString("nombrep") ?: ""
            val horaP = it.arguments?.getString("horap") ?: ""
            val ubicacionP = it.arguments?.getString("ubicacionp") ?: ""
            val paradaId = it.arguments?.getString("paradaid") ?: ""
            registrarParadaBarraEditar(
                navController = navController,
                userid = userId,
                viajeid = viajeId,
                nombrep = nombreP,
                horap = horaP,
                ubicacionP = ubicacionP,
                paradaId = paradaId
            )
            // registrarParadaBarra(navController,userId,viajeId, nombreP, horaP)
        }

        //Ver pasajero Caro --- 335-345
        composable("ver_pasajeros_conductor/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            verPasajeros(
                navController = navController,
                userid = userId
            )
        }









        //ver solicitudes Hannia --- 346-356
        composable("ver_solicitudes_conductor/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            verSolicitudesCon(
                navController = navController,
                userId = userId
            )
        }
        //Ruta para iniciar el viaje
        composable("empezar_viaje/{correo}/{viajeid}"
        ) {
            val activity = LocalContext.current as MainActivity
            println("Tipo de activity: ${activity?.javaClass?.simpleName}")

            val userId = it.arguments?.getString("correo") ?: ""
            val viajeId = it.arguments?.getString("viajeid") ?: ""
            obtenerCoordenadas(userId = userId, viajeId =viajeId, navController=navController, viewModel = viewModel)
        }


        ///////////////////////////////////////
        //--------------------Pantallas entrando con pasajero---------------------------
        composable(
            "cuenta_pasajero/{userid}"
        ) {
            val userId = it.arguments?.getString("userid") ?: ""
            cuentaPantallaPas(navController = navController, userID = userId, authViewModel)

        }

        composable("perfil_pasajero/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            perfilPas(navController = navController, userId = userId)

        }
        composable(
            "home_pasajero/{userid}"
        ) {
            val userId = it.arguments?.getString("userid") ?: ""
            homePantallaPasajero(navController, userId)

        }

        composable("modificar_password_pasajero/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            modificarPasswordPas(navController = navController, userId = userId)

        }

        composable("horario_inicio/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            horariosInicio(navController = navController, userId = userId)
        }
        composable("general_horario_pasajero/{userid}") {
            val userId = it.arguments?.getString("userid") ?: ""
            generalViajePas(navController = navController, userId = userId)
        }

        //---------Horario pasajero--------------
        composable("registrar_origen_pasajero/{userid}/{dia}/{horao}") {
            val userId = it.arguments?.getString("userid") ?: ""
            val dia = it.arguments?.getString("dia") ?: ""
            val horao = it.arguments?.getString("horao") ?: ""

            registrarOrigenPasajero(navController = navController, userId, dia, horao)
        }

        composable("registrar_destino_pasajero/{userid}/{dia}/{horao}") {
            val userId = it.arguments?.getString("userid") ?: ""
            val dia = it.arguments?.getString("dia") ?: ""
            val horao = it.arguments?.getString("horao") ?: ""

            registrarDestinoPasajero(navController = navController, userId, dia, horao)
        }

        composable(
            "ver_itinerario_pasajero/{userid}"
        ) {
            val userid = it.arguments?.getString("userid") ?: ""
            verItinerarioPas(navController = navController, userid)
        }

        composable(
            "ver_mapa_viaje_pas/{horarioid}/{email}/{pantalla}"
        ) {
            val horarioId = it.arguments?.getString("horarioid") ?: ""
            val userId = it.arguments?.getString("email") ?: ""
            val pantalla = it.arguments?.getString("pantalla") ?: ""
            verMapaViajePasajero(
                navController = navController,
                horarioId = horarioId,
                correo = userId,
                pantalla = pantalla
            )
        }

        composable(
            "ver_mapa_viaje_pas_sin/{horarioid}/{email}/{pantalla}"
        ) {
            val horarioId = it.arguments?.getString("horarioid") ?: ""
            val userId = it.arguments?.getString("email") ?: ""
            val pantalla = it.arguments?.getString("pantalla") ?: ""
            verMapaViajePasajeroSinPar(
                navController = navController,
                horarioId = horarioId,
                correo = userId,
                pantalla = pantalla
            )
        }

        composable(
            "ver_paradas_pasajero/{correo}/{idhorario}"
        ) {
            val correo = it.arguments?.getString("correo") ?: ""
            val idhorario = it.arguments?.getString("idhorario") ?: ""
            conBuscarViajePas(navController = navController, correo = correo, horarioId = idhorario)
        }

        //ruta ver conductores 450-460 -- Caro
        //ruta ver conductores 450-460 -- Caro
        composable( "ver_conductores_pasajero/{userid}"
        ) {
            val userId= it.arguments?.getString("userid")?:""
            verConductores(navController = navController, userid = userId)
        }



//Ruta para ver el avance del viaje
        composable(
            "ver_progreso_viaje/{correo}/{idviaje}/{idsolicitud}/{idhorario}/{idparada}"
        ) {
            val correo = it.arguments?.getString("correo") ?: ""
            val idviaje = it.arguments?.getString("idviaje") ?: ""
            val idHorario = it.arguments?.getString("idhorario") ?: ""
            val idsolicitud = it.arguments?.getString("idsolicitud") ?: ""
            val idParada= it.arguments?.getString("idparada") ?: ""
            verUbicacionMonitoreo(
                userId = correo,
                viajeId = idviaje,
                horarioId = idHorario,
                solicitudId = idsolicitud,
                paradaId=idParada,
                navController = navController
            )


        }

        ///////////////////////////

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
