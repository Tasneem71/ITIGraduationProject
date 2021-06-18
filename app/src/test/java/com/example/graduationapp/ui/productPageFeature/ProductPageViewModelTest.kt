package com.example.graduationapp.ui.productPageFeature

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.graduationapp.CoroutineTestRule
import com.example.graduationapp.getOrAwaitValue
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.DefaultRemote
import com.example.graduationapp.remote.RemoteDataSource
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.Registration.RegistrationViewModel
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class ProductPageViewModelTest : TestCase(){

    private var viewModel: ProductPageViewModel?=null

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()


    @Before
    fun setupViewModel() {
        viewModel = ProductPageViewModel(ApplicationProvider.getApplicationContext())
    }


    @Config(sdk = [Build.VERSION_CODES.O_MR1])
    @Test
    fun testWhenViewModel_getProductDetails() {

        viewModel?.getProductDetails("6687367921862")
        val value = viewModel?.productDetails?.getOrAwaitValue()
        Assert.assertNotNull(value)

    }

    @After
    fun clearViewModel() {
        viewModel = null
    }
}