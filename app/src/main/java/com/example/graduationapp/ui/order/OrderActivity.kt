package com.example.graduationapp.ui.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graduationapp.databinding.ActivityOrderBinding


class OrderActivity : AppCompatActivity() {
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var binding: ActivityOrderBinding
    var  bagItemAdapter = OrderAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar?.hide()
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        initUI()

       // bagItemAdapter.updateShopBag(shopItemsList)
        binding.close.setOnClickListener {
          finish()
        }


        orderViewModel.getAllCarts()
        orderViewModel.carts?.observe(this, Observer {
            bagItemAdapter.updateShopBag(it)
            orderViewModel.allPrice(it)
        })
        orderViewModel.sumOfItems.observe(this,Observer {
            Log.i("Menna","/*******"+it)
           binding.total.text ="Total = "+it.toString()
        })
        binding.checkOut.setOnClickListener(View.OnClickListener {

        })


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