package com.example.graduationapp.ui.productPageFeature

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.ViewPagerAdapter
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.graduationapp.R
import com.example.graduationapp.data.Products
import com.example.graduationapp.databinding.ActivityScrollingBinding

import com.example.graduationapp.ui.favoriteFeature.FavoriteViewModel

class
ProductDetails : AppCompatActivity() {
    private lateinit var productPageViewModel: ProductPageViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    lateinit var binding:ActivityScrollingBinding
    var currentProduct :Products? = null
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
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
            var imgs= listOf<String>()
            imgs=it.images.map { it.src }
            //binding.productPageDiscount.text=it.vendor
            //binding.productPageInventoryQuantity.text= it.variants?.get(0)?.inventory_quantity.toString()
            val adapter = ViewPagerAdapter(imgs)
            viewPager2 = findViewById(R.id.viewPager)
            viewPager2.adapter = adapter
            binding.content.productPagePrice.text=it.variants?.get(0)?.price.toString()+" EG"
            binding.content.productPageTitle.text=it.title
            binding.content.productPageProductType.text=it.product_type
            binding.content.productPageVendor.text=it.vendor
            binding.content.productPageInventoryQuantity.text= it.variants?.get(0)?.inventory_quantity?:"not Available"
            val sizes=it.options.filter { it.name=="Size" }
            val colors=it.options.filter { it.name=="Color" }
            binding.content.productPageSizeDetails.text= sizes[0].values?.get(0) ?:"not Available"
            binding.content.productPageColorDetails.text= colors[0].values?.get(0) ?:"not Available"
            //binding.content.productPageTags.text=it.tags

            val y: String = it.image.src ?: "www.google.com/ss.png/"

            //Glide.with(this).load(y).placeholder(R.drawable.ic_search).into(binding.productPageThumbnail)



        })


        binding.content.productPageAddToCart.setOnClickListener(View.OnClickListener {
            favoriteViewModel.addToFavorite(Favorite(currentProduct!!.id.toLong(), currentProduct!!.title,
                currentProduct!!.handle, currentProduct?.variants?.get(0)?.price!!.toInt(),currentProduct!!.image.src,'C'))

        })
        binding.content.productPageAddToFavorite.setOnClickListener(View.OnClickListener {

            favoriteViewModel.addToFavorite(Favorite(currentProduct!!.id.toLong(), currentProduct!!.title,
                currentProduct!!.handle, currentProduct?.variants?.get(0)?.price!!.toInt(),currentProduct!!.image.src,'F'))

        })



    }



}