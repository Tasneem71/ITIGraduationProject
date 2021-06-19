package com.example.graduationapp.ui.checkoutAddress

import junit.framework.TestCase

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.graduationapp.CoroutineTestRule
import com.example.graduationapp.GetProductsByCollectionIDQuery
import com.example.graduationapp.HomeCollectionQuery
import com.example.graduationapp.data.Address
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.data.CreateAddress
import com.example.graduationapp.getOrAwaitValue
import com.example.graduationapp.graphql.GraphRepo
import com.example.graduationapp.graphql.GraphViewModel
import com.example.graduationapp.remote.retro.DefaultRepo
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class CustomerDataViewModelTest : TestCase() {
    private lateinit var viewModel: CustomerDataViewModel
    lateinit var repository: DefaultRepo


    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        repository = mockk(relaxed = true)
    }

    @After
    fun tear() {
        clearAllMocks()
    }

    @Test
    fun testGetCustomerAddress()
    {
        coEvery { repository.getCustomerAddress("") } returns mockk()

        // When
        viewModel = CustomerDataViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.getCustomerAddress("")

        // Then
        Assert.assertNotNull(viewModel.firstAddressDetails.getOrAwaitValue())
    }

    @Test
    fun testCreateCustomerAddress()
    {
        coEvery { repository.createCustomerAdd("id",
            CreateAddress(Address("cairooo","cairo","Mohamed", "+201141411514","cairo","Egypt","12312")) ) } returns mockk()

        // When
        viewModel = CustomerDataViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.createCustomerAddress("id",CreateAddress(Address("cairooo","cairo","Mohamed", "+201141411514","cairo","Egypt","12312")))

        // Then
        Assert.assertNotNull(viewModel.createAddressLiveData.getOrAwaitValue())
    }

    @Test
    fun testEditCustomerAddress()
    {
        coEvery { repository.editCustomerAdd("id","111",CreateAddress(Address("cairooo","cairo","Mohamed", "+201141411514","cairo","Egypt","12312"))) } returns mockk()

        // When
        viewModel = CustomerDataViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.editCustomerAddress("id","111",CreateAddress(Address("cairooo","cairo","Mohamed", "+201141411514","cairo","Egypt","12312")))

        // Then
        Assert.assertNotNull(viewModel.editAddressLiveData.getOrAwaitValue())
    }

}

