package com.example.graduationapp.ui.productPageFeature

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.MainActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.Products
import com.example.graduationapp.databinding.ActivityScrollingBinding
import com.example.graduationapp.ui.cart.CartActivity
import com.example.graduationapp.ui.checkoutAddress.CustomerDataActivity
import com.example.graduationapp.ui.favoriteFeature.FavoriteActivity

import com.example.graduationapp.ui.favoriteFeature.FavoriteViewModel
import com.example.graduationapp.utils.Validation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class
ProductDetails : AppCompatActivity() {
    private lateinit var productPageViewModel: ProductPageViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    lateinit var binding:ActivityScrollingBinding
    var currentProduct :Products? = null
    private lateinit var viewPager2: ViewPager2
    val userId = SharedPref.getUserID().toString()
    var product_id=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        productPageViewModel = ViewModelProvider(this).get(ProductPageViewModel::class.java)
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        val intent=intent

        if (intent!=null)
            product_id= intent.getStringExtra("product_id").toString()

        setFavoriteImage(product_id.toLong())
        isCart(product_id.toLong())

        productPageViewModel.productDetails.observe(this, Observer {

           // Log.i("Mohamed", "onCreate: ${it.title}")

            currentProduct=it
            var imgs= listOf<String>()
            imgs=it.images.map { it.src }
            //binding.productPageDiscount.text=it.vendor
            //binding.productPageInventoryQuantity.text= it.variants?.get(0)?.inventory_quantity.toString()
            val adapter = ViewPagerAdapter(imgs)
            viewPager2 = findViewById(R.id.viewPager)
            viewPager2.adapter = adapter
            binding.dotsIndicator.setViewPager2(viewPager2)
            binding.content.productPagePrice.text=it.variants?.get(0)?.price.toString()+" EGP"
            binding.content.productPageTitle.text=it.title
            binding.content.productPageProductType.text=it.product_type
            binding.content.productPageVendor.text=it.vendor
            binding.content.productPageInventoryQuantity.text= it.variants?.get(0)?.inventory_quantity?:this.getString(R.string.notAve)
            binding.content.descriptionTv.text=it.body_html

            val sizes=it.options.filter { it.name=="Size" }
            val colors=it.options.filter { it.name=="Color" }
            if(!sizes.isNullOrEmpty()){
            binding.content.productPageSizeDetails.text= sizes[0].values?.joinToString (",") ?:this.getString(R.string.notAve)
            }
            if(!colors.isNullOrEmpty()){
            binding.content.productPageColorDetails.text= colors[0].values?.joinToString (",") ?:this.getString(R.string.notAve)
            }
            //binding.content.productPageTags.text=it.tags

            val y: String = it.image.src ?: "www.google.com/ss.png/"

            //Glide.with(this).load(y).placeholder(R.drawable.ic_search).into(binding.productPageThumbnail)

            binding.menu.setOnClickListener {
                showPopupMenu(it)

            }

        })



        binding.content.productPageAddToCart.setOnClickListener(View.OnClickListener {
            if(SharedPref.getUserStatus()){
            favoriteViewModel.addToCart(Favorite(currentProduct!!.id.toLong(), currentProduct!!.title,
                currentProduct!!.handle, currentProduct?.variants?.get(0)?.price!!.toInt(),currentProduct!!.image.src,'C',1
                ,currentProduct?.variants?.get(0)!!.id,userId))
            Toast.makeText(this,getString(R.string.has_been_add_to_cart),Toast.LENGTH_LONG).show()
            binding.content.productPageAddToCart.visibility=View.GONE
            binding.content.productPageInCart.visibility=View.VISIBLE
            }else{
                Toast.makeText(this,this.getString(R.string.pleaselogin),Toast.LENGTH_LONG).show()
            }

        })


        binding.content.productPageAddToFavorite.setOnClickListener(View.OnClickListener {
            when(binding.content.productPageAddToFavorite.drawable.constantState)
            {
                resources.getDrawable(R.drawable.favorite).constantState -> {
                    favoriteViewModel.addToFavorite(Favorite(currentProduct!!.id.toLong(), currentProduct!!.title,
                        currentProduct!!.handle, currentProduct?.variants?.get(0)?.price!!.toInt(),currentProduct!!.image.src,'F',1,currentProduct?.variants?.get(0)!!.id,userId))
                    binding.content.productPageAddToFavorite.setImageResource(R.drawable.favorite2)
                }
                resources.getDrawable(R.drawable.favorite2).constantState -> {

                    favoriteViewModel.deleteFromFavorite(product_id.toLong(),userId)
                    binding.content.productPageAddToFavorite.setImageResource(R.drawable.favorite)

                }
            }
            favoriteViewModel.addToFavorite(Favorite(currentProduct!!.id.toLong(), currentProduct!!.title,
                currentProduct!!.handle, currentProduct?.variants?.get(0)?.price!!.toInt(),currentProduct!!.image.src,'F',1,
                currentProduct?.variants?.get(0)!!.id,userId))

        })

        if (SharedPref.getUserStatus()){
            binding.content.productPageAddToCart.visibility=View.VISIBLE
            binding.content.productPageAddToFavorite.visibility=View.VISIBLE
        }else{
            binding.content.productPageAddToCart.visibility=View.GONE
            binding.content.productPageAddToFavorite.visibility=View.GONE
        }

        binding.back.setOnClickListener({
            startActivity(Intent(this, MainActivity::class.java))
        })

    }
    private fun setFavoriteImage(id: Long) {
        lifecycleScope.launch(Dispatchers.IO) {
            var result=favoriteViewModel.isFavorite(id,userId)
            withContext(Dispatchers.Main){
                when(result){
                    0 -> {binding.content.productPageAddToFavorite.setImageResource(R.drawable.favorite) }
                    1 -> {binding.content.productPageAddToFavorite.setImageResource(R.drawable.favorite2) }
                }
            }

        }
    }

    private fun isCart(id: Long) {
        lifecycleScope.launch(Dispatchers.IO) {
            var result=favoriteViewModel.isCart(id,userId)
            withContext(Dispatchers.Main){
                if (result==1){
                    binding.content.productPageAddToCart.visibility=View.GONE
                    binding.content.productPageInCart.visibility=View.VISIBLE
                }else{
                    binding.content.productPageAddToCart.visibility=View.VISIBLE
                    binding.content.productPageInCart.visibility=View.GONE
                }
            }

        }
    }

    fun showPopupMenu(view: View) = PopupMenu(view.context, view).run {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.action_favorite -> {
                    val intent = Intent(applicationContext,FavoriteActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.action_cart -> {
                    val intent = Intent(applicationContext,CartActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
            Toast.makeText(view.context, "You Clicked : ${item.title}", Toast.LENGTH_SHORT).show()
            true
        }
        show()
    }

    override fun onStart() {
        super.onStart()
        if (Validation.isOnline(this)){
            productPageViewModel.getProductDetails(product_id)
        }else{
            Toast.makeText(this,this.getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        }
    }

}