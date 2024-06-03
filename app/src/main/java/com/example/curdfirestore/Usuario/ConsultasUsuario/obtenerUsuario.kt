package com.example.avanti.Usuario.ConsultasUsuario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.UserData
import com.example.avanti.Usuario.RetrofitClientUsuario
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

import javax.crypto.spec.IvParameterSpec
import java.security.MessageDigest

@Composable
fun conObtenerUsuarioId(correo: String): UserData? {
    var usuario by remember { mutableStateOf<UserData?>(null) }
    var fin by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            try {
                val resultadoUsuario = RetrofitClientUsuario.apiService.obtenerUsuario(correo)
                usuario = resultadoUsuario

                // Obtener claves de desencriptación desde Firestore
                val keysDocument = FirebaseFirestore.getInstance().collection("userKeys").document(correo).get().await()
                val keysData = keysDocument.data?.get("encryptionKeys") as? Map<String, String>

                println("KEYSSSDATA $keysData")

                if (keysData != null && usuario != null) {
                    val identifierKey = keysData["identifier"]
                    val phoneKey = keysData["phone"]

                    println("IDENTIFIERKEY $identifierKey")
                    println("phoneRKEY $phoneKey")

                    // Verificar que las claves no sean nulas ni vacías
                    if (!identifierKey.isNullOrEmpty() && !phoneKey.isNullOrEmpty()) {
                        val decryptedIdentifier = decryptAES(usuario!!.usu_boleta, identifierKey)
                        val decryptedPhone = decryptAES(usuario!!.usu_telefono, phoneKey)

                        usuario = usuario?.copy(
                            usu_boleta = decryptedIdentifier,
                            usu_telefono = decryptedPhone
                        )
                    } else {
                        throw IllegalArgumentException("Empty key")
                    }
                }
            } catch (e: Exception) {
                println("Error al obtener usuario o claves: $e")
            } finally {
                fin = true
            }
        }
    }

    // Puedes retornar el usuario directamente
    return if (fin) {
        usuario
    } else {
        null
    }
}


fun decryptAES(encryptedData: String, secretKey: String): String {
    try {
        println("Secret Key: $secretKey")
        println("Encrypted Data: $encryptedData")

        // Decodifica la cadena codificada en Base64
        val decodedData = Base64.decode(encryptedData, Base64.DEFAULT)

        // Compruebe si los datos cifrados comienzan con el prefijo salt de OpenSSL
        val saltHeader = "Salted__".toByteArray(Charsets.UTF_8)
        val salt = decodedData.copyOfRange(8, 16)

        // Genere la clave y el IV a partir de la contraseña y la sal.
        val keyAndIv = generateKeyAndIv(secretKey.toByteArray(Charsets.UTF_8), salt)
        val key = keyAndIv.copyOfRange(0, 32)
        val iv = keyAndIv.copyOfRange(32, 48)

        // Extracción de los datos cifrados (excluyendo el encabezado salt y salt)
        val encryptedBytes = decodedData.copyOfRange(16, decodedData.size)

        // Descrifrar
        val secretKeySpec = SecretKeySpec(key, "AES")
        val ivParameterSpec = IvParameterSpec(iv)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)

        val decryptedBytes = cipher.doFinal(encryptedBytes)
        val decryptedString = String(decryptedBytes)

        println("Decrypted Data: $decryptedString")
        return decryptedString
    } catch (e: Exception) {
        println("Error in decryption: ${e.message}")
        throw e
    }
}

fun generateKeyAndIv(password: ByteArray, salt: ByteArray): ByteArray {
    val md5 = MessageDigest.getInstance("MD5")
    val keyAndIv = ByteArray(48)
    var lastDigest: ByteArray? = null

    for (i in 0 until 3) {
        md5.update(lastDigest ?: ByteArray(0))
        md5.update(password)
        md5.update(salt)
        lastDigest = md5.digest()
        System.arraycopy(lastDigest, 0, keyAndIv, i * 16, lastDigest!!.size)
    }
    return keyAndIv
}




@Composable
fun conObtenerUsuarioRT(
    usuarioId: String,
    botonFin: () -> Unit
): UserData? {

    var fin by remember { mutableStateOf(false) }
    // Obtener objeto ViajeData
    var viaje by remember { mutableStateOf<UserData?>(null) }

    LaunchedEffect(key1 = usuarioId) {
        val db = Firebase.firestore

        val viajeRef = db.collection("usuario").document(usuarioId)

        viajeRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error al obtener viaje: $error")
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                viaje = snapshot.toObject(UserData::class.java)

            }
            fin = true

        }
    }
    if(fin){
        botonFin()
        println("Aquiiiii")
    }
    print("El boton $fin")
    return if (fin) {
        viaje
    } else {
        null
    }
}

suspend fun validarUsuarioExistente(
    usuarioId: String
): Pair<Boolean, UserData?> {
    return try {
        val db = FirebaseFirestore.getInstance()
        val documentoRef = db.collection("usuario").document(usuarioId)
        val snapshot = documentoRef.get().await()

        if (snapshot.exists()) {
            val usuario = snapshot.toObject(UserData::class.java)
            Pair(true, usuario)
        } else {
            Pair(false, null)
        }
    } catch (e: FirebaseFirestoreException) {
        println("Error al verificar usuario: $e")
        Pair(false, null)
    }
}






