package com.example.graduationapp.remote

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.graduationapp.data.*
import com.example.graduationapp.remote.retro.ApiServes
import kotlinx.coroutines.*
import retrofit2.Response

class ApiRepository {
    //    var localDataSource: LocalDataSource
    var apiCollection = MutableLiveData<ApiCollections>()
    var apiSmartCollection = MutableLiveData<ApiCollections>()
    var apiSmart1Collection = MutableLiveData<CollectionProducts>()
    var apiSmart2Collection = MutableLiveData<CollectionProducts>()
    var apiSmart3Collection = MutableLiveData<CollectionProducts>()
    var apiSmart4Collection = MutableLiveData<CollectionProducts>()
    var apiSmart5Collection = MutableLiveData<CollectionProducts>()
    var apiproduct = MutableLiveData<CollectionProducts>()


    suspend fun fetchCustomCollectionData() {

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

    }

    suspend fun fetchSmartCollectionData() {

        val response = ApiServes.shopfiyService.getSmartCollections()
        try {
            if (response.isSuccessful) {
                response.body()?.let {

                    apiSmartCollection.postValue(it)
                }
            } else {
                Log.i("Tasneem", "response failuer" + response.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Tasneem", " error?" + e.printStackTrace())

        }

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

    suspend fun fetchSmartProductsData(id: String,num: Int) {
        //if (isOnline(context)) {
        val response = ApiServes.shopfiyService.getProductFromCollection(id)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    when (num) {
                        0 -> apiSmart1Collection.postValue(it)
                        1 -> apiSmart2Collection.postValue(it)
                        2 -> apiSmart3Collection.postValue(it)
                        3 -> apiSmart4Collection.postValue(it)
                        4 -> apiSmart5Collection.postValue(it)
                    }
                }
            } else {
                Log.i("Tasneem", "response failuer" + response.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()

        }

        //}
    }

    suspend fun fetchAllCustomerData(): Response<ApiCustomers> {

        val response = ApiServes.shopfiyService.getAllCustomer()
        return response

    }

    suspend fun createCustomer(customerJson: CreatedCustomer): Customers? {

        val response = ApiServes.shopfiyService.createCustomer(customerJson)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("Tasneem", "response" + it)
                    return it
                }
            } else {
                Log.i("Tasneem", "response failuer" + response.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Tasneem", " error?" + e.printStackTrace())

        }
        return null

    }

    suspend fun getAllProducts(): CollectionProducts? {

        val response = ApiServes.shopfiyService.getAllProduct()
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("Tasneem", "response" + it)
                    return it
                }
            } else {
                Log.i("Tasneem", "response failuer" + response.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Tasneem", " error?" + e.printStackTrace())

        }
        return null

    }

}


/* try {
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
 */