package com.example.graduationapp.graphql

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.apollographql.apollo.api.Operation
import com.example.graduationapp.GetProductsQuery
import com.example.graduationapp.HomeCollectionQuery
import com.example.graduationapp.data.ApiCollections
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.data.Customers
import com.example.graduationapp.data.priceRules.CreatedDiscount
import com.example.graduationapp.data.priceRules.DiscountCode
import com.example.graduationapp.data.priceRules.DiscountCodeClass
import com.example.graduationapp.remote.ApiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Node

class GraphViewModel (application: Application) : AndroidViewModel(application) {


    var adidas = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var nike = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var puma = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var converse = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var asicsTiger = MutableLiveData<List<HomeCollectionQuery.Edge1>>()

    var graphRepo: GraphRepo = GraphRepo(application)

    init {
        getCollectionData()
        Log.i("tasneem","init ")
    }


    private fun getCollectionData(){
        viewModelScope.launch {
            try {
                val response =
                    graphRepo.suspendQuery(HomeCollectionQuery()).data()
                val error =
                    graphRepo.suspendQuery(HomeCollectionQuery()).errors()
                val hasError =
                    graphRepo.suspendQuery(HomeCollectionQuery()).hasErrors()


                adidas.value = filterCollection("adidas",response!!)
                nike.value = filterCollection("nike",response!!)
                puma.value = filterCollection("puma",response!!)
                converse.value = filterCollection("converse",response!!)
                asicsTiger.value = filterCollection("asics-tiger",response!!)
                Log.i("tasneem",""+ adidas.value as Any?)
                Log.i("tasneem","error "+ error)
                Log.i("tasneem",""+ hasError)
                Log.i("tasneem","response "+ response)
                Log.i("tasneem","whatever ")
            } catch (e: Exception) {
                e.printStackTrace()
            }finally {
                // do
            }
        }
    }
    private fun filterCollection(collectionKey:String, collections : HomeCollectionQuery.Data ) : List<HomeCollectionQuery.Edge1>
    {
        val oneCollection =collections.collections.edges.filter {
            it.node?.handle ==collectionKey
        }
        return oneCollection.get(0).node.products.edges
    }

}
