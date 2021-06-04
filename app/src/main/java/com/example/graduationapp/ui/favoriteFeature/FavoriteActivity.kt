package com.example.graduationapp.ui.favoriteFeature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.domain.core.subFeature.GridSpacingItemDecoration
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.R
import com.example.graduationapp.databinding.ActivityFavoriteBinding
import com.example.graduationapp.databinding.ActivityScrollingBinding
import com.example.graduationapp.ui.favoriteFeature.adapater.FavoriteAdapter
import com.example.graduationapp.ui.productPageFeature.ProductDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity(),FavoriteAdapter.OnEditFavoriteListener {
    lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private  var favoriteAdapter= FavoriteAdapter(emptyList(),this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)


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

        favoriteViewModel.getAllFavorite()
    }

    override fun onRemoveFavoriteClick(item: Favorite) {
        favoriteViewModel.deleteFromFavorite(item)
        favoriteViewModel.getAllFavorite()

    }
    override fun onAddToCartClick(item: Favorite) {
        lifecycleScope.launch(Dispatchers.IO) {
            Log.i("TAG", "onAddToCartClick:${favoriteViewModel.isFavorite(item.id)} ")
        }
    }
    override fun onImageClick(item: Favorite) {
        val intent= Intent(this, ProductDetails::class.java)
        intent.putExtra("product_id",item.id.toString())
        startActivity(intent)
    }
}