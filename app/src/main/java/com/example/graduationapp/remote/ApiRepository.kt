package com.example.graduationapp.remote

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.graduationapp.data.ApiCollections
import com.example.graduationapp.remote.retro.ApiServes
import kotlinx.coroutines.*

class ApiRepository {
//    var localDataSource: LocalDataSource
    var apiService: ApiServes
    var apiCollection= MutableLiveData<ApiCollections>()

    constructor(application: Application) {
        //localDataSource = LocalDataSource(application)
        apiService = ApiServes
    }



    fun fetchCustomCollectionData(context: Context){
        if (isOnline(context)) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiService.apiService.getCustomCollections()
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
                }
            }
        }
    }


    fun isOnline(context: Context): Boolean {
        val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }




}