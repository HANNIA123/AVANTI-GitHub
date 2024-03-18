package com.example.curdfirestore.NivelAplicacion


import android.content.Context
import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.model.Place

import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

//barra de busqueda 27/12/2023
suspend fun searchPlaces(query: String, context: Context): List<Place> {
    // Inicializar el cliente de Places API
    Places.initialize(context, "AIzaSyAZmpsa99qsen70ktUWCSDbmEChisRMdlQ")
    val placesClient = Places.createClient(context)
    // Crear un token de sesión con información de idioma
    val sessionToken = AutocompleteSessionToken.newInstance()

    // Realizar una solicitud para obtener predicciones de autocompletado
    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery("CDMX State of Mexico $query") // Agregar prefijos de las ciudades

        .build()


    val response = withContext(Dispatchers.IO) {
        placesClient.findAutocompletePredictions(request).await()
    }
    // Mapear las predicciones a objetos Place
    return response.autocompletePredictions.map { prediction ->
        val placeId = prediction.placeId
        // Realizar una solicitud para obtener más detalles del lugar
        val placeRequest = FetchPlaceRequest.builder(placeId, listOf(Place.Field.ADDRESS, Place.Field.LAT_LNG)).build()
        val placeResponse = withContext(Dispatchers.IO) {
            placesClient.fetchPlace(placeRequest).await()
        }
        // Extraer la información del lugar
        val place = placeResponse.place
        val address = place.address
        val latLng = place.latLng
        // Crear una instancia de Place
        Place.builder()
            .setName(prediction.getFullText(null).toString())
            .setAddress(address)
            .setLatLng(LatLng(latLng?.latitude ?: 0.0, latLng?.longitude ?: 0.0))
            .build()
    }
}



@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: MutableState<String>,
    searchResults: List<Place>,
    onSearch: (String) -> Unit,
    onPlaceSelected: (Place) -> Unit,
    onMapButtonClick: (String) -> Unit,
    TipoBusqueda:String,
    TextoBarra:String
) {

    var selectedPlace by remember { mutableStateOf<Place?>(null) }
    var TextoOpcion by remember { mutableStateOf<String?>("") }
    var TipoBot by remember { mutableStateOf<String?>("") }
    var isTextFieldSelected by remember { mutableStateOf(false) }

    Column(
        modifier=Modifier.fillMaxWidth()
    ){


        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){

                Column(
                    modifier=Modifier .border(
                            1.dp,
                    Color.LightGray
                )
                        .background(Color.White)
                        .size(60.dp)
                        .clickable {
                            if (TipoBusqueda == "barra") {
                                onMapButtonClick("marker")
                            }
                            if (TipoBusqueda == "marker") {
                                onMapButtonClick("barra")
                            }
                        }
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally
                    , verticalArrangement = Arrangement.Center
                ){
                    Icon(
                        imageVector = Icons.Filled.Place,
                        contentDescription = null,
                        tint = Color.Black
                    )

                    Text(
                        text = "Mover",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 12.sp),
                        modifier = Modifier

                    )

                }



            Column(
                modifier=Modifier.background(Color.White)
            ){
                TextField(
                    value = searchQuery.value,

                    modifier = Modifier
                        .fillMaxWidth()

                        .border(1.dp, Color.LightGray)
                        .onFocusChanged { isTextFieldSelected = it.isFocused
                        }
                    ,
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
                    ,
                    onValueChange = { newValue ->
                        if (!newValue.contains("\n")) {
                            searchQuery.value = newValue
                            onSearch(newValue)
                        }

                    },
                    label = { Text(TextoBarra,
                        style = TextStyle(
                            color = Color(104, 104, 104),
                            fontSize = 14.sp
                        )
                    )
                    },

                    )
                var isPlaceNameEmpty by remember { mutableStateOf(true) }
                var showResults by remember { mutableStateOf(true) }
                var boton by remember { mutableStateOf(false) }
                if (showResults) {
                    LazyColumn {
                        items(searchResults) { place ->
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        searchQuery.value = place.name
                                        onPlaceSelected(place)
                                        selectedPlace = place
                                        boton = true
                                        showResults = false
                                        //validar = true
                                    }
                                    .border(
                                        1.dp,
                                        Color.LightGray
                                    )
                                    .padding(8.dp)
                                    .fillMaxWidth()
                            ) {
                                LaunchedEffect(place.name) {
                                    isPlaceNameEmpty = place.name.isEmpty()
                                }

                                Text(
                                    text = place.name,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold // Nombre en negrita
                                    ),
                                    modifier = Modifier
                                        .padding(bottom = 4.dp)
                                )
                                Text(
                                    text = place.address ?: "",
                                    style = TextStyle(
                                        color = Color(50, 50, 50), // Color diferente para la dirección
                                        fontSize = 12.sp
                                    )
                                )
                            }
                        }
                    }
                }

                LaunchedEffect(key1 =  searchQuery.value){
                    boton=false
                    showResults=true

                }
                LaunchedEffect(key1 = selectedPlace){
                    showResults=false
                }

                if (boton==true){
                    LocalSoftwareKeyboardController.current?.hide() //oculta el teclado{
                }
            }


        }


    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Buscar(){
    LocalSoftwareKeyboardController.current?.hide() //oculta el teclado
}
/*
@Composable
fun MapScreen() {
    val searchResults = remember { mutableStateOf(emptyList<Place>()) }
    val context = LocalContext.current
    val searchQuery = remember { mutableStateOf("") }
    var selectedPlace by remember { mutableStateOf<Place?>(null) }


    LaunchedEffect(searchQuery.value) {
        // Lanzar un bloque coroutine para ejecutar la búsqueda de lugares
        try {
            val results = withContext(Dispatchers.IO) {
                searchPlaces(searchQuery.value, context)
            }

            searchResults.value = results
            println("Bloqe $results")
        } catch (e: Exception) {
            // Manejar cualquier error que pueda ocurrir durante la búsqueda
            e.printStackTrace()
        }
    }
Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),

) {

    SearchBar(searchQuery, searchResults.value, { newQuery ->
        searchQuery.value = newQuery
    }) { place ->
        selectedPlace = place
    }

    val miUbic = LatLng(19.389816, -99.110234)
    var markerState = rememberMarkerState(position = miUbic)
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(markerState.position, 17f)
    }


    selectedPlace?.let { place ->
markerState=MarkerState(LatLng(
    place.latLng.latitude,
    place.latLng.longitude
))
        cameraPositionState.position = CameraPosition.fromLatLngZoom(LatLng(place.latLng.latitude, place.latLng.longitude), 17f)

    }

        GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(450.dp),
            cameraPositionState = cameraPositionState
        ) {
            selectedPlace?.let { place ->
                Marker(
                    state =markerState,
                    title = place.name,
                    snippet = place.address

                )
            }
        }





}}


*/











/*
@Composable
fun MyApp() {
    var searchState by remember { mutableStateOf("") }
    var places by remember { mutableStateOf<List<Place>>(emptyList()) }

    // Obtener un ámbito de corrutina con rememberCoroutineScope
    val coroutineScope = rememberCoroutineScope()
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.18:3000") // Reemplaza con la dirección de tu servidor Node.js
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val placesApiService = retrofit.create(PlacesApiService::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = searchState,
            onValueChange = { newQuery ->
                searchState = newQuery
            },
            textStyle = TextStyle.Default.copy(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color.Gray)
        )

        Button(onClick = {
            // Llamamos a la función que realiza la búsqueda de lugares
            coroutineScope.launch {
                try {

                   // val  resultadoPlace = placesApiService.buscarLugares(searchState)

                    val response = placesApiService.buscarLugares(searchState)
                    println("Estosss $response")
                    /*
                    places = response.lugares.map { googlePlace ->
                        com.example.curdfirestore.util.Place(
                            nombre = googlePlace.name ?: "",
                            direccion = googlePlace.address ?: "",
                            coordenadas = Coordenadas(
                                latitud = googlePlace.latLng?.latitude ?: 0.0,
                                longitud = googlePlace.latLng?.longitude ?: 0.0
                            )
                        )


                    }
                    */

                } catch (e: Exception) {
                    // Manejar el error, por ejemplo, mostrar un mensaje de error en la UI
                    e.printStackTrace()
                }
            }
        }) {
            Text("Buscar lugares")
        }

        // Mostrar información sobre los lugares encontrados
        places.forEach { place ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Magenta)
            ) {
                Text("Nombre: ${place.nombre}")
                Text("Dirección: ${place.direccion}")
                Text("Coordenadas: ${place.coordenadas.latitud}, ${place.coordenadas.longitud}")
            }
        }
    }
}
*/

/*
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MyApp() {
    var searchState by remember { mutableStateOf("") }
    var places by remember { mutableStateOf<List<Place>>(emptyList()) }
// Obtener un ámbito de corrutina con rememberCoroutineScope
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Cyan),
        horizontalAlignment =Alignment.CenterHorizontally

    ) {
        BasicTextField(
            value = searchState,

            onValueChange = { newQuery ->
                searchState = newQuery
            },
            textStyle = TextStyle.Default.copy(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(Color.Gray)
        )


        Button(onClick = {
            // Llamamos a la función que realiza la búsqueda de lugares
            coroutineScope.launch {
                println("clic en boton")
                try {
                    val response = performSearch(searchState)
                    places = response
                    println("Lugareees $places")
                } catch (e: Exception) {
                    // Manejar el error, por ejemplo, mostrar un mensaje de error en la UI
                    e.printStackTrace()
                }
            }
        }) {
            Text("Buscar lugares")
        }


        // Mostrar información sobre los lugares encontrados
        places.forEach { place ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Magenta)
            ) {
                Text("Nombre: ${place.nombre}")
                Text("Dirección: ${place.direccion}")
                Text("Coordenadas: ${place.coordenadas.latitud}, ${place.coordenadas.longitud}")
            }
        }
    }
}




suspend fun performSearch(query: String): List<Place> {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.18:3000") // Reemplaza con la URL de tu servidor
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val placesApiService = retrofit.create(PlacesApiService::class.java)

    return withContext(Dispatchers.IO) {
        val googlePlaces = placesApiService.buscarLugares(query)

        // Convierte los lugares de Google a tu modelo de datos personalizado
        googlePlaces.map { googlePlace ->
            com.example.curdfirestore.util.Place(
                nombre = googlePlace.name ?: "",
                direccion = googlePlace.address ?: "",
                coordenadas = Coordenadas(
                    latitud = googlePlace.latLng?.latitude ?: 0.0,
                    longitud = googlePlace.latLng?.longitude ?: 0.0
                )
            )
        }
    }
}
*/