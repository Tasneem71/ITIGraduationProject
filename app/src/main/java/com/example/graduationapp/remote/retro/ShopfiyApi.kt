package com.example.graduationapp.remote.retro

import com.example.graduationapp.data.ApiCollections
import com.example.graduationapp.data.CollectionProducts
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ShopfiyApi {

    @GET("custom_collections.json")
    suspend fun getCustomCollections(): Response<ApiCollections>

    @GET("smart_collections.json")
    suspend fun getSmartCollections(): Response<ApiCollections>

    @GET("collections/{collection_id}/products.json")
    suspend fun getProductFromCollection(@Path("collection_id") id:String): Response<CollectionProducts>

}