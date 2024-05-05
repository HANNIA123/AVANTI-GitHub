package com.example.curdfirestore.Viaje.Pantallas.Monitoreo

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.avanti.ParadaData
import com.example.avanti.SolicitudData
import com.example.avanti.ui.theme.Aplicacion.lineaGrisModificada
import com.example.curdfirestore.ContadorViewModel
import com.example.curdfirestore.MainActivity
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParada
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerListaParadasRT
import com.example.curdfirestore.R
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.actualizarCampoSolicitud
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.actualizarCampoSolicitudPorBusqueda
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesPorViaje
import com.example.curdfirestore.Usuario.Conductor.cabeceraConMenuCon
import com.example.curdfirestore.Usuario.Conductor.menuDesplegableCon
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeRT
import com.example.curdfirestore.Viaje.Funciones.UbicacionRealTime
import com.example.curdfirestore.Viaje.Funciones.accionesComienzoViaje
import com.example.curdfirestore.Viaje.Funciones.accionesTerminoViaje
import com.example.curdfirestore.Viaje.Funciones.convertCoordinatesToAddressRec
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.Viaje.Funciones.registrarNotificacionViaje
import com.example.curdfirestore.Viaje.Pantallas.Huella.autenticaHuella
import com.example.curdfirestore.Viaje.Pantallas.Huella.dialogoHuellaFallida
import com.example.curdfirestore.lineaCargando
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun obtenerCoordenadas(
    userId: String,
    viajeId: String,
    navController: NavController,
    viewModel: ContadorViewModel
) {
    val contador by viewModel.contador.collectAsState()
    println("Contador--------- $contador")


    val activity1 = LocalContext.current as MainActivity

    var maxh by remember {
        mutableStateOf(0.dp)
    }
    var maxw by remember {
        mutableStateOf(0.dp)
    }

    BoxWithConstraints {
        maxh = this.maxHeight
    }
    BoxWithConstraints {
        maxw = this.maxWidth
    }

    val context = LocalContext.current
    val newUserId = userId.substringBefore('@')
    var validar by remember {
        mutableStateOf(false)
    }
    var empezarUbicacion by remember {
        mutableStateOf(false)
    }
    var textoDialogo by remember {
        mutableStateOf("")
    }

    var huellaIngresada by remember {
        mutableStateOf(false)
    }


    var visibilidadInicioViaje by remember {
        mutableStateOf(true)
    }
    val referencia = Firebase.database.getReference("ubicacion").child(viajeId)


    var latLng by remember { mutableStateOf(LatLng(0.0, 0.0)) } // Coordenadas de San Francisco
    var markerOptions by remember {
        mutableStateOf(
            MarkerOptions().position(latLng).title("San Francisco")
        )
    }


    //Consulta a la BD
    var datosAnteriores: Map<String, Any>? = null
    UbicacionRealTime(context, viajeId)


    referencia.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Obtén los nuevos datos
            val nuevosDatos: Map<String, Any>? = dataSnapshot.value as? Map<String, Any>

            // Compara los nuevos datos con los datos anteriores
            if (nuevosDatos != null && nuevosDatos != datosAnteriores) {
                // Los datos han cambiado, actúa en consecuencia
                val latitud = nuevosDatos["latitud"] as Double
                val longitud = nuevosDatos["longitud"] as Double
                latLng = LatLng(latitud, longitud)

                markerOptions = MarkerOptions().position(latLng).title("San Francisco")
                // Haz lo que necesites con latitud y longitud
                validar = true

                // Actualiza los datos anteriores
                datosAnteriores = nuevosDatos

            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Manejar errores, si es necesario
            println("Error al obtener los datos: ${databaseError.message}")
        }
    })


    //Paradas en el mapa y control de ellas
    var boton by remember {
        mutableStateOf(false)
    }
    var botonNotificacionInicio by remember {
        mutableStateOf(false)
    }
    var botonNotificacionParada by remember {
        mutableStateOf(false)
    }
    var botonNotificacionFin by remember {
        mutableStateOf(false)
    }
    var botonInicioViaje by remember {
        mutableStateOf(false)
    }
    var botonParadas by remember {
        mutableStateOf(false)
    }
    var verInformacionViaje by remember {
        mutableStateOf(false)
    }
    var huellaCorrecta by remember {
        mutableStateOf(true)
    }

    var cargando by remember {
        mutableStateOf(false)
    }

    var idParadaActual by remember {
        mutableStateOf("")
    }
    var numParadaActual by remember {
        mutableStateOf(0)
    }

    val listaParadasCom = conObtenerListaParadasRT(viajeId = viajeId)
    val infViaje = conObtenerViajeRT(viajeId = viajeId)
    //conObtenerViajeId(viajeId = viajeId)
    var solicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }
    var paradasOrdenadasPasar by remember {
        mutableStateOf<List<Pair<String, ParadaData>>?>(null)
    }


    conObtenerSolicitudesPorViaje(viajeId, "Aceptada") { resultado ->
        solicitudes = resultado
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(maxh)
                .verticalScroll(rememberScrollState())

        ) {

            cabeceraConMenuCon(
                titulo = "Tu viaje",
                navController,
                userId,
                boton = { estaBoton ->
                    boton = estaBoton
                })

            infViaje?.let {
                listaParadasCom?.let {

                    val paradasOrdenadas = listaParadasCom.sortedBy { it.second.par_hora }
                    val totalParadas = paradasOrdenadas.size
                    val listParadasRecorridas =
                        paradasOrdenadas.filter { it.second.par_recorrido == "si" }

                    val viajeComenzado = paradasOrdenadas.filter {
                        it.second.para_viaje_comenzado == "si"
                    }
                    var num by remember {
                        mutableStateOf(listParadasRecorridas.size)
                    }

                    numParadaActual = listParadasRecorridas.size
                    val animationDurationMillis = 2000
                    val scope = rememberCoroutineScope()
                    var currentLatLng by remember { mutableStateOf(latLng) }

                    val animatedLatitude by animateDpAsState(
                        targetValue = currentLatLng.latitude.toDouble().dp,
                        animationSpec = tween(durationMillis = animationDurationMillis), label = ""
                    )
                    val animatedLongitude by animateDpAsState(
                        targetValue = currentLatLng.longitude.toDouble().dp,
                        animationSpec = tween(durationMillis = animationDurationMillis), label = ""
                    )

                    LaunchedEffect(Unit) {
                        while (true) {
                            val newLatitude = latLng.latitude
                            val newLongitude = latLng.longitude
                            scope.launch {
                                currentLatLng = LatLng(newLatitude, newLongitude)
                            }
                            delay(3000)
                        }
                    }


                    val lineH = 3.dp
                    // Variable para almacenar el valor máximo de width

                    val lineaW = maxw / (totalParadas + 2)

                    Column(
                        modifier = Modifier
                            .height(45.dp)
                            .background(Color.White)
                            .padding(10.dp)
                    ) {


                        Text(
                            "Progreso del viaje...", fontSize = 12.sp,
                            color = Color(165, 165, 165)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Row() {


                            for (i in 0..(totalParadas + 2)) {
                                val color: Color
                                if (viajeComenzado.isEmpty()) {
                                    println("No ha comenzado")
                                    color = Color(222, 222, 222)
                                } else {
                                    val numeros = listParadasRecorridas.size
                                    if (i <= numeros) {
                                        color = Color.Blue
                                    } else {
                                        color = Color(222, 222, 222)
                                    }
                                }
                                lineaGrisModificada(
                                    width = lineaW,
                                    height = lineH,
                                    color = color
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                            }


                        }
                    }



                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(maxh - 175.dp)
                    ) {
                        val cameraPosition = remember {
                            CameraPosition(
                                latLng,
                                16f,
                                0f,
                                0f
                            )
                        }
                        val cameraPositionState = remember { CameraPositionState(cameraPosition) }

                        val zoomLevel = rememberSaveable { mutableStateOf(cameraPosition.zoom) }

                        LaunchedEffect(Unit) {
                            while (true) {
                                if (latLng.latitude != 0.0 && latLng.longitude != 0.0) {
                                    // Solo actualiza la posición de la cámara si latLng no es (0.0, 0.0)
                                    cameraPositionState.position = CameraPosition(
                                        latLng,
                                        zoomLevel.value,
                                        cameraPosition.tilt,
                                        cameraPosition.bearing
                                    )
                                    delay(8000)
                                } else {


                                    delay(500)
                                }
                            }
                        }


                        GoogleMap(
                            modifier = Modifier
                                .fillMaxSize(),
                            cameraPositionState = cameraPositionState,
                            onMapLoaded = {
                                cargando = true
                            },
                            /*cameraPositionState = CameraPositionState(CameraPosition(LatLng(animatedLatitude.value.toDouble(),
                                        animatedLongitude.value.toDouble()
                                    ), 16f, 0f,0f))
                                  */
                        ) {
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        latLng.latitude,
                                        latLng.longitude
                                    )
                                ),
                                title = "Tu ubicación",
                                icon = BitmapDescriptorFactory.fromResource(R.drawable.autooficial),
                            )

                            paradasOrdenadas.forEach { parada ->
                                val parLatLng = convertirStringALatLng(parada.second.par_ubicacion)
                                if (parLatLng != null) {
                                    val direccionPar =
                                        convertirStringALatLng(parada.second.par_ubicacion)?.let { it1 ->
                                            convertCoordinatesToAddressRec(
                                                coordenadas = it1
                                            )
                                        }


                                    Marker(
                                        state = MarkerState(
                                            position = LatLng(
                                                parLatLng.latitude,
                                                parLatLng.longitude
                                            )
                                        ),
                                        title = "Parada ${parada.second.par_nombre}",
                                        //snippet = "Dirección: $direccionPar",
                                        icon = BitmapDescriptorFactory.fromResource(R.drawable.paradas),
                                    )
                                }


                            }

                            val origenLatLng = convertirStringALatLng(infViaje.viaje_origen)
                            val destinoLatLng = convertirStringALatLng(infViaje.viaje_destino)
                            val direccionOri = origenLatLng?.let { it1 ->
                                convertCoordinatesToAddressRec(
                                    it1
                                )
                            }
                            val direccionDes = destinoLatLng?.let { it1 ->
                                convertCoordinatesToAddressRec(
                                    it1
                                )
                            }

                            if (origenLatLng != null) {
                                Marker(
                                    state = MarkerState(
                                        position = LatLng(
                                            origenLatLng.latitude,
                                            origenLatLng.longitude
                                        )
                                    ),
                                    title = "Origen",
                                    //  snippet = "Dirección: $direccionOri",
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.origendestino),
                                )
                            }
                            if (destinoLatLng != null) {
                                Marker(
                                    state = MarkerState(
                                        position = LatLng(
                                            destinoLatLng.latitude,
                                            destinoLatLng.longitude
                                        )
                                    ),
                                    title = "Punto de llegada",
                                    //snippet = "Ubicación: $direccionDes",
                                    icon = BitmapDescriptorFactory.fromResource(R.drawable.origendestino),
                                )
                            }

                        }

                        //boton flotante
                        Box(
                            contentAlignment = Alignment.BottomCenter,

                            modifier = Modifier
                                .size(57.dp)
                                .offset(x = 15.dp, y = 12.dp),
                        ) {
                            Button(
                                onClick = {
                                    //Reportar imprevisto
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(180, 13, 13)
                                ),
                                shape = CircleShape, // Cambiado a CircleShape para hacer el botón redondo
                                contentPadding = ButtonDefaults.ContentPadding,
                                elevation = ButtonDefaults.elevation(defaultElevation = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier.size(48.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    androidx.compose.material.Icon(
                                        Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = Color.White
                                    )
                                }
                            }
                        }

                    }

                    val textoBoton = if (viajeComenzado.isEmpty()) {
                        "Comenzar viaje"
                    } else {
                        if (numParadaActual < totalParadas) {
                            "Llegué a la parada"
                        } else {
                            "Finalizar viaje"
                        }
                    }


                    //Boton de llegada
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)

                            .background(Color.White),
                        horizontalArrangement = Arrangement.SpaceBetween, // Distribuye los elementos de forma equitativa en el eje horizontal
                        verticalAlignment = Alignment.CenterVertically // Alin

                    ) {

                        if (visibilidadInicioViaje){
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        137,
                                        13,
                                        88
                                    )
                                ),
                                onClick = {

                                    empezarUbicacion = true
                                    num = listParadasRecorridas.size

                                    // numParadaActual = listParadasRecorridas.size
                                    if (viajeComenzado.isEmpty()) {


                                        autenticaHuella(
                                            activity = activity1,
                                            exitoso = {
                                                huellaCorrecta = true
                                                huellaIngresada = true
                                            },
                                            fallido = {
                                                huellaIngresada = true
                                                huellaCorrecta = false
                                            },
                                            maxIntentos = 3
                                        )


                                        botonInicioViaje = true

                                    } else {
                                        huellaIngresada = false
                                        if (numParadaActual < totalParadas) {


                                            idParadaActual = paradasOrdenadas[numParadaActual].first
                                            paradasOrdenadasPasar = paradasOrdenadas
                                            autenticaHuella(
                                                activity = activity1,
                                                exitoso = {
                                                    huellaCorrecta = true
                                                    huellaIngresada = true
                                                },
                                                fallido = {
                                                    huellaIngresada = true
                                                    huellaCorrecta = false
                                                },
                                                maxIntentos = 3
                                            )

                                            botonParadas = true


                                        } else {
                                            // Restablecer el status del viaje y las paradas
                                            botonNotificacionFin = true

                                            accionesTerminoViaje(
                                                viajeId,
                                                solicitudes,
                                                navController,
                                                userId,
                                                idParadaActual
                                            )

                                        }

                                    }

                                },
                                modifier = Modifier

                                    .height(54.dp)
                                    .padding(5.dp)
                                    .weight(0.6f) // Ocupa el 80% del ancho disponible
                            ) {
                                Text(
                                    text = textoBoton,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = Color.White
                                    )
                                )
                            }

                    }
                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = {
                                paradasOrdenadasPasar = paradasOrdenadas
                                verInformacionViaje = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(137, 13, 88)
                            ),
                            modifier = Modifier

                                .height(54.dp)
                                .padding(5.dp)


                        ) {

                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = Color.White
                            )

                        }

                        if (botonInicioViaje) {

                            if (huellaIngresada) {
                                textoDialogo =
                                    "Tu identidad no ha sido validada.No puedes comenzar el viaje en este momento. "
println("huella correcta $huellaCorrecta -----------")
                              if(huellaCorrecta){
                                  accionesComienzoViaje(
                                      viajeId = viajeId,
                                      solicitudes = solicitudes,

                                      )
                                  botonNotificacionInicio = true
                              }
                                else{
                                  viewModel.iniciarContador(1)
visibilidadInicioViaje=false



                              }

                                botonInicioViaje = false


                            }

                        }
                        if (botonParadas) {

                            if (huellaIngresada) {
                                textoDialogo =
                                    "Tu identidad no ha sido validada, el pasajero será notificado de esto. "
                                if (huellaCorrecta) {
                                    println("huella correcta y el id parada $idParadaActual")
                                    actualizarCampoSolicitudPorBusqueda(
                                        "parada_id",
                                        idParadaActual,
                                        "solicitud_validacion_conductor",
                                        "si"

                                    )


                                    //Enviar notificacion a los pasajeros

                                } else {
                                    actualizarCampoSolicitudPorBusqueda(
                                        "parada_id",
                                        idParadaActual,
                                        "solicitud_validacion_conductor",
                                        "no"

                                    )
                                }
                                actualizarCampoParada(
                                    idParadaActual,
                                    "par_recorrido",
                                    "si"
                                )


                                botonNotificacionParada = true
                                botonParadas = false
                            }

                        }

                        if (botonNotificacionInicio) {
                            if (solicitudes != null) {
                                registrarNotificacionViaje(
                                    tipoNot = "vc",
                                    solicitudes!!,
                                    userId,
                                    viajeId
                                )
                            }
                            botonNotificacionInicio = false
                        }

                        if (botonNotificacionParada) {
                            if (solicitudes != null) {
                                registrarNotificacionViaje(
                                    tipoNot = "llp",
                                    solicitudes!!,
                                    userId,
                                    viajeId
                                )
                            }
                            botonNotificacionParada = false
                        }
                        if (botonNotificacionFin) {
                            if (solicitudes != null) {
                                registrarNotificacionViaje(
                                    tipoNot = "vt",
                                    solicitudes!!,
                                    userId,
                                    viajeId
                                )
                            }
                            botonNotificacionFin = false
                        }

                    }

                }
            }
        }
    }


    if (boton) {
        menuDesplegableCon(
            onDismiss = { boton = false },
            navController,
            userID = userId
        )
    }
    if (huellaIngresada && !huellaCorrecta) {
        dialogoHuellaFallida(
            onDismiss = { huellaCorrecta = true },
            text = textoDialogo
        )
    }

    if (!cargando) {
        lineaCargando(text = "Cargando Mapa....")
    }
    if (verInformacionViaje) {
        //Pantalla para ver la lista de paradas, con sus pasajeros y si validaron o no su identidad

        paradasOrdenadasPasar?.let {
            verInformacionViajeComenzada(
                navController = navController,
                userid = userId,
                paradasOrdenadas = it,
                viajeId = viajeId
            )
        }
    }
}


/*
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MyScaffoldContentPreviewMapa() {
    val navController = rememberNavController()
    obtenerCoordenadas(userId = "hannia", viajeId = "viajeId", navController = navController)

}

*/