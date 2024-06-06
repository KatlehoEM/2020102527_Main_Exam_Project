package com.example.a2020102527_main_exam_project.ui.maps

/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.a2020102527_main_exam_project.R
import com.example.a2020102527_main_exam_project.ui.maps.utils.getCoordinatesFromPlaceName
import com.example.a2020102527_main_exam_project.ui.maps.utils.isWithinVicinity
import com.example.a2020102527_main_exam_project.ui.theme.Color3
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Locale

/*  Purpose of code:
    This composable provides a customizable Google Map view with
    interactive elements such as map type selection, location search,
    and camera position reset.
*/

@Composable
fun MyMap(
    context: Context,
    latLng: LatLng,
    mapProperties: MapProperties = MapProperties(),
    onChangeMapType: (mapType: MapType) -> Unit
){
    var mapTypeMenuExpanded by remember { mutableStateOf(false)}
    var mapTypeMenuSelectedText by remember {
        mutableStateOf(
            MapType.NORMAL.name
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }

    // Sample store list
    val stores = remember { mutableStateListOf(
        Store("Store 0", LatLng(37.7749, -122.4194)),
    ) }

    val nearbyStores by remember(latLng) {
        derivedStateOf {
            stores.filter { store ->
                isWithinVicinity(latLng, store.location)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng,15f)
            },
                shape = CircleShape,
                containerColor = Color.White,
                contentColor = Color3,
                ) {
                Icon(modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.locator), contentDescription = "locator image", tint = Color3)

            }
        }
    ) {paddingValues ->

        val mapUiSettings = MapUiSettings(zoomControlsEnabled = false)
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues), ){
            GoogleMap(
                uiSettings = mapUiSettings,
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                onMapLongClick = { latLng ->
                    val existingStore = stores.find { isWithinVicinity(latLng, it.location, 50.0) }
                    if (existingStore != null) {
                        stores.remove(existingStore)
                        Toast.makeText(context, "Removed ${existingStore.name}", Toast.LENGTH_SHORT).show()
                    } else {
                        val storeName = "Store ${stores.size + 1}"
                        val newStore = Store(storeName, latLng)
                        stores.add(newStore)
                        Toast.makeText(context, "Added $storeName at (${latLng.latitude}, ${latLng.longitude})", Toast.LENGTH_SHORT).show()
                    }
                },
                onMapClick = {
                    Toast.makeText(context,
                        "Lat: ${it.latitude}, Long: ${it.longitude}",
                        Toast.LENGTH_SHORT)
                        .show()
                }
            ){
                Marker(
                    state = MarkerState(position = latLng),
                    title = "Location",
                    snippet = "Lat: ${latLng.latitude}, Long: ${latLng.longitude}",
                )

                // Add markers for nearby stores
                nearbyStores.forEach { store ->
                    Marker(
                        state = MarkerState(position = store.location),
                        title = store.name,
                        snippet = "Lat: ${store.location.latitude}, Long: ${store.location.longitude}",
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp)
        ){
            Row{
                Button(onClick = { mapTypeMenuExpanded = true}) {
                    Text(text = mapTypeMenuSelectedText)
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown arrow",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
                DropdownMenu(
                    expanded = mapTypeMenuExpanded,
                    onDismissRequest = { mapTypeMenuExpanded = false }) {
                    MapType.values().forEach {
                        val mapType = it.name.lowercase(Locale.getDefault())
                        DropdownMenuItem(text = {
                            Text(text = mapType)
                        }, onClick = {
                            onChangeMapType(it)
                            mapTypeMenuSelectedText = mapType
                            mapTypeMenuExpanded = false
                        })
                    }
                }
            }

            var currentValue by remember {
                mutableStateOf("")
            }

            SearchTextField(
                onTextChanged = {
                            currentValue = it
                },
                placeholder = "Search here",
                modifier = Modifier.fillMaxWidth(),
                onSearchClicked = {
                    val location = getCoordinatesFromPlaceName(context,currentValue)
                    if(location.latitude == 0.0){
                        Toast.makeText(context,
                            "Sorry, location not found",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                    else{
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(location,15f)
                    }

                }
            )
        }
    }
}