package com.example.graduationapp.graphql

import android.net.ParseException
import androidx.core.net.toUri
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.response.CustomTypeAdapter
import com.apollographql.apollo.response.CustomTypeValue
import com.apollographql.apollo.response.CustomTypeValue.GraphQLString
import com.example.graduationapp.remote.retro.BasicAuthInterceptor
import com.example.graduationapp.type.CustomType
import com.google.zxing.client.result.URLTOResultParser
import okhttp3.OkHttpClient
import retrofit2.http.Url
import java.net.URL
import java.net.URLConnection
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


object MyApolloClient {

    private const val SERVER_URL = "https://mohamedabdallah.myshopify.com/admin/api/2021-04/graphql"

    private val client = OkHttpClient.Builder().addInterceptor(
        BasicAuthInterceptor(
            "043be955a8db3bd91f8a910a6b5c7df8",
            "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"
        )
    ).build()

    fun getApolloClient(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl(SERVER_URL)
            .okHttpClient(client)
            //.addCustomTypeAdapter(CustomType.URL, urlCustomAdapter)
                // URL is Generated by Apollo
            .build()
    }

}