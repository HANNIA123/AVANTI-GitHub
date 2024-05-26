package com.example.curdfirestore.Notificaciones.Pantallas

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
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

import com.example.avanti.ui.theme.Aplicacion.cabeceraSin
import com.example.curdfirestore.Notificaciones.Consultas.TextoNotificacionVer
import com.example.curdfirestore.R
import com.example.curdfirestore.Usuario.Pasajero.menuPas
import com.example.curdfirestore.lineaCargando
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

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
        maxh = this.maxHeight - 55.dp
    }
    Scaffold(
        bottomBar = {
            BottomAppBar(modifier = Modifier.height(50.dp)) {

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
            cabeceraSin(titulo = "Notificaciones")
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                if (isLoading) {
                    lineaCargando(text = "Cargando notificaciones...")
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

                    if (notificaciones?.isEmpty() == false) {

                        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

                        // Ordenar la lista de notificaciones por fecha y por hora
                        val notificacionOrdenada = notificaciones!!.sortedWith(compareByDescending<NoticacionData> { dateFormat.parse(it.notificacion_fecha) }.thenByDescending {
                            val (hora, minuto) = it.notificacion_hora.split(":").map { it.toInt() }
                            hora * 60 + minuto
                        })


                        for (notificacion in notificacionOrdenada) {

                            val usuario =
                                conObtenerUsuarioId(correo = notificacion.notificacion_usu_origen)

                            if (usuario != null) {
                                Row(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()

                                ) {
                                    CoilImage(
                                        url = usuario.usu_foto, modifier = Modifier
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
                                                    append("${usuario.usu_nombre} ${usuario!!.usu_primer_apellido} ")
                                                }

                                                withStyle(
                                                    style = SpanStyle(
                                                        fontSize = 15.sp
                                                    )
                                                ) {
                                                    append(TextoNotificacionVer(tipoNot = notificacion.notificacion_tipo))
                                                }
                                            }
                                        )
                                        Row(
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Text(
                                                text = "${notificacion.notificacion_hora} hrs",
                                                style = TextStyle(
                                                    fontSize = 13.sp,
                                                    color = Color.Gray
                                                ),
                                                modifier = Modifier.weight(1f)
                                            )
                                            Text(
                                                text = notificacion.notificacion_fecha,
                                                style = TextStyle(
                                                    fontSize = 13.sp,
                                                    color = Color.Gray
                                                ),
                                                textAlign = TextAlign.End
                                            )
                                        }

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

