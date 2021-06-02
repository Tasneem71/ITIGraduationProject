package com.example.graduationapp.create_order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.graduationapp.LoginViewModel
import com.example.graduationapp.MainActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.*
import com.example.graduationapp.databinding.ActivityCreateOrderBinding
import com.example.graduationapp.databinding.ActivityLoginBinding

class CreateOrderActivity : AppCompatActivity() {

    private lateinit var createOrderViewModel: CreateOrderViewModel
    private lateinit var binding: ActivityCreateOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_order)
        binding = ActivityCreateOrderBinding.inflate(layoutInflater)
        createOrderViewModel = ViewModelProvider(this).get(CreateOrderViewModel::class.java)

       binding.order.close.setOnClickListener({
           Log.d("order", "post")
           createOrderApi()
       })
    }

    private fun createOrderApi() {
        Log.d("order", "post")
        val customerEmail =SharedPref.getUserEmail()
        val title = binding.order.titletv.text.toString()
        val price = binding.order.titletv.text.toString()
        val quantity = 2
        val variant_title = binding.order.titletv.text.toString()
        val order_number = 5
        val list : List<LineItems> = mutableListOf<LineItems>(LineItems(title,price,quantity ,variant_title,order_number))
        val orders = Order(customerEmail!!,"fulfilled","200",list)
        val orderJson= CreatedOrder(orders)
        createOrderViewModel.createOrder(orderJson)
    }

}