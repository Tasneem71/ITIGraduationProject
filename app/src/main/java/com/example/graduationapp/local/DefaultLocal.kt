package com.example.graduationapp.local

import com.example.domain.core.feature.favoriteFeature.Favorite

interface DefaultLocal {

    suspend fun addToFavorite(item: Favorite)

    suspend fun deleteFromFavorite(item: Favorite)
    suspend fun deleteListFromCart(id:String)

    suspend fun deleteFromCart(id:Long,userId: String)


    suspend fun deleteFromFavorite(id: Long,userId: String)

    suspend fun getAllFavorite(userId: String): List<Favorite>?

    suspend fun isFavorite(id: Long,userId: String): Int

    suspend fun isCart(id: Long,userId: String): Int

    suspend fun getCartCount(userId: String): Int

    suspend fun getAllCart(userId: String): List<Favorite>?

    suspend fun moveToCart( id: Long,userId: String)

    suspend fun updateCount( id: Long,count: Int,userId: String)


}