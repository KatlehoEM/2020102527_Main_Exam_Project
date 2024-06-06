/*
    Surname and Initials: Makhooane KE
    Student Number: 2020102527
    Date: 02 June 2024
    Module Code: CSIP6853
*/
package com.example.a2020102527_main_exam_project.ui.maps.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

/*  Purpose of code
    This code contains functions that provide utilities for
    handling location-related tasks in an Android application,
    including checking permissions, fetching the current device location,
    and retrieving coordinates based on place names.
*/

fun checkForPermission(context: Context): Boolean {
    return !(ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )  != PackageManager.PERMISSION_GRANTED
            )
}


@SuppressLint("MissingPermission")
fun getCurrentLocation(context: Context, onLocationFetched: (location: LatLng) -> Unit){
    var loc: LatLng
    var fusedLocationClient: FusedLocationProviderClient
            = LocationServices.getFusedLocationProviderClient(context)

    val locationCallback = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            locationResult.lastLocation?.let {
                val latitude = it.latitude
                val logitude = it.longitude
                loc = LatLng(latitude,logitude)
                onLocationFetched(loc)

            }
        }
    }

    val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY, 2000).build()

    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
}

fun getCoordinatesFromPlaceName(context: Context, placeName: String): LatLng {
    val geocoder = Geocoder(context, Locale.getDefault())
    val addresses: List<Address>?
    try {
        addresses = geocoder.getFromLocationName(placeName, 1)
        if (!addresses.isNullOrEmpty()) {
            val latitude = addresses[0].latitude
            val longitude = addresses[0].longitude
            return LatLng(latitude,longitude)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return LatLng(0.0,0.0)
}

fun isWithinVicinity(currentLocation: LatLng, storeLocation: LatLng, radius: Double = 50000.0): Boolean {
    val results = FloatArray(1)
    Location.distanceBetween(
        currentLocation.latitude,
        currentLocation.longitude,
        storeLocation.latitude,
        storeLocation.longitude,
        results
    )
    return results[0] < radius
}