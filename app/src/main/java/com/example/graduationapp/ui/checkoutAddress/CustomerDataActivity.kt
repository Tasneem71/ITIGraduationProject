package com.example.graduationapp.ui.checkoutAddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.MainActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.*
import com.example.graduationapp.databinding.ActivityCustomerDataBinding

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
                   }

//                   else{
//                       customerDataViewModel.carts?.observe(this,{
//                           orderDone(it)
//                       })
//                   }

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
                   }

//                   else{
//                       customerDataViewModel.carts?.observe(this,{
//                           orderDone(it)
//                       })
//
//                   }

               })
           }
        }

        binding.saveBtn.setOnClickListener {
            if (haveOneAddress) {
                editCustomerData()
            } else {
                addCustomerData()
            }
            customerDataViewModel.carts?.observe(this, Observer {
                Log.d("tag", "in observe")
                it?.let {
                    var count =
                        it.map { it.count * it.price }.reduce { acc, i -> acc + i }.toString()
                    Log.d("tag", "count" + count)
                    val email = SharedPref.getUserEmail().toString()
                    val listOfOrder = createOrderApi(it)
                    customerDataViewModel.createOrder(
                        CreatedOrder(
                            Order(
                                email,
                                "fulfilled",
                                count,
                                listOfOrder
                            )
                        )
                    )
                    Log.d("tag", "list" + listOfOrder)
                    if (it.isNullOrEmpty()) {
                        Toast.makeText(this, "Failuer", Toast.LENGTH_SHORT).show()
                    } else {
                        orderDone(it)
                    }

                }
            })
        }

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
            customerDataViewModel.editCustomerAddress(userId, id,addressJson)
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
    private fun orderDone(list : List<Favorite>) {
        val orderDialogBuilder = AlertDialog.Builder(this)
        orderDialogBuilder.setTitle(this.getString(R.string.order))
        orderDialogBuilder.setMessage(this.getString(R.string.order_created))
        orderDialogBuilder.setPositiveButton(this.getString(R.string.ok)) { dialog, which ->
            for (item in list){
                Log.d("del","delete " + item)
                customerDataViewModel.deleteFromFavorite(item)

            }
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }
        orderDialogBuilder.setCancelable(false)
        orderDialogBuilder.show()
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