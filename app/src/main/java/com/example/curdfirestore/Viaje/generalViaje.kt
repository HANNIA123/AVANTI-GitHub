package com.example.curdfirestore.Viaje

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.avanti.Usuario.Conductor.Pantallas.maxh
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.Usuario.LoginViewModel
import com.example.avanti.ui.theme.Aplicacion.encabezado
import com.example.curdfirestore.Usuario.Conductor.menuCon

//Pantalla para agregar el formulario con la información general del viaje
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun generalViaje(
    navController: NavController,
    userID:String
) {

    BoxWithConstraints {
        maxh = this.maxHeight - 50.dp
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(45.dp)) {
                menuCon(navController = navController, userID = userID)
                //pruebaMenu(navController,userID)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(maxh)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {


        }
    }
}
