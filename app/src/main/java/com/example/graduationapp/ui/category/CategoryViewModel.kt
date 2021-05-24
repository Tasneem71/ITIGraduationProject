package com.example.graduationapp.ui.category

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.graduationapp.data.ApiCollections
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.remote.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel (application: Application) : AndroidViewModel(application) {

    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository()
    }

    fun loadData(context: Context): MutableLiveData<ApiCollections> {
        Log.i("Tasneem","inside the load")
        CoroutineScope(Dispatchers.IO).launch {
            apiRepository.fetchCustomCollectionData(context)
        }

        Log.i("Tasneem","after")
        return apiRepository.apiCollection
    }


    fun loadProductData(id:String): MutableLiveData<CollectionProducts> {
        Log.i("Tasneem","inside the load")
        CoroutineScope(Dispatchers.IO).launch {
            apiRepository.fetchProductsData(id)
        }
        //Log.i("Tasneem","after")
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
