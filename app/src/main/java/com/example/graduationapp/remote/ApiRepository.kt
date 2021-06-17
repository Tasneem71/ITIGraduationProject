package com.example.graduationapp.remote

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.data.*
import com.example.graduationapp.data.orders.OrderAPI
import com.example.graduationapp.data.priceRules.CreatedDiscount
import com.example.graduationapp.data.priceRules.DiscountCode
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.retro.ApiServes
import com.example.graduationapp.remote.retro.DefaultRepo
import retrofit2.Response

class ApiRepository(application: Application, var local :DefaultLocal , var remote: DefaultRemote) : DefaultRepo {

    override suspend fun getAllCart(userId: String): List<Favorite>? {
       return local.getAllCart(userId)
    }

    override suspend fun fetchAllCustomerData(): Response<ApiCustomers> {

        val response = remote.fetchAllCustomerData()
        return response

    }

    override suspend fun createCustomer(customerJson: CreatedCustomer): ApiCustomers? {

        return remote.createCustomer(customerJson)


    }


    override suspend fun fetchOpenOrders(id:String): OrderAPI? {

        return remote.fetchOpenOrders(id)

    }


    override suspend fun fetchOrderedProducts(id:String): ProductDetails? {

        return remote.fetchOrderedProducts(id)

    }

    override suspend fun createOrder(orderJson: CreatedOrder): OrderAPI? {
        return remote.createOrder(orderJson)

    }

    override suspend fun cancelOrder(id : String, orderJson: CancelOrder): OrderAPI? {
        Log.i("order","  orderrrrrrCancel"+ orderJson)
        return remote.cancelOrder(id,orderJson)

    }


    override suspend fun getAllProducts(): CollectionProducts? {

        return remote.getAllProducts()

    }

    //*************
    override suspend fun getCustomerAddress(id:String): List<Addresse?>? {

        return remote.getCustomerAddress(id)

    }

    override suspend fun createCustomerAdd(id:String, addressJson: CreateAddress): AddressData? {
        return remote.createCustomerAdd(id,addressJson)

    }
    override suspend fun editCustomerAdd(id:String, addressIP:String, addressJson: CreateAddress): AddressData? {

        return remote.editCustomerAdd(id,addressIP,addressJson)


    }
    override suspend fun setDefaultAddress(id:String, addressIP:String): AddressData? {

        return remote.setDefaultAddress(id,addressIP)

    }
    override suspend fun getDefaultAddress(id:String, addressIP:String): AddressData? {

        return remote.getDefaultAddress(id,addressIP)


    }
    //cant delete defult
    override suspend fun deleteAddress(id:String, addressIP:String) {


        remote.deleteAddress(id,addressIP)


    }
    override suspend fun getAllCustomerAddress(id:String): List<Addresse?>? {

        return remote.getAllCustomerAddress(id)

    }


    override suspend fun getCustomerByEmail(email:String): ApiCustomers? {

        return remote.getCustomerByEmail(email)

    }


    override suspend fun generateDiscount(priceRule:String, discount: CreatedDiscount): DiscountCode? {

        Log.i("Tasneem", "response$priceRule , $discount")

        return remote.generateDiscount(priceRule,discount)


    }

    override suspend fun getDiscount10(): DiscountCode? {
        return remote.getDiscount10()

    }

    override suspend fun getUpdatedCount(userId :String): Int {
        return local.getCartCount(userId)

    }


}




