package com.example.graduationapp.ui.productPageFeature

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.graduationapp.data.Products
import com.example.graduationapp.remote.retro.ApiServes
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.*

class ProductPageViewModel(application: Application) : AndroidViewModel(application){

    var productDetails  =MutableLiveData<Products>()
    var network =MutableLiveData<Boolean>()

    fun getProductDetails(id:String) {
            viewModelScope.launch {
                val response = ApiServes.shopfiyService.getProductDetails(id)
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            productDetails.postValue(it.products)

                        }
                    } else {
                        Log.i("Mohamed", "$")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i("Mohamed", " error?" + e.printStackTrace())
                }
            }
    }


}