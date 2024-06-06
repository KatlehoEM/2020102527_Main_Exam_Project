package com.example.a2020102527_main_exam_project.ui.maps
/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 05 June 2024
    Module Code: CSIP6853
*/

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a2020102527_main_exam_project.ui.theme.Color3

/* Purpose of code:
   This composable provides a simple interface for requesting location
   permission from the user in a Jetpack Compose UI.
 */

@Composable
fun LocationPermissionScreen(
    onPermissionGranted: () -> Unit
) {
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        var isGranted = true
        permissions.entries.forEach {
            if (!it.value) {
                isGranted = false
                return@forEach
            }
        }

        if (isGranted) {
            onPermissionGranted()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Location Permission Required",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                locationPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color3)
        ) {
            Text(text = "Press for Permissions")
        }
    }
}

