package com.example.graduationapp.ui.favoriteFeature

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.local.LocalSource
import kotlinx.coroutines.*

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val local = LocalSource(application)
    var favorites :MutableLiveData<List<Favorite>>? = MutableLiveData<List<Favorite>>()

    fun addToFavorite(item: Favorite){
        viewModelScope.launch {
            local.addToFavorite(item)
        }
    }

    fun deleteFromFavorite(item: Favorite){
        viewModelScope.launch {
            local.deleteFromFavorite(item)
        }
    }

    fun deleteFromFavorite(id: Long){
        viewModelScope.launch {
            local.deleteFromFavorite(id)
        }
    }

     fun getAllFavorite(){
         viewModelScope.launch {
            val result= async{local.getAllFavorite()}
             result.join()
             favorites?.value=result.await()
         }
    }

     suspend fun isFavorite(id: Long):Int{
         return viewModelScope.async {
             local.isFavorite(id)
         }.await()
//         var isFavorite=false
//         viewModelScope.launch {
//             val res=async {
//                 local.isFavorite(id)
//             }
//             isFavorite = res.await()>0
//             Log.i("TAG", "isFavorite: $isFavorite")
//         }
//         return isFavorite
    }

    fun addToCart(item: Favorite){
        viewModelScope.launch {
            local.addToFavorite(item)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}