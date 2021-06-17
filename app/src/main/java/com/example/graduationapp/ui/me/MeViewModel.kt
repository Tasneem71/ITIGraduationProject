package com.example.graduationapp.ui.me

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.example.graduationapp.data.*
import com.example.graduationapp.data.orders.OrderAPI
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.addressbook.AddressBookViewModel
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MeViewModel (application: Application,var apiRepository :DefaultRepo) : AndroidViewModel(application) {

    var openOrdersLiveData = MutableLiveData<List<Orders>?>()
    var cancelOrderLiveData = MutableLiveData<Orders?>()
    var network =MutableLiveData<Boolean>()
    var cartCount = MutableLiveData<Int>()
    var orderedProductsLiveData = MutableLiveData<List<Products>?>()

//    var apiRepository: ApiRepository
//
//    init{
//        apiRepository = ApiRepository(application)
//    }

    fun getOpenOrders(id:String) {
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiRepository.fetchOpenOrders(id)
                Log.i("tasneem", "" + response)
                openOrdersLiveData.postValue(response?.orders)
            }
        }
        else
        {
            network.postValue(false)
        }
    }

    fun getOrderedProducts(ids:List<String>) {
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                var products= mutableListOf<Products>()
                for (id in ids){
                    val response = apiRepository.fetchOrderedProducts(id)
                    Log.i("tasneem", "" + response)
                    response?.let { products.add(it.products) }
                }
                orderedProductsLiveData.postValue(products)
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

    fun cartCount(userId: String){
        viewModelScope.launch {
            val result=apiRepository.getUpdatedCount(userId)
            cartCount.value=result
            Log.i("cart",""+result)
        }
    }

}

@Suppress("UNCHECKED_CAST")
class MeViewModelFactory(val application: Application,val repo: DefaultRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MeViewModel(application, repo) as T
    }
}