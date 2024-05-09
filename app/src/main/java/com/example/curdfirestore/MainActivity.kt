package com.example.curdfirestore

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.Usuario.NavGraph

import com.example.curdfirestore.ui.theme.CURDFirestoreTheme

class MainActivity : FragmentActivity() {

    private lateinit var navController: NavHostController

    private val authViewModel: AuthViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val savedStateHandle = SavedStateHandle() // Opcionalmente puedes pasar un SavedStateHandle personalizado aquí
        val viewModelFactory = ContadorViewModelFactory(savedStateHandle)

        setContent {
            CURDFirestoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        authViewModel
                    )
                }
            }
        }
        authViewModel.checkLoggedInState()


    }

}