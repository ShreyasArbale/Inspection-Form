package com.example.inspectionapp.model

data class InspectionData(
    var customerName: String = "",
    var siteName: String = "",
    var plantNumber: String = "",
    var serialNumber: String = "",
    var selectedDate: String = "",
    var conditionResponses: MutableList<ConditionResponse> = mutableListOf(),
    var commentList: MutableList<String> = mutableListOf(),
    var selectedImageUri: String = "",
    var cost: String = "",
    var preSignImageUri: String = "",
    var preCustomerSignImageUri: String = "",
    var preHireSelectedDate: String = "",
    var postSignImageUri: String = "",
    var postCustomerSignImageUri: String = "",
    var postHireSelectedDate: String = ""
){
    fun toMap(): Map<String, Any> {
        return mapOf(
            "customerName" to customerName,
            "siteName" to siteName,
            "plantNumber" to plantNumber,
            "serialNumber" to serialNumber,
            "selectedDate" to selectedDate,
            "conditionResponses" to conditionResponses.map { it.toMap() },
            "commentList" to commentList,
            "selectedImageUri" to selectedImageUri,
            "cost" to cost,
            "preSignImageUri" to preSignImageUri,
            "preCustomerSignImageUri" to preCustomerSignImageUri,
            "preHireSelectedDate" to preHireSelectedDate,
            "postSignImageUri" to postSignImageUri,
            "postCustomerSignImageUri" to postCustomerSignImageUri,
            "postHireSelectedDate" to postHireSelectedDate
        )
    }
}

data class ConditionResponse(
    val question: String = "",
    val comment: String = "",
    val preCondition: String = "",
    val preCost: String = "",
    val postCondition: String = "",
    val postCost: String = ""
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "question" to question,
            "comment" to comment,
            "preCondition" to preCondition,
            "preCost" to preCost,
            "postCondition" to postCondition,
            "postCost" to postCost
        )
    }
}