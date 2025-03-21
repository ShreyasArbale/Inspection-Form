package com.example.inspectionapp.local

import androidx.room.TypeConverter
import com.example.inspectionapp.model.ConditionResponse
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromConditionResponseList(value: List<ConditionResponse>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toConditionResponseList(value: String): List<ConditionResponse> {
        val listType = object : TypeToken<List<ConditionResponse>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
}