package com.example.graduationapp.ui.home

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.graduationapp.CoroutineTestRule
import com.example.graduationapp.data.CollectionProducts
import com.example.graduationapp.data.priceRules.DiscountCode
import com.example.graduationapp.data.priceRules.DiscountCodeClass
import com.example.graduationapp.getOrAwaitValue
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
class HomeViewModelTest : TestCase(){
    private lateinit var viewModel: HomeViewModel
    lateinit var repository: DefaultRepo
    var collection: DiscountCode?= DiscountCode(DiscountCodeClass(11218018074822,951388569798,"SUMMERSALE10OFF",12,"2021-06-05T17:28:05+02:00","2021-06-16T07:30:01+02:00"))

    @get:Rule
    var mainCoroutineRule = CoroutineTestRule()

    @get:Rule
    val taskRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        repository = mockk(relaxed = true)
    }

    @Test
    fun testWhenViewModel_VerifygetDiscount10Called() {
        // Given
        coEvery { repository.getDiscount10() } returns mockk()

        // When
        viewModel = HomeViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.getDiscount10()

        // Then
        coVerify { repository.getDiscount10() }
    }


    @Test
    fun testWhenMoviesListReturnedSuccessfully_VerifygetDiscount10Changed() {
        coEvery { repository.getDiscount10() } returns collection

        viewModel = HomeViewModel(ApplicationProvider.getApplicationContext(),repository)
        viewModel.getDiscount10()

        Assert.assertNotNull(viewModel.generatedDiscountLiveData.getOrAwaitValue())
    }

    @Test
    fun testMoviesViewModelFactory() {
        coEvery { repository.getDiscount10() } returns mockk(relaxed = true)

        val viewModel = HomeViewModelFactory(ApplicationProvider.getApplicationContext(),repository).create(HomeViewModel::class.java)

        assert(viewModel is HomeViewModel)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMoviesViewModelFactoryOfUnknownType_VerifyExceptionThrown() {

        HomeViewModelFactory(ApplicationProvider.getApplicationContext(),repository).create(
            ProductPageViewModel::class.java)
    }

    @After
    fun tear() {
        clearAllMocks()
    }




}