package com.example.avanti.Usuario

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.curdfirestore.R


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

    var hidden by remember { mutableStateOf(true) } //1
    var nameError by remember { mutableStateOf(false) } // 1 -- Field obligatorio
    var nameError1 by remember { mutableStateOf(false) } // 1 -- Field obligatorio
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
                fontSize = 28.sp,
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
                label = { Text("Email") },
                isError = nameError, //3
                singleLine = true,
                trailingIcon = {// 4
                    Icon(
                        modifier = Modifier.size(50.dp),
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Icono Usuario",
                        tint = Color(137, 13, 88)
                    )
                }
            )

            //Texto obligatorio

            val assistiveElementText = if (nameError) "Ingresa tu correo electrónico" else "" // 4
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
                label = { Text("Contraseña") },
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
                }
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
                navController.navigate(route = "ResetPassword")


            }) {
                Text(
                    text = "¿Ha olvidado su contraseña?", style = TextStyle(
                        color = Color(137, 13, 88),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center

                    )
                )
            }
            Spacer(Modifier.size(16.dp))
            //Boton Ingreso
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(137, 13, 88)),
                onClick = {
                    nameError = email.isBlank()
                    nameError1 = password.isBlank()
                    viewModel.signInWithEmailAndPassword(email, password, context = context) {
                        onButtonClick(email)
                    }
                }) {
                Text(
                    text = "Iniciar sesión", style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )
            }
        }


    }
}
