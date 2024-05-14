package com.example.curdfirestore.Viaje.Pantallas.Huella


import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.curdfirestore.MainActivity


fun autenticaHuella(
    activity: MainActivity,
    exitoso: () -> Unit,
    fallido: () -> Unit,
    maxIntentos: Int // Valor por defecto de 3 intentos
) {

    var intentos = 0

    val executor = ContextCompat.getMainExecutor(activity)

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Validación de identidad")
        .setSubtitle("Por favor, coloca tu dedo en el sensor")
        .setNegativeButtonText("Cancelar")
        .build()
    lateinit var biometricPrompt: BiometricPrompt // Define la variable fuera del constructor

     biometricPrompt = BiometricPrompt(activity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                fallido()
                // Maneja el error de autenticación
                Toast.makeText(
                    activity,
                    "Limite de intentos superado",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                exitoso()
                Toast.makeText(activity, "Validado", Toast.LENGTH_SHORT).show()
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
                    println("Autenticación fallida-----")
                    biometricPrompt.cancelAuthentication()
                }
            }
        })

    biometricPrompt.authenticate(promptInfo)

}