package com.example.midtermapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.midtermapp.database.UserShoes
import com.example.midtermapp.database.UserShoesDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserShoesViewModel(private val userShoesDao: UserShoesDao): ViewModel() {
    fun getAll(): LiveData<List<UserShoes>> = userShoesDao.getAll()
    private fun insert(userShoes: UserShoes) {
        viewModelScope.launch(Dispatchers.IO) {
            userShoesDao.insert(userShoes)
        }
    }
    private fun getNewEntry(name: String, price: Double, size: Int, thumbnail: String): UserShoes {
        return UserShoes(itemName = name,
            itemPrice = price,
            size = size,
            imgUrl = thumbnail)
    }
    fun insert(name: String, price: Double, size: Int, thumbnail: String) {
        val newShoes = getNewEntry(name,price,size,thumbnail)
        insert(newShoes)
    }
    fun deleteItem(shoes: UserShoes) {
        viewModelScope.launch(Dispatchers.IO) {
            userShoesDao.delete(shoes)
        }
    }
    private fun updateItem(shoes : UserShoes) {
        viewModelScope.launch(Dispatchers.IO) {
            userShoesDao.update(shoes)
        }
    }
    fun buyAll() {
        viewModelScope.launch(Dispatchers.IO) {
            userShoesDao.deleteAll()
        }

    }
}
class UserShoesViewModelFactory(private val userShoesDao: UserShoesDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserShoesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserShoesViewModel(userShoesDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}