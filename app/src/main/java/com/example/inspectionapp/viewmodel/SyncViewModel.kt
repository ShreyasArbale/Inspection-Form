package com.example.inspectionapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.inspectionapp.utils.SyncInspectionWorker
import java.util.concurrent.TimeUnit

class SyncViewModel : ViewModel() {
    fun scheduleSyncWorker(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<SyncInspectionWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "SyncInspectionWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}