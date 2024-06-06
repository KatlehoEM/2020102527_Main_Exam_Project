package com.example.a2020102527_main_exam_project.ui.maps

/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 02 June 2024
    Module Code: CSIP6853
*/

import android.content.Context
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.a2020102527_main_exam_project.ui.maps.utils.getCurrentLocation
import com.example.a2020102527_main_exam_project.ui.theme.Color3
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties

/* Purpose of code:
    This composable provides a way to display a map screen
    in a Jetpack Compose UI. It asynchronously retrieves the user's
    current location and displays the map once the location is obtained.
    If the location is not yet available, it displays a loading message.
*/
@Composable
fun MapScreen(context: Context){
    var showMap by remember {mutableStateOf(false)}
    var location by remember { mutableStateOf(LatLng(0.0,0.0))}
    var mapProperties by remember {mutableStateOf(MapProperties(
        isBuildingEnabled = true
    ))}

    getCurrentLocation(context){
        location = it
        showMap = true
    }
    if(showMap){
        MyMap(
            context = context,
            latLng = location,
            mapProperties = mapProperties,
            onChangeMapType = {
                mapProperties = mapProperties.copy(mapType = it)
            })

    }
    else {
        Text(text = "Loading Map..", color = Color3)
    }
}
