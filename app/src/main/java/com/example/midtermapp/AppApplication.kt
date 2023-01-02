package com.example.midtermapp

import android.app.Application
import com.example.midtermapp.database.UserShoesDatabase
import com.example.midtermapp.datanetwork.FirebaseDatabase

class AppApplication : Application() {
    val database : UserShoesDatabase by lazy {
        UserShoesDatabase.getDatabase(this)
    }
    val firebase : FirebaseDatabase by lazy {
        FirebaseDatabase()
    }
}