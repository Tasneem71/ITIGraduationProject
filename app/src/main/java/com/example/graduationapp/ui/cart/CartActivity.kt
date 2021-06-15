package com.example.graduationapp.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.MainActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.databinding.ActivityOrderBinding
import com.example.graduationapp.ui.cart.adapter.CartAdapter
import com.example.graduationapp.ui.checkoutAddress.CustomerDataActivity
import com.example.graduationapp.ui.favoriteFeature.FavoriteActivity
import com.example.graduationapp.ui.paymentsummary.PaymentSummary
import com.example.graduationapp.ui.productPageFeature.ProductDetails

import com.google.android.material.snackbar.Snackbar

class CartActivity : AppCompatActivity(), CartAdapter.OnCartItemListener {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var binding: ActivityOrderBinding
    private var cartAdapter = CartAdapter(emptyList(), this)
    private lateinit var userId :String
    var empty : Boolean =false


    var code=false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar?.hide()
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = SharedPref.getUserID().toString()
        Log.i("Menna", "user id == "+userId)

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        initUI()

        if (SharedPref.getUserStatus()){
            cartViewModel.getAllCarts(userId)
            binding.notLoged.visibility=View.GONE
            binding.recyclerShopBag.visibility=View.VISIBLE
            binding.checkOut.visibility=View.VISIBLE
        }else{
            binding.notLoged.visibility=View.VISIBLE
            binding.recyclerShopBag.visibility=View.GONE
            binding.checkOut.visibility=View.GONE

        }

        cartViewModel.carts?.observe(this, Observer {
            empty = it.isNullOrEmpty()
            if (empty){
                binding.total.visibility=View.INVISIBLE
                binding.total2.visibility=View.INVISIBLE
            }else{
                binding.total.visibility=View.VISIBLE
                binding.total2.visibility=View.VISIBLE
            }

            cartAdapter.setData(it)

        })

        cartViewModel.sumOfItems.observe(this, Observer {
            if(!empty){
                binding.total2.text =it.toString()
            }
        })
        cartViewModel.getAllCustomerAddress(userId)
//        cartViewModel.allCustomerAddresses.observe(this, Observer {
//            it?.let {
//                val defaultID = it.filter { it?.default == true }.map { it?.id }.get(0).toString()
//                Log.i("Menna","defalt it //////////// "+defaultID)
//                if (defaultID.isNullOrEmpty()){
//                    SharedPref.setAddressID(defaultID)
//                }
//            }
//        })
        cartViewModel.allCustomerAddresses.observe(this, Observer { it ->
            if (!it.isNullOrEmpty())
            {
                Log.i("Menna", "onCreate: not null not empty")
                SharedPref.setAddressID(it.filter { it?.default == true }.map { it?.id }.get(0).toString())
                code=true

            }
            else
            {
                Log.i("Menna", "onCreate: nullllllllllllllllllllllllllllllllllllllllllllllllllllllll")
               // SharedPref.setAddressID(null)
                code=false


            }
        })

        binding.checkOut.setOnClickListener {

            if(empty){
                Toast.makeText(this,this.getString(R.string.empty_cart),Toast.LENGTH_LONG).show()
            }else{
                Log.i("Menna", "onCreate   shared: ${SharedPref.getAddressID()}")
                if(!code){
                    val intent = Intent(this, CustomerDataActivity::class.java)
                    intent.putExtra("price", binding.total2.text.toString())
                    intent.putExtra("Key", "New")
                    startActivity(intent)
                }else{
                    val intent = Intent(this, PaymentSummary::class.java)
                    intent.putExtra("price", binding.total2.text.toString())
                    startActivity(intent)
                }
            }

        }
        binding.favorite.setOnClickListener {
            startActivity(Intent(this,FavoriteActivity::class.java))
        }

        binding.close.setOnClickListener {
         startActivity(Intent(this,MainActivity::class.java))
        }

    }

    private fun initUI() {
        binding.recyclerShopBag.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerShopBag)
    }


    private val swipeToDeleteCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),
        ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            //TODO("Not yet implemented")
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val item = cartAdapter.getData()[position]

            cartViewModel.deleteFromFavorite(item)
            cartViewModel.getAllCarts(userId)

            Snackbar.make(binding.recyclerShopBag, "Item Is Removed", Snackbar.LENGTH_LONG)
                .setAction("Undo", View.OnClickListener {
                     cartViewModel.addToCart(item)
                    cartViewModel.getAllCarts(userId)
                }).show()

        }
    }

    override fun onIncreaseCountClick(item: Favorite) {
        if (item.count > 0) {
            cartViewModel.updateCount(item.id, ++(item.count),userId)
            cartViewModel.getAllCarts(userId)
        }
    }

    override fun onDecreaseCountClick(item: Favorite) {

        if (item.count > 1) {
            cartViewModel.updateCount(item.id, --(item.count),userId)
            cartViewModel.getAllCarts(userId)
        }
    }

    override fun onImageCountClick(item: Favorite) {
        val intent= Intent(this, ProductDetails::class.java)
        intent.putExtra("product_id",item.id .toString())
        startActivity(intent)
    }



}

//
//fun deleteItem(pos: Int) {
//    val i=( itemsList[pos].price *itemsList[pos].count)
//    Log.i("Menna",(itemsList[pos].price).toString()+"***count***"+(itemsList[pos].count).toString())
//    val old = cartViewModel.sumOfItems.value!!
//    val sum = old - i
//    cartViewModel.sumOfItems.postValue(sum)
//    //*******************************************
//    cartViewModel.deleteFromFavorite(itemsList[pos])
//    itemsList.removeAt(pos)
//    notifyItemRemoved(pos)
//}