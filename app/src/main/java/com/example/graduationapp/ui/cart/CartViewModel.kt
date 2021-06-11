package com.example.graduationapp.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.local.LocalSource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val local = LocalSource(application)
    var carts : MutableLiveData<List<Favorite>>? = MutableLiveData<List<Favorite>>()
    var sumOfItems : MutableLiveData<Int> = MutableLiveData<Int>()
    var network =MutableLiveData<Boolean>()
    //var count : MutableLiveData<Int> = MutableLiveData<Int>()


    fun addToCart(item: Favorite){
        viewModelScope.launch {
            local.addToFavorite(item)
        }
    }
    fun getAllCarts(userId: String){
        var sumPrices :Int =0
        viewModelScope.launch {
            val result= async{local.getAllCart(userId)}
            result.join()
            carts?.value=result.await()
            for(item in carts?.value?.toList()!!){

                sumPrices += (item.price * item.count)
            }
            sumOfItems.postValue(sumPrices)
        }
    }

    fun updateCount(id:Long,count:Int,userId: String){
        viewModelScope.launch {
            local.updateCount(id,count,userId)
        }
    }
    fun deleteFromFavorite(item: Favorite){
        viewModelScope.launch {
            local.deleteFromFavorite(item)
        }
    }


}