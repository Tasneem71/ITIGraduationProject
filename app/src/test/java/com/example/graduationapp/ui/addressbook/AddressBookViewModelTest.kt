package com.example.graduationapp.ui.addressbook

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.graduationapp.CoroutineTestRule
import com.example.graduationapp.data.AddressData
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.getOrAwaitValue
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.productPageFeature.ProductPageViewModel
import com.example.graduationapp.ui.search.SearchViewModel
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
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
class AddressBookViewModelTest : TestCase(){

    private lateinit var viewModel: AddressBookViewModel
    lateinit var repository: DefaultRepo
    var address: MutableList<Addresse>? = mutableListOf<Addresse>()
    var defAddress: AddressData?= AddressData(null,arrayListOf())

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
    fun testWhenGetAllCustomerAddressInit_VerifyFetchMoviesCalled() {
        // Given
        coEvery { repository.getAllCustomerAddress("5255560691910") } returns mockk()

        // When
        viewModel = AddressBookViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.getAllCustomerAddress("5255560691910")

        // Then
        coVerify { repository.getAllCustomerAddress("5255560691910") }
    }
    @Test
    fun testWhenGetCustomerAddressesListReturnedSuccessfully_VerifyMoviesListChanged() {
        coEvery { repository.getAllCustomerAddress("5255560691910")} returns address

        viewModel = AddressBookViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.getAllCustomerAddress("5255560691910")

        assertEquals(address, viewModel.allCustomerAddresses.getOrAwaitValue())
    }


    @Test
    fun testWhenSetDefaultAddress_VerifyFetchMoviesCalled() {
        // Given
        coEvery { repository.setDefaultAddress("5255560691910","6486324904134")} returns mockk()

        // When
        viewModel = AddressBookViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.setDefaultAddress("5255560691910","6486324904134")

        // Then
        coVerify { repository.setDefaultAddress("5255560691910","6486324904134") }
    }
    @Test
    fun testWhenDefaultAddressReturnedSuccessfully_VerifyMoviesListChanged() {
        coEvery { repository.setDefaultAddress("5255560691910","6486324904134")} returns defAddress

        viewModel = AddressBookViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.setDefaultAddress("5255560691910","6486324904134")

        Assert.assertNotNull(viewModel.defualtAddress.getOrAwaitValue())
    }
    fun testWhenDeleteAddress_VerifyFetchMoviesCalled() {
        // Given
        coEvery { repository.deleteAddress("5255560691910","6486312190150") } returns mockk()

        // When
        viewModel = AddressBookViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.deleteAddress("5255560691910","6486312190150")

        // Then
        coVerify { repository.deleteAddress("5255560691910","6486312190150") }
    }
    @Test
    fun testWhenDeleteAddressSuccessfully_VerifyMoviesListChanged() {
        coEvery { repository.deleteAddress("5255560691910","6486312190150")}

        viewModel = AddressBookViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.deleteAddress("5255560691910","6486312190150")
        viewModel.getAllCustomerAddress("5255560691910")

        assertThat(address).doesNotContain(address?.filter { it.id == "6486312190150"})
    }


    @Test
    fun testViewModelFactory() {
        coEvery { repository.getAllCustomerAddress("5255560691910") } returns mockk(relaxed = true)

        val viewModel = AddressBookViewModelFactory(ApplicationProvider.getApplicationContext(),repository).create( AddressBookViewModel::class.java)

        assert(viewModel is AddressBookViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testViewModelFactoryOfUnknownType_VerifyExceptionThrown() {
        AddressBookViewModelFactory(ApplicationProvider.getApplicationContext(),repository).create(
            ProductPageViewModel::class.java)
    }


}