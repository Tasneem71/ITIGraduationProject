package com.example.graduationapp.ui.checkoutAddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.Toast
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
        if (SharedPref.isHaveOneAddress()){
            checkAddressExist()
        }
       // checkAddressExist()
        Log.i("Menna", "My id === "+SharedPref.getUserID().toString())
        binding.saveBtn.setOnClickListener(View.OnClickListener {
            if (SharedPref.isHaveOneAddress()){
                editCustomerData()
            }else{
                addCustomerData()
            }
        })
        binding.skip.setOnClickListener(View.OnClickListener {

        })

    }
    private fun checkAddressExist(){
        val customerId =SharedPref.getUserID().toString()
        customerDataViewModel.getCustomerAddress(customerId)
        customerDataViewModel.addressDetails.observe(this) {
            it?.let {
                SharedPref.setAddressIp(it.addressList?.get(0)?.id)
                binding.address1.text = SpannableStringBuilder("${it.addressList?.get(0)?.address1?:' '}")
                binding.address2.text = SpannableStringBuilder("${it.addressList?.get(0)?.address2?:' '}")
                binding.city.text =  SpannableStringBuilder("${it.addressList?.get(0)?.city?:' '}")
                binding.company.text =  SpannableStringBuilder("${it.addressList?.get(0)?.company?:' '}")
                binding.phone.text = SpannableStringBuilder("${it.addressList?.get(0)?.phone?:' '}")
                binding.province.text = SpannableStringBuilder("${it.addressList?.get(0)?.province?:' '}")
                binding.country.text = SpannableStringBuilder("${it.addressList?.get(0)?.country?:' '}")
                binding.zip.text = SpannableStringBuilder("${it.addressList?.get(0)?.zip?:' '}")
               // binding.provinceCode.text = SpannableStringBuilder("${it.addressList?.get(0)?.province_code?:' '}")
              // binding.countryCode.text = SpannableStringBuilder("${it.addressList?.get(0)?.country_code?:' '}")
               // binding.countryName.text = SpannableStringBuilder("${it.addressList?.get(0)?.country_name?:' '}")
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
        val address = Address(add1,add2,city,company,customerFname,customerLname,phone,province,country,zip,name,"","","")
        val addressJson= CreateAddress(address)
        if (checkPhoneNum(phone)) customerDataViewModel.createCustomerAddress(customerId, addressJson)
       // customerDataViewModel.createCustomerAddress(customerId, addressJson)
    }

    private fun editCustomerData() {

        val id = SharedPref.getAddressIp().toString()
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
       // val provinceCode = binding.provinceCode.text.toString()
        //val countryCode = binding.countryCode.text.toString()
        //val countryName = binding.countryName.text.toString()
        val address = Address(add1,add2,city,company,customerFname,customerLname,phone,province,country,zip,name,"","","")
        val addressJson= CreateAddress(address)
        if (checkPhoneNum(phone)) customerDataViewModel.editCustomerAddress(customerId, id,addressJson)
    }
    private fun checkPhoneNum(phone :String) : Boolean{
        //^[+]?[0-9]{10,13}$
        return if(phone.length != 13 || phone.isEmpty() ){
            Toast.makeText(this,"Enter valid Phone Number",Toast.LENGTH_SHORT).show()
            false
        } else if(phone.take(1) != "+"){
            Toast.makeText(this,"please Enter Country Code in the First like +2 for Egypt",Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
}