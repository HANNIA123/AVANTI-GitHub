package com.example.curdfirestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.avanti.Usuario.NavGraph


class Act_Notificaciones_Con : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val intent = intent

        val userId = intent.getStringExtra("userId") ?: ""

        setContent {
            val navController = rememberNavController()
            NavGraph(
                navController = navController,
                authViewModel
            )
            LaunchedEffect(true) {
                navController.navigate("ver_notificaciones_conductor/$userId")
            }

        }
        authViewModel.checkLoggedInState()
    }
}

class Act_Notificaciones_Pas : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent

        val userId = intent.getStringExtra("userId") ?: ""

        setContent {
            val navController = rememberNavController()
            NavGraph(
                navController = navController,
                authViewModel
            )
            LaunchedEffect(true) {
                navController.navigate("ver_notificaciones_pasajero/$userId")
            }
            authViewModel.checkLoggedInState()
        }
    }
}