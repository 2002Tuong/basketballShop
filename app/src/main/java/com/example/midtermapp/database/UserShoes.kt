package com.example.midtermapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserShoes(@PrimaryKey(autoGenerate = false) val id: Int = 0,
                     @ColumnInfo(name = "name") val itemName: String,
                     @ColumnInfo(name="size") val size: Int,
                     @ColumnInfo(name = "price") val itemPrice: Double,
                     @ColumnInfo(name="thumbnail") val imgUrl: String,
                     @ColumnInfo(name="is_buy")val isBuy: Boolean = false)
