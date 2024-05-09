package com.example.avanti.Usuario
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await


class LoginViewModel: ViewModel () {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        context: Context,
        home: () -> Unit,
        errorCallback: () -> Unit
    ) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        Log.d("Logueo", "Logueado!!")
                        home()

                    } else {
                        errorCallback()
                    }
                }
            } catch (ex: Exception) {
                Log.d("Logueo", "SignInWithEmailandPassword: ${ex.message}")
            }
        }


    fun signOut(email: String) = viewModelScope.launch {



        auth.signOut()
    }




    fun resetPassword(
        email: String,
        context: Context,
        navController: NavController,
    )
    {
        try{
            auth.sendPasswordResetEmail(email).addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    Log.d("ResetPassword", "Se ha enviado el correo de restablecimiento!!")
                    Toast.makeText(context, "Se ha enviado el correo de restablecimiento", Toast.LENGTH_SHORT).show()
                    navController.navigate(route = "login")
                } else {
                    Toast.makeText(context, "Correo electrónico no registrado", Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch (e:Exception){

            Log.d("ResetPassword","Error al restablcer la contraseña ${e.message}")
        }
    }





}

fun eliminarToken(email:String){
    try {
        val usuarioRef = FirebaseFirestore.getInstance().collection("usuario").document(email)


        val updates = hashMapOf<String, Any>(
            "usu_token" to FieldValue.delete()
        )

        usuarioRef.update(updates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                println("Campo usu_token eliminado correctamente de Firestore.")
            } else {
                println("Error al intentar eliminar el campo usu_token de Firestore: ${task.exception}")
            }
        }
    } catch (e: Exception) {
        println("Error al intentar eliminar el campo usu_token de Firestore: $e")
    }

}