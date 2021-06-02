package com.example.graduationapp.ui.checkoutAddress

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.graduationapp.data.AddressData
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.data.CreateAddress
import com.example.graduationapp.data.CreatedCustomer
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.retro.ApiServes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerDataViewModel : ViewModel(){
    var addressDetails  = MutableLiveData<AddressData>()
    var createAddressLiveData = MutableLiveData<AddressData?>()
    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository()
    }

    fun getCustomerAddress(id:String) {
        CoroutineScope(Dispatchers.IO).launch {
            apiRepository.getCustomerAddress(id).let {
                addressDetails.postValue(it)
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
}
