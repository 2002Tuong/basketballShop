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
import com.example.midtermapp.datanetwork.Shoes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat

class UserShoesViewModel(private val userShoesDao: UserShoesDao): ViewModel() {
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
            userShoesDao.bought()
        }
        _totalPrice.value = 0.0
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