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

    fun getProductDetails(id:Long) {
        viewModelScope.launch {
            val response = ApiServes.shopfiyService.getProductDetails("6687367823558")
            try {
                if (response.isSuccessful) {
                    Log.i("Mohamed", "getProductDetails: ggggggggggggggggggggg")
                    response.body()?.let {
                        productDetails.postValue(it)
                        Log.i("Mohamed", "getProductDetails: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                        Log.i("Mohamed", "getProductDetails: ${it.title}")
                        Log.i("Mohamed", "getProductDetails: ${it.id}")
                        Log.i("Mohamed", "getProductDetails: ${it.handle}")

                    }
                } else {
                    Log.i("Mohamed", "getError}")

                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("Mohamed", " error?" + e.printStackTrace())

            }
        }
    }
    override fun onCleared() {
        super.onCleared()
    }

}