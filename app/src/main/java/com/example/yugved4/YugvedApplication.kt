package com.example.yugved4

import android.app.Application
import com.google.firebase.FirebaseApp

class YugvedApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
    }
}
