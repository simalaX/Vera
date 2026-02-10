package com.example.simala.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// 1. Data model for the GPS Ping
data class LocationPing(
    val userId: String,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long = System.currentTimeMillis()
)

// 2. The API Interface
interface VeraApiService {
    @POST("/ping")
    suspend fun sendLocation(@Body location: LocationPing)

    @POST("/sos")
    suspend fun triggerSOS(@Body userId: String)
}

// 3. The Repository (Singleton)
object VeraRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://your-python-backend.com") // Replace with your actual IP/URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: VeraApiService = retrofit.create(VeraApiService::class.java)
}