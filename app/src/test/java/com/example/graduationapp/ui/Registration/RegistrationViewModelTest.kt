package com.example.graduationapp.ui.Registration

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.graduationapp.CoroutineTestRule
import com.example.graduationapp.data.ApiCustomers
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.data.CreatedCustomer
import com.example.graduationapp.data.Customer
import com.example.graduationapp.getOrAwaitValue
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.DefaultRemote
import com.example.graduationapp.remote.RemoteDataSource
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
class RegistrationViewModelTest : TestCase(){

    private lateinit var viewModel: RegistrationViewModel
    lateinit var repository: DefaultRepo
    lateinit var local: DefaultLocal
    lateinit var remote: DefaultRemote
    var collection: ApiCustomers?= ApiCustomers(arrayListOf(),null)


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
        coEvery { repository.createCustomer(
            CreatedCustomer(
                Customer("ta","ta"
                    ,"ta@gmail.com",null,"Aa123456",true,null,"Aa123456"
                    ,"Aa123456",false)
            )
        ) } returns mockk()

        // When
        viewModel = RegistrationViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.validate_Registration(CreatedCustomer(
            Customer("ta","ta"
                ,"ta@gmail.com",null,"Aa123456",true,null,"Aa123456"
                ,"Aa123456",false)
        ))

        // Then
        coVerify { repository.createCustomer(
            CreatedCustomer(
                Customer("ta","ta"
            ,"ta@gmail.com",null,"Aa123456",true,null,"Aa123456"
            ,"Aa123456",false)
            )
        ) }
    }


    @Test
    fun testWhenMoviesListReturnedSuccessfully_VerifyMoviesListChanged() {

        local= LocalSource(ApplicationProvider.getApplicationContext())
        remote= RemoteDataSource()
        repository= ApiRepository(ApplicationProvider.getApplicationContext(),local,remote)
        viewModel = RegistrationViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.validate_Registration(CreatedCustomer(
            Customer("fatma","Motafa"
                ,"fatma@gmail.com",null,"Aa123456",true,null,"Aa123456"
                ,"Aa123456",false)
        ))

        Assert.assertNotNull(viewModel.customerLiveData.getOrAwaitValue())
    }

    @Test
    fun testMoviesViewModelFactory() {
        coEvery { repository.getAllProducts() } returns mockk(relaxed = true)

        val viewModel = RegistrationViewModelFactory(ApplicationProvider.getApplicationContext(),repository).create(
            RegistrationViewModel::class.java)

        assert(viewModel is RegistrationViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMoviesViewModelFactoryOfUnknownType_VerifyExceptionThrown() {

        RegistrationViewModelFactory(ApplicationProvider.getApplicationContext(),repository).create(
            ProductPageViewModel::class.java)
    }

    @After
    fun tear() {
        clearAllMocks()
    }
}