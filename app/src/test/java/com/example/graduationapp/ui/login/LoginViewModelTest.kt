package com.example.graduationapp.ui.login

import android.os.Build
import android.os.Looper.getMainLooper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.graduationapp.CoroutineTestRule
import com.example.graduationapp.data.ApiCustomers
import com.example.graduationapp.data.CreatedCustomer
import com.example.graduationapp.data.Customer
import com.example.graduationapp.data.Customers
import com.example.graduationapp.getOrAwaitValue
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.DefaultRemote
import com.example.graduationapp.remote.RemoteDataSource
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.productPageFeature.ProductPageViewModel
import io.mockk.*
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config


@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class LoginViewModelTest  : TestCase() {
    private lateinit var viewModel: LoginViewModel
    lateinit var repository: DefaultRepo
    lateinit var local: DefaultLocal
    lateinit var remote: DefaultRemote
    var collection: ApiCustomers?= ApiCustomers(arrayListOf(),null)


    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = mockk(relaxed = true)
        viewModel = LoginViewModel(ApplicationProvider.getApplicationContext(), repository)
    }

    @Test
    fun testWhenViewModelInit_VerifyLoginCalled() {

        coEvery { repository.getCustomerByEmail("fatma@gmail.com") }returns mockk()

        viewModel.getOneCustomer("fatma@gmail.com")

        coEvery { repository.getCustomerByEmail("fatma@gmail.com") }
    }

    @Test
    fun testWhenGetOneCustomerReturnedSuccessfully_VerifyCustomerChanged() {
        coEvery { repository.getCustomerByEmail("fatma@gmail.com") } returns collection

        viewModel.getOneCustomer("fatma@gmail.com")
        Assert.assertNotNull(viewModel.allCustomersLiveData.getOrAwaitValue())

    }

    @Test
    fun testWhenValidateLoginReturnedSuccessfully_VerifyLoginChanged() {
        local= LocalSource(ApplicationProvider.getApplicationContext())
        remote= RemoteDataSource()
        repository= ApiRepository(ApplicationProvider.getApplicationContext(),local,remote)
      val  viewModel = LoginViewModel(ApplicationProvider.getApplicationContext(), repository)

        viewModel.validate_login("fatma@gmail.com","Aa123456")

        Assert.assertNotNull(viewModel.customerLiveData.getOrAwaitValue())

    }


    @Test
    fun testLoginViewModelFactory() {
        coEvery { repository.getAllCustomerAddress("5255560691910")  } returns mockk(relaxed = true)

        val viewModel = LoginViewModelFactory(
            ApplicationProvider.getApplicationContext(),
            repository
        ).create(
            LoginViewModel::class.java
        )

        assert(viewModel is LoginViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testLoginViewModelFactoryOfUnknownType_VerifyExceptionThrown() {

        LoginViewModelFactory(
            ApplicationProvider.getApplicationContext(),
            repository
        ).create(
            ProductPageViewModel::class.java
        )
    }

    @After
    fun tear() {
        clearAllMocks()
    }
}

