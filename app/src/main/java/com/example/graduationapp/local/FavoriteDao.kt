package com.example.graduationapp.local
import androidx.room.*
import com.example.domain.core.favoriteFeature.Favorite


@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(item: Favorite)

    @Delete
    suspend fun deleteFromFavorite(item: Favorite)

    @Query("delete from Favorite where id is :id")
    suspend fun deleteFromFavorite(id: Long)

    @Query("select * from Favorite ")
    suspend fun getAllFavorite(): List<Favorite>?

    @Query("SELECT COUNT() FROM Favorite WHERE id = :id")
    suspend fun isFavorite(id: Long): Int
}