package com.example.graduationapp.remote.retro

import com.example.graduationapp.data.ApiCollections
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ShopfiyApi {

    @GET("custom_collections.json")
    suspend fun getCustomCollections(): Response<ApiCollections>

    @GET("admin/api/2021-04/smart_collections.json")
    suspend fun getSmartCollections(): Response<ApiCollections>

}