package com.example.graduationapp.ui.productPageFeature

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.domain.core.favoriteFeature.Favorite
import com.example.graduationapp.R
import com.example.graduationapp.data.Products
import com.example.graduationapp.databinding.ProductPageBinding
import com.example.graduationapp.ui.favoriteFeature.FavoriteViewModel
import kotlin.math.log

class ProductDetails : AppCompatActivity() {
    private lateinit var productPageViewModel: ProductPageViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    lateinit var binding:ProductPageBinding
    var currentProduct :Products? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        productPageViewModel = ViewModelProvider(this).get(ProductPageViewModel::class.java)
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        val intent=intent
        var x=""
        if (intent!=null)
            x= intent.getStringExtra("product_id").toString()

        productPageViewModel.getProductDetails(x)
        productPageViewModel.productDetails.observe(this, Observer {

           // Log.i("Mohamed", "onCreate: ${it.title}")

            currentProduct=it
            binding.productPageDiscount.text=it.vendor
            //binding.productPageInventoryQuantity.text= it.variants?.get(0)?.inventory_quantity.toString()
            //binding.productPagePrice.text=it.variants?.get(0)?.price.toString()
            binding.productPageTitle.text=it.title
            binding.productPageProductType.text=it.product_type
            binding.productPageTags.text=it.tags

            val y: String = it.image.src ?: "www.google.com/ss.png/"

            Glide.with(this).load(y).placeholder(R.drawable.ic_search).into(binding.productPageThumbnail)



        })

        binding.productPageAddToCart.setOnClickListener(View.OnClickListener {

        })
        binding.productPageAddToFavorite.setOnClickListener(View.OnClickListener {

            favoriteViewModel.addToFavorite(Favorite(currentProduct!!.id.toLong(), currentProduct!!.title,
                currentProduct!!.handle, currentProduct?.variants?.get(0)?.price!!.toInt(),currentProduct!!.image.src))

        })



    }



}