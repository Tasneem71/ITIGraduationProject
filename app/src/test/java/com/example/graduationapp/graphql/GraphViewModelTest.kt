package com.example.graduationapp.graphql

import junit.framework.TestCase

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.graduationapp.CoroutineTestRule
import com.example.graduationapp.GetProductsByCollectionIDQuery
import com.example.graduationapp.HomeCollectionQuery
import com.example.graduationapp.data.ApiCustomers
import com.example.graduationapp.getOrAwaitValue
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.remote.DefaultRemote
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.Registration.RegistrationViewModel
import com.example.graduationapp.ui.search.SearchViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class GraphViewModelTest : TestCase(){
    private lateinit var viewModel: GraphViewModel
    lateinit var repository: GraphRepo


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
    fun testGetCollectionData_VerifyLiveDataUpdated() {
        // Given
        coEvery { repository.suspendQuery(HomeCollectionQuery()) } returns mockk()

        // When
        viewModel = GraphViewModel(ApplicationProvider.getApplicationContext())
        viewModel.getCollectionData()

        // Then
        Assert.assertNotNull(viewModel.adidas.getOrAwaitValue())
    }

    @Test
    fun testGetCollection_VerifyHomeLiveDataUpdated() {
        // Given
        coEvery { repository.suspendQuery(GetProductsByCollectionIDQuery("gid://shopify/Collection/267715608774")) } returns mockk()

        // When
        viewModel = GraphViewModel(ApplicationProvider.getApplicationContext())
        viewModel.getCollection("gid://shopify/Collection/267715608774",0)

        // Then
        Assert.assertNotNull(viewModel.home.getOrAwaitValue())
    }
    @Test
    fun testGetCollection_VerifyKidLiveDataUpdated() {
        // Given
        coEvery { repository.suspendQuery(GetProductsByCollectionIDQuery("gid://shopify/Collection/268359663814")) } returns mockk()

        // When
        viewModel = GraphViewModel(ApplicationProvider.getApplicationContext())
        viewModel.getCollection("gid://shopify/Collection/268359663814",1)

        // Then
        Assert.assertNotNull(viewModel.kid.getOrAwaitValue())
    }

}