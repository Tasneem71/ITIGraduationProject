package com.example.graduationapp.ui.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.example.graduationapp.data.ApiCollections
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.data.Customers
import com.example.graduationapp.data.priceRules.CreatedDiscount
import com.example.graduationapp.data.priceRules.DiscountCode
import com.example.graduationapp.data.priceRules.DiscountCodeClass
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.addressbook.AddressBookViewModel
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (application: Application,var apiRepository :DefaultRepo) : AndroidViewModel(application) {
    var generatedDiscountLiveData = MutableLiveData<DiscountCodeClass?>()
    var network =MutableLiveData<Boolean>()
    var cartCount = MutableLiveData<Int>()

//    var apiRepository: ApiRepository
//
//    init{
//        apiRepository = ApiRepository(application)
//    }


    fun cartCount(userId: String){
         viewModelScope.launch {
             val result=apiRepository.getUpdatedCount(userId)
             cartCount.value=result
             Log.i("cart",""+result)
         }
    }

    fun getDiscount10(){
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiRepository.getDiscount10()
                generatedDiscountLiveData.postValue(response?.discount_code)
            }
        }
        else{
            network.postValue(false)
        }

    }
    fun checkNetwork(){
        network.postValue(Validation.isOnline(getApplication()))
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(val application: Application,val repo: DefaultRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(application, repo) as T
    }
}
