package com.example.graduationapp.ui.checkoutAddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.*
import com.example.graduationapp.databinding.ActivityCustomerDataBinding

class CustomerDataActivity : AppCompatActivity() {
    private lateinit var customerDataViewModel: CustomerDataViewModel
    private lateinit var binding: ActivityCustomerDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_customer_data)
        binding = ActivityCustomerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customerDataViewModel = ViewModelProvider(this).get(CustomerDataViewModel::class.java)

        checkAddressExist()
        Log.i("Menna", "My id ============== "+SharedPref.getUserID().toString())
        binding.saveBtn.setOnClickListener(View.OnClickListener {
           // checkAddressExist()
            addCustomerData()
            Log.i("Menna", "CustomerDataAdded")
        })


    }
    private fun checkAddressExist(){
        val customerId =SharedPref.getUserID().toString()
        customerDataViewModel.getCustomerAddress(customerId)
        customerDataViewModel.addressDetails.observe(this) {
            it?.let {
                binding.address1.text = SpannableStringBuilder("${it.addressList?.get(0)?.address1?:' '}")
                binding.address2.text = SpannableStringBuilder("${it.addressList?.get(0)?.address2?:' '}")
                binding.city.text =  SpannableStringBuilder("${it.addressList?.get(0)?.city?:' '}")
                binding.phone.text = SpannableStringBuilder("${it.addressList?.get(0)?.phone?:' '}")
                binding.province.text = SpannableStringBuilder("${it.addressList?.get(0)?.province?:' '}")
                binding.country.text = SpannableStringBuilder("${it.addressList?.get(0)?.country?:' '}")
                binding.zip.text = SpannableStringBuilder("${it.addressList?.get(0)?.zip?:' '}")
                binding.provinceCode.text = SpannableStringBuilder("${it.addressList?.get(0)?.province_code?:' '}")
                binding.countryCode.text = SpannableStringBuilder("${it.addressList?.get(0)?.country_code?:' '}")
                binding.countryName.text = SpannableStringBuilder("${it.addressList?.get(0)?.country_name?:' '}")
                Log.i("Menna","checkAddressExist **** $it")
            }
        }
    }
    private fun addCustomerData() {

        val customerId =SharedPref.getUserID().toString()
        val customerFname =SharedPref.getUserFname().toString()
        val customerLname =SharedPref.getUserFname().toString()
        val name = SharedPref.getUserFname() +" "+SharedPref.getUserFname()
        val add1 = binding.address1.text.toString()
        val add2 = binding.address2.text.toString()
        val city = binding.city.text.toString()
        val company = binding.company.text.toString()
        val phone = binding.phone.text.toString()
        val province = binding.province.text.toString()
        val country = binding.country.text.toString()
        val zip = binding.zip.text.toString()
        val provinceCode = binding.provinceCode.text.toString()
        val countryCode = binding.countryCode.text.toString()
        val countryName = binding.countryName.text.toString()
        val address = Address(add1,add2,city,company,customerFname,customerLname,phone,province,country,zip,name,provinceCode,countryCode,countryName)
        val addressJson= CreateAddress(address)
        customerDataViewModel.createCustomerAddress(customerId, addressJson)
    }
}