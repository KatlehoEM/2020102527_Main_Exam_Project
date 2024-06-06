package com.example.a2020102527_main_exam_project.ui.maps
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a2020102527_main_exam_project.ui.maps.utils.checkForPermission
/*
This code defines a composable function ShowMap that displays a map
screen if the app has location permission; otherwise, it shows a location permission screen.
 */
@Composable
fun ShowMap(context: Context) {
    Box(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp,70.dp,10.dp,70.dp)
    ) {
        var hasLocationPermission by remember {
            mutableStateOf(checkForPermission(context))
        }
        // Check if the app has location permission
        if(hasLocationPermission){
            // If yes, display the MapScreen
            MapScreen(context)
        }
        else{
            // If no, display the LocationPermissionScreen
            LocationPermissionScreen{
                hasLocationPermission = true;
            }
        }
    }
}