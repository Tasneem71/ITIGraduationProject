package com.example

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.graduationapp.data.ApiCustomers
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.data.CreatedCustomer
import com.example.graduationapp.data.Customers
import com.example.graduationapp.remote.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel (application: Application) : AndroidViewModel(application) {
    var getAllProductsLiveData = MutableLiveData<CollectionProducts?>()
    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository()
    }


    fun getAllProducts() {
        CoroutineScope(Dispatchers.IO).launch {
            val response=apiRepository.getAllProducts()
            getAllProductsLiveData.postValue(response)
        }
    }
}
