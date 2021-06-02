package com.example.graduationapp.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.graduationapp.data.*
import com.example.graduationapp.remote.retro.ApiServes
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

    suspend fun fetchSmartProductsData(id: String, num: Int) {
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


    suspend fun fetchAllOrders(): Response<Orders> {

        val response = ApiServes.shopfiyService.getAllOrder()
        return response

    }

    suspend fun createOrder(orderJson: CreatedOrder): OrdersItem? {

        val response = ApiServes.shopfiyService.createOrder(orderJson)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("order", "response" + it)
                    return it
                }
            } else {
                Log.i("order", "response failuer" + response.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("order", " error?" + e.printStackTrace())
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

    //*************
    suspend fun getCustomerAddress(id:String):AddressData? {

        val response = ApiServes.shopfiyService.getCustomerAddById(id)
        try {
            if (response.isSuccessful) {
                Log.i("Menna", "getCustomerAddById: enteeereeed")
                response.body()?.let {
                    Log.i("Menna", "getCustomerAddById: Donnnnnnnnnnnnnnnne")
                    return it

                }
                Log.i("Menna", "getCustomerAddById: dddddddddddddd")
            } else {
                Log.i("Menna", "not success")

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Menna", " error?" + e.printStackTrace())

        }
        return null
    }

    suspend fun createCustomerAdd(id:String,addressJson: CreateAddress): AddressData? {

        val response = ApiServes.shopfiyService.createNewCustomerAddById(id,addressJson)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("Menna", "response succcess****************" + it)
                    return it
                }
            } else {
                Log.i("Menna", "response failuer ************ " + response.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Memma", " error?" + e.printStackTrace())

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