package com.example.graduationapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.graduationapp.data.*
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel (application: Application) : AndroidViewModel(application) {
    var allCustomersLiveData = MutableLiveData<ApiCustomers?>()
    var createCustomerLiveData = MutableLiveData<Customers?>()
    var customerLiveData = MutableLiveData<Customers?>()
    var network =MutableLiveData<Boolean>()

    var apiRepository: ApiRepository

    init{
        apiRepository = ApiRepository(application)
    }

    fun loadData(context: Context){
        Log.i("Tasneem","inside the load")
        if (Validation.isOnline(getApplication())) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = apiRepository.fetchAllCustomerData()
                try {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            allCustomersLiveData.postValue(it)
                        }
                    } else {
                        Log.i("Tasneem", "response failuer" + response.errorBody().toString())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.i("Tasneem", " error?" + e.printStackTrace())

                }
            }
        }
        else
        {
            network.postValue(false)
        }
    }


    fun createCustomer(customerJson: CreatedCustomer) {
        if (Validation.isOnline(getApplication())){
            CoroutineScope(Dispatchers.IO).launch {
                val response=apiRepository.createCustomer(customerJson)
                    createCustomerLiveData.postValue(response?.customer)
                }
            }
        else
        {
            network.postValue(false)
        }
    }



    fun validate_login(userEmail: String, password: String) {
        if (Validation.isOnline(getApplication())) {
            if (!userEmail.isNullOrEmpty() || !password.isNullOrEmpty()) {
                viewModelScope.launch {
                    if (Validation.validateRegistration(userEmail, password)) {
                        val customers = apiRepository.getCustomerByEmail(userEmail)

                        println(customers)
                        if (!customers?.customers.isNullOrEmpty()) {
                            Log.i("tasneem", "inside not null")
                            if (customers?.customers?.get(0) != null && customers?.customers?.get(0)?.note == password) {
                                customerLiveData.postValue(customers?.customers?.get(0))

                            } else {
                                Toast.makeText(
                                    getApplication(),
                                    "password is wrong",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Log.i("tasneem", "Account not found")
                        }
                    } else {
                        Toast.makeText(
                            getApplication(),
                            "your email and pass don't match",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    getApplication(),
                    "User name and password can't be Empty",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        else {
            network.postValue(false)
        }

    }

    }
