package com.example.simala.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.simala.data.VeraRepository

class EmergencyWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // If this worker is still running and hasn't been cancelled by
            // the "I'm Safe" button, trigger the backend SOS immediately.
            VeraRepository.api.triggerSOS("user_123")
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}