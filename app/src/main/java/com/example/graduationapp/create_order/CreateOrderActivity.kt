package com.example.graduationapp.create_order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.LoginViewModel
import com.example.graduationapp.MainActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.*
import com.example.graduationapp.databinding.ActivityCreateOrderBinding
import com.example.graduationapp.databinding.ActivityLoginBinding
import com.example.graduationapp.ui.cart.CartViewModel
import com.example.graduationapp.ui.cart.adapter.CartAdapter

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

        createOrderViewModel.getAllOrderd()

        createOrderViewModel.orders?.observe(this, Observer {
            Log.d("tag","in observe")
            it?.let {
                var count = it.map { it.count*it.price }.reduce { acc, i ->  acc+i  }.toString()
                Log.d("tag","count"+count)
                val email = SharedPref.getUserEmail().toString()
                val listOfOrder = createOrderApi(it)
              createOrderViewModel.createOrder(CreatedOrder(Order(email,"fulfilled",count,listOfOrder)))
                Log.d("tag","list"+listOfOrder)

            }
        })

    }

    private fun createOrderApi(list:List<Favorite>) : List<LineItems> {
        val lines : MutableList<LineItems> = mutableListOf<LineItems>()
       for (item in list){
           val lineObject : LineItems = LineItems(item.title,item.price.toString(),item.count,item.varient_id)
           lines.add(lineObject)
       }
        return lines
    }


    private fun initUI() {
        binding.order.titile
    }

}