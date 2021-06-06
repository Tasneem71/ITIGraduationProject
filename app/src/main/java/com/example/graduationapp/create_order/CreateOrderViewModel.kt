package com.example.graduationapp.create_order


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.data.CancelOrder
import com.example.graduationapp.data.CreatedOrder
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CreateOrderViewModel(application: Application) : AndroidViewModel(application) {

    var createOrderLiveData = MutableLiveData<Orders?>()
    var orders : MutableLiveData<List<Favorite>>? = MutableLiveData<List<Favorite>>()
    private val local = LocalSource(application)

    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository(application)
    }

    fun getAllOrderd(userId: String){
        viewModelScope.launch {
            val result= async{apiRepository.local.getAllCart(userId)}
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

    fun cancelOrder(orderJson: CancelOrder) {
        CoroutineScope(Dispatchers.IO).launch {
            val response=apiRepository.cancelOrder(orderJson)
            createOrderLiveData.postValue(response?.order!!)
        }
    }
    fun deleteListFromCart(id:String){
        viewModelScope.launch {
           local.deleteListFromCart(id)
        }
    }
}