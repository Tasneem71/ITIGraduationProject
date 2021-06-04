package com.example.graduationapp.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graduationapp.R
import com.example.graduationapp.databinding.ActivityOrderBinding
import com.example.graduationapp.ui.checkoutAddress.CustomerDataActivity

class CartActivity : AppCompatActivity() {
    private lateinit var cartViewModel: CartViewModel
    private lateinit var binding: ActivityOrderBinding
    lateinit var  bagItemAdapter :CartAdapter
    var empty : Boolean =false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar?.hide()
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        bagItemAdapter = CartAdapter(arrayListOf(),cartViewModel)
        initUI()

        cartViewModel.getAllCarts()
        cartViewModel.carts?.observe(this, Observer {
            if (it.isNullOrEmpty()){
                empty = true
            }else{
              empty = false
            }
            bagItemAdapter.updateShopBag(it)
            cartViewModel.allPrice(it)
        })
        cartViewModel.sumOfItems.observe(this,Observer {
            Log.i("Menna","/***"+it)
            binding.total.text =getString(R.string.total)+it.toString()
        })
        binding.checkOut.setOnClickListener(View.OnClickListener {
            if (empty){
                Toast.makeText(this,getString(R.string.empty_cart),Toast.LENGTH_LONG).show()
            }else{
                val intent= Intent(this, CustomerDataActivity::class.java)
                Log.i("Menna", "CustomerDataActivity")
                startActivity(intent)
            }

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
        val itemTouchHelper = ItemTouchHelper(SwipeToDelete(bagItemAdapter,this))
        itemTouchHelper.attachToRecyclerView(binding.recyclerShopBag)
    }
}