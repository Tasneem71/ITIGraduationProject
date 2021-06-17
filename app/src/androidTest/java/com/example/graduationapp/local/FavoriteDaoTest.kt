package com.example.graduationapp.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.google.common.truth.Truth.assertThat
import com.mohamedabdallah.weather.local.FavoriteDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavoriteDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: FavoriteDatabase
    private lateinit var dao: FavoriteDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavoriteDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertFavoriteItem() = runBlockingTest {
        val favoriteItem = Favorite(1, "skirt", "handel", 100 ,"image",'F',5,"154","123456")
        dao.addToFavorite(favoriteItem)
        val allFavoriteItems = dao.getAllFavorite("123456")
        assertThat(allFavoriteItems).contains(favoriteItem)
    }
    @Test
    fun getAllCartItems() = runBlockingTest {
        val cartItem1 = Favorite(1, "skirt", "handel", 100 ,"image",'C',5,"154","123456")
        val cartItem2 = Favorite(2, "skirt", "handel", 10 ,"image",'C',1,"154","123456")
        val cartItem3 = Favorite(3, "skirt", "handel", 80 ,"image",'C',2,"154","123456")
        val favoriteItem4 = Favorite(4, "skirt", "handel", 80 ,"image",'F',2,"154","123456")

        dao.addToFavorite(cartItem1)
        dao.addToFavorite(cartItem2)
        dao.addToFavorite(cartItem3)
        dao.addToFavorite(favoriteItem4)

        //return 3 because the fourth is in favorite
        val allCartItems = dao.getAllCart("123456")
        assertThat(allCartItems?.size).isEqualTo(3)
    }
    fun getAllFavoriteItems() = runBlockingTest {
        val favoriteItem1 = Favorite(1, "skirt", "handel", 100 ,"image",'F',5,"154","123456")
        val favoriteItem2 = Favorite(2, "skirt", "handel", 10 ,"image",'F',1,"154","123456")
        val favoriteItem3 = Favorite(3, "skirt", "handel", 80 ,"image",'F',2,"154","123456")
        val cartItem4 = Favorite(4, "skirt", "handel", 80 ,"image",'C',2,"154","123456")

        dao.addToFavorite(favoriteItem1)
        dao.addToFavorite(favoriteItem2)
        dao.addToFavorite(favoriteItem3)
        dao.addToFavorite(cartItem4)

        //return 3 because the fourth is in cart
        val allCartItems = dao.getAllCart("123456")
        assertThat(allCartItems?.size).isEqualTo(3)
    }
    @Test
    fun deleteFromFavorite() = runBlockingTest {
        val favoriteItem = Favorite(1, "skirt", "handel", 100 ,"image",'F',5,"154","123456")
        dao.addToFavorite(favoriteItem)
        dao.deleteFromCart(1,"123456")
        val allFavoriteItems = dao.getAllFavorite("123456")
        assertThat(allFavoriteItems).doesNotContain(favoriteItem)
    }
    @Test
    fun deleteAllCartItems() = runBlockingTest {
        val favoriteItem1 = Favorite(1, "skirt", "handel", 100 ,"image",'C',5,"154","123456")
        val favoriteItem2 = Favorite(2, "skirt", "handel", 10 ,"image",'C',1,"154","123456")
        val favoriteItem3 = Favorite(3, "skirt", "handel", 80 ,"image",'C',2,"154","123456")

        dao.addToFavorite(favoriteItem1)
        dao.addToFavorite(favoriteItem2)
        dao.addToFavorite(favoriteItem3)

        dao.deleteListFromCart("123456")
        val allCartItems = dao.getAllCart("123456")
        assertThat(allCartItems).isEmpty()
    }
    @Test
    fun moveToCart() = runBlockingTest {
        val favoriteItem = Favorite(6, "skirt", "handel", 100 ,"image",'F',5,"154","123456")
        val result = Favorite(6, "skirt", "handel", 100 ,"image",'C',5,"154","123456")

        dao.addToFavorite(favoriteItem)
        dao.moveToCart(6,"123456")

        val allCartItems = dao.getAllCart("123456")
        val allFavoriteItems = dao.getAllFavorite("123456")

        assertThat(allCartItems).contains(result)
        assertThat(allFavoriteItems).doesNotContain(favoriteItem)

    }
    @Test
    fun updateCount() = runBlockingTest {
        val favoriteItem = Favorite(6, "skirt", "handel", 100 ,"image",'C',1,"154","123456")
        val result = Favorite(6, "skirt", "handel", 100 ,"image",'C',5,"154","123456")

        dao.addToFavorite(favoriteItem)
        dao.updateCount(6,5,"123456")
        val allCartItems = dao.getAllCart("123456")

        assertThat(allCartItems).doesNotContain(favoriteItem)
        assertThat(allCartItems).contains(result)
    }
    @Test
    fun isFavorite() = runBlockingTest {
        val favoriteItem = Favorite(6, "skirt", "handel", 100 ,"image",'F',1,"154","123456")
        dao.addToFavorite(favoriteItem)

        val result = dao.isFavorite(6,"123456")
        assertThat(result).isEqualTo(1)
    }
    @Test
    fun isNotFavorite() = runBlockingTest {
        val cartItem = Favorite(8, "skirt", "handel", 100 ,"image",'C',1,"154","123456")
        dao.addToFavorite(cartItem)

        val result = dao.isFavorite(8,"123456")
        assertThat(result).isEqualTo(0)
    }
    @Test
    fun isCart() = runBlockingTest {
        val cartItem = Favorite(8, "skirt", "handel", 100 ,"image",'C',1,"154","123456")
        dao.addToFavorite(cartItem)

        val result = dao.isCart(8,"123456")
        assertThat(result).isEqualTo(1)
    }
    @Test
    fun isNotCart() = runBlockingTest {
        val favoriteItem = Favorite(6, "skirt", "handel", 100 ,"image",'F',1,"154","123456")
        dao.addToFavorite(favoriteItem)

        val result = dao.isCart(6,"123456")
        assertThat(result).isEqualTo(0)
    }


}

