package com.example.midtermapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserShoes::class], version = 3, exportSchema = false)
abstract class UserShoesDatabase : RoomDatabase() {
    abstract fun userShoesDao() : UserShoesDao

    companion object {
        @Volatile
        private var INSTANCE: UserShoesDatabase? = null
        fun getDatabase(context: Context): UserShoesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserShoesDatabase::class.java,
                    "user_shoes_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}