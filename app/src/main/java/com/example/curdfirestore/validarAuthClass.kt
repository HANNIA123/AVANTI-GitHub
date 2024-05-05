package com.example.curdfirestore

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {
    private val _isLoggedIn = mutableStateOf(false)
    val isLoggedIn: State<Boolean> = _isLoggedIn
    private val firebaseAuth = FirebaseAuth.getInstance()
    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    init {
        checkLoggedInState()
    }

    fun checkLoggedInState() {
        val currentUser = firebaseAuth.currentUser

        if (currentUser != null) {
            // El usuario está autenticado
            _isLoggedIn.value = true
        } else {
            // El usuario no está autenticado
            _isLoggedIn.value = false
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        _isLoggedIn.value = false
    }
}
