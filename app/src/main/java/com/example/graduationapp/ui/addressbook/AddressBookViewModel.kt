package com.example.graduationapp.ui.addressbook

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.AddressData
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.data.CreateAddress
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.search.SearchViewModel
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddressBookViewModel(application: Application, var apiRepository :DefaultRepo) : AndroidViewModel(application){
    var firstAddressDetails  = MutableLiveData<List<Addresse?>?>()
    var allCustomerAddresses = MutableLiveData<List<Addresse?>?>()
    var defualtAddress = MutableLiveData<AddressData?>()
    var network =MutableLiveData<Boolean>()
    var createAddressLiveData = MutableLiveData<AddressData?>()
    var editAddressLiveData = MutableLiveData<AddressData?>()



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


    fun setDefaultAddress(id:String, addressID:String) {
        Log.i("Menna", "setDefaultAddress:")

        if (Validation.isOnline(getApplication())) {
            viewModelScope.launch {
                apiRepository.setDefaultAddress(id, addressID).let {
                    defualtAddress.postValue(it)
                    Log.i("Menna", "setDefaultAdhggggggggggdress:")

                }
            }.invokeOnCompletion {
                getAllCustomerAddress(id)
                SharedPref.setAddressID(addressID)

            }
        }
        else
        {
            Log.i("Menna", "setDefaulteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeAddress:")

            network.postValue(false)
        }
    }
    fun deleteAddress(id:String,addressIp:String) {
        if (Validation.isOnline(getApplication())) {
            viewModelScope.launch {
                apiRepository.deleteAddress(id, addressIp).let {
                    Log.i("Menna","delete done it = $it")
                }
            }.invokeOnCompletion {
                getAllCustomerAddress(id)

            }
        }
        else
        {
            network.postValue(false)
        }
    }



}
@Suppress("UNCHECKED_CAST")
class AddressBookViewModelFactory(val application: Application,val repo: DefaultRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddressBookViewModel(application, repo) as T
    }
}