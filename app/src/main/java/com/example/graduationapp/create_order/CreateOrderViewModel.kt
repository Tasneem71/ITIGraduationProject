package com.example.graduationapp.create_order

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.graduationapp.data.CreatedOrder
import com.example.graduationapp.data.Orders
import com.example.graduationapp.data.OrdersItem
import com.example.graduationapp.remote.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateOrderViewModel : ViewModel() {

    var allOrdersLiveData = MutableLiveData<Orders?>()
    var createOrderLiveData = MutableLiveData<OrdersItem?>()
    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository()
    }

    fun loadData(context: Context){
        Log.i("order","inside the load")
        CoroutineScope(Dispatchers.IO).launch {
            val response=apiRepository.fetchAllOrders()
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        allOrdersLiveData.postValue(it)
                    }
                } else {
                    Log.i("order", "response failuer" + response.errorBody().toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("order", " error?" + e.printStackTrace())

            }
        }
    }


    fun createOrder(orderJson: CreatedOrder) {
        CoroutineScope(Dispatchers.IO).launch {
            val response=apiRepository.createOrder(orderJson)
            createOrderLiveData.postValue(response)
        }
    }
}