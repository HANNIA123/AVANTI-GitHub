package com.example.avanti.Usuario


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.avanti.Usuario.Conductor.Pantallas.cuentaPantallaCon
import com.example.avanti.Usuario.Conductor.Pantallas.homePantallaConductor
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
            Login(  navController = navController) {
                navController.navigate("home/$it")
                //-------------------FCM--------------------------------------------
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    println("TOKEN")
                    println(token)
                    println("ID")
                    println(it)
                    sendTokenToServer(it,token)    }
                else {
                    println("FCM -> Error al obtener el token: ${task.exception}")    }}

            }
        }

        composable( "home/{useid}"
        ) {
            val userId= it.arguments?.getString("useid")?:""
        obtenerTipoUsuario(navController = navController, userId = userId)
        // homePantallaConductor(navController = navController, userid = )

        }
        composable( "cuenta_conductor/{userid}"
        ) {
            val userId= it.arguments?.getString("userid")?:""
            cuentaPantallaCon( navController = navController, userID =userId )

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
