package com.example.curdfirestore.Horario.ConsultasHorario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.curdfirestore.Horario.RespuestaApiHorario
import com.example.curdfirestore.Horario.RetrofitClientHorario.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//Modificar que un horario tiene una solicitud
@Composable
fun conActualizarSolicitudHorario(
    horarioId: String,
    status:String
) {
 var resp by remember {
     mutableStateOf("")
 }

    val call = apiService.modificarSolicitudHorario(horarioId, status)
    call.enqueue(object : Callback<RespuestaApiHorario> {
        override fun onResponse(call: Call<RespuestaApiHorario>, response: Response<RespuestaApiHorario>) {
            if (response.isSuccessful) {
                // La modificación fue exitosa
                val respuestaApi = response.body()

                    resp= respuestaApi?.message.toString()



            } else {
                resp="Ocurrio un error"
                // Ocurrió un error, manejar según sea necesario
                // Puedes obtener más información del error desde response.errorBody()
            }
        }

        override fun onFailure(call: Call<RespuestaApiHorario>, t: Throwable) {
            // Manejar errores de red o excepciones
            t.printStackTrace()
        }
    })



}
