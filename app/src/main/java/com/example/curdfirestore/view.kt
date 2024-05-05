package com.example.curdfirestore

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ContadorViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {    private val CONTADOR_KEY = "contador_key"

    private val _contador = MutableStateFlow(0)
    val contador: StateFlow<Int> = _contador

    init {
        if (savedStateHandle.contains(CONTADOR_KEY)) {
            _contador.value = savedStateHandle[CONTADOR_KEY] ?: 0
        }
    }
    constructor() : this(SavedStateHandle())
    fun iniciarContador(minutos: Long) {
        val duracionMillis = TimeUnit.MINUTES.toMillis(minutos)
        viewModelScope.launch {
            var tiempoTranscurrido = 0L
            while (tiempoTranscurrido < duracionMillis) {
                delay(1000) // Esperar 1 segundo
                tiempoTranscurrido += 1000
                val segundos = TimeUnit.MILLISECONDS.toSeconds(tiempoTranscurrido)
                _contador.value = segundos.toInt()
            }
        }
    }
    fun actualizarContador(nuevoValor: Int) {
        _contador.value = nuevoValor
        savedStateHandle.set(CONTADOR_KEY, nuevoValor)
    }
}

class ContadorViewModelFactory(private val savedStateHandle: SavedStateHandle) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContadorViewModel::class.java)) {
            return ContadorViewModel(savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

