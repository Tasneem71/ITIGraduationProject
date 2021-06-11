package com.example.graduationapp.ui.me

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
import com.example.graduationapp.data.orders.OrderAPI
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MeViewModel (application: Application) : AndroidViewModel(application) {

    var openOrdersLiveData = MutableLiveData<List<Orders>?>()
    var cancelOrderLiveData = MutableLiveData<Orders?>()
    var network =MutableLiveData<Boolean>()

    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository(application)
    }

    fun getOpenOrders() {
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiRepository.fetchOpenOrders()
                Log.i("tasneem", "" + response)
                openOrdersLiveData.postValue(response?.orders)
            }
        }
        else
        {
            network.postValue(false)
        }
    }

    fun cancelOrder(id : String,orderJson: CancelOrder) {
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiRepository.cancelOrder(id, orderJson)
                cancelOrderLiveData.postValue(response?.order)
            }
        }else {
            network.postValue(false)
        }
    }

}