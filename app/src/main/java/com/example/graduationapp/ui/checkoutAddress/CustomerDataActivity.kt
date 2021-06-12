package com.example.graduationapp.ui.checkoutAddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.*
import com.example.graduationapp.databinding.ActivityCustomerDataBinding
import com.example.graduationapp.ui.paymentsummary.PaymentSummary
import com.example.graduationapp.utils.Validation

class CustomerDataActivity : AppCompatActivity() {
    private lateinit var customerDataViewModel: CustomerDataViewModel
    private lateinit var binding: ActivityCustomerDataBinding
    private lateinit var userId :String
    var haveOneAddress :Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = SharedPref.getUserID().toString()
        Log.i("Menna", "CustomerDataActivity user id == "+userId)


        customerDataViewModel = ViewModelProvider(this).get(CustomerDataViewModel::class.java)

        customerDataViewModel.getAllCarts(userId)
        Log.i("Menna", "My id === "+SharedPref.getUserID().toString())
        customerDataViewModel.getCustomerAddress(SharedPref.getUserID().toString())
        //check
        customerDataViewModel.allAddressDetails.observe(this) {
            Log.i("Menna","Chheck if empty "+it)
           if (it.isNullOrEmpty()) {
               haveOneAddress = false
               customerDataViewModel.createAddressLiveData.observe(this, Observer {
                   Log.i("Menna",""+it?.address)
                   if (it?.address?.province.isNullOrEmpty() )
                   {
                       Toast.makeText(this,this.getString(R.string.valid),Toast.LENGTH_SHORT).show()
                   }else{
                       goToSummary(it!!)
                   }

               })
           }
           else{
               haveOneAddress = true
               fillIfAddressExist()
               customerDataViewModel.editAddressLiveData.observe(this, Observer {
                   Log.i("Menna","edit address ***** "+it?.address)
                   if (it?.address?.province.isNullOrEmpty())
                   {
                       Toast.makeText(this,this.getString(R.string.valid),Toast.LENGTH_SHORT).show()
                   }else{
                      goToSummary(it!!)
                   }

               })
           }
        }

        binding.saveBtn.setOnClickListener {
            if (haveOneAddress) {
                editCustomerData()
            } else {
                addCustomerData()
            }

        }

    }
    fun goToSummary(it : AddressData){
        val intent1= Intent(this, PaymentSummary::class.java)
        intent1.putExtra("province",(it?.address?.province+it?.address?.city))
        intent1.putExtra("phone",(it?.address?.phone))
        intent1.putExtra("address1",(it?.address?.address1))
        intent1.putExtra("price",intent.getStringExtra("price").toString())
        startActivity(intent1)
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
        if (Validation.validatePhone(phone)){
            customerDataViewModel.createCustomerAddress(customerId, addressJson)
        }else{
            Toast.makeText(this,this.getString(R.string.valid_num),Toast.LENGTH_SHORT).show()
        }

    }
    private fun editCustomerData() {
        val id = SharedPref.getAddressID().toString()
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
            customerDataViewModel.editCustomerAddress(userId, id,addressJson)
        }
        else{
            Toast.makeText(this,this.getString(R.string.valid_num),Toast.LENGTH_SHORT).show()
        }

    }
    private fun checkPhoneNum(phone :String) : Boolean{
        //^[+]?[0-9]{10,13}$
        return if(phone.length != 13 || phone.isEmpty() ){
            Toast.makeText(this,this.getString(R.string.valid_num),Toast.LENGTH_SHORT).show()
            false
        } else if(phone.length ==11 && phone.take(1) != "+"){
            Toast.makeText(this,this.getString(R.string.valid_code),Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun createOrderApi(list:List<Favorite>) : List<LineItems> {
        val lines : MutableList<LineItems> = mutableListOf<LineItems>()
        for (item in list){
            val lineObject : LineItems = LineItems(item.title,item.price.toString(),item.count,item.varient_id)
            lines.add(lineObject)
        }
        return lines
    }
}