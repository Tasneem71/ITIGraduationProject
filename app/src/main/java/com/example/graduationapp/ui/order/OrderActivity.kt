package com.example.graduationapp.ui.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graduationapp.databinding.ActivityOrderBinding
import com.example.graduationapp.ui.home.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderActivity : AppCompatActivity() {
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var binding: ActivityOrderBinding
    var  bagItemAdapter = OrderAdapter(arrayListOf())
    private lateinit var shopItemsList:ArrayList<Category>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar?.hide()
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        initUI()
        //bagItemAdapter.updateShopBag(shopItemsList)
        binding.close.setOnClickListener {
          finish()
        }
    }
    private fun initUI() {
        binding.recyclerShopBag.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = bagItemAdapter
        }
    }
}