package com.example.graduationapp.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.SharedPref
import com.example.graduationapp.databinding.ActivityOrderBinding
import com.example.graduationapp.ui.cart.adapter.CartAdapter
import com.example.graduationapp.ui.checkoutAddress.CustomerDataActivity
import com.example.graduationapp.ui.favoriteFeature.adapater.FavoriteAdapter
import com.example.graduationapp.ui.productPageFeature.ProductDetails
import com.google.android.material.snackbar.Snackbar
import java.time.Duration

class CartActivity : AppCompatActivity(), CartAdapter.OnCartItemListener {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var binding: ActivityOrderBinding
    private var cartAdapter = CartAdapter(emptyList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar?.hide()
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        initUI()

        if (SharedPref.getUserStatus()){
            cartViewModel.getAllCarts()
            binding.notLoged.visibility=View.GONE
            binding.recyclerShopBag.visibility=View.VISIBLE
            binding.checkOut.visibility=View.VISIBLE
            binding.total.visibility=View.VISIBLE
        }else{
            binding.notLoged.visibility=View.VISIBLE
            binding.recyclerShopBag.visibility=View.GONE
            binding.checkOut.visibility=View.GONE
            binding.total.visibility=View.GONE
        }

        cartViewModel.carts?.observe(this, Observer {
            cartAdapter.setData(it)
        })

        cartViewModel.sumOfItems.observe(this, Observer {
            binding.total.text = "Total = " + it.toString()
        })


        binding.checkOut.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CustomerDataActivity::class.java)
            intent.putExtra("price",binding.total.text.toString())
            Log.i("Menna", "CustomerDataActivity")
            startActivity(intent)
        })
        binding.close.setOnClickListener {
            finish()
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
            var position = viewHolder.adapterPosition
            var x = cartAdapter.getData()[position]

            cartViewModel.deleteFromFavorite(x)
            cartViewModel.getAllCarts()

            Snackbar.make(binding.recyclerShopBag, "Item Is Removed", Snackbar.LENGTH_LONG)
                .setAction("Undo", View.OnClickListener {
                     cartViewModel.addToCart(x)
                    cartViewModel.getAllCarts()
                }).show()

        }
    }

override fun onIncreaseCountClick(item: Favorite) {
    if (item.count > 0) {
        cartViewModel.updateCount(item.id, ++(item.count))
        cartViewModel.getAllCarts()
    }
}

override fun onDecreaseCountClick(item: Favorite) {

    if (item.count > 1) {
        cartViewModel.updateCount(item.id, --(item.count))
        cartViewModel.getAllCarts()
    }
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