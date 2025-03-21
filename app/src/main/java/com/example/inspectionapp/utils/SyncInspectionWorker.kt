package com.example.inspectionapp.utils


import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.inspectionapp.local.InspectionDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SyncInspectionWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {

    private val db = FirebaseFirestore.getInstance()
    private val inspectionDao = InspectionDatabase.getDatabase(appContext).inspectionDao()

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val unsyncedInspections = inspectionDao.getAllInspections()

                if (unsyncedInspections.isEmpty()) {
                    Log.d("SyncWorker", "No unsynced data found")
                    return@withContext Result.success()
                }

                for (inspection in unsyncedInspections) {
                    val inspectionDataMap = mapOf(
                        "customerName" to inspection.customerName,
                        "siteName" to inspection.siteName,
                        "plantNumber" to inspection.plantNumber,
                        "serialNumber" to inspection.serialNumber,
                        "selectedDate" to inspection.selectedDate,
                        "conditionResponses" to inspection.conditionResponses,
                        "comments" to inspection.comments,
                        "selectedImageUri" to inspection.selectedImageUri,
                        "cost" to inspection.cost,
                        "preSignImageUri" to inspection.preSignImageUri,
                        "preCustomerSignImageUri" to inspection.preCustomerSignImageUri,
                        "preHireSelectedDate" to inspection.preHireSelectedDate,
                        "postSignImageUri" to inspection.postSignImageUri,
                        "postCustomerSignImageUri" to inspection.postCustomerSignImageUri,
                        "postHireSelectedDate" to inspection.postHireSelectedDate
                    )

                    db.collection("inspections")
                        .add(inspectionDataMap)
                        .addOnSuccessListener {
                            Log.d("SyncWorker", "Data synced successfully to Firestore")

                            CoroutineScope(Dispatchers.IO).launch {
                                inspectionDao.deleteInspection(inspection)
                            }
                        }
                        .addOnFailureListener { e ->
                            Log.e("SyncWorker", "Failed to sync data: ${e.message}")
                        }
                }
                Result.success()
            } catch (e: Exception) {
                Log.e("SyncWorker", "Error syncing data: ${e.message}")
                Result.retry()
            }
        }
    }
}