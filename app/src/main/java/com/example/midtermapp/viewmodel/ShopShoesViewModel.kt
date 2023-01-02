package com.example.midtermapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.example.midtermapp.datanetwork.FirebaseDatabase
import com.example.midtermapp.datanetwork.Shoes
import com.example.midtermapp.datanetwork.toListOfShoes
import com.example.midtermapp.datanetwork.toShoes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
retrieve data from firebase
 */
class ShopShoesViewModel(private val firebaseDatabase: FirebaseDatabase) : ViewModel() {
    fun retrieveShopShoes(): LiveData<List<Shoes>> {
        val data = MutableLiveData<List<Shoes>>()
        viewModelScope.launch(Dispatchers.IO) {
            data.postValue(firebaseDatabase.getListShoes().toListOfShoes())
        }
        return data
    }

    fun retrieveSpecifyShoesById(id: Int): LiveData<Shoes> {
        val data = MutableLiveData<Shoes>()
        viewModelScope.launch(Dispatchers.IO) {
            data.postValue(firebaseDatabase.retrieveShoes(id).toShoes())
        }
        return data
    }


    fun update(id: Int, updateShoes: Map<String, Any>) {
        viewModelScope.launch(Dispatchers.IO) {
            firebaseDatabase.updateShoes(id,updateShoes)
        }
    }
}

class ShopShoesViewModelFactory(private val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShopShoesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShopShoesViewModel(firebaseDatabase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}