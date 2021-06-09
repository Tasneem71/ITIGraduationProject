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
                val response = MyApolloClient.getApolloClient().suspendQuery(GetProductsQuery()).data()
                var x= response?.products?.edges?.get(0)?.node?.tags
                var d= response?.products?.edges?.get(0)?.node?.featuredImage?.originalSrc

            }catch (e: Exception) {
                e.printStackTrace()
            }
        }


//        client.query(GetProductsQuery.builder().build()).enqueue(
//            object : ApolloCall.Callback<GetProductsQuery.Data>()
//            {
//                override fun onFailure(e: ApolloException) {
//                    Log.i("GraphQl", "onFailure: ${e.localizedMessage}")
//                }
//                override fun onResponse(response: Response<GetProductsQuery.Data>) {
//                    Log.i("GraphQl", "onResponse: ${response.data().toString()} ")
//                }
//
//            }
//        )


    }

    /*
    * val client = ApolloClient.builder()
            .serverUrl("https://example.com/graphql")
            .okHttpClient(                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        chain.proceed(
                            chain.request().newBuilder()
                                .addHeader("Authorization", "Basic cnllYnJ5ZTpiVarArsVzMTIz")
                                .build()
                        )
                    }
                    .build()
            )
            .build()

        client.query()

        val apolloClient  =ApolloClient.builder().serverUrl("https://043be955a8db3bd91f8a910a6b5c7df8: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@mohamedabdallah.myshopify.com/admin/").build()
        apolloClient.query(GetProductsQuery.builder().build()).enqueue(
           object : ApolloCall.Callback<GetProductsQuery.Data>()
           {
               override fun onFailure(e: ApolloException) {
                   Log.i("GraphQl", "onFailure: ${e.localizedMessage}")
               }
               override fun onResponse(response: Response<GetProductsQuery.Data>) {
                   Log.i("GraphQl", "onResponse: ${response.data().toString()} ")
               }

           }
        )
    }
    *
    * */
}