package com.example.graduationapp.local

import android.app.Application
import android.app.SharedElementCallback
import android.service.autofill.UserData
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.mohamedabdallah.weather.local.FavoriteDatabase

class LocalSource (application:Application) :DefaultLocal {


    private val database: FavoriteDatabase = FavoriteDatabase.getInstance(application)

    override suspend fun addToFavorite(item: Favorite){
        database.dao.addToFavorite(item)
    }

    override suspend fun deleteFromFavorite(item: Favorite){
        database.dao.deleteFromFavorite(item)
    }
    override suspend fun deleteListFromCart(id:String){
      database.dao.deleteListFromCart(id)
    }

    override suspend fun deleteFromCart(id:Long, userId: String){
        database.dao.deleteFromCart(id,userId)
    }


    override suspend fun deleteFromFavorite(id: Long, userId: String){
        database.dao.deleteFromCart(id,userId)
    }

    override suspend fun getAllFavorite(userId: String): List<Favorite>?{
        return database.dao.getAllFavorite(userId)
    }

    override suspend fun isFavorite(id: Long, userId: String): Int{
        return database.dao.isFavorite(id,userId)
    }

    override suspend fun isCart(id: Long, userId: String): Int{
        return database.dao.isCart(id,userId)
    }

    override suspend fun getCartCount(userId: String): Int{
        return database.dao.cartCount(userId)
    }

    override suspend fun getAllCart(userId: String): List<Favorite>?{
        return database.dao.getAllCart(userId)
    }

    override suspend fun moveToCart(id: Long, userId: String){
        database.dao.moveToCart(id,userId)
    }
    override suspend fun updateCount(id: Long, count: Int, userId: String){
        database.dao.updateCount(id,count,userId)
    }

}