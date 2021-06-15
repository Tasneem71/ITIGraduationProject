package com.example.graduationapp.ui.favoriteFeature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.domain.core.subFeature.GridSpacingItemDecoration
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.SharedPref
import com.example.graduationapp.databinding.ActivityFavoriteBinding
import com.example.graduationapp.ui.addressbook.adapater.FavoriteAdapter
import com.example.graduationapp.ui.productPageFeature.ProductDetails

class FavoriteActivity : AppCompatActivity(), FavoriteAdapter.OnEditFavoriteListener {
    lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private  var favoriteAdapter= FavoriteAdapter(emptyList(),this)
    private lateinit var userId :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        userId = SharedPref.getUserID().toString()


        binding.favoriteRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.favoriteRecyclerView.setHasFixedSize(true)
        binding.favoriteRecyclerView.addItemDecoration(
            GridSpacingItemDecoration(1,
                RecyclerViewAnimation.dpToPx(6),true)
        )
        binding.favoriteRecyclerView.itemAnimator= DefaultItemAnimator()
        binding.favoriteRecyclerView.adapter = favoriteAdapter
        favoriteViewModel.favorites?.observe(this, Observer {
            favoriteAdapter.setData(it)

        })

        if(SharedPref.getUserStatus()){
            favoriteViewModel.getAllFavorite(userId)
            binding.notLoged.visibility=View.GONE
            binding.favoriteRecyclerView.visibility=View.VISIBLE
        }else{
            binding.notLoged.visibility=View.VISIBLE
            binding.favoriteRecyclerView.visibility=View.GONE
        }
    }

    override fun onRemoveFavoriteClick(item: Favorite) {
        favoriteViewModel.deleteFromFavorite(item)
        favoriteViewModel.getAllFavorite(userId)

    }
    override fun onAddToCartClick(item: Favorite) {
        favoriteViewModel.moveToCart(item.id,userId)
        favoriteViewModel.getAllFavorite(userId)

    }
    override fun onImageClick(item: Favorite) {
        val intent= Intent(this, ProductDetails::class.java)
        intent.putExtra("product_id",item.id.toString())
        startActivity(intent)
    }
}