package com.example.midtermapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.midtermapp.database.UserShoes
import com.example.midtermapp.database.UserShoesDao
import com.example.midtermapp.datanetwork.FirebaseDatabase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat

class UserShoesViewModel(
    private val userShoesDao: UserShoesDao,
    private val firebaseDatabase: FirebaseDatabase
): ViewModel() {
    private val _totalPrice = MutableLiveData<Double>(0.0)
    val totalPrice : LiveData<String> get() = Transformations.map(_totalPrice) {
        NumberFormat.getCurrencyInstance().format(it)
    }

    fun getAll(): LiveData<List<UserShoes>> = userShoesDao.getAll()

    private fun insert(userShoes: UserShoes) {
        viewModelScope.launch(Dispatchers.IO) {
            userShoesDao.insert(userShoes)
        }
    }

    private fun getNewEntry(id: Int, name: String, price: Double, size: Int, thumbnail: String): UserShoes {
        return UserShoes(
            id = id,
            itemName = name,
            itemPrice = price,
            size = size,
            imgUrl = thumbnail)
    }
    fun insert(id: Int, name: String, price: Double, size: Int, thumbnail: String) {
        val newShoes = getNewEntry(id,name,price,size,thumbnail)
        insert(newShoes)
    }
    fun deleteItem(shoes: UserShoes) {
        viewModelScope.launch(Dispatchers.IO) {
            userShoesDao.delete(shoes)
        }
    }
    fun updateItem(shoes : UserShoes) {
        viewModelScope.launch(Dispatchers.IO) {
            userShoesDao.update(shoes)
        }
    }
    fun buyAll() {
        viewModelScope.launch(Dispatchers.IO) {
            userShoesDao.deleteAll()
        }
    }


    fun calculatePrice(item: UserShoes) {
        if(item.isBuy) {
            _totalPrice.value = _totalPrice.value?.plus(item.itemPrice)
        }else {
            _totalPrice.value = _totalPrice.value?.minus(item.itemPrice)
        }
    }

    fun buyItem() {
        viewModelScope.launch(Dispatchers.IO) {

            userShoesDao.updateServerDb().forEach { id ->
                Log.d("UserShoesViewModel", id.toString())
                var quantity = firebaseDatabase.retrieveShoes(id).quantity
                quantity -= 1
                val data = mutableMapOf("quantity" to quantity)
                firebaseDatabase.updateShoes(id, data)
            }
            userShoesDao.bought()
        }
        _totalPrice.value = 0.0
    }
}
class UserShoesViewModelFactory(
    private val userShoesDao: UserShoesDao,
    private val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserShoesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserShoesViewModel(userShoesDao, firebaseDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}