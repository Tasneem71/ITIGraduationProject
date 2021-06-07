package com.example.graduationapp

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.graduationapp.data.ApiCustomers
import com.example.graduationapp.data.CreatedCustomer
import com.example.graduationapp.data.Customers
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegistrationViewModel (application: Application) : AndroidViewModel(application) {

    var customerLiveData = MutableLiveData<Customers?>()
    var network =MutableLiveData<Boolean>()

    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository(application)
    }


    fun validate_Registration(customer: CreatedCustomer) {
        if (Validation.isOnline(getApplication())){
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
        else
        {
            network.postValue(false)
        }

    }

}