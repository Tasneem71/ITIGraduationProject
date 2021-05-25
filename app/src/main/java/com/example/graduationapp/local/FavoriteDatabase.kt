package com.mohamedabdallah.weather.local

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.domain.core.favoriteFeature.Favorite
import com.example.graduationapp.local.FavoriteDao

@Database(entities = [Favorite::class],version = 1,exportSchema = false)

abstract class FavoriteDatabase : RoomDatabase() {
    abstract val dao: FavoriteDao

    companion object {
        private var INSTANCE: FavoriteDatabase? = null

        fun getInstance(application: Application): FavoriteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        application,
                    FavoriteDatabase::class.java,
                        "favorite_db"
                )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}


