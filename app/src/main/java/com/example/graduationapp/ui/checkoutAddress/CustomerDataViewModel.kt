package com.example.graduationapp.ui.checkoutAddress

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.data.*
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.retro.ApiServes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch



class CustomerDataViewModel (application: Application) : AndroidViewModel(application){
    var allAddressDetails  = MutableLiveData<List<Addresse?>?>()
    var createOrderLiveData = MutableLiveData<Orders?>()
    private val local = LocalSource(application)
    var carts : MutableLiveData<List<Favorite>>? = MutableLiveData<List<Favorite>>()

    var createAddressLiveData = MutableLiveData<AddressData?>()
    var editAddressLiveData = MutableLiveData<AddressData?>()

    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository(application )
    }

    fun getAllCarts(userId: String){
        viewModelScope.launch {
            val result= async{local.getAllCart(userId)}
            result.join()
            carts?.value=result.await()
        }
    }

    fun getCustomerAddress(id:String) {
        CoroutineScope(Dispatchers.IO).launch {
            apiRepository.getCustomerAddress(id).let {
                allAddressDetails.postValue(it)
            }
        }

    }

    fun createCustomerAddress(id:String, addressJson: CreateAddress) {
        CoroutineScope(Dispatchers.IO).launch {
            apiRepository.createCustomerAdd(id,addressJson).let {
                createAddressLiveData.postValue(it)
            }
        }
    }

    fun editCustomerAddress(id:String,addressIp:String,addressJson: CreateAddress) {
        CoroutineScope(Dispatchers.IO).launch {
            apiRepository.editCustomerAdd(id,addressIp,addressJson).let {
                editAddressLiveData.postValue(it)
            }
        }
    }
    fun deleteFromFavorite(item: Favorite){
        viewModelScope.launch {
            local.deleteFromFavorite(item)
        }
    }
    fun createOrder(orderJson: CreatedOrder) {
        CoroutineScope(Dispatchers.IO).launch {
            val response=apiRepository.createOrder(orderJson)
            createOrderLiveData.postValue(response?.order!!)
        }
    }

}
