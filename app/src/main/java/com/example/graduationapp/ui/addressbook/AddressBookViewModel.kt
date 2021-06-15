package com.example.graduationapp.ui.addressbook

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.data.AddressData
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.data.CreateAddress
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddressBookViewModel(application: Application) : AndroidViewModel(application){
    var firstAddressDetails  = MutableLiveData<List<Addresse?>?>()
    var allCustomerAddresses = MutableLiveData<List<Addresse?>?>()
    var defualtAddress = MutableLiveData<AddressData?>()
    var network =MutableLiveData<Boolean>()
    var createAddressLiveData = MutableLiveData<AddressData?>()
    var editAddressLiveData = MutableLiveData<AddressData?>()
    var apiRepository: ApiRepository = ApiRepository(application)



    fun getFirstCustomerAddress(id:String) {
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                apiRepository.getCustomerAddress(id).let {
                    firstAddressDetails.postValue(it)
                }
            }
        }
        else
        {
            network.postValue(false)
        }

    }
    fun getAllCustomerAddress(id:String) {
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                apiRepository.getAllCustomerAddress(id).let {
                    allCustomerAddresses.postValue(it)
                }
            }
        }
        else
        {
            network.postValue(false)
        }

    }


    fun setDefaultAddress(id:String,addressIp:String) {
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                apiRepository.setDefaultAddress(id, addressIp).let {
                    defualtAddress.postValue(it)
                }
            }
        }
        else
        {
            network.postValue(false)
        }
    }
    fun deleteAddress(id:String,addressIp:String) {
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                apiRepository.deleteAddress(id, addressIp).let {
                    Log.i("Menna","delete done it = $it")
                }
            }
        }
        else
        {
            network.postValue(false)
        }
    }

//    fun createCustomerAddress(id:String, addressJson: CreateAddress) {
//        if (Validation.isOnline(getApplication())) {
//            CoroutineScope(Dispatchers.IO).launch {
//                apiRepository.createCustomerAdd(id, addressJson).let {
//                    createAddressLiveData.postValue(it)
//                }
//            }
//        }
//        else{
//            network.postValue(false)
//        }
//    }
//
//    fun editCustomerAddress(id:String,addressIp:String,addressJson: CreateAddress) {
//        if (Validation.isOnline(getApplication())) {
//            CoroutineScope(Dispatchers.IO).launch {
//                apiRepository.editCustomerAdd(id, addressIp, addressJson).let {
//                    editAddressLiveData.postValue(it)
//                }
//            }
//        }
//        else
//        {
//            network.postValue(false)
//        }
//    }

}
