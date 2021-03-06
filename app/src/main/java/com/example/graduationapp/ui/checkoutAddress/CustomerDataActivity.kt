package com.example.graduationapp.ui.checkoutAddress

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.Address
import com.example.graduationapp.data.AddressData
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.data.CreateAddress
import com.example.graduationapp.databinding.ActivityCustomerDataBinding
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.DefaultRemote
import com.example.graduationapp.remote.RemoteDataSource
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.cart.CartActivity
import com.example.graduationapp.ui.paymentsummary.PaymentSummary
import com.example.graduationapp.ui.search.SearchViewModel
import com.example.graduationapp.ui.search.SearchViewModelFactory
import com.example.graduationapp.utils.Validation

class CustomerDataActivity : AppCompatActivity() {
    private lateinit var customerDataViewModel: CustomerDataViewModel
    private lateinit var binding: ActivityCustomerDataBinding
    private lateinit var userId :String
    private lateinit var addressID :String

    var frromEdit :Boolean = false
    lateinit var repository: DefaultRepo
    lateinit var local: DefaultLocal
    lateinit var remote: DefaultRemote


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)


        local= LocalSource(this.application)
        remote=RemoteDataSource()
        repository= ApiRepository(this.application,local,remote)

        val factory = CustomerDataViewModelFactory(this.application,repository)
        customerDataViewModel = ViewModelProviders.of(this,factory).get(CustomerDataViewModel::class.java)


        //customerDataViewModel = ViewModelProvider(this).get(CustomerDataViewModel::class.java)

        userId = SharedPref.getUserID().toString()
        Log.i("Menna", "CustomerDataActivity user id == "+userId)

        val intent =intent
        if (intent != null)
        {
            Log.i("Menna", "onCreate: inside intent")
            when(intent.getStringExtra("key"))
            {
                "Edit" ->{
                    frromEdit = true
                    val userInfo = getIntent().getSerializableExtra("Addresse") as Addresse?
                    if (userInfo != null) {
                        addressID =userInfo.id
                    }
                    fillIfAddressExist(userInfo!!)
                    customerDataViewModel.editAddressLiveData.observe(this, Observer {
                        Log.i("Menna","edit address ***** "+it?.address)
                        if (it?.address?.province.isNullOrEmpty())
                        {
                            Toast.makeText(this,this.getString(R.string.valid),Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this,this.getString(R.string.editDone),Toast.LENGTH_SHORT).show()
                            finish()
                            //goToSummary(it!!)
                        }

                    })
                    Log.i("Menna", "onCreate: ${userInfo?.address1}")
                }
                "New" ->{
                    Log.i("Menna", "NEW: ")
                    frromEdit = false
                    customerDataViewModel.createAddressLiveData.observe(this, Observer {
                        Log.i("Menna",""+it?.address)
                        if (it?.address?.province.isNullOrEmpty() )
                        {
                            Toast.makeText(this,this.getString(R.string.valid),Toast.LENGTH_SHORT).show()
                        }else{
                            if(SharedPref.getAddressID().isNullOrEmpty()){
                                SharedPref.setAddressID(it?.address?.id)
                            }
                            Toast.makeText(this,this.getString(R.string.newAddress),Toast.LENGTH_SHORT).show()
                            finish()
                            //goToSummary(it!!)
                        }

                    })

                }
                "First" ->{
                    Log.i("Menna", "First: ")
                    frromEdit = false
                    customerDataViewModel.createAddressLiveData.observe(this, Observer {
                        Log.i("Menna",""+it?.address)
                        if (it?.address?.province.isNullOrEmpty() )
                        {
                            Toast.makeText(this,this.getString(R.string.valid),Toast.LENGTH_SHORT).show()
                        }else{

                            SharedPref.setAddressID(it?.address?.id)

                            Toast.makeText(this,this.getString(R.string.newAddress),Toast.LENGTH_SHORT).show()
                            val price = intent.getStringExtra("price").toString()
                            val paymentIntent = Intent(this, PaymentSummary::class.java)
                            paymentIntent.putExtra("price", price)
                            startActivity(paymentIntent)
                            finish()
                        }

                    })

                }
            }
        }

        binding.saveBtn.setOnClickListener {
            if (frromEdit) {
                editCustomerData()
            } else {
                addCustomerData()
            }

        }
        binding.back.setOnClickListener {
            finish()
        }

    }
    private fun fillIfAddressExist(address : Addresse){
        binding.address1.text = SpannableStringBuilder("${address.address1 ?:' '}")
        binding.city.text =  SpannableStringBuilder("${address.city ?:' '}")
        binding.phone.text = SpannableStringBuilder("${address.phone ?:' '}")
        binding.province.text = SpannableStringBuilder("${address.province ?:' '}")
        binding.country.text = SpannableStringBuilder("${address.country ?:' '}")
        binding.zip.text = SpannableStringBuilder("${address.zip ?:' '}")
    }
    private fun addCustomerData() {
        val customerId =SharedPref.getUserID().toString()
        val customerFname =SharedPref.getUserFname().toString()
        val add1 = binding.address1.text.toString()
        val city = binding.city.text.toString()
        val phone = binding.phone.text.toString()
        val province = binding.province.text.toString()
        val country = binding.country.text.toString()
        val zip = binding.zip.text.toString()
        val address = Address(add1,city,customerFname,phone,province,country,zip)
        val addressJson= CreateAddress(address)
        if (Validation.validatePhone(phone)){
            customerDataViewModel.createCustomerAddress(customerId, addressJson)
        }else{
            Toast.makeText(this,this.getString(R.string.valid_num),Toast.LENGTH_SHORT).show()
        }

    }
    private fun editCustomerData() {
        val customerFname =SharedPref.getUserFname().toString()
        val add1 = binding.address1.text.toString()
        val city = binding.city.text.toString()
        val phone = binding.phone.text.toString()
        val province = binding.province.text.toString()
        val country = binding.country.text.toString()
        val zip = binding.zip.text.toString()
        val address = Address(add1,city,customerFname,phone,province,country,zip)
        val addressJson= CreateAddress(address)
        if (Validation.validatePhone(phone)) {
            customerDataViewModel.editCustomerAddress(userId, addressID,addressJson)
        }
        else{
            Toast.makeText(this,this.getString(R.string.valid_num),Toast.LENGTH_SHORT).show()
        }
    }

}