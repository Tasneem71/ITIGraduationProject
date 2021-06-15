package com.example.graduationapp.local
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.domain.core.feature.favoriteFeature.Favorite


@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(item: Favorite)

    @Delete
    suspend fun deleteFromFavorite(item: Favorite)

    @Query("delete from Favorite where page = 67 and userId= :userId ")
    suspend fun deleteListFromCart(userId: String)

    @Query("delete from Favorite where id is :id and userId= :userId")
    suspend fun deleteFromCart(id: Long, userId:String)

    @Query("select * from Favorite where page = 70 and userId= :userId")
    suspend fun getAllFavorite(userId: String): List<Favorite>?

    @Query("SELECT COUNT() FROM Favorite WHERE id = :id And page = 70 and userId= :userId")
    suspend fun isFavorite(id: Long,userId: String): Int

    @Query("SELECT COUNT() FROM Favorite WHERE id = :id And page = 67 and userId= :userId")
    suspend fun isCart(id: Long,userId: String): Int

    @Query("select * from Favorite where page = 67 and userId= :userId")
    suspend fun getAllCart(userId: String): List<Favorite>?

    @Query("UPDATE Favorite SET count=:count WHERE id = :id and userId= :userId")
    fun updateCount( id: Long,count: Int,userId: String)

    @Query("UPDATE Favorite SET page= 67 WHERE id = :id and userId= :userId")
    fun moveToCart( id: Long,userId: String)

    @Query("SELECT COUNT(userId) FROM favorite where page = 67 and userId= :userId ")
    fun cartCount(userId:String): Int

}