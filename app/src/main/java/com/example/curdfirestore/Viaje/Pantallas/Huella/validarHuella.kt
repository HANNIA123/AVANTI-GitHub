package com.example.curdfirestore.Viaje.Pantallas.Huella

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.avanti.HorarioDataReturn
import com.example.curdfirestore.MainActivity
import com.example.curdfirestore.Viaje.Pantallas.Monitoreo.obtenerCoordenadas

@Composable
fun Greeting() {
    println("Funcion----")

    val context = LocalContext.current
    val activity = context as? FragmentActivity

    println("Llama a la huella y $activity")
    activity?.let {


        val executor = activity?.let { ContextCompat.getMainExecutor(it) }

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación Biométrica")
            .setSubtitle("Por favor, coloca tu dedo en el sensor")
            .setNegativeButtonText("Cancelar")
            .build()

        val biometricPrompt = executor?.let { it1 ->
            BiometricPrompt(activity, it1,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        // Maneja el error de autenticación
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        // Autenticación biométrica exitosa
                    }

                    override fun onAuthenticationFailed() {
                        // Autenticación biométrica fallida
                    }
                })
        }

        // Suponiendo que tienes un botón en tu composable que llama a esta función
        if (biometricPrompt != null) {
            biometricPrompt.authenticate(promptInfo)
        }
    }
}

/*
@Composable
fun Greeting2(
    activity: MainActivity,
    exitoso: () -> Unit,
    fallido: () -> Unit,
    maxIntentos: Int  // Valor por defecto de 3 intentos
) {
    val context = LocalContext.current
    var intentos = 0

    val executor = ContextCompat.getMainExecutor(activity)

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Autenticación de esta parada")
        .setSubtitle("Por favor, coloca tu dedo en el sensor")
        .setNegativeButtonText("Cancelar")
        .build()

    val biometricPrompt = BiometricPrompt(activity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                // Maneja el error de autenticación
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                exitoso()
                Toast.makeText(activity, "Validado", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                if (intentos < maxIntentos - 1) {
                    intentos++

                } else {
                    Toast.makeText(activity, "Limite de intentos superado", Toast.LENGTH_SHORT).show()
                    println("Autenticación fallida")
                    fallido()
                }
            }
        })

    // Suponiendo que tienes un botón en tu composable que llama a esta función
    biometricPrompt.authenticate(promptInfo)
}
*/



fun autenticaHuella(
    activity: MainActivity,
    exitoso: () -> Unit,
    fallido: () -> Unit,
    maxIntentos: Int // Valor por defecto de 3 intentos
) {

    var intentos = 0
    var autenticacionActiva = true // Variable para controlar si la autenticación está activa

    val executor = ContextCompat.getMainExecutor(activity)

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Validación de identidad")
        .setSubtitle("Por favor, coloca tu dedo en el sensor")
        .setNegativeButtonText("Cancelar")
        .build()

    val biometricPrompt = BiometricPrompt(activity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                // Maneja el error de autenticación
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                exitoso()
                Toast.makeText(activity, "Validado", Toast.LENGTH_SHORT).show()
                autenticacionActiva = false // Marca la autenticación como no activa después de éxito
            }

            override fun onAuthenticationFailed() {
                intentos++ // Incrementa el contador de intentos al fallar la autenticación

                if (intentos >= maxIntentos) {
             // Verifica si se alcanzó el límite de intentos
                    fallido()
                    Toast.makeText(
                        activity,
                        "Limite de intentos superado",
                        Toast.LENGTH_SHORT
                    ).show()
                    println("Autenticación fallida")
                    autenticacionActiva = false // Marca la autenticación como no activa después de falla
                }
            }
        })

    biometricPrompt.authenticate(promptInfo)
println("Autenticacion activ $autenticacionActiva")
    // Verifica si la autenticación sigue activa después de llamar authenticate
    if (!autenticacionActiva) {
        biometricPrompt.cancelAuthentication() // Cancela la autenticación si no está activa
    }
}
