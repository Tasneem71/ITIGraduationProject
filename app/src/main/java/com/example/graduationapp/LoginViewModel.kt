package com.example.graduationapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.graduationapp.data.*
import com.example.graduationapp.remote.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel (application: Application) : AndroidViewModel(application) {
    var allCustomersLiveData = MutableLiveData<ApiCustomers?>()
    var createCustomerLiveData = MutableLiveData<Customers?>()
    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository()
    }



    fun loadData(context: Context){
        Log.i("Tasneem","inside the load")
        CoroutineScope(Dispatchers.IO).launch {
            val response=apiRepository.fetchAllCustomerData()
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        allCustomersLiveData.postValue(it)
                    }
                } else {
                    Log.i("Tasneem", "response failuer" + response.errorBody().toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("Tasneem", " error?" + e.printStackTrace())

            }
        }
    }


    fun createCustomer(customerJson: CreatedCustomer) {
        CoroutineScope(Dispatchers.IO).launch {
            val response=apiRepository.createCustomer(customerJson)
                createCustomerLiveData.postValue(response)
            }
        }
    }
