package com.example.graduationapp.create_order


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.data.CreatedOrder
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.remote.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CreateOrderViewModel(application: Application) : AndroidViewModel(application) {

    var allOrdersLiveData = MutableLiveData<List<Orders?>>()
    var createOrderLiveData = MutableLiveData<Orders?>()
    var orders : MutableLiveData<List<Favorite>>? = MutableLiveData<List<Favorite>>()
    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository(application)
    }

    fun getAllOrderd(){
        viewModelScope.launch {
            val result= async{apiRepository.local.getAllCart()}
            result.join()
            orders?.value=result.await()
        }
    }

    fun createOrder(orderJson: CreatedOrder) {
        CoroutineScope(Dispatchers.IO).launch {
            val response=apiRepository.createOrder(orderJson)
            createOrderLiveData.postValue(response?.order!!)
        }
    }
}