package com.example.curdfirestore.Viaje.Pantallas.Huella


import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.curdfirestore.MainActivity


fun autenticaHuella(
    activity: MainActivity,
    exitoso: () -> Unit,
    fallido: () -> Unit,
    maxIntentos: Int
) {
    var intentos = 0
    val executor = ContextCompat.getMainExecutor(activity)

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Validación de identidad")
        .setSubtitle("Por favor, coloca tu dedo en el sensor")
        .setNegativeButtonText("Cancelar")
        .build()
    lateinit var biometricPrompt: BiometricPrompt

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
                intentos++
                if (intentos >= maxIntentos) {
                    // Verifica si se alcanzó el límite de intentos
                    fallido()
                    Toast.makeText(
                        activity,
                        "Limite de intentos superado",
                        Toast.LENGTH_SHORT
                    ).show()
                    biometricPrompt.cancelAuthentication()
                }
            }
        })
    biometricPrompt.authenticate(promptInfo)
}