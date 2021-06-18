package com.example.graduationapp.ui.Registration

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.graduationapp.data.CreatedCustomer
import com.example.graduationapp.data.Customers
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.addressbook.AddressBookViewModel
import com.example.graduationapp.ui.me.MeViewModel
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.launch


class RegistrationViewModel (application: Application, var apiRepository :DefaultRepo) : AndroidViewModel(application) {

    var customerLiveData = MutableLiveData<Customers?>()
    var network =MutableLiveData<Boolean>()

//    var apiRepository: ApiRepository
//
//    init{
//        apiRepository = ApiRepository(application)
//    }


    fun validate_Registration(customer: CreatedCustomer) {

            if (!customer.customer.first_name.isNullOrEmpty() && !customer.customer.password.isNullOrEmpty()
                && !customer.customer.password_confirmation.isNullOrEmpty() && !customer.customer.last_name.isNullOrEmpty()
                && !customer.customer.email.isNullOrEmpty()) {
                viewModelScope.launch {
                    if (Validation.validateRegistration(customer.customer.email, customer.customer.password)) {
                        println(customer.customer)
                        val customers= apiRepository.createCustomer(customer)
                        println(customer.customer)
                        if (customers != null){

                            customerLiveData.postValue(customers.customer)

                        }else{
                            Toast.makeText(getApplication(), "has already been taken", Toast.LENGTH_SHORT).show() }
                    }else{
                        Toast.makeText(getApplication(), "your email and pass don't match", Toast.LENGTH_SHORT).show() }
                }
            }else{
                Toast.makeText(getApplication(), "User name and password can't be Empty", Toast.LENGTH_SHORT).show() }

    }

}

@Suppress("UNCHECKED_CAST")
class RegistrationViewModelFactory(val application: Application,val repo: DefaultRepo): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
            return RegistrationViewModel(application, repo) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}