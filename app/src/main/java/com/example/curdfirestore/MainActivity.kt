package com.example.curdfirestore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface

import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.Usuario.NavGraph

import com.example.curdfirestore.ui.theme.CURDFirestoreTheme

class MainActivity :ComponentActivity() {

    private lateinit var navController: NavHostController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CURDFirestoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    navController = rememberNavController()
                    NavGraph(
                        navController = navController
                    )
                }
            }
        }
    }
}