package com.example.graduationapp.ui.order

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.local.LocalSource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {

    private val local = LocalSource(application)
    var carts : MutableLiveData<List<Favorite>>? = MutableLiveData<List<Favorite>>()
    var sumOfItems : MutableLiveData<Int> = MutableLiveData<Int>()

    fun getAllCarts(){
        viewModelScope.launch {
            val result= async{local.getAllCart()}
            result.join()
            carts?.value=result.await()
        }
    }
    fun allPrice( list :List<Favorite>) {
        var sumPrices :Int =0
        for(item in list){
            sumPrices += item.price
        }
        sumOfItems.postValue(sumPrices)
    }
}