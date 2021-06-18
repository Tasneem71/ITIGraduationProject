package com.example.graduationapp.ui.search

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.graduationapp.CoroutineTestRule
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.getOrAwaitValue
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.DefaultRemote
import com.example.graduationapp.remote.RemoteDataSource
import com.example.graduationapp.remote.retro.ApiServes
import com.example.graduationapp.remote.retro.ApiServes.shopfiyService
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.productPageFeature.ProductPageViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.robolectric.annotation.Config


@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class SearchViewModelTest : TestCase(){
    private lateinit var viewModel: SearchViewModel
    lateinit var repository: DefaultRepo
    lateinit var local: DefaultLocal
    lateinit var remote: DefaultRemote
     var collection:CollectionProducts?=CollectionProducts(arrayListOf())

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        repository = mockk(relaxed = true)
    }

    @Test
    fun testWhenViewModelInit_VerifyFetchMoviesCalled() {
        // Given
        coEvery { repository.getAllProducts() } returns mockk()

        // When
        viewModel = SearchViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.getAllProducts()

        // Then
        coVerify { repository.getAllProducts() }
    }


    @Test
    fun testWhenMoviesListReturnedSuccessfully_VerifyMoviesListChanged() {
        coEvery { repository.getAllProducts() } returns collection

        viewModel = SearchViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.getAllProducts()

        assertEquals(collection, viewModel.getAllProductsLiveData.getOrAwaitValue())
    }

    @Test
    fun testMoviesViewModelFactory() {
        coEvery { repository.getAllProducts() } returns mockk(relaxed = true)

        val viewModel = SearchViewModelFactory(ApplicationProvider.getApplicationContext(),repository).create(SearchViewModel::class.java)

        assert(viewModel is SearchViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMoviesViewModelFactoryOfUnknownType_VerifyExceptionThrown() {

        SearchViewModelFactory(ApplicationProvider.getApplicationContext(),repository).create(
            ProductPageViewModel::class.java)
    }

    @After
    fun tear() {
        clearAllMocks()
    }




    // Executes each task synchronously using Architecture Components.
    /*@get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(dispatcher)

    @Before
    fun setupViewModel() {
        local= LocalSource(ApplicationProvider.getApplicationContext())
        remote= RemoteDataSource()
        repository= ApiRepository(ApplicationProvider.getApplicationContext(),local,remote)
        searchViewModel = SearchViewModel(ApplicationProvider.getApplicationContext(),repository)
    }


    @Config(sdk = [Build.VERSION_CODES.O_MR1])
    @Test
    fun addNewTask_setsNewTaskEvent() {

        searchViewModel.getAllProducts()
        val value = searchViewModel.getAllProductsLiveData.getOrAwaitValue()
        Assert.assertNotNull(value)

    }*/


}