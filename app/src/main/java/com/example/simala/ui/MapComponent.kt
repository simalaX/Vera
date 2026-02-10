package com.vera.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@SuppressLint("UnrememberedMutableState")
@Composable
fun VeraMapComponent(
    userLocation: LatLng, // Passed from your LocationService
    isEmergency: Boolean = false
) {
    // 1. Map Camera State: Moves as the user walks
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 17f) // Close zoom for walking
    }

    // Update camera when location changes
    LaunchedEffect(userLocation) {
        cameraPositionState.animate(
            update = com.google.android.gms.maps.CameraUpdateFactory.newLatLng(userLocation)
        )
    }

    // 2. Map Styling & UI Settings
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = true,
                zoomControlsEnabled = false, // Keep UI clean
                mapToolbarEnabled = false
            )
        )
    }

    val properties by remember(isEmergency) {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true,
                // If emergency, we could switch to Satellite for better ground detail
                mapType = if (isEmergency) MapType.HYBRID else MapType.NORMAL
            )
        )
    }

    // 3. The Map View
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = uiSettings,
        properties = properties
    ) {
        // Place the "Vera Sentinel" marker at user's current spot
        Marker(
            state = MarkerState(position = userLocation),
            title = "Vera Protection Active",
            snippet = if (isEmergency) "EMERGENCY BROADCASTING" else "You are safe",
            // You can replace this with a custom icon of your Vera logo
            alpha = if (isEmergency) 1.0f else 0.8f
        )
    }
}