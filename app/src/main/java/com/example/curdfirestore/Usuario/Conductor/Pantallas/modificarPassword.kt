package com.example.curdfirestore.Usuario.Conductor.Pantallas

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.ui.theme.Aplicacion.cabeceraConBotonAtras

import com.example.curdfirestore.AuthViewModel
import com.example.curdfirestore.R
import com.example.curdfirestore.Usuario.Conductor.menuCon
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



var maxhm=0.dp
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun modificarPasswordCon(
    navController: NavController,
    userId: String,
    authViewModel: AuthViewModel
) {



    var currentPassword by remember { mutableStateOf(TextFieldValue("")) }
    var newPassword by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue("")) }

    val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser: FirebaseUser? = firebaseAuth.currentUser

    var hidden by remember { mutableStateOf(true) } //1
    var hiddenNewPassword by remember { mutableStateOf(true) }
    var isPasswordValidNew by remember { mutableStateOf(true) }

    var hiddenConfirmPassword by remember { mutableStateOf(true) }
    var isPasswordValidConfirm by remember { mutableStateOf(true) }
    var passwordsMatch by remember { mutableStateOf(true) }

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    // Función para actualizar la variable passwordsMatch

    fun actualizarPassword(password: String, confirm: String) {
        passwordsMatch = password == confirm
    }

    BoxWithConstraints {
        maxhm = this.maxHeight - 50.dp
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(45.dp)) {
                menuCon(navController, userId)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxhm)
                .background(Color.White)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                cabeceraConBotonAtras(titulo = "Contraseña", navController = navController)


                Column(
                    modifier = Modifier
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    OutlinedTextField(
                        value = currentPassword.text,
                        onValueChange = {
                            currentPassword = TextFieldValue(it)
                        },
                        label = { Text("Contraseña actual") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation =
                        if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIcon = {
                            IconButton(
                                onClick = { hidden = !hidden },
                                modifier = Modifier.size(40.dp),
                            ) {
                                val vector = painterResource(
                                    if (hidden) R.drawable.novisible
                                    else R.drawable.visible
                                )
                                val description =
                                    if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                                Icon(
                                    painter = vector,
                                    contentDescription = description,
                                    tint = Color(137, 13, 88)
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = {
                            newPassword = it
                            // Validar la contraseña en tiempo real y actualizar isPasswordValid
                            isPasswordValidNew = criteriosPassword(it.text)
                            actualizarPassword(newPassword.text, confirmPassword.text)
                        },
                        label = { Text("Nueva Contraseña") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation =
                        if (hiddenNewPassword) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIcon = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                if (newPassword.text.length > 0) {
                                    if (isPasswordValidNew) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.Green
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = null,
                                            tint = Color.Red,
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = { hiddenNewPassword = !hiddenNewPassword },
                                    modifier = Modifier.size(40.dp),
                                ) {
                                    val vector = painterResource(
                                        if (hiddenNewPassword) R.drawable.novisible
                                        else R.drawable.visible
                                    )
                                    val description =
                                        if (hiddenNewPassword) "Ocultar contraseña" else "Revelar contraseña"
                                    Icon(
                                        painter = vector,
                                        contentDescription = description,
                                        tint = Color(137, 13, 88)
                                    )
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            // Validar la contraseña en tiempo real y actualizar isPasswordValid
                            isPasswordValidConfirm = criteriosPassword(it.text)
                            actualizarPassword(newPassword.text, it.text)
                        },
                        label = { Text("Confirmar Contraseña") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation =
                        if (hiddenConfirmPassword) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIcon = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                // Primero, el Icon
                                if (confirmPassword.text.length > 0) {
                                    if (isPasswordValidConfirm && passwordsMatch) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = Color.Green
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = null,
                                            tint = Color.Red,
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = { hiddenConfirmPassword = !hiddenConfirmPassword },
                                    modifier = Modifier.size(40.dp),
                                ) {
                                    val vector = painterResource(
                                        if (hiddenConfirmPassword) R.drawable.novisible
                                        else R.drawable.visible
                                    )
                                    val description =
                                        if (hiddenConfirmPassword) "Ocultar contraseña" else "Revelar contraseña"
                                    Icon(
                                        painter = vector,
                                        contentDescription = description,
                                        tint = Color(137, 13, 88)
                                    )
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )

                        Text(
                            text = "Recuerda que la contraseña debe tener al menos 8 carácteres, incluyendo una letra mayúscula, una minúscula, un número y un caracter especial.",
                            color = Color.Gray,
                            fontSize = 15.sp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(137, 13, 88)),
                        onClick = {
                            try {
                                val email = currentUser?.email ?: ""
                                val credential = EmailAuthProvider.getCredential(email, currentPassword.text)

                                currentUser?.reauthenticate(credential)?.addOnCompleteListener { reauthTask ->
                                    if (reauthTask.isSuccessful) {
                                        println("¡Reautenticación exitosa!")

                                        // Verificar los criterios de la nueva contraseña
                                        val newPasswordText = newPassword.text
                                        val confirmPasswordText = confirmPassword.text

                                        if (criteriosPassword(newPasswordText) && newPasswordText == confirmPasswordText) {
                                            cambiarPassword(currentUser, newPassword, confirmPassword)
                                            showDialog = true

                                        } else {
                                            if (!criteriosPassword(newPasswordText)) {
                                                Toast.makeText(
                                                    context,
                                                    "La nueva contraseña no cumple con los criterios.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                            if (newPasswordText != confirmPasswordText) {
                                                Toast.makeText(
                                                    context,
                                                    "Las contraseñas no coinciden.",
                                                    Toast.LENGTH_SHORT
                                                ).show()


                                            }
                                        }

                                    } else {
                                        Toast.makeText(
                                            context,
                                            "La contraseña actual es incorrecta.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            } catch (e: Exception) {
                                println("Error durante la reautenticación: ${e.message}")
                                Toast.makeText(
                                    context,
                                    "Error durante la reautenticación: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }) {

                        Text(
                            text = "Guardar", style = TextStyle(
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        )

                    }
                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = {
                                authViewModel.signOut()
                                navController.navigate(route = "login")
                            },
                            title = {
                                Text("Contraseña cambiada exitosamente")
                            },
                            text = {
                                Text("Por seguridad, tú sesión se cerrará para que ingreses con tu nueva contraseña.")
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        authViewModel.signOut()
                                        navController.navigate(route = "login")

                                        showDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(137, 13, 88))
                                ) {
                                    Text("Cerrar", color = Color.White)
                                }
                            }
                        )
                    }


                }


            }
        }
    }

}


 fun cambiarPassword(
    currentUser: FirebaseUser?,
    newPassword: TextFieldValue,
    confirmPassword: TextFieldValue
    ) {
    if (newPassword.text == confirmPassword.text) {
        currentUser?.updatePassword(newPassword.text)?.addOnCompleteListener { updatePasswordTask ->
            if (updatePasswordTask.isSuccessful) {
                println("Se ha actualizado la contraseña")
            } else {
                println("No se pudo actualizar: ${updatePasswordTask.exception?.message}")
            }
        }
    } else {
        println("Las contraseñas no coinciden")
    }
}

// Función para verificar los criterios de la nueva contraseña
fun criteriosPassword(password: String): Boolean {
    val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}\$")
    return regex.matches(password)
}

