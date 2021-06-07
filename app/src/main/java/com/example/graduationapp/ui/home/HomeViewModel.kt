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
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) : AndroidViewModel(application) {
    var generatedDiscountLiveData = MutableLiveData<DiscountCodeClass?>()
    var network =MutableLiveData<Boolean>()
    var cartCount = MutableLiveData<Int>()

    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository(application)
    }

    fun loadData(context: Context): MutableLiveData<ApiCollections> {
        Log.i("Tasneem","inside the load")
        CoroutineScope(Dispatchers.IO).launch {
            apiRepository.fetchSmartCollectionData()
        }

        Log.i("Tasneem","after")

        return apiRepository.apiCollection
    }


    fun loadProductData(id:String,num:Int): MutableLiveData<CollectionProducts> {
        Log.i("Tasneem","inside the load")
        if (Validation.isOnline(getApplication())){
            CoroutineScope(Dispatchers.IO).launch {
                apiRepository.fetchSmartProductsData(id,num)
            }
            //Log.i("Tasneem","after")
            when (num) {
                0 -> return apiRepository.apiSmart1Collection
                1 -> return apiRepository.apiSmart2Collection
                2 -> return apiRepository.apiSmart3Collection
                3 -> return apiRepository.apiSmart4Collection
                4 -> return apiRepository.apiSmart5Collection
            }
        }
        else
        {
            network.postValue(false)
        }
        return apiRepository.apiproduct
    }

    fun generatingDiscount(priceRule:String,discount: CreatedDiscount){
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiRepository.generateDiscount(priceRule, discount)
                generatedDiscountLiveData.postValue(response?.discount_code)
            }
        }
        else{
            network.postValue(false)
        }


    }

    fun cartCount(userId: String){
         viewModelScope.launch {
             val result=apiRepository.getUpdatedCount(userId)
             cartCount.value=result
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

}
