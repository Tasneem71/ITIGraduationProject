package com.example.graduationapp.remote.retro

import com.example.graduationapp.data.ApiCollections
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.data.ProductDetails
import com.example.graduationapp.data.Products
import com.example.graduationapp.data.*
import retrofit2.Response
import retrofit2.http.*


interface ShopfiyApi {

    @GET("custom_collections.json")
    suspend fun getCustomCollections(): Response<ApiCollections>

    @GET("smart_collections.json")
    suspend fun getSmartCollections(): Response<ApiCollections>

    @GET("collections/{collection_id}/products.json")
    suspend fun getProductFromCollection(@Path("collection_id") id:String): Response<CollectionProducts>


    @GET("products/{product_id}.json")
    suspend fun getProductDetails(@Path("product_id") id:String): Response<ProductDetails>


    @GET("products/{product_id}.json")
    suspend fun getProductById(@Path("product_id") id:String): Response<Products>

    @GET("products.json")
    suspend fun getAllProduct(): Response<CollectionProducts>

    @GET("customers.json")
    suspend fun getAllCustomer(): Response<ApiCustomers>

    @POST("customers.json")
    suspend fun createCustomer(@Body customerJson:CreatedCustomer): Response<Customers>//what is the response?

    @DELETE("customers/{customers_id}.json")
    suspend fun deleteCustomer(@Path("customers_id") id:String): Response<String>//what is the response?


    @POST("orders.json")
    suspend fun createOrder(@Body orderJson:CreatedOrder): Response<OrdersItem>//what is the response?

    @GET("orders.json")
    suspend fun getAllOrder(): Response<Orders>

    @DELETE("orders/{order_id}.json")
    suspend fun deleteOrder(@Path("order_id") id:String): Response<String>//what is the response?

    //address
    @GET("customers/{customer_id}/addresses.json")
    suspend fun getCustomerAddById(@Path("customer_id") id:String): Response<AddressData>

    @POST("customers/{customer_id}/addresses.json")
    suspend fun createNewCustomerAddById(@Path("customer_id") id:String,@Body addressJson:CreateAddress): Response<AddressData>

    @PUT("customers/{customer_id}/addresses/{address_id}.json")
    suspend fun editCustomerAdd(@Path("customer_id") id:String,@Path("address_id") addId:String,@Body addressJson:CreateAddress): Response<AddressData>
}