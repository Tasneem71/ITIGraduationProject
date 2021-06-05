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
    suspend fun deleteListFromCart(){
      database.dao.deleteListFromCart()
    }


    suspend fun deleteFromFavorite(id: Long){
        database.dao.deleteFromFavorite(id)
    }

    suspend fun getAllFavorite(): List<Favorite>?{
        return database.dao.getAllFavorite()
    }

    suspend fun isFavorite(id: Long): Int{
        return database.dao.isFavorite(id)
    }

    suspend fun getAllCart(): List<Favorite>?{
        return database.dao.getAllCart()
    }
    suspend fun updateCount( id: Long,count: Int){
        database.dao.updateCount(id,count)
    }

}