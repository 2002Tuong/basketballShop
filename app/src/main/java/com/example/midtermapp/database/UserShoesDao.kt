package com.example.midtermapp.database

import android.content.ClipData
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserShoesDao {
    @Query("select * from UserShoes")
    fun getAll() : LiveData<List<UserShoes>>
    @Query("select* from UserShoes where UserShoes.id = :id")
    fun getShoes(id : Int) : LiveData<UserShoes>
    @Query("delete from UserShoes")
    fun deleteAll()
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shoes: UserShoes)
    @Delete
    suspend fun delete(shoes: UserShoes)
    @Update
    suspend fun update(shoes: UserShoes)
    @Query("delete from UserShoes where is_buy == 1")
    suspend fun bought()
}