package com.example.graduationapp.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel (application: Application,repo :DefaultRepo) : AndroidViewModel(application) {
    var getAllProductsLiveData = MutableLiveData<CollectionProducts?>()
    var network =MutableLiveData<Boolean>()
    var apiRepository: DefaultRepo=repo


//    init {
//        getAllProducts()
//    }


    fun getAllProducts() {
            CoroutineScope(Dispatchers.IO).launch {
                val response=apiRepository.getAllProducts()
                getAllProductsLiveData.postValue(response)
            }
    }
}

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(val application: Application,val repo: DefaultRepo): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(application,repo) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }


    }
}
