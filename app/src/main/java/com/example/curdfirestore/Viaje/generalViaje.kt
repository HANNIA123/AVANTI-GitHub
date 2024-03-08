package com.example.curdfirestore.Viaje

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row



import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.HorizontalAlignmentLine
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.Usuario.Conductor.Pantallas.homePantallaConductor
import com.example.avanti.Usuario.Conductor.Pantallas.maxh
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.Usuario.LoginViewModel
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.avanti.ui.theme.Aplicacion.tituloNoAtras
import com.example.curdfirestore.R
import com.example.curdfirestore.Usuario.Conductor.Pantallas.mhv
import com.example.curdfirestore.Usuario.Conductor.menuCon
import com.example.curdfirestore.Usuario.Conductor.menuDesplegableCon
import kotlinx.coroutines.launch
import okhttp3.internal.wait


//Pantalla para agregar el formulario con la información general del viaje
@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun generalViajeCon(
    navController: NavController,
    userId: String
) {
    var expanded by remember { mutableStateOf(false) }
    BoxWithConstraints {
        maxh = this.maxHeight
    }
    var isDrawerVisible by remember { mutableStateOf(false) }

    Scaffold(


    ) {

        var selectedIndex by remember { mutableStateOf(0) }

        var tamEspacio = 23.dp
        var tamIcono = 55.dp

        var showDialogDia by remember { mutableStateOf(false) }
        var selectedDays by remember { mutableStateOf(emptySet<Int>()) }

        var showDialogTrayecto by remember { mutableStateOf(false) }
        var selectedTrayecto by remember { mutableStateOf(emptySet<Int>()) }


        Column(
            modifier = Modifier
                .fillMaxWidth()


                .background(


                    Color.White // Cambiar a tu color cuando la condición es falsa


                )

                .height(maxh)
                .verticalScroll(rememberScrollState()),

            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            )
            {


                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    painter = painterResource(id = R.drawable.fondorec),
                    contentDescription = "Fondo inicial",
                    contentScale = ContentScale.FillBounds
                )

                IconButton(
                    //  backgroundColor = Color.Black.copy(alpha = 0.0f),
                    onClick = { expanded = !expanded },
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .offset(x = -165.dp)

                ) {
                    Icon(
                        Icons.Filled.Menu, contentDescription = "Open menu",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }


                Column(
                    modifier = Modifier

                        .padding(20.dp, 60.dp, 20.dp, 20.dp)
                        .background(

                            Color.White // Cambiar a tu color cuando la condición es falsa


                        )

                )
                {


                    Column(modifier = Modifier.padding(10.dp)) {


//Otro menu


                        //


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {


                            Text(
                                text = "Registro de viaje",
                                style = TextStyle(
                                    color = Color(71, 12, 107),
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.weight(1f) // El texto ocupará el espacio restante
                            )


                        }

                        Spacer(modifier = Modifier.height(tamEspacio))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    Color.LightGray
                                )
                                .clickable {
                                    showDialogDia = true
                                    // show = true

                                }

                        ) {
                            Icon(
                                imageVector = Icons.Filled.DateRange,
                                contentDescription = "Icono días",
                                modifier = Modifier
                                    .size(tamIcono)
                                    .padding(10.dp, 5.dp),
                                tint = Color(137, 13, 86)
                            )
                            Text(
                                text = "Seleccionar día",
                                textAlign = TextAlign.Left,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp, 15.dp, 10.dp, 10.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black

                                )
                            )

                        }

                        Spacer(modifier = Modifier.height(tamEspacio))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    Color.LightGray
                                )
                                .clickable {
                                    showDialogTrayecto = true

                                }

                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowForward,
                                contentDescription = "Icono trayecto",
                                modifier = Modifier
                                    .size(tamIcono)
                                    .padding(10.dp, 5.dp),
                                tint = Color(137, 13, 86)
                            )
                            Text(
                                text = "Seleccionar trayecto",
                                textAlign = TextAlign.Left,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp, 15.dp, 10.dp, 10.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black

                                )
                            )

                        }


                        Spacer(modifier = Modifier.height(tamEspacio))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    Color.LightGray
                                )
                                .clickable {
                                    showDialogTrayecto = true

                                }

                        ) {
                            Icon(

                                painter = painterResource(id = R.drawable.clock),
                                contentDescription = "Icono horario",
                                modifier = Modifier
                                    .size(tamIcono)
                                    .padding(10.dp, 5.dp),
                                tint = Color(137, 13, 86)
                            )
                            Text(
                                text = "Seleccionar horario",
                                textAlign = TextAlign.Left,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp, 15.dp, 10.dp, 10.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black

                                )
                            )

                        }


                        Spacer(modifier = Modifier.height(tamEspacio))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    Color.LightGray
                                )
                                .clickable {
                                    showDialogTrayecto = true

                                }

                        ) {
                            Icon(

                                painter = painterResource(id = R.drawable.carro),
                                contentDescription = "Icono lugares",
                                modifier = Modifier
                                    .size(tamIcono)
                                    .padding(10.dp, 5.dp),
                                tint = Color(137, 13, 86)
                            )
                            Text(
                                text = "Lugares disponibles",
                                textAlign = TextAlign.Left,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp, 15.dp, 10.dp, 10.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black

                                )
                            )

                        }

                        Spacer(modifier = Modifier.height(tamEspacio))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    1.dp,
                                    Color.LightGray
                                )
                                .clickable {
                                    showDialogTrayecto = true

                                }

                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.tarifa),
                                contentDescription = "Icono tarifa",
                                modifier = Modifier
                                    .size(tamIcono)
                                    .padding(10.dp, 5.dp),
                                tint = Color(137, 13, 86)
                            )
                            Text(
                                text = "Tarifa del viaje",
                                textAlign = TextAlign.Left,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(30.dp, 15.dp, 10.dp, 10.dp),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.Black

                                )
                            )

                        }

                        Spacer(modifier = Modifier.height(tamEspacio))

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    137,
                                    13,
                                    88
                                )
                            ),
                            onClick = {

                            },
                            modifier = Modifier.fillMaxWidth()

                        ) {
                            androidx.compose.material.Text(
                                text = "Siguiente", style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        //atras
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    220,
                                    219,
                                    219
                                )
                            ),
                            onClick = {

                            },
                            modifier = Modifier.fillMaxWidth()

                        ) {
                            Text(
                                text = "Atrás", style = TextStyle(
                                    fontSize = 20.sp,
                                    color = Color(137, 13, 88)
                                )
                            )
                        }

                    }

                }






            }


        }

        // Mostrar días seleccionados
        if (selectedTrayecto.isNotEmpty()) {
            Text("Trayecto seleccionados: ${selectedTrayecto.joinToString(", ")}")
        }

        // Diálogo para la selección de días
        if (showDialogTrayecto) {
            dialogoSeleccionTrayecto(
                onDismiss = { showDialogTrayecto = false },
                onDaysSelected = { selectedTrayecto = it }
            )
        }
        // Mostrar días seleccionados
        if (selectedDays.isNotEmpty()) {
            Text("Días seleccionados: ${selectedDays.joinToString(", ")}")
        }

        // Diálogo para la selección de días
        if (showDialogDia) {
            dialogSeleccionDia(
                onDismiss = { showDialogDia = false },
                onDaysSelected = { selectedDays = it }
            )
        }


        // Contenido principal de la pantalla
        if (expanded) {
menuDesplegableCon(onDismiss = { expanded = false },
    navController = navController, userID = userId)

        }


    }
}






@Composable
fun DayButton(
    day: String,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    val selectedState = rememberUpdatedState(isSelected)

    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selectedState.value) {
                Color(116, 116, 116) // Cambiar a tu color cuando está seleccionado
            } else {
                Color(238, 236, 239) // Cambiar a tu color cuando no está seleccionado
            }
        ),
        modifier = Modifier
            .padding(4.dp)
            .height(50.dp)
            .width(220.dp),
        onClick = { onToggle() },
    ) {
        Text(
            text = day,
            style = TextStyle(
                color = if (selectedState.value) Color.White else Color(137, 13, 88),
                fontSize = 20.sp
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun Myviaje() {
    val navController = rememberNavController()

    generalViajeCon(navController = navController, userId = "hannia")
}

