package com.example.graduationapp.ui.me

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.graduationapp.CoroutineTestRule
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.CancelOrder
import com.example.graduationapp.data.CanceledOrder
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.data.orders.OrderAPI
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.getOrAwaitValue
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.remote.DefaultRemote
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.productPageFeature.ProductPageViewModel
import com.example.graduationapp.ui.search.SearchViewModel
import com.example.graduationapp.ui.search.SearchViewModelFactory
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class MeViewModelTest : TestCase(){

    private lateinit var viewModel: MeViewModel
    lateinit var repository: DefaultRepo
    var order: OrderAPI?= OrderAPI(arrayListOf(),null)
    //var orde: Orders?= Orders()

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        repository = mockk(relaxed = true)
    }

    @Test
    fun testWhenViewModelInit_VerifygetOpenOrdersCalled() {
        // Given
        coEvery { repository.fetchOpenOrders("5255560691910") } returns mockk()

        // When
        viewModel = MeViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.getOpenOrders("5255560691910")

        // Then
        coVerify { repository.fetchOpenOrders("5255560691910") }
    }



    @Test
    fun testWhenViewModelInit_VerifycancelOrderCalled() {
        // Given
        coEvery { repository.cancelOrder("3854684192966", CancelOrder()) } returns mockk()

        // When
        viewModel = MeViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.cancelOrder("3854684192966",CancelOrder())

        // Then
        coVerify { repository.cancelOrder("3854684192966", CancelOrder()) }
    }


    @Test
    fun testWhenViewModelInit_VerifygetcartCountCalled() {
        // Given
        coEvery { repository.getUpdatedCount("5255560691910") } returns mockk()

        // When
        viewModel = MeViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.cartCount("5255560691910")
        // Then
        coVerify { repository.getUpdatedCount("5255560691910") }
    }



    @Test
    fun testWhenMoviesListReturnedSuccessfully_VerifyopenOrdersLiveDataChanged() {
        coEvery { repository.fetchOpenOrders("5255560691910") } returns order

        viewModel = MeViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.getOpenOrders("5255560691910")


        Assert.assertNotNull( viewModel.openOrdersLiveData.getOrAwaitValue())
    }

    @Test
    fun testWhenMoviesListReturnedSuccessfully_VerifycancelOrderLiveDataChanged() {
        coEvery { repository.cancelOrder("3854246183110",CancelOrder()) } returns order

        viewModel = MeViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.cancelOrder("3854246183110",CancelOrder())


        Assert.assertNotNull( viewModel.cancelOrderLiveData.getOrAwaitValue())
    }

    @Test
    fun testViewModelFactory() {
        coEvery { repository.fetchOpenOrders("5255560691910") } returns mockk(relaxed = true)

        val viewModel = MeViewModelFactory(ApplicationProvider.getApplicationContext(),repository).create( MeViewModel::class.java)

        assert(viewModel is MeViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testViewModelFactoryOfUnknownType_VerifyExceptionThrown() {

        MeViewModelFactory(ApplicationProvider.getApplicationContext(),repository).create(ProductPageViewModel::class.java)
    }

    @After
    fun tear() {
        clearAllMocks()
    }

}