package com.example.graduationapp.graphql

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.apollographql.apollo.api.Operation
import com.example.graduationapp.GetProductsByCollectionIDQuery

import com.example.graduationapp.GetProductsQuery
import com.example.graduationapp.HomeCollectionQuery
import com.example.graduationapp.data.ApiCollections
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.data.Customers
import com.example.graduationapp.data.priceRules.CreatedDiscount
import com.example.graduationapp.data.priceRules.DiscountCode
import com.example.graduationapp.data.priceRules.DiscountCodeClass
import com.example.graduationapp.remote.ApiRepository
import com.facebook.internal.Mutable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Node

class GraphViewModel (application: Application) : AndroidViewModel(application) {


    var map= MutableLiveData<HashMap<String,List<HomeCollectionQuery.Edge1>>>()

    var adidas = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var nike = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var puma = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var converse = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var asicsTiger = MutableLiveData<List<HomeCollectionQuery.Edge1>>()

    var men = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var women = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var kid = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var sale = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var home = MutableLiveData<List<HomeCollectionQuery.Edge1>>()

    var graphRepo: GraphRepo = GraphRepo(application)




    var byCollection = MutableLiveData<List<HomeCollectionQuery.Edge1>>()


    init {
        getCollectionData()
        getCollection()
    }



    private fun getCollection()
    {
        viewModelScope.launch {
            val response=graphRepo.suspendQuery(GetProductsByCollectionIDQuery("gid://shopify/Collection/267138760894")).data()
            Log.i("One", "getCollection: ${response?.collection?.products?.edges?.get(0)?.node?.id}")
            Log.i("One", "getCollection: ${response?.collection?.products?.edges?.get(0)?.node?.handle}")
            Log.i("One", "getCollection: ${response?.collection?.products?.edges?.get(0)?.node?.title}")


            byCollection.value=response?.collection?.products?.edges  as? List<HomeCollectionQuery.Edge1>
        }
    }
    private fun getCollectionData(){
        viewModelScope.launch {
            try {
                val response =
                    graphRepo.suspendQuery(HomeCollectionQuery()).data()
                val error =
                    graphRepo.suspendQuery(HomeCollectionQuery()).errors()
                val hasError =
                    graphRepo.suspendQuery(GetProductsQuery()).hasErrors()


                val x=response?.collections
                    ?.edges?.map {
                    it.node.handle
                }

                Log.i("ABCDE", "getCollectionData: $x")


                adidas.value = filterCollection("adidas",response!!)
                nike.value = filterCollection("nike",response!!)
                puma.value = filterCollection("puma",response!!)
                converse.value = filterCollection("converse",response!!)
                asicsTiger.value = filterCollection("asics-tiger",response!!)

                men.value = filterCollection("men",response!!)
                women.value = filterCollection("women",response!!)
                sale.value = filterCollection("sale",response!!)
                kid.value = filterCollection("kid",response!!)
               // home.value = filterCollection("home-page",response!!)
//
                Log.i("AAAA", "getCollectionData: ${men.value}")
                Log.i("AAAA", "getCollectionData: ${women.value}")
                Log.i("AAAA", "getCollectionData: ${kid.value}")
                Log.i("AAAA", "getCollectionData: ${sale.value}")


            } catch (e: Exception) {
                e.printStackTrace()
            }finally {
                // do
            }
        }
    }
    private fun filterCollection(collectionKey:String, collections : HomeCollectionQuery.Data )
            : List<HomeCollectionQuery.Edge1> {
        val oneCollection =collections.collections.edges.filter {
            it.node?.handle ==collectionKey
        }
        return oneCollection.get(0).node.products.edges
    }


}
