package com.example.graduationapp.graphql

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.coroutines.toDeferred


suspend fun <D : Operation.Data, T, V : Operation.Variables> ApolloClient.suspendQuery(query: com.apollographql.apollo.api.Query<D, T, V>): com.apollographql.apollo.api.Response<T> =
    query(query).toDeferred().await()

suspend fun <D : Operation.Data, T, V : Operation.Variables> ApolloClient.suspendMutate(mutation: Mutation<D, T, V>): com.apollographql.apollo.api.Response<T> =
    mutate(mutation).toDeferred().await()