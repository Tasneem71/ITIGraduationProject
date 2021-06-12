package com.example.graduationapp.graphql

import android.app.Application
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.coroutines.toDeferred
import com.example.graduationapp.GetProductsQuery
import  com.apollographql.apollo.api.Response
import  com.apollographql.apollo.api.Query
import com.example.graduationapp.local.LocalSource
import java.lang.reflect.Type

class GraphRepo(application: Application) {

    var client = MyApolloClient.getApolloClient()
    val local = LocalSource(application)


    suspend fun <D : Operation.Data, T, V : Operation.Variables> suspendQuery(query : Query<D, T, V>): Response<T> {

        return client.suspendQuery(query)
    }

}