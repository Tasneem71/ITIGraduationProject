package com.example.graduationapp.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.core.favoriteFeature.Favorite
import com.example.graduationapp.local.LocalSource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val local = LocalSource(application)
    var carts : MutableLiveData<List<Favorite>>? = MutableLiveData<List<Favorite>>()
    var sumOfItems : MutableLiveData<Int> = MutableLiveData<Int>()
    //var count : MutableLiveData<Int> = MutableLiveData<Int>()


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

            sumPrices += (item.price * item.count)
        }
        sumOfItems.postValue(sumPrices)
    }
    fun updateCount(id:Long,count:Int){
        viewModelScope.launch {
            local.updateCount(id,count)
        }
    }
    fun deleteFromFavorite(item: Favorite){
        viewModelScope.launch {
            local.deleteFromFavorite(item)
        }
    }


}