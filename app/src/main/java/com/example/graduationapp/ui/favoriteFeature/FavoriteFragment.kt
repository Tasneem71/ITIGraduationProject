package com.example.graduationapp.ui.favoriteFeature

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.core.favoriteFeature.Favorite
import com.example.graduationapp.R
import com.example.graduationapp.databinding.ActivityLoginBinding
import com.example.graduationapp.databinding.FragmentFavoriteBinding
import com.example.graduationapp.ui.favoriteFeature.adapater.FavoriteAdapter
import com.example.graduationapp.ui.home.HomeViewModel

class FavoriteFragment : Fragment(),FavoriteAdapter.OnEditFavoriteListener {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private  var favoriteAdapter=FavoriteAdapter(emptyList(),this)
    private lateinit var favoriteListRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_favorite, container, false)
        favoriteListRecyclerView=root.findViewById(R.id.favorite_recyclerView)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        // TODO: Use the ViewModel

        favoriteListRecyclerView.adapter = favoriteAdapter
        favoriteListRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        favoriteListRecyclerView.setHasFixedSize(true)

        favoriteViewModel.favorites?.observe(viewLifecycleOwner, Observer {

            favoriteAdapter.setData(it)
        })

        favoriteViewModel.favorites?.observe(viewLifecycleOwner, Observer {
            Log.i("TAG", "onCreateView: ${it.size}")
        })
//        favoriteViewModel.addToFavorite(Favorite(25312512,"Mohamed","abcd",150,""))
//        favoriteViewModel.addToFavorite(Favorite(2532512,"Abdallah","abcd",150,""))
//        favoriteViewModel.addToFavorite(Favorite(2512512,"Ahmed","abcd",150,""))
//        favoriteViewModel.addToFavorite(Favorite(2312512,"Ali","abcd",150,""))
//        favoriteViewModel.addToFavorite(Favorite(2314442512,"Abcd","abcd",150,""))

        favoriteViewModel.getAllFavorite()
    }
    override fun onRemoveFavoriteClick(item: Favorite) {
        favoriteViewModel.deleteFromFavorite(item)
        favoriteViewModel.getAllFavorite()

    }

    override fun onAddToCartClick(item: Favorite) {
        Log.i("TAG", "onAddToCartClick:${favoriteViewModel.isFavorite(item.id)} ")

    }


}