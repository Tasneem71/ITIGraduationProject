package com.example.graduationapp.remote.retro

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServes {

    private const val BASE_URL = "https://043be955a8db3bd91f8a910a6b5c7df8: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@mohamedabdallah.myshopify.com/admin/api/2021-04/"
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiService: ShopfiyApi = getRetrofit()
        .create(ShopfiyApi::class.java)


}