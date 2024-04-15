package com.example.curdfirestore.Horario.ConsultasHorario

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.avanti.Usuario.BASE_URL
import com.example.avanti.ViajeData
import com.example.avanti.ViajeDataReturn
import com.example.curdfirestore.Horario.ApiServiceHorario
import com.example.curdfirestore.Horario.RetrofitClientHorario
import com.example.curdfirestore.Parada.ConsultasParada.conBuscarParadasPas
import com.example.curdfirestore.Parada.Pantallas.ventanaNoEncontrado
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*Primero busca un viaje que coincida con los datos que porporciono el pasajero,
de acuerdo al día y tipo de trayecto (no se considera el horario para una mayor probabilidad de
encontrar paradas. Esta busqueda se hace en el servidor.
* */

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@Composable
fun conBuscarViajePas(
    navController: NavController,
    correo: String,
    horarioId: String,

    ) {
    //Obtener lista de viajes (Itinerario)
    var viajes by remember { mutableStateOf<List<ViajeData>?>(null) }
    var show by rememberSaveable { mutableStateOf(false) }

    //var parada by remember { mutableStateOf<ParadaData?>(null) }
    var text by remember { mutableStateOf("") }
    var busqueda by remember { mutableStateOf(false) }
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiServiceHorario::class.java)

    LaunchedEffect(key1 = true) {
        try {
            //val  resultadoViaje = RetrofitClient.apiService.busquedaViajesPas(horarioId)
            val response = RetrofitClientHorario.apiService.busquedaViajesPas(horarioId)
            if (response.isSuccessful) {
                viajes=response.body()
            } else {
                text="No se encontró ningún viaje que coincida con tu búsqueda"
                busqueda=true
            }
            // Haz algo con el objeto Usuario

        } catch (e: Exception) {
            text="Error al obtener viaje: $e"
            println("Error al obtener viaje: $e")
        }
    }



    if (viajes != null  && busqueda==false) {
     conBuscarParadasPas(navController = navController, correo = correo, horarioId = horarioId, viajes = viajes!!)
    }
    if (busqueda==true){
        show=true
        ventanaNoEncontrado(
                show = show,
        onDismiss = {show=false },
        onConfirm = { /*TODO*/ },
        userId = correo,
        navController = navController
        )

    }

}
