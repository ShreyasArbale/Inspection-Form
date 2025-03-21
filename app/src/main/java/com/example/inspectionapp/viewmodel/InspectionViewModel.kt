package com.example.inspectionapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.inspectionapp.local.InspectionDatabase
import com.example.inspectionapp.model.ConditionResponse
import com.example.inspectionapp.model.InspectionData
import com.example.inspectionapp.model.InspectionEntity
import com.example.inspectionapp.utils.NetworkUtils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InspectionViewModel : ViewModel() {

    var commentsList = mutableStateListOf("")
    var inspectionData = mutableStateOf(InspectionData())
    private val db = FirebaseFirestore.getInstance()

    private val _conditionResponses = MutableStateFlow<Map<String, ConditionResponse>>(emptyMap())
    val conditionResponses: StateFlow<Map<String, ConditionResponse>> = _conditionResponses

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving.asStateFlow()

    fun addComment(context: Context) {
        if (commentsList.isNotEmpty() && commentsList.last().isBlank()) {
            Toast.makeText(context, "Previous comment field is empty", Toast.LENGTH_SHORT).show()
            return
        }
        commentsList.add("")
    }

    fun updateComment(index: Int, newValue: String) {
        commentsList[index] = newValue
    }

    fun deleteComment(index: Int) {
        if (index in commentsList.indices) {
            commentsList.removeAt(index)
        }
    }

    fun savePropertyDetails(
        customerName: String,
        siteName: String,
        plantNumber: String,
        serialNumber: String,
        selectedDate: String
    ) {
        inspectionData.value = inspectionData.value.copy(
            customerName = customerName,
            siteName = siteName,
            plantNumber = plantNumber,
            serialNumber = serialNumber,
            selectedDate = selectedDate
        )
        Log.d("my InspectionData", "Property Details: ${inspectionData.value}")
    }

    fun updateConditionResponse(
        question: String,
        comment: String,
        preCondition: String,
        preCost: String,
        postCondition: String,
        postCost: String
    ) {
        val updatedResponses = _conditionResponses.value.toMutableMap()
        updatedResponses[question] = ConditionResponse(
            question, comment, preCondition, preCost, postCondition, postCost
        )
        _conditionResponses.value = updatedResponses
    }

    fun saveConditionResponses(responses: List<ConditionResponse>) {
        val responseMap = responses.associateBy { it.question }
        _conditionResponses.value = responseMap

        Log.d("my ConditionResponses", "Condition Responses: ${_conditionResponses.value}")
        Log.d("my FinalInspectionData", "InspectionData: ${inspectionData.value}")
    }

    fun clearConditionResponses(questions: List<String>) {
        _conditionResponses.value = questions.associateWith {
            ConditionResponse(it, "", "A", "", "A", "")
        }
    }

    fun saveAdditionalInfo(
        context: Context,
        selectedImageUri: String,
        cost: String,
        preSignImageUri: String,
        preCustomerSignImageUri: String,
        preHireSelectedDate: String,
        postSignImageUri: String,
        postCustomerSignImageUri: String,
        postHireSelectedDate: String,
        onSuccess: () -> Unit
    ) {
        inspectionData.value = inspectionData.value.copy(
            conditionResponses = _conditionResponses.value.values.toMutableList(),
            commentList = commentsList.toMutableList(),
            selectedImageUri = selectedImageUri,
            cost = cost,
            preSignImageUri = preSignImageUri,
            preCustomerSignImageUri = preCustomerSignImageUri,
            preHireSelectedDate = preHireSelectedDate,
            postSignImageUri = postSignImageUri,
            postCustomerSignImageUri = postCustomerSignImageUri,
            postHireSelectedDate = postHireSelectedDate
        )

        Log.d("my InspectionData", "Additional Info Saved: ${inspectionData.value}")

        if (NetworkUtils.isInternetAvailable(context)) {
            saveInspectionDataToFirestore(context, onSuccess)
        } else {
            saveInspectionDataToRoomDatabase(context, onSuccess)
        }
    }

    fun clearAdditionalDetailsTab() {
        inspectionData.value = InspectionData()
        commentsList.clear()
        commentsList.add("")
    }

    fun saveInspectionDataToFirestore(context: Context, onSuccess: () -> Unit) {
        _isSaving.value = true

        val inspectionDataMap = inspectionData.value.toMap()

        db.collection("inspections")
            .add(inspectionDataMap)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    "my Firestore",
                    "InspectionData successfully saved with ID: ${documentReference.id}"
                )
                Toast.makeText(
                    context,
                    "Inspection details stored successfully to Firebase",
                    Toast.LENGTH_SHORT
                ).show()
                _isSaving.value = false
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.d("my Firestore", "Error saving inspectionData", e)
                Toast.makeText(
                    context,
                    "Failed to store inspection details to Firebase",
                    Toast.LENGTH_SHORT
                ).show()
                _isSaving.value = false
            }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun saveInspectionDataToRoomDatabase(context: Context, onSuccess: () -> Unit) {
        val inspectionEntity = InspectionEntity(
            customerName = inspectionData.value.customerName,
            siteName = inspectionData.value.siteName,
            plantNumber = inspectionData.value.plantNumber,
            serialNumber = inspectionData.value.serialNumber,
            selectedDate = inspectionData.value.selectedDate,
            conditionResponses = _conditionResponses.value.values.toList(),
            comments = commentsList.toList(),
            selectedImageUri = inspectionData.value.selectedImageUri,
            cost = inspectionData.value.cost,
            preSignImageUri = inspectionData.value.preSignImageUri,
            preCustomerSignImageUri = inspectionData.value.preCustomerSignImageUri,
            preHireSelectedDate = inspectionData.value.preHireSelectedDate,
            postSignImageUri = inspectionData.value.postSignImageUri,
            postCustomerSignImageUri = inspectionData.value.postCustomerSignImageUri,
            postHireSelectedDate = inspectionData.value.postHireSelectedDate
        )

        val database = InspectionDatabase.getDatabase(context)
        val dao = database.inspectionDao()

        kotlinx.coroutines.GlobalScope.launch {
            dao.insertInspection(inspectionEntity)
            Log.d("RoomDB", "Inspection data saved locally")

            kotlinx.coroutines.MainScope().launch {
                Toast.makeText(context, "Inspection details saved locally", Toast.LENGTH_SHORT)
                    .show()
                onSuccess()
            }
        }
    }
}
