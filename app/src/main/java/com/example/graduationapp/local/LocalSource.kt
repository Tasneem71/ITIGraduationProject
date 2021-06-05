package com.example.graduationapp.local

import android.app.Application
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.mohamedabdallah.weather.local.FavoriteDatabase

class LocalSource (application:Application) {


    private val database: FavoriteDatabase = FavoriteDatabase.getInstance(application)

    suspend fun addToFavorite(item: Favorite){
        database.dao.addToFavorite(item)
    }

    suspend fun deleteFromFavorite(item: Favorite){
        database.dao.deleteFromFavorite(item)
    }

    suspend fun deleteFromFavorite(id: Long){
        database.dao.deleteFromFavorite(id)
    }

    suspend fun getAllFavorite(userId: String): List<Favorite>?{
        return database.dao.getAllFavorite(userId)
    }

    suspend fun isFavorite(id: Long,userId: String): Int{
        return database.dao.isFavorite(id,userId)
    }

    suspend fun getAllCart(userId: String): List<Favorite>?{
        return database.dao.getAllCart(userId)
    }
    suspend fun updateCount( id: Long,count: Int,userId: String){
        database.dao.updateCount(id,count,userId)
    }

}