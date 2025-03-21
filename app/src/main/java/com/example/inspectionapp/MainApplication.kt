package com.example.inspectionapp

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
            Log.d("my FirebaseInit", "Firebase initialized successfully")
        } else {
            Log.d("my FirebaseInit", "Firebase already initialized")
        }
    }
}