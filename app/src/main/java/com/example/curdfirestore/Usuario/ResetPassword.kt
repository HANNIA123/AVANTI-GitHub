package com.example.curdfirestore.Usuario
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.avanti.Usuario.Conductor.Pantallas.homePantallaConductor
import com.example.avanti.Usuario.LoginViewModel
import com.example.curdfirestore.R


@Composable

fun resetPassword(
navController: NavController
) {
    val context = LocalContext.current
val viewModel= LoginViewModel()
    var email by remember {
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
            text = "¿Ha olvidado su contraseña?", style = TextStyle(
                color = Color(71, 12, 107),
                fontSize = 28.sp,
                textAlign = TextAlign.Center

            ),
            modifier = Modifier.padding(10.dp)

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


            Spacer(Modifier.size(16.dp))
            //Boton Ingreso
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(137, 13, 88)),
                onClick = {
                    nameError = email.isBlank()
                    viewModel.resetPassword(email, context, navController)
                }) {
                Text(
                    text = "Restablecer contraseña", style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )
            }
            Spacer(Modifier.size(16.dp))
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(131, 139, 131)),
                onClick = {
                    navController.popBackStack()

                }) {

                Text(
                    text = "Regresar", style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )


            }

    }


    }
}

@Preview(showBackground = true)
@Composable
fun ContentReser() {
    val navController = rememberNavController()
    resetPassword(navController = navController)


}

