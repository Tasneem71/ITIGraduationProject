package com.example.graduationapp.ui.checkoutAddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.graduationapp.SharedPref
import com.example.graduationapp.create_order.CreateOrderActivity
import com.example.graduationapp.data.*
import com.example.graduationapp.databinding.ActivityCustomerDataBinding

class CustomerDataActivity : AppCompatActivity() {
    private lateinit var customerDataViewModel: CustomerDataViewModel
    private lateinit var binding: ActivityCustomerDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        customerDataViewModel = ViewModelProvider(this).get(CustomerDataViewModel::class.java)

        Log.i("Menna", "My id === "+SharedPref.getUserID().toString())
        customerDataViewModel.getCustomerAddress(SharedPref.getUserID().toString())
        //check
        customerDataViewModel.allAddressDetails.observe(this) {
            Log.i("Menna","Chheck if empty "+it)
           if (it.isNullOrEmpty()) {
                SharedPref.haveOneAddress(false)
               customerDataViewModel.createAddressLiveData.observe(this, Observer {
                   Log.i("Menna",""+it?.address)
                   if (it?.address?.province.isNullOrEmpty() )
                   {
                       Toast.makeText(this,"ADD Please, Enter Valid Country, province and Address ",Toast.LENGTH_SHORT).show()
                   }
                   else{
                       startActivity(Intent(this,CreateOrderActivity::class.java))
                   }

               })
           }
           else{
               SharedPref.haveOneAddress(true)
               fillIfAddressExist()
               customerDataViewModel.editAddressLiveData.observe(this, Observer {
                   Log.i("Menna","edit address ***** "+it?.address)
                   if (it?.address?.province.isNullOrEmpty())
                   {
                       Toast.makeText(this,"Please, Enter Valid Country, province and Address ",Toast.LENGTH_SHORT).show()
                   }
                   else{
                       startActivity(Intent(this,CreateOrderActivity::class.java))
                   }

               })
           }
        }
        binding.saveBtn.setOnClickListener(View.OnClickListener {

            if (SharedPref.isHaveOneAddress()){
                editCustomerData()
            }else{
                addCustomerData()
            }
        })

    }
    private fun fillIfAddressExist(){
        customerDataViewModel.allAddressDetails.observe(this) {
            it?.let {
                SharedPref.setAddressID(it[0]?.id)
                binding.address1.text = SpannableStringBuilder("${it[0]?.address1 ?:' '}")
                binding.city.text =  SpannableStringBuilder("${it[0]?.city ?:' '}")
                binding.phone.text = SpannableStringBuilder("${it[0]?.phone ?:' '}")
                binding.province.text = SpannableStringBuilder("${it[0]?.province ?:' '}")
                binding.country.text = SpannableStringBuilder("${it[0]?.country ?:' '}")
                binding.zip.text = SpannableStringBuilder("${it[0]?.zip ?:' '}")
            }
        }
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
        if (checkPhoneNum(phone) ){
            customerDataViewModel.createCustomerAddress(customerId, addressJson)
        }

    }
    private fun editCustomerData() {
        val id = SharedPref.getAddressID().toString()
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

        if (checkPhoneNum(phone) ) {
            customerDataViewModel.editCustomerAddress(customerId, id,addressJson)
        }

    }
    private fun checkPhoneNum(phone :String) : Boolean{
        //^[+]?[0-9]{10,13}$
        return if(phone.length != 13 || phone.isEmpty() ){
            Toast.makeText(this,"Enter valid Phone Number",Toast.LENGTH_SHORT).show()
            false
        } else if(phone.length ==11 && phone.take(1) != "+"){
            Toast.makeText(this,"please Enter Country Code in the First like +2 for Egypt",Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }
}