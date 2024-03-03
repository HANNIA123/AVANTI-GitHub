package com.example.avanti.Usuario

sealed class Screens(val route: String) {
    //object MainScreen: Screens(route = "main_screen")
    object GetDataScreen: Screens(route = "get_data_screen")
    object AddDataScreen: Screens(route = "add_data_screen")
    object Login: Screens(route = "login")

}
