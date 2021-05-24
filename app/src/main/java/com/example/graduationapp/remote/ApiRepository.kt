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
    var apiCollection = MutableLiveData<ApiCollections>()
    var apiproduct = MutableLiveData<CollectionProducts>()


    suspend fun fetchCustomCollectionData(context: Context) {

        val response = ApiServes.shopfiyService.getCustomCollections()
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    apiCollection.postValue(it)
                }
            } else {
                Log.i("Tasneem", "response failuer" + response.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Tasneem", " error?" + e.printStackTrace())

        }

        //}
    }


    suspend fun fetchProductsData(id: String) {
        //if (isOnline(context)) {
        val response = ApiServes.shopfiyService.getProductFromCollection(id)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    apiproduct.postValue(it)
                }
            } else {
                Log.i("Tasneem", "response failuer" + response.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }

        //}
    }


}