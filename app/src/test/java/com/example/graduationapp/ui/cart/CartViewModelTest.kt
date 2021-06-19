package com.example.graduationapp.ui.cart

import android.os.Build
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.CoroutineTestRule
import com.example.graduationapp.create_order.CreateOrderViewModel
import com.example.graduationapp.create_order.CreateOrderViewModelFactory
import com.example.graduationapp.data.*
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.getOrAwaitValue
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.remote.DefaultRemote
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.productPageFeature.ProductPageViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class CartViewModelTest  : TestCase() {
    private lateinit var viewModel: CartViewModel
    lateinit var repository: DefaultRepo

    var address: MutableList<Addresse>? = mutableListOf<Addresse>()

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
    }

    @Test
    fun testWhenViewModelInit_VerifyFetchOrdersCalled() {
        // Given
        coEvery { repository.getAllCustomerAddress("5255560691910")} returns mockk()

        // When
        viewModel = CartViewModel(ApplicationProvider.getApplicationContext(), repository)
        viewModel.getAllCustomerAddress("5255560691910")

        // Then
        coVerify { repository.getAllCustomerAddress("5255560691910") }
    }

    @Test
    fun testWhenAddressessListReturnedSuccessfully_VerifyAddressesListChanged() {
        coEvery { repository.getAllCustomerAddress("5255560691910") } returns address

        viewModel = CartViewModel(ApplicationProvider.getApplicationContext(), repository)
       viewModel.getAllCustomerAddress("5255560691910")

        Assert.assertNotNull( viewModel.allCustomerAddresses?.getOrAwaitValue())
       //  assertEquals(address, viewModel.allCustomerAddresses?.getOrAwaitValue())
    }


    @Test
    fun testCartViewModelFactory() {
        coEvery { repository.getAllCustomerAddress("5255560691910")  } returns mockk(relaxed = true)

        val viewModel = CartViewModelFactory(
            ApplicationProvider.getApplicationContext(),
            repository
        ).create(
            CartViewModel::class.java
        )

        assert(viewModel is CartViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCartViewModelFactoryOfUnknownType_VerifyExceptionThrown() {

        CartViewModelFactory(ApplicationProvider.getApplicationContext(), repository).create(
            ProductPageViewModel::class.java
        )
    }

    @After
    fun tear() {
        clearAllMocks()
    }
}

