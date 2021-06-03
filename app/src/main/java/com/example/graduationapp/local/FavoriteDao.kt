package com.example.graduationapp.local
import androidx.room.*
import com.example.domain.core.feature.favoriteFeature.Favorite


@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(item: Favorite)

    @Delete
    suspend fun deleteFromFavorite(item: Favorite)

    @Query("delete from Favorite where id is :id")
    suspend fun deleteFromFavorite(id: Long)

    @Query("select * from Favorite where page = 70")
    suspend fun getAllFavorite(): List<Favorite>?

    @Query("SELECT COUNT() FROM Favorite WHERE id = :id")
    suspend fun isFavorite(id: Long): Int

    @Query("select * from Favorite where page = 67")
    suspend fun getAllCart(): List<Favorite>?

    @Query("UPDATE Favorite SET count=:count WHERE id = :id")
    fun updateCount( id: Long,count: Int)
}