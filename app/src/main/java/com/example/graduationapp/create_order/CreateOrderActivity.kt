package com.example.graduationapp.create_order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graduationapp.LoginViewModel
import com.example.graduationapp.MainActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.*
import com.example.graduationapp.databinding.ActivityCreateOrderBinding
import com.example.graduationapp.databinding.ActivityLoginBinding
import com.example.graduationapp.ui.cart.CartAdapter
import com.example.graduationapp.ui.cart.SwipeToDelete

class CreateOrderActivity : AppCompatActivity() {

    private lateinit var createOrderViewModel: CreateOrderViewModel
    private lateinit var binding: ActivityCreateOrderBinding
    lateinit var  cartAdapter : CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)
        binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        createOrderViewModel = ViewModelProvider(this).get(CreateOrderViewModel::class.java)
        initUI()

    }

    private fun createOrderApi() {
//        Log.d("order", "post")
//        val customerEmail =SharedPref.getUserEmail()
//        val title = binding.order.titile.text.toString()
//        val price = binding.order.price.text.toString()
//        val quantity = binding.order.count.text.toString()
//        val order_number = 5
//        val list : List<LineItems> = mutableListOf<LineItems>(LineItems(title,price,quantity ,order_number))
//        val orders = Order(customerEmail!!,"fulfilled","200",list)
//        val orderJson= CreatedOrder(orders)
//        createOrderViewModel.createOrder(orderJson)
    }

    private fun checkOrderCreated(){

//        createOrderViewModel.loadData(this)
//        createOrderViewModel.addressDetails.observe(this) {
//            it?.let {
//
//                binding.countryName.text = SpannableStringBuilder("${it.addressList?.get(0)?.country_name?:' '}")
//                Log.i("Menna","checkAddressExist **** $it")
//            }
//        }
    }

    private fun initUI() {
//        binding.order.recyclerShopBag.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//            adapter = cartAdapter
//        }
    }

}