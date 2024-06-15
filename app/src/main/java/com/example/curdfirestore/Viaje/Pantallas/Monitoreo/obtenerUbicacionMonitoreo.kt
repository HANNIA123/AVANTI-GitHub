package com.example.curdfirestore.Viaje.Pantallas.Monitoreo

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.avanti.HistorialViajesData
import com.example.avanti.ParadaData
import com.example.avanti.SolicitudData
import com.example.avanti.Usuario.ConsultasUsuario.conObtenerUsuarioId
import com.example.avanti.ui.theme.Aplicacion.obtenerFechaFormatoddmmyyyy
import com.example.avanti.ui.theme.Aplicacion.obtenerHoraActual
import com.example.curdfirestore.Horario.Pantallas.Monitoreo.barraProgresoViaje
import com.example.curdfirestore.Horario.Pantallas.Monitoreo.dialogoViajeFinalizo
import com.example.curdfirestore.Imprevistos.Pantallas.dialogoImprevisto
import com.example.curdfirestore.MainActivity
import com.example.curdfirestore.Parada.ConsultasParada.actualizarCampoParada
import com.example.curdfirestore.Parada.ConsultasParada.conObtenerListaParadasRT
import com.example.curdfirestore.R
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.actualizarCampoSolicitudPorBusqueda
import com.example.curdfirestore.Solicitud.ConsultasSolicitud.conObtenerSolicitudesPorViaje
import com.example.curdfirestore.Usuario.Conductor.cabeceraConMenuConNot
import com.example.curdfirestore.Usuario.Conductor.menuDesplegableCon
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerHistorialViajeRT
import com.example.curdfirestore.Viaje.ConsultasViaje.conObtenerViajeRT
import com.example.curdfirestore.Viaje.ConsultasViaje.editarDocumentoHistorial
import com.example.curdfirestore.Viaje.ConsultasViaje.registrarHistorialViaje
import com.example.curdfirestore.Viaje.Funciones.UbicacionRealTime
import com.example.curdfirestore.Viaje.Funciones.accionesComienzoViaje
import com.example.curdfirestore.Viaje.Funciones.accionesTerminoViaje
import com.example.curdfirestore.Viaje.Funciones.convertirStringALatLng
import com.example.curdfirestore.Viaje.Funciones.obtenerHoraActualSec
import com.example.curdfirestore.Viaje.Funciones.registrarHistorialBloqueo
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


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun obtenerCoordenadas(
    userId: String,
    viajeId: String,
    navController: NavController,
) {

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

    var validar by remember {
        mutableStateOf(false)
    }

    var textoDialogo by remember {
        mutableStateOf("")
    }

    var huellaIngresada by remember {
        mutableStateOf(false)
    }

    var viajeFinalizado by remember {
        mutableStateOf(false)
    }
    var dialogoAutFall by remember {
        mutableStateOf(false)
    }

    var dialogoImprevisto by remember { mutableStateOf(false) }
    val ruta = "empezar_viaje/$userId/${viajeId}"

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
            val nuevosDatos: Map<String, Any>? = dataSnapshot.value as? Map<String, Any>
            // Compara los nuevos datos con los datos anteriores
            if (nuevosDatos != null && nuevosDatos != datosAnteriores) {
                val latitud = nuevosDatos["latitud"] as Double
                val longitud = nuevosDatos["longitud"] as Double
                latLng = LatLng(latitud, longitud)

                markerOptions = MarkerOptions().position(latLng).title("San Francisco")
                validar = true

                datosAnteriores = nuevosDatos

            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
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
    var botonNotificacionValida by remember {
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
    val conductor = conObtenerUsuarioId(correo = userId)

    val listaParadasCom = conObtenerListaParadasRT(viajeId = viajeId)
    val infViaje = conObtenerViajeRT(viajeId = viajeId)
    //conObtenerViajeId(viajeId = viajeId)
    var solicitudes by remember { mutableStateOf<List<SolicitudData>?>(null) }
    var paradasOrdenadasPasar by remember {
        mutableStateOf<List<Pair<String, ParadaData>>?>(null)
    }

    var idInicioViaje by remember {
        mutableStateOf("")
    }
    var historial by remember {
        mutableStateOf<HistorialViajesData?>(null)
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

            cabeceraConMenuConNot(
                titulo = "Tu viaje",
                navController,
                boton = { estaBoton ->
                    boton = estaBoton
                },
                ruta = "ver_notificaciones_conductor/$userId"
            )

            infViaje?.let {
                listaParadasCom?.let {
                    idInicioViaje = infViaje.viaje_id_iniciado
                    var botonActivo by remember {
                        mutableStateOf(true)
                    }
                    var hisCreado by remember {
                        mutableStateOf(false)
                    }

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

                    println("Id historial $idInicioViaje")



                    if (idInicioViaje != "") { // existe
                        println("este")
                        historial = conObtenerHistorialViajeRT(viajeId = idInicioViaje)
                        hisCreado = true

                        registrarHistorialBloqueo(
                            idInicioViaje = idInicioViaje,
                            botonActivo = {
                                botonActivo = true

                            },
                            botonNoActivo = {
                                botonActivo = false

                            },

                            )

                    }



                    barraProgresoViaje(
                        totalParadas = totalParadas,
                        viajeComenzado = viajeComenzado,
                        maxw = maxw,
                        texto = "Progreso del viaje...",
                        listParadasRecorridas = listParadasRecorridas
                    )



                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(maxh - 181.dp)
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
                                    delay(9000)
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
                            }
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

                            val origenLatLng =
                                infViaje.let { convertirStringALatLng(it.viaje_origen) }
                            val destinoLatLng =
                                infViaje.let { convertirStringALatLng(it.viaje_destino) }

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
                                    dialogoImprevisto = true
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
                Icon(
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



                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)

                            .background(Color.White),
                        horizontalArrangement = Arrangement.SpaceBetween, // Distribuye los elementos de forma equitativa en el eje horizontal
                        verticalAlignment = Alignment.CenterVertically // Alin

                    ) {

                        if (botonActivo) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(
                                        137,
                                        13,
                                        88
                                    )
                                ),
                                onClick = {
                                    num = listParadasRecorridas.size
                                    // numParadaActual = listParadasRecorridas.size
                                    if (viajeComenzado.isEmpty()) {
                                        autenticaHuella(
                                            activity = activity1,
                                            exitoso = {
                                                huellaCorrecta = true
                                                huellaIngresada = true
                                                botonInicioViaje = true
                                            },
                                            fallido = {
                                                huellaIngresada = true
                                                huellaCorrecta = false
                                                botonInicioViaje = true
                                            },
                                            maxIntentos = 3
                                        )


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
                                                idParadaActual,
                                                paradasOrdenadasPasar
                                            )

                                            val nuevosValores = mapOf(
                                                "hora_fin_viaje" to obtenerHoraActual(),
                                            )
                                            editarDocumentoHistorial(idInicioViaje, nuevosValores)



                                            viajeFinalizado = true
                                        }

                                    }

                                },
                                modifier = Modifier
                                    .height(60.dp)
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

                        } else {

                            Text(
                                text = "Viaje bloqueado, espera 1 min",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color(
                                        137,
                                        13,
                                        88
                                    )
                                ),
                                modifier = Modifier
                                    .padding(5.dp)
                            )


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

                                .height(60.dp)
                                .padding(5.dp)


                        ) {

                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = Color.White
                            )

                        }

                        //--------Acciones al iniciar el viaje---------
                        if (botonInicioViaje) {

                            if (huellaIngresada) {
                                println("huella ingresada inicio huelaa $huellaCorrecta")
                                textoDialogo =
                                    "Tu identidad no ha sido validada. No puedes comenzar el viaje en este momento. "

                                if (huellaCorrecta) {

                                    accionesComienzoViaje(
                                        viajeId = viajeId,
                                        solicitudes = solicitudes,
                                        conductorId = userId,
                                        hisCreado = hisCreado,
                                        idInicioViaje = idInicioViaje,


                                        )
                                    botonNotificacionInicio = true
                                } else {
                                    dialogoAutFall = true
                                    if (!hisCreado) {
                                        val viajeIniciado = HistorialViajesData(
                                            conductor_id = userId,
                                            validacion_conductor_inicio = false,
                                            bloqueo_inicio_viaje = true,
                                            viaje_id = viajeId,
                                            fecha_bloqueo_viaje = obtenerFechaFormatoddmmyyyy(),
                                            hora_bloqueo_viaje = obtenerHoraActualSec()
                                        )
                                        registrarHistorialViaje(viajeIniciado)
                                    } else {
                                        val nuevosValores = mapOf(
                                            "bloqueo_inicio_viaje" to true,
                                            "fecha_bloqueo_viaje" to obtenerFechaFormatoddmmyyyy(),
                                            "hora_bloqueo_viaje" to obtenerHoraActualSec()
                                        )
                                        editarDocumentoHistorial(idInicioViaje, nuevosValores)
                                    }
                                }

                                botonInicioViaje = false
                            }

                        }


                        //--------Acciones en las paradas---------
                        if (botonParadas) {
                            if (huellaIngresada) {
                                var validacionBoolean = false

                                textoDialogo =
                                    "Tu identidad no ha sido validada, el pasajero será notificado de esto. "
                                if (huellaCorrecta) {
                                    validacionBoolean = true
                                    println("huella correcta y el id parada $idParadaActual")
                                    actualizarCampoSolicitudPorBusqueda(
                                        "parada_id",
                                        idParadaActual,
                                        "solicitud_validacion_conductor",
                                        "si"
                                    )
                                    botonNotificacionValida = true

                                } else {
                                    dialogoAutFall = true
                                    actualizarCampoSolicitudPorBusqueda(
                                        "parada_id",
                                        idParadaActual,
                                        "solicitud_validacion_conductor",
                                        "no"

                                    )
                                }

                                historial?.let {

                                    val listaParadasVal = historial!!.validacion_conductor_paradas
                                    val newLista = listaParadasVal.plus(validacionBoolean)
                                    val nuevosValores = mapOf(
                                        "validacion_conductor_paradas" to newLista,
                                    )
                                    editarDocumentoHistorial(idInicioViaje, nuevosValores)
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
                                conductor?.let {
                                    registrarNotificacionViaje(
                                        tipoNot = "vc",
                                        solicitudes!!,
                                        userId,
                                        viajeId,
                                        conductor
                                    )
                                }
                            }

                            botonNotificacionInicio = false
                        }

                        if (botonNotificacionParada) {
                            if (solicitudes != null) {
                                conductor?.let {
                                    registrarNotificacionViaje(
                                        tipoNot = "llp",
                                        solicitudes!!,
                                        userId,
                                        viajeId,
                                        conductor
                                    )

                                }

                            }
                            botonNotificacionParada = false
                        }
                        if (botonNotificacionValida) {
                            if (solicitudes != null) {
                                conductor?.let {
                                    registrarNotificacionViaje(
                                        tipoNot = "vi",
                                        solicitudes!!,
                                        userId,
                                        viajeId,
                                        conductor
                                    )
                                }
                            }
                            botonNotificacionValida = false
                        }
                        if (botonNotificacionFin) {
                            if (solicitudes != null) {
                                conductor?.let {
                                    registrarNotificacionViaje(
                                        tipoNot = "vt",
                                        solicitudes!!,
                                        userId,
                                        viajeId,
                                        conductor
                                    )
                                }
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
    if (dialogoAutFall) {
        dialogoHuellaFallida(
            onDismiss = {
                dialogoAutFall = false
            },
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
    if (viajeFinalizado) {
        dialogoViajeFinalizo(
            "El viaje ha finalizado",
            navController,
            ruta = "homeconductor/$userId"
        )
    }

    if (dialogoImprevisto) {
        dialogoImprevisto(
            onDismiss = { dialogoImprevisto = false },
            userId,
            viajeId,
            navController,
            ruta
        )
    }
}


