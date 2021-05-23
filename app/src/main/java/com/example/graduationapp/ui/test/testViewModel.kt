package com.example.graduationapp.ui.test

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.graduationapp.data.ApiCollections
import com.example.graduationapp.remote.ApiRepository
import java.text.SimpleDateFormat
import java.util.*


class testActivityVM (application: Application) : AndroidViewModel(application) {

    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository(application)
    }

    fun loadData(context: Context): MutableLiveData<ApiCollections> {
        Log.i("Tasneem","inside the load")
        apiRepository.fetchCustomCollectionData(context)
        Log.i("Tasneem","after")
        return apiRepository.apiCollection
    }





}
