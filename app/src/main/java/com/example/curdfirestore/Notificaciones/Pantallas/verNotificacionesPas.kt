package com.example.curdfirestore.Notificaciones.Pantallas

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.NoticacionData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.CoilImage
import com.example.avanti.ui.theme.Aplicacion.cabecera
import com.example.curdfirestore.Notificaciones.Consultas.ObtenerNavGraphPas
import com.example.curdfirestore.Notificaciones.Consultas.TextoNotificacionVer
import com.example.curdfirestore.R
import com.example.curdfirestore.Usuario.Pasajero.menuPas
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun verNotificacionesPas(
    navController: NavController,
    userID: String
) {
    var isLoading by remember {
        mutableStateOf(true)
    }
    var final by remember {
        mutableStateOf(false)
    }
    BoxWithConstraints {
        maxh = this.maxHeight - 50.dp
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(45.dp)) {

                menuPas(navController = navController, userID = userID)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(maxh)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            cabecera(titulo = "Notificaciones")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {


                if (isLoading) {
                    println("Caragandooo----------------------------------")

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .background(Color.White)
                                .padding(16.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            LinearProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                            )
                            Text(
                                text = "Cargando Notificaciones",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(4.dp),
                                color = Color.Gray
                            )
                        }
                    }
                }

                var notificaciones by remember { mutableStateOf<List<NoticacionData>?>(null) }

                // Realizar la consulta y actualizar el estado de notificaciones
                LaunchedEffect(true) {
                    val db = FirebaseFirestore.getInstance()
                    val collection = db.collection("notificacion")

                    val fieldName = "notificacion_usu_destino"
                    val fieldValue = userID

                    val result = mutableListOf<NoticacionData>()

                    try {
                        val querySnapshot =
                            collection.whereEqualTo(fieldName, fieldValue).get().await()
                        for (document in querySnapshot.documents) {
                            val notificacion = document.toObject(NoticacionData::class.java)
                            notificacion?.let { result.add(it) }
                        }
                        notificaciones = result
                    } catch (e: Exception) {
                        println("este es catch")
                        // Manejar errores
                    } finally {
                        final = true
                    }
                }
                println("finaaal $final")

                if (final) {
                    println("noti $notificaciones")
                    if (notificaciones?.isEmpty() == false) {
                        println("No nulo")
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                        // Ordenar la lista de notificaciones por fecha
                        val notificacionOrdenada =
                            notificaciones!!.sortedByDescending { dateFormat.parse(it.notificacion_fecha) }


                        for (notificacion in notificacionOrdenada!!) {

                            val usuario= conObtenerUsuarioId(correo = notificacion.notificacion_usu_origen)

                            if (usuario != null) {
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            var ruta =
                                                ObtenerNavGraphPas(notificacion.notificacion_tipo)
                                            navController.navigate("$ruta/$userID")
                                        }
                                ) {
                                    CoilImage(
                                        url = usuario!!.usu_foto, modifier = Modifier
                                            .size(85.dp)
                                            .clip(CircleShape)
                                    )
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .padding(10.dp)

                                    ) {


                                        Text(
                                            buildAnnotatedString {
                                                withStyle(
                                                    style = SpanStyle(
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 15.sp
                                                    )
                                                ) {
                                                    append("${usuario!!.usu_nombre} ${usuario!!.usu_primer_apellido} ")
                                                }

                                                withStyle(
                                                    style = SpanStyle(
                                                        fontSize = 15.sp
                                                    )
                                                ) {
                                                    append(TextoNotificacionVer(tipoNot = notificacion.notificacion_tipo))
                                                }

                                                appendLine()

                                                withStyle(
                                                    style = SpanStyle(
                                                        fontSize = 13.sp,
                                                        color = Color.Gray
                                                    )
                                                ) {
                                                    append("${notificacion.notificacion_fecha}")
                                                }


                                            }
                                        )


                                    }

                                }
                                //LineaGris()
                            }

                        }
                        isLoading = false

                    } else {

                        Text(
                            text = " No hay notificaciones para mostrar ",
                            modifier = Modifier
                                .padding(2.dp),
                            style = TextStyle(
                                color = Color(104, 104, 104),
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center,
                            )
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                modifier = Modifier
                                    .size(70.dp)
                                    .padding(5.dp),
                                painter = painterResource(id = R.drawable.notificacion),
                                contentDescription = "Icono Viajes",
                                tint = Color(137, 13, 88)
                            )
                        }


                        isLoading = false
                    }
                }

            }

        }
    }

}
