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
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel (application: Application) : AndroidViewModel(application) {

    var apiRepository: ApiRepository
    var network =MutableLiveData<Boolean>()

    init{
        apiRepository = ApiRepository(application)
    }

    fun loadData(context: Context): MutableLiveData<ApiCollections> {
        if (Validation.isOnline(getApplication())) {
            Log.i("Tasneem", "inside the load")
            CoroutineScope(Dispatchers.IO).launch {
                apiRepository.fetchCustomCollectionData()
            }
        }
        else{
            network.postValue(false)
        }

        Log.i("Tasneem","after")
        return apiRepository.apiCollection
    }


    fun loadProductData(id:String): MutableLiveData<CollectionProducts> {
        Log.i("Tasneem","inside the load")
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                apiRepository.fetchProductsData(id)
            }
        }
        else{
            network.postValue(false)
        }
        //Log.i("Tasneem","after")
        return apiRepository.apiproduct
    }

}
