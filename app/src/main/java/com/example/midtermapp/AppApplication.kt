package com.example.midtermapp

import android.app.Application
import com.example.midtermapp.database.UserShoesDatabase

class AppApplication : Application() {
    val database : UserShoesDatabase by lazy {
        UserShoesDatabase.getDatabase(this)
    }
}