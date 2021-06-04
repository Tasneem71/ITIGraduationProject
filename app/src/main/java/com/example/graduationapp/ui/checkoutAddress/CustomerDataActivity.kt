package com.example.graduationapp.ui.checkoutAddress

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
        if (SharedPref.isHaveOneAddress()){
            checkAddressExist()
            customerDataViewModel.editAddressLiveData.observe(this, Observer {
                Log.i("Menna","999999999999999999999999999999999999999999999999999999999999999999"+it?.address)
                if (it?.address?.province.isNullOrEmpty())
                {
                    Toast.makeText(this,"Please, Enter Valid Country, province and Address ",Toast.LENGTH_SHORT).show()
                }
            })
        }
        else{
            customerDataViewModel.createAddressLiveData.observe(this, Observer {
            Log.i("Menna",it?.addressList.toString())
            if (it?.addressList.isNullOrEmpty() )
            {
                Toast.makeText(this,"ADD Please, Enter Valid Country, province and Address ",Toast.LENGTH_SHORT).show()
            }
        })
        }
        Log.i("Menna", "My id === "+SharedPref.getUserID().toString())


        binding.saveBtn.setOnClickListener(View.OnClickListener {
            if (SharedPref.isHaveOneAddress()){
                editCustomerData()
            }else{
                addCustomerData()
            }
        })

    }
    private fun checkAddressExist(){
        val customerId =SharedPref.getUserID().toString()
        customerDataViewModel.getCustomerAddress(customerId)
        customerDataViewModel.addressDetails.observe(this) {
            it?.let {
                SharedPref.setAddressIp(it.addressList?.get(0)?.id)
                binding.address1.text = SpannableStringBuilder("${it.addressList?.get(0)?.address1?:' '}")
                binding.city.text =  SpannableStringBuilder("${it.addressList?.get(0)?.city?:' '}")
                binding.company.text =  SpannableStringBuilder("${it.addressList?.get(0)?.company?:' '}")
                binding.phone.text = SpannableStringBuilder("${it.addressList?.get(0)?.phone?:' '}")
                binding.province.text = SpannableStringBuilder("${it.addressList?.get(0)?.province?:' '}")
                binding.country.text = SpannableStringBuilder("${it.addressList?.get(0)?.country?:' '}")
                binding.zip.text = SpannableStringBuilder("${it.addressList?.get(0)?.zip?:' '}")
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

        val city = binding.city.text.toString()
        val company = binding.company.text.toString()
        val phone = binding.phone.text.toString()
        val province = binding.province.text.toString()
        val country = binding.country.text.toString()
        val zip = binding.zip.text.toString()
        val address = Address(add1,null,city,company,customerFname,customerLname,phone,province,country,zip,name,null,null,null)
        val addressJson= CreateAddress(address)
        if (checkPhoneNum(phone) ){
            customerDataViewModel.createCustomerAddress(customerId, addressJson)
        }

    }
    private fun editCustomerData() {
        val id = SharedPref.getAddressIp().toString()
        val customerId =SharedPref.getUserID().toString()
        val customerFname =SharedPref.getUserFname().toString()
        val customerLname =SharedPref.getUserFname().toString()
        val name = SharedPref.getUserFname() +" "+SharedPref.getUserFname()
        val add1 = binding.address1.text.toString()
        val city = binding.city.text.toString()
        val company = binding.company.text.toString()
        val phone = binding.phone.text.toString()
        val province = binding.province.text.toString()
        val country = binding.country.text.toString()
        val zip = binding.zip.text.toString()


        val address = Address(add1,null,city,company,customerFname,customerLname,phone,province,country,zip,name,null,null,null)
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