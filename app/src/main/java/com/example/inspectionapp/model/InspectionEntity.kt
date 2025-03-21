package com.example.inspectionapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.inspectionapp.local.Converters

@Entity(tableName = "inspections")
@TypeConverters(Converters::class)
data class InspectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val customerName: String,
    val siteName: String,
    val plantNumber: String,
    val serialNumber: String,
    val selectedDate: String,
    val conditionResponses: List<ConditionResponse>,
    val comments: List<String>,
    val selectedImageUri: String,
    val cost: String,
    val preSignImageUri: String,
    val preCustomerSignImageUri: String,
    val preHireSelectedDate: String,
    val postSignImageUri: String,
    val postCustomerSignImageUri: String,
    val postHireSelectedDate: String
)