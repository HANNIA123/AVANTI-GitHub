package com.example.curdfirestore
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.avanti.HistorialViajesData
import com.example.avanti.TokenData
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaFormatoddmmyyyy
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerHistorialViajeRT
import com.example.curdfirestore.Viaje.ConsultasViaje.editarCampoHistorial
import com.example.curdfirestore.Viaje.Funciones.convertirStringATimeSec
import com.example.curdfirestore.Viaje.Funciones.obtenerHoraActualSec
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import java.time.temporal.ChronoUnit

fun registrarToken(tokenData: TokenData, callback: (result: Result<Pair<String, TokenData?>>) -> Unit) {
    val db: FirebaseFirestore = Firebase.firestore
    val imprevistoRef = db.collection("token")

    // Buscar el documento que tenga el campo token igual al token dado
    imprevistoRef.whereEqualTo("token", tokenData.token)
        .get()
        .addOnSuccessListener { querySnapshot ->
            if (!querySnapshot.isEmpty) {
                // Si encontramos un documento con ese token, devolver su ID y datos
                val document = querySnapshot.documents[0]
                val existingTokenData = document.toObject(TokenData::class.java)
                println("El token ya existe en Firebase.")
                callback(Result.success(Pair(document.id, existingTokenData)))
            } else {
                // Si no encontramos ningún documento con ese token, crear uno nuevo
                imprevistoRef.add(tokenData)
                    .addOnSuccessListener { documentReference ->
                        println("Token agregado correctamente a Firebase con ID: ${documentReference.id}")
                        callback(Result.success(Pair(documentReference.id, tokenData)))
                    }
                    .addOnFailureListener { e ->
                        println("Error al agregar el token a Firebase: $e")
                        callback(Result.failure(e))
                    }
            }
        }
        .addOnFailureListener { e ->
            println("Error al buscar el token en Firebase: $e")
            callback(Result.failure(e))
        }
}

fun conObtenerTokenRT(
    tokenId: String,
    callback: (TokenData?) -> Unit
): ListenerRegistration {
    val db = FirebaseFirestore.getInstance()
    val tokenRef = db.collection("token").document(tokenId)

    val listenerRegistration = tokenRef.addSnapshotListener { snapshot, error ->
        if (error != null) {
            println("Error al obtener token: $error")
            callback(null)
            return@addSnapshotListener
        }

        if (snapshot != null && snapshot.exists()) {
            val tokenData = snapshot.toObject(TokenData::class.java)
            callback(tokenData)
        } else {
            callback(null)
        }
    }

    return listenerRegistration
}

fun validaBloqueoLogin(
    idToken: String,
    botonActivo: () -> Unit,
    botonNoActivo: () -> Unit
) {
    val handler = Handler(Looper.getMainLooper())
    val delayMillis = 2000L // 2 segundos en milisegundos, para recargar

    val runnableCode = object : Runnable {
        override fun run() {
            conObtenerTokenRT(idToken) { token ->

                if(token!=null && obtenerFechaFormatoddmmyyyy()==token.token_fecha){
                    if (token.token_bloqueo) {
                        val tiempoBloqueo = 10
                        val horaActual = convertirStringATimeSec(obtenerHoraActualSec())
                        val horaBloqueo = convertirStringATimeSec(token.token_hora_bloqueo)

                        val diferencia = ChronoUnit.SECONDS.between(horaBloqueo, horaActual)
                        println("Diferenciaaa $diferencia")
                        println("Actua: $horaActual  Bloqueo $horaBloqueo")

                        if (diferencia > tiempoBloqueo) {
                            val nuevo = mapOf(
                                "token_hora_bloqueo" to "",
                                "token_bloqueo" to false,
                                "token_intentos" to 0
                            )

                            editarCamposToken(idToken, nuevo)

                            println("La diferencia de tiempo es mayor a 1 minuto. Boton ACTIVO")
                            botonActivo()
                        } else {
                            botonNoActivo()
                            println("La diferencia de tiempo es menor o igual a 1 minuto. INACTIVO")
                        }
                    }

                }
                else{
                    val nuevo = mapOf(
                        "token_hora_bloqueo" to "",
                        "token_bloqueo" to false,
                        "token_intentos" to 0,
                        "token_fecha" to ""
                    )

                    editarCamposToken(idToken, nuevo)

                }


                // Vuelve a programar la ejecución del código después de 2 segundos
                handler.postDelayed(this, delayMillis)
            }
        }
    }

    // Iniciar la ejecución del código por primera vez
    handler.postDelayed(runnableCode, delayMillis)
}

fun editarCampoToken(documentId: String, campo: String, valor: Any) {
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("token").document(documentId).update(campo, valor)
            .addOnSuccessListener {
                println("Documento con ID $documentId actualizado correctamente de Firestore.")
            }
            .addOnFailureListener { exception ->
                println("Error al intentar actualizar el documento de Firestore: $exception")
            }
    } catch (e: Exception) {
        println("Excepción al intentar actualizar el documento de Firestore: $e")
    }
}

fun editarCamposToken(documentId: String, nuevosValores: Map<String, Any>) {
    try {
        val db = FirebaseFirestore.getInstance()
        db.collection("token").document(documentId).update(nuevosValores)
            .addOnSuccessListener {
                println("Documento con ID $documentId actualizado correctamente en Firestore.")
            }
            .addOnFailureListener { exception ->
                println("Error al intentar actualizar el documento en Firestore: $exception")
            }
    } catch (e: Exception) {
        println("Excepción al intentar actualizar el documento en Firestore: $e")
    }
}
@Composable
fun conObtenerTokenRTCom(
    tokenId: String
): TokenData? {

    var fin by remember { mutableStateOf(false) }
    // Obtener objeto ViajeData
    var token by remember { mutableStateOf<TokenData?>(null) }

    LaunchedEffect(key1 = tokenId) {
        val db = Firebase.firestore

        val viajeRef = db.collection("token").document(tokenId)

        viajeRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                println("Error al obtener historiaaal: $error")
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                token = snapshot.toObject(TokenData::class.java)
            }
            fin = true
        }
    }

    return if (fin) {
        token
    } else {
        null
    }
}