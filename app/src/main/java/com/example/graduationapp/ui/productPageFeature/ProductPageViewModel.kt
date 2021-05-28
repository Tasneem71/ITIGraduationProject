package com.example.graduationapp.ui.productPageFeature

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.domain.core.favoriteFeature.Favorite
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.data.Products
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.retro.ApiServes
import kotlinx.coroutines.*
import kotlin.properties.Delegates

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