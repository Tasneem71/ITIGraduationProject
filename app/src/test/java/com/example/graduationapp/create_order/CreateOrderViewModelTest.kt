package com.example.graduationapp.create_order

import android.os.Build
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.CoroutineTestRule
import com.example.graduationapp.data.*
import com.example.graduationapp.data.orders.OrderAPI
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
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class CreateOrderViewModelTest : TestCase() {
    private lateinit var viewModel: CreateOrderViewModel
    lateinit var repository: DefaultRepo
    lateinit var local: DefaultLocal
    lateinit var remote: DefaultRemote
    var order: MutableList<Orders>? = mutableListOf<Orders>()
//    var orderObj : Orders = Orders("","","","",false,"",
//    "","","","","",true,"","","",
//    170.0,)
    var addressObj : Addresse? = Addresse("","","","","", "", "",
        "","","","","","","","","",false)
  //  var orders: OrderAPI? = OrderAPI(order,orderObj)
    var code: MutableList<DiscountCodes>? = mutableListOf<DiscountCodes>()
    var line: MutableList<LineItems>? = mutableListOf<LineItems>()
    var carts: MutableList<Favorite>? = mutableListOf<Favorite>()
    var address: MutableList<Addresse>? = mutableListOf<Addresse>()
    var addresses: AddressData? = AddressData(addressObj,address)
    var orderJson : CreatedOrder = CreatedOrder(Order("ana@gmail.com",null,
        "pending","170",line,null,code))


    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        repository = mockk(relaxed = true)
    }

    @Test
    fun testWhenViewModelInit_VerifyFetchOrdersCalled() {
        // Given
        coEvery { repository.getAllCart("")} returns mockk()

        // When
        viewModel = CreateOrderViewModel(ApplicationProvider.getApplicationContext(), repository)
        viewModel.getAllOrderd("")


        // Then
        coVerify { repository.getAllCart("") }
    }
    @Test
    fun testWhenViewModelInit_VerifyAddressCalled() {
        // Given
        coEvery { repository.getDefaultAddress("","")} returns mockk()

        // When
        viewModel = CreateOrderViewModel(ApplicationProvider.getApplicationContext(), repository)
         viewModel.getDefaultAddress("","")

        // Then
        coVerify { repository.getDefaultAddress("","") }
    }

    @Test
    fun testWhenOrdersListReturnedSuccessfully_VerifyOrdersListChanged() {
        coEvery { repository.getAllCart("") } returns carts

        viewModel = CreateOrderViewModel(ApplicationProvider.getApplicationContext(), repository)
     //   viewModel.orders?.postValue(carts)
       // viewModel.network?.postValue(true)
        //Shadows.shadowOf(Looper.getMainLooper()).idle()
        viewModel.getAllOrderd("")
        Assert.assertNotNull( viewModel.orders?.getOrAwaitValue())
       // assertEquals(orders, viewModel.orders?.getOrAwaitValue())
    }

//    @Test
//    fun testWhenOrdersListCreatedReturnedSuccessfully_VerifyOrdersListChanged() {
//        coEvery { repository.createOrder(orderJson) } returns orders
//
//        viewModel = CreateOrderViewModel(ApplicationProvider.getApplicationContext(), repository)
//
//         Assert.assertNotNull( viewModel.createOrderLiveData.getOrAwaitValue())
//        //assertEquals(orders, viewModel.createOrderLiveData.getOrAwaitValue())
//    }
    @Test
    fun testWhenAddressReturnedSuccessfully_VerifyOrdersListChanged() {
        coEvery { repository.getDefaultAddress("","") } returns addresses

        viewModel = CreateOrderViewModel(ApplicationProvider.getApplicationContext(), repository)
       viewModel.getDefaultAddress("","")
        Assert.assertNotNull( viewModel.getDefaultAddLifeData.getOrAwaitValue())
        //assertEquals(orders, viewModel.createOrderLiveData.getOrAwaitValue())
    }

    @Test
    fun testOrderViewModelFactory() {
        coEvery { repository.getAllCart("")} returns mockk(relaxed = true)

        val viewModel = CreateOrderViewModelFactory(
            ApplicationProvider.getApplicationContext(),
            repository
        ).create(
            CreateOrderViewModel::class.java
        )

        assert(viewModel is CreateOrderViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testOrdersViewModelFactoryOfUnknownType_VerifyExceptionThrown() {

        CreateOrderViewModelFactory(ApplicationProvider.getApplicationContext(), repository).create(
            ProductPageViewModel::class.java
        )
    }

    @After
    fun tear() {
        clearAllMocks()
    }
}

