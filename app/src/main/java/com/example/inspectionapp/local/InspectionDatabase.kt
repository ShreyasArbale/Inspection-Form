package com.example.inspectionapp.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.inspectionapp.model.InspectionEntity

@Database(entities = [InspectionEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class InspectionDatabase : RoomDatabase() {

    abstract fun inspectionDao(): InspectionDao

    companion object {
        @Volatile
        private var INSTANCE: InspectionDatabase? = null

        fun getDatabase(context: Context): InspectionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    InspectionDatabase::class.java,
                    "inspection_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}