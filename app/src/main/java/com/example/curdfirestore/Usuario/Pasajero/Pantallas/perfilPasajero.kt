package com.example.curdfirestore.Usuario.Pasajero.Pantallas

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.avanti.ui.theme.Aplicacion.cabeceraSin
import com.example.avanti.ui.theme.Aplicacion.lineaGris
import com.example.avanti.ui.theme.Aplicacion.nombreCompleto

import com.example.curdfirestore.InfTextos
import com.example.curdfirestore.Usuario.Conductor.ConsultasConductor.conObtenerVehiculoId
import com.example.curdfirestore.Usuario.Conductor.Pantallas.maxp
import com.example.curdfirestore.Usuario.Pasajero.menuPas

var maxp=0.dp
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun perfilPas(
    navController: NavController,
    userId: String
){
    val usuario= conObtenerUsuarioId(correo = userId)
    val vehiculo= conObtenerVehiculoId(correo = userId)

    BoxWithConstraints {
        maxp = this.maxHeight - 50.dp
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(45.dp)) {
                menuPas(navController,userId)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxp)
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                cabeceraSin(titulo = "Mi perfil")

                //importante, agregar esta condición
                usuario?.let {
                        // Cargar y mostrar la imagen con Coil
                        CoilImage(url = usuario!!.usu_foto, modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally),
                        )
                        Text(
                            text = "Información de la cuenta",
                            modifier= Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(
                                color = Color(71, 12, 107),
                                fontSize = 23.sp,
                            )
                        )

                        //Datos del conductor
                        InfTextos(Title = "Nombre de usuario", Inf =usuario!!.usu_nombre_usuario )
                        InfTextos(Title = "Tipo de usuario", Inf =usuario!!.usu_tipo )
                        //Falta cambiar contraseñ
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(230.dp)
                        ) {
                            Text(
                                text = "Contraseña",
                                modifier = Modifier
                                    .offset(x = 30.dp)
                                    .padding(2.dp),
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 18.sp
                                )
                            )
                            IconButton(
                                modifier = Modifier.offset(y = -10.dp),
                                onClick = {
                                    navController.navigate(route = "modificar_password_pasajero/$userId")
                                }) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Filled.KeyboardArrowRight,
                                    contentDescription = "Icono Usuario",
                                    tint = Color(104, 104, 104),


                                    )

                            }

                        }
                        lineaGris()
                        Text(
                            text = "Información personal",
                            modifier= Modifier.align(Alignment.CenterHorizontally),
                            style = TextStyle(
                                color = Color(71, 12, 107),
                                fontSize = 23.sp,
                            )
                        )
                        InfTextos(Title = "Nombre", Inf = nombreCompleto(
                            nombre = usuario.usu_nombre,
                            apellidop = usuario.usu_primer_apellido,
                            apellidom = usuario.usu_segundo_apellido
                        ) )

                        InfTextos(Title = "Boleta: ", Inf = usuario.usu_boleta)
                        InfTextos(Title = "Correo electrónico", Inf =userId )
                        InfTextos(Title = "Número telefónico", Inf =usuario.usu_telefono)


                }







            }
        }
    }
}