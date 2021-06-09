package com.example.graduationapp.graphql

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.graduationapp.GetProductsQuery
import com.example.graduationapp.R
import com.example.graduationapp.remote.retro.ApiServes
import com.example.graduationapp.type.CustomType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.IOException


class GraphQlProducts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_ql_products)


        var client = MyApolloClient.getApolloClient()

        GlobalScope.launch {
            try {
                val response =
                    client.suspendQuery(GetProductsQuery()).data()
                val error =
                    client.suspendQuery(GetProductsQuery()).errors()
                var x = response?.products?.edges?.get(0)?.node?.tags
                var d = response?.products?.edges?.get(0)?.node?.featuredImage?.originalSrc

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}