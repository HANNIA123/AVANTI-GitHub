package com.example.avanti.Usuario

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.TokenData
import com.example.avanti.UserData

import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioRT
import com.example.curdfirestore.Notificaciones.Consultas.showNotificationPermissionDialog

import com.example.curdfirestore.R
import com.example.curdfirestore.Usuario.dialogoNoToken

import com.example.curdfirestore.conObtenerTokenRTCom

import com.example.curdfirestore.registrarToken
import com.example.curdfirestore.validaBloqueoLogin
import com.google.firebase.messaging.FirebaseMessaging


@SuppressLint("SuspiciousIndentation", "CoroutineCreationDuringComposition")
@Composable

fun Login(
    navController: NavController,
    onButtonClick: (String) -> Unit
) {
    val context = LocalContext.current
    val viewModel = LoginViewModel()
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var logueo by remember {
        mutableStateOf(false)
    }

    var hidden by remember { mutableStateOf(true) } //1
    var nameError by remember { mutableStateOf(false) } // 1 -- Field obligatorio
    var nameError1 by remember { mutableStateOf(false) } // 1 -- Field obligatorio

    var ejecutado by remember { mutableStateOf(false) }
    var boton by remember { mutableStateOf(false) }
    var loginAttempts by remember { mutableStateOf(0) }
    var newIntentos by remember { mutableStateOf(0) }
    val maxLoginAttempts = 3
    var botonInicio by remember { mutableStateOf(true) } // 1 -- Field obligatorio

    var showDialogo by remember {
        mutableStateOf(false)
    }

    var usuarioData by remember {
        mutableStateOf<UserData?>(null)
    }

    var tokenActual by remember {
        mutableStateOf("")
    }
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            tokenActual = task.result

        } else {
            println("FCM -> Error al obtener el token: ${task.exception}")
        }
    }


    val tokenData = TokenData(
        token = tokenActual,
        token_bloqueo = false,
        token_intentos = loginAttempts,
        token_hora_bloqueo = "",
        token_fecha = ""
    )


    var idTokenRegistrado by remember {
        mutableStateOf("")
    }
    /*
        registrarToken(tokenData) { result ->
            result.onSuccess { data ->
                data.let { (id, token) ->
                    idTokenRegistrado = id

                }
            }.onFailure { e ->
                println("Error: $e")
            }
        }
    */


    LaunchedEffect(ejecutado) {
        if (ejecutado) {
            boton = false
            ejecutado = false

        }
    }


    /*   validaBloqueoLogin(
           idToken = idTokenRegistrado,
           botonActivo = {
               botonInicio = true
               println("Botón activado")
           },
           botonNoActivo = {
               botonInicio = false
               println("Botón desactivado")
           }
       )
     */

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.TopCenter
        )
        {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                painter = painterResource(id = R.drawable.elipse),
                contentDescription = "Fondo inicial",
                contentScale = ContentScale.FillBounds
            )
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                contentScale = ContentScale.FillWidth
            )
        }
        Text(
            text = "Bienvenido", style = TextStyle(
                color = Color(71, 12, 107),
                fontSize = 30.sp,
                textAlign = TextAlign.Center

            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    nameError = false //2
                },
                label = {
                    Text(
                        "Correo electrónico",
                        style = TextStyle(
                            fontSize = 18.sp
                        )
                    )
                },
                isError = nameError, //3
                singleLine = true,
                trailingIcon = {// 4
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Icono Usuario",
                        tint = Color(137, 13, 88)
                    )
                },
                modifier = Modifier.width(300.dp)
            )

            //Texto obligatorio

            val assistiveElementText =
                if (nameError) "Ingresa tu correo electrónico" else "" // 4
            val assistiveElementColor = if (nameError) { // 5
                MaterialTheme.colors.error
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
            }
            Text(
// 6
                text = assistiveElementText,
                color = assistiveElementColor,
                style = MaterialTheme.typography.caption,
                //modifier = Modifier.offset(y = -16.dp)
            )

            Spacer(Modifier.size(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    nameError1 = false
                },
                label = {
                    Text(
                        "Contraseña",
                        style = TextStyle(
                            fontSize = 18.sp
                        )
                    )
                },
                isError = nameError1, // 3
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),//2
                singleLine = true,
                visualTransformation =
                if (hidden) PasswordVisualTransformation() else VisualTransformation.None,//3
                trailingIcon = {// 4
                    IconButton(
                        onClick = { hidden = !hidden }, Modifier.size(40.dp),

                        ) {
                        val vector = painterResource(//5
                            if (hidden) R.drawable.novisible
                            else R.drawable.visible
                        )
                        val description =
                            if (hidden) "Ocultar contraseña" else "Revelar contraseña" //6
                        Icon(
                            painter = vector,
                            contentDescription = description,
                            tint = Color(137, 13, 88)
                        )
                    }
                },
                modifier = Modifier.width(300.dp)
            )

            val assistiveElementText1 = if (nameError1) "Ingresa tu contraseña" else "" // 4
            val assistiveElementColor1 = if (nameError1) { // 5
                MaterialTheme.colors.error
            } else {
                MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
            }

            Text(
// 6
                text = assistiveElementText1,
                color = assistiveElementColor1,
                style = MaterialTheme.typography.caption,

                )

            Spacer(Modifier.size(16.dp))
            // ***


            TextButton(onClick = {
                navController.navigate(route = "reset_password")


            }) {
                Text(
                    text = "¿Ha olvidado su contraseña?", style = TextStyle(
                        color = Color(137, 13, 88),
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center

                    )
                )
            }
            Spacer(Modifier.size(16.dp))

            //Boton Ingreso
            if (botonInicio) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            137,
                            13,
                            88
                        )
                    ),
                    onClick = {
                        nameError = email.isBlank()
                        nameError1 = password.isBlank()
                        if (!nameError && !nameError1) {
                            boton = true

                        }
                    },
                    modifier = Modifier
                        .width(300.dp)
                        .width(80.dp)
                ) {
                    Text(
                        text = "Iniciar sesión", style = TextStyle(
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    )
                }
            } else {
                Text(
                    text = "Sistema bloqueado... Espere 5 minutos", style = TextStyle(
                        fontSize = 18.sp,
                        color = Color(137, 13, 88)
                    )
                )
            }

        }
    }

    var finaliza by remember {
        mutableStateOf(false)
    }

    if (boton) {
        val usuario = conObtenerUsuarioRT(usuarioId = email, botonFin = {
            finaliza = true
        })
        if (!ejecutado) {

            if (finaliza) {

                if (loginAttempts < maxLoginAttempts) {

                    if (usuario == null) {

                        loginAttempts++
                        Toast.makeText(
                            context,
                            "Usuario o contraseña inconrrectos",
                            Toast.LENGTH_SHORT
                        ).show()
                        ejecutado = true
                    } else {
                        val tokenRegistrado = usuarioData?.usu_token_reg
                        if (usuario.usu_status == "Activo") {
                            if (tokenRegistrado == "") {
                                println("Primera vez")
                                viewModel.signInWithEmailAndPassword(email,
                                    password,
                                    context = context,
                                    home = {
                                        sendTokenToServer2(
                                            email,
                                            tokenActual
                                        )
                                        onButtonClick(email)
                                        showNotificationPermissionDialog(context)

                                    },
                                    errorCallback = {
                                        loginAttempts++
                                        Toast.makeText(
                                            context,
                                            "Usuario o contraseña inconrrectos",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        ejecutado = true
                                    })


                            } else {
                                println("ya registardo Actual $tokenActual  Regi $tokenRegistrado")
                                if (tokenActual == tokenRegistrado) {
                                    println("Adentroooo")

                                    viewModel.signInWithEmailAndPassword(email,
                                        password,
                                        context = context,
                                        home = {
                                            sendTokenToServer(
                                                email,
                                                tokenActual
                                            )
                                            onButtonClick(email)
                                            showNotificationPermissionDialog(context)

                                        },
                                        errorCallback = {
                                            loginAttempts++
                                            Toast.makeText(
                                                context,
                                                "Usuario o contraseña inconrrectos",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            ejecutado = true
                                        })


                                } else {
                                    showDialogo = true
                                }
                            }
                        } else {

                            Toast.makeText(
                                context,
                                "Usuario dado de baja",
                                Toast.LENGTH_SHORT
                            ).show()

                        }

                    }


                } else {
                    Toast.makeText(
                        context,
                        "Sistema bloqueado",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }

    }

    if (showDialogo) {
        dialogoNoToken({
            showDialogo = false

        }, navController)
    }
}









