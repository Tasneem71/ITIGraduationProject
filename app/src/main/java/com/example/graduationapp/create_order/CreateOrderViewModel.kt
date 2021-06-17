package com.example.graduationapp.create_order


import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.data.AddressData
import com.example.graduationapp.data.CancelOrder
import com.example.graduationapp.data.CreateAddress
import com.example.graduationapp.data.CreatedOrder
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.addressbook.AddressBookViewModel
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CreateOrderViewModel(application: Application,var apiRepository : DefaultRepo) : AndroidViewModel(application) {

    var createOrderLiveData = MutableLiveData<Orders?>()
    var orders : MutableLiveData<List<Favorite>>? = MutableLiveData<List<Favorite>>()
    var network =MutableLiveData<Boolean>()
    var getDefaultAddLifeData = MutableLiveData<AddressData?>()
    private val local = LocalSource(application)

//    var apiRepository: ApiRepository
//
//    init{
//        apiRepository = ApiRepository(application)
//    }

    fun getAllOrderd(userId: String){
        if (Validation.isOnline(getApplication())) {
            viewModelScope.launch {
                val result = async { apiRepository.getAllCart(userId) }
                result.join()
                orders?.value = result.await()
            }
        }
        else
        {
            network.postValue(false)
        }
    }

    fun createOrder(orderJson: CreatedOrder) {
        Log.i("discount","............................."+orderJson)
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiRepository.createOrder(orderJson)
                createOrderLiveData.postValue(response?.order!!)
            }
        }
        else
        {
            network.postValue(false)
        }
    }

    fun deleteListFromCart(id:String){
        viewModelScope.launch {
           local.deleteListFromCart(id)
        }
    }
    fun getDefaultAddress(id:String,addressIp:String) {
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                apiRepository.getDefaultAddress(id, addressIp).let {
                    getDefaultAddLifeData.postValue(it)
                }
            }
        }
        else
        {
            network.postValue(false)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CreateOrderViewModelFactory(val application: Application,val repo: DefaultRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateOrderViewModel(application, repo) as T
    }
}