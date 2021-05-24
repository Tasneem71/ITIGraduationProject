package com.example.graduationapp.remote

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.graduationapp.data.ApiCollections
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.remote.retro.ApiServes
import kotlinx.coroutines.*

class ApiRepository {
//    var localDataSource: LocalDataSource
    var apiCollection= MutableLiveData<ApiCollections>()
    var apiproduct= MutableLiveData<CollectionProducts>()



    fun fetchCustomCollectionData(context: Context){
        //if (isOnline(context)) {
        Log.i("Tasneem","in fetch")
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("Tasneem","in coruotine")
                val response = ApiServes.shopfiyService.getCustomCollections()
            Log.i("Tasneem","in coruotine responce")
                try {
                    Log.i("Tasneem","in try")
                    if (response.isSuccessful) {
                        response.body()?.let {
                            apiCollection.postValue(it)
                            Log.i("Tasneem","after post")
                        }
                    }else {
                        Log.i("Tasneem","response failuer"+response.errorBody().toString())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i("Tasneem"," error?"+e.printStackTrace())

                }
            }
        //}
    }


    fun fetchProductsData(id:String){
        //if (isOnline(context)) {
        Log.i("Tasneem","in fetch")
        CoroutineScope(Dispatchers.IO).launch {
            Log.i("Tasneem","in coruotine")
            val response = ApiServes.shopfiyService.getProductFromCollection(id)
            Log.i("Tasneem","in coruotine responce")
            try {
                Log.i("Tasneem","in try")
                if (response.isSuccessful) {
                    response.body()?.let {
                        apiproduct.postValue(it)
                        Log.i("Tasneem","after post")
                    }
                }else {
                    Log.i("Tasneem","response failuer"+response.errorBody().toString())
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("Tasneem"," error?"+e.printStackTrace())

            }
        }
        //}
    }









}