package com.example.graduationapp.remote.retro

import com.example.graduationapp.data.*
import retrofit2.Call
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
    suspend fun getProductById(@Path("product_id") id:String): Response<Products>

    @GET("products.json")
    suspend fun getAllProduct(): Response<CollectionProducts>

    @GET("customers.json")
    suspend fun getAllCustomer(): Response<ApiCustomers>

    @POST("customers.json")
    suspend fun createCustomer(@Body customerJson:CreatedCustomer): Response<Customers>//what is the response?

    @DELETE("customers/{customers_id}.json")
            suspend fun deleteCustomer(@Path("customers_id") id:String): Response<String>//what is the response?

}