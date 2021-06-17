package com.example.graduationapp.remote

import android.util.Log
import com.example.graduationapp.data.*
import com.example.graduationapp.data.orders.OrderAPI
import com.example.graduationapp.data.priceRules.CreatedDiscount
import com.example.graduationapp.data.priceRules.DiscountCode
import com.example.graduationapp.remote.retro.ApiServes
import retrofit2.Response

interface DefaultRemote {


     suspend fun fetchAllCustomerData(): Response<ApiCustomers>

     suspend fun createCustomer(customerJson: CreatedCustomer): ApiCustomers?


     suspend fun fetchOpenOrders(id:String): OrderAPI?


     suspend fun fetchOrderedProducts(id:String): ProductDetails?

     suspend fun createOrder(orderJson: CreatedOrder): OrderAPI?

     suspend fun cancelOrder(id : String, orderJson: CancelOrder): OrderAPI?


     suspend fun getAllProducts(): CollectionProducts?

    //*************
     suspend fun getCustomerAddress(id:String): List<Addresse?>?

     suspend fun createCustomerAdd(id:String, addressJson: CreateAddress): AddressData?
     suspend fun editCustomerAdd(id:String, addressIP:String, addressJson: CreateAddress): AddressData?
     suspend fun setDefaultAddress(id:String, addressIP:String): AddressData?
     suspend fun getDefaultAddress(id:String, addressIP:String): AddressData?
    //cant delete defult
     suspend fun deleteAddress(id:String, addressIP:String)
     suspend fun getAllCustomerAddress(id:String): List<Addresse?>?


     suspend fun getCustomerByEmail(email:String): ApiCustomers?


     suspend fun generateDiscount(priceRule:String, discount: CreatedDiscount): DiscountCode?

     suspend fun getDiscount10(): DiscountCode?


}