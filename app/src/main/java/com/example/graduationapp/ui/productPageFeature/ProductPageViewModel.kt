package com.example.graduationapp.ui.productPageFeature

import android.util.Log
import androidx.lifecycle.*
import com.example.graduationapp.data.Products
import com.example.graduationapp.remote.retro.ApiServes
import kotlinx.coroutines.*

class ProductPageViewModel() : ViewModel() {

    var productDetails  =MutableLiveData<Products>()

    fun getProductDetails(id:String) {
        viewModelScope.launch {
            val response = ApiServes.shopfiyService.getProductDetails(id)
            try {
                if (response.isSuccessful) {
                    Log.i("Mohamed", "getProductDetails: ggggggggggggggggggggg")
                    response.body()?.let {
                        productDetails.postValue(it.products)
                        Log.i("Mohamed", "getProductDetails: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")

                    }
                    Log.i("Mohamed", "getProductDetails: dddddddddddddd")
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