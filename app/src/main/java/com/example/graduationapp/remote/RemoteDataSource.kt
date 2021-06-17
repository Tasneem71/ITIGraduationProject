package com.example.graduationapp.remote

import android.util.Log
import com.example.graduationapp.data.*
import com.example.graduationapp.data.orders.OrderAPI
import com.example.graduationapp.data.priceRules.CreatedDiscount
import com.example.graduationapp.data.priceRules.DiscountCode
import com.example.graduationapp.remote.retro.ApiServes
import retrofit2.Response

class RemoteDataSource :DefaultRemote {


    override suspend fun fetchAllCustomerData(): Response<ApiCustomers> {

        val response = ApiServes.shopfiyService.getAllCustomer()
        return response

    }

    override suspend fun createCustomer(customerJson: CreatedCustomer): ApiCustomers? {

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


    override suspend fun fetchOpenOrders(id:String): OrderAPI? {

        val response = ApiServes.shopfiyService.getOpenOrders(id)
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


    override suspend fun fetchOrderedProducts(id:String): ProductDetails? {

        val response = ApiServes.shopfiyService.getProductDetails(id)
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

    override suspend fun createOrder(orderJson: CreatedOrder): OrderAPI? {
        Log.i("order","  orderrrrrr"+ orderJson)
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

    override suspend fun cancelOrder(id : String, orderJson: CancelOrder): OrderAPI? {
        Log.i("order","  orderrrrrrCancel"+ orderJson)
        val response = ApiServes.shopfiyService.cancelOrder(id,orderJson)
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


    override suspend fun getAllProducts(): CollectionProducts? {

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
    override suspend fun getCustomerAddress(id:String): List<Addresse?>? {

        val response = ApiServes.shopfiyService.getFirstCustomerAdd(id)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("Menna", "getCustomeraddress success")
                    return it.allAddressList
                }
            } else {
                Log.i("Menna", "not success")

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Menna", " error?" + e.printStackTrace())

        }
        return null
    }

    override suspend fun createCustomerAdd(id:String, addressJson: CreateAddress): AddressData? {
        val response = ApiServes.shopfiyService.createNewCustomerAddById(id,addressJson)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("Menna", "response Add cusstomer succcess**" + it)
                    //SharedPref.haveOneAddress(true)
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
    override suspend fun editCustomerAdd(id:String, addressIP:String, addressJson: CreateAddress): AddressData? {

        val response = ApiServes.shopfiyService.editCustomerAdd(id,addressIP,addressJson)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("Menna", "response  EdiiiitCustomerAdd succcess")
                    return it
                }
            } else {
                Log.i("Menna", "response Ediit failuer ------------ " + response.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Memma", " error?" + e.printStackTrace())

        }
        return null

    }
    override suspend fun setDefaultAddress(id:String, addressIP:String): AddressData? {

        val response = ApiServes.shopfiyService.setDefaultAddress(id,addressIP)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("Menna", "response  setDefaultAddress succcess")
                    return it
                }
            } else {
                Log.i("Menna", "response setDefaultAddress failuer ------------ " + response.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Memma", " error?" + e.printStackTrace())

        }
        return null

    }
    override suspend fun getDefaultAddress(id:String, addressIP:String): AddressData? {

        val response = ApiServes.shopfiyService.getCustomerAddById(id,addressIP)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("Menna", "response  getDefaultAddress succcess")
                    return it
                }
            } else {
                Log.i("Menna", "response getDefaultAddress failuer ------------ " + response.errorBody().toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Memma", " error?" + e.printStackTrace())

        }
        return null

    }
    //cant delete defult
    override suspend fun deleteAddress(id:String, addressIP:String) {


        ApiServes.shopfiyService.deleteAddress(id,addressIP)

//        val response = ApiServes.shopfiyService.deleteAddress(id,addressIP)
//        try {
//            if (response.isSuccessful) {
//                response.body()?.let {
//                    Log.i("Menna", "response  deleteAddress succcess")
//                }
//            } else {
//                Log.i("Menna", "response deleteAddress failuer ------------ " + response.errorBody().toString())
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Log.i("Memma", " error?" + e.printStackTrace())
//
//        }

    }
    override suspend fun getAllCustomerAddress(id:String): List<Addresse?>? {

        val response = ApiServes.shopfiyService.getAllCustomerAddress(id)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("Menna", "getAllCustomerAddress success")
                    return it.allAddressList
                }
            } else {
                Log.i("Menna", "getAllCustomerAddress not success")

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Menna", "getAllCustomerAddress error?" + e.printStackTrace())

        }
        return null
    }


    override suspend fun getCustomerByEmail(email:String): ApiCustomers? {

        val response = ApiServes.shopfiyService.getCustomerByEmail(email)
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


    override suspend fun generateDiscount(priceRule:String, discount: CreatedDiscount): DiscountCode? {

        Log.i("Tasneem", "response$priceRule , $discount")

        val response = ApiServes.shopfiyService.generatingDiscount(priceRule,discount)
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("Tasneem", "response$it")
                    return it
                }
            } else {
                Log.i("Tasneem", "response failuer" + response.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Tasneem", " error?" + e.printStackTrace())

        }
        return null

    }

    override suspend fun getDiscount10(): DiscountCode? {


        val response = ApiServes.shopfiyService.getDiscount10()
        try {
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.i("Tasneem", "response$it")
                    return it
                }
            } else {
                Log.i("Tasneem", "response failuer" + response.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("Tasneem", " error?" + e.printStackTrace())

        }
        return null

    }




}