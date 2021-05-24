package com.example.graduationapp.ui.test

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.graduationapp.data.ApiCollections
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.remote.ApiRepository
import java.text.SimpleDateFormat
import java.util.*


class testActivityVM (application: Application) : AndroidViewModel(application) {

    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository()
    }

    fun loadData(context: Context): MutableLiveData<ApiCollections> {
        Log.i("Tasneem","inside the load")
        apiRepository.fetchCustomCollectionData(context)
        Log.i("Tasneem","after")
        return apiRepository.apiCollection
    }


    fun loadProductData(id:String): MutableLiveData<CollectionProducts> {
        Log.i("Tasneem","inside the load")
        apiRepository.fetchProductsData(id)
        Log.i("Tasneem","after")
        return apiRepository.apiproduct
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
