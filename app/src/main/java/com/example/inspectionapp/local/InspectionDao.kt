package com.example.inspectionapp.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.inspectionapp.model.InspectionEntity

@Dao
interface InspectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInspection(inspection: InspectionEntity)

    @Query("SELECT * FROM inspections")
    suspend fun getAllInspections(): List<InspectionEntity>

    @Delete
    suspend fun deleteInspection(inspection: InspectionEntity)
}