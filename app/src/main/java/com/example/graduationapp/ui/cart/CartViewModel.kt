package com.example.graduationapp.ui.cart

import android.app.Application
import androidx.lifecycle.*
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.data.AddressData
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.data.CreateAddress
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.addressbook.AddressBookViewModel
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CartViewModel(application: Application,var apiRepository : DefaultRepo) : AndroidViewModel(application) {

    private val local = LocalSource(application)
    var carts : MutableLiveData<List<Favorite>>? = MutableLiveData<List<Favorite>>()
    var sumOfItems : MutableLiveData<Int> = MutableLiveData<Int>()
    var network =MutableLiveData<Boolean>()
    //var apiRepository: ApiRepository = ApiRepository(application)
    var allCustomerAddresses = MutableLiveData<List<Addresse?>?>()


    //var count : MutableLiveData<Int> = MutableLiveData<Int>()


    fun addToCart(item: Favorite){
        viewModelScope.launch {
            local.addToFavorite(item)
        }
    }
    fun getAllCarts(userId: String){
        var sumPrices :Int =0
        viewModelScope.launch {
            val result= async{local.getAllCart(userId)}
            result.join()
            carts?.value=result.await()
            for(item in carts?.value?.toList()!!){

                sumPrices += (item.price * item.count)
            }
            sumOfItems.postValue(sumPrices)
        }
    }

    fun updateCount(id:Long,count:Int,userId: String){
        viewModelScope.launch {
            local.updateCount(id,count,userId)
        }
    }
    fun deleteFromFavorite(item: Favorite){
        viewModelScope.launch {
            local.deleteFromFavorite(item)
        }
    }

    fun getAllCustomerAddress(id:String) {
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                apiRepository.getAllCustomerAddress(id).let {
                    allCustomerAddresses.postValue(it)
                }
            }
        }
        else
        {
            network.postValue(false)
        }

    }

}

@Suppress("UNCHECKED_CAST")
class CartViewModelFactory(val application: Application,val repo: DefaultRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CartViewModel(application, repo) as T
    }
}