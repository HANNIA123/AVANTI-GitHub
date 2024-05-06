package com.example.curdfirestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.example.avanti.Usuario.NavGraph


class Act_Notificaciones_Con : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent

        val userId = intent.getStringExtra("userId") ?: ""

        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController)
            LaunchedEffect(true) {
                navController.navigate("ver_notificaciones_conductor/$userId")
            }

        }
    }
}

class Act_Notificaciones_Pas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent

        val userId = intent.getStringExtra("userId") ?: ""

        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController)
            LaunchedEffect(true) {
                navController.navigate("ver_notificaciones_pasajero/$userId")
            }
        }
    }
}