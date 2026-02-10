package com.example.simala.services

import android.Manifest
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import com.example.simala.R
import com.example.simala.data.LocationPing
import com.example.simala.data.VeraRepository
import com.google.android.gms.location.*
import kotlinx.coroutines.*

class SafetyService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Create Notification Channel for Android 8+
        val channelId = "simala_safety_channel"
        val channel = NotificationChannel(channelId, "Simala Protection", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Simala is watching")
            .setContentText("Your live location is being shared with your guardians.")
            .setSmallIcon(android.R.drawable.ic_dialog_info)  // Using system icon temporarily
            .setOngoing(true)
            .build()

        // Start as Foreground Service
        startForeground(1, notification)

        startLocationTracking()
        return START_STICKY
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun startLocationTracking() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 30000).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let { location ->
                    serviceScope.launch {
                        try {
                            VeraRepository.api.sendLocation(
                                LocationPing("user_123", location.latitude, location.longitude)
                            )
                        } catch (e: Exception) { /* Log network error */ }
                    }
                }
            }
        }
        // Actually start the updates
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }
}