package com.example.graduationapp.graphql

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.apollographql.apollo.api.Operation
import com.example.domain.core.feature.favoriteFeature.Favorite
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
import com.example.graduationapp.utils.Validation
import com.facebook.internal.Mutable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.w3c.dom.Node

class GraphViewModel (application: Application) : AndroidViewModel(application) {

    var adidas = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var nike = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var puma = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var converse = MutableLiveData<List<HomeCollectionQuery.Edge1>>()
    var asicsTiger = MutableLiveData<List<HomeCollectionQuery.Edge1>>()

    var men = MutableLiveData<List<GetProductsByCollectionIDQuery.Edge>>()
    var women = MutableLiveData<List<GetProductsByCollectionIDQuery.Edge>>()
    var kid = MutableLiveData<List<GetProductsByCollectionIDQuery.Edge>>()
    var sale = MutableLiveData<List<GetProductsByCollectionIDQuery.Edge>>()
    var home = MutableLiveData<List<GetProductsByCollectionIDQuery.Edge>>()

    var network =MutableLiveData<Boolean>()


    var graphRepo: GraphRepo = GraphRepo(application)
    var cartCount = MutableLiveData<Int>()


    fun addToFavorite(item: Favorite){
        viewModelScope.launch {
            graphRepo.local.addToFavorite(item)
        }
    }

    fun deleteFromFavorite(id: Long){
        viewModelScope.launch {
            graphRepo.local.deleteFromFavorite(id)
        }
    }

   suspend fun deleteFromCart(id: Long,userId: String){
            graphRepo.local.deleteFromCart(id,userId)
    }

    suspend fun isFavorite(id: Long,userId: String):Int{
        return viewModelScope.async {
            graphRepo.local.isFavorite(id,userId)
        }.await()
    }

    suspend fun isCart(id: Long,userId: String):Int{
        return viewModelScope.async {
            graphRepo.local.isCart(id,userId)
        }.await()
    }

    fun cartCount(userId: String){
        viewModelScope.launch {
            val result=graphRepo.local.getCartCount(userId)
            cartCount.value=result
            Log.i("cart",""+result)
        }
    }

    suspend fun addToCart(item: Favorite){

            graphRepo.local.addToFavorite(item)

    }


    fun getCollection(id:String , num:Int){
        if(Validation.isOnline(getApplication())){
        viewModelScope.launch {
            val response=graphRepo.suspendQuery(GetProductsByCollectionIDQuery(id)).data()
            Log.i("One", "getCollection: ${response?.collection?.products?.edges?.get(0)?.node?.id}")
            Log.i("One", "getCollection: ${response?.collection?.products?.edges?.get(0)?.node?.handle}")
            Log.i("One", "getCollection: ${response?.collection?.products?.edges?.get(0)?.node?.title}")

            when (num) {
                0 -> home.value=response?.collection?.products?.edges
                1 -> kid.value=response?.collection?.products?.edges
                2 -> men.value=response?.collection?.products?.edges
                3 -> sale.value=response?.collection?.products?.edges
                4 -> women.value=response?.collection?.products?.edges
            }
        }
        }else{
            network.postValue(Validation.isOnline(getApplication()))
        }
    }
    fun getCollectionData(){
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

    fun checkNetwork(){
        network.postValue(Validation.isOnline(getApplication()))
    }

}
