package com.example.graduationapp.graphql

import com.apollographql.apollo.ApolloClient
import com.example.graduationapp.remote.retro.BasicAuthInterceptor
import okhttp3.OkHttpClient

object MyApolloClient {

    private const val SERVER_URL = "https://mohamedabdallah.myshopify.com/admin/api/2021-04/graphql"

    private val client = OkHttpClient.Builder().addInterceptor(
        BasicAuthInterceptor(
            "043be955a8db3bd91f8a910a6b5c7df8",
            "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"
        )
    ).build()

    fun getApolloClient(): ApolloClient {
        return ApolloClient.builder().serverUrl(SERVER_URL).okHttpClient(client).build()
    }

}