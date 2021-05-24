package com.example.graduationapp.remote.retro

import android.R.attr.password
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiServes {

    private const val BASE_URL = "https://itiana.myshopify.com//admin/api/2021-04/"

    var client = OkHttpClient.Builder().addInterceptor(BasicAuthInterceptor("ce751b18c7156bf720ea405ad19614f4",
        "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")).build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val shopfiyService: ShopfiyApi = getRetrofit()
        .create(ShopfiyApi::class.java)


}