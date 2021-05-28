package com.example.graduationapp.ui.productPageFeature

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.core.favoriteFeature.Favorite
import com.example.graduationapp.R
import com.example.graduationapp.ui.favoriteFeature.adapater.FavoriteAdapter

class ProductPageFragment : Fragment() {

    private lateinit var productPageViewModel: ProductPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.product_page, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        productPageViewModel = ViewModelProvider(this).get(ProductPageViewModel::class.java)


        productPageViewModel.getProductDetails(2222222222)
        productPageViewModel.productDetails.observe(viewLifecycleOwner, Observer {
            Log.i("TAG", "onActivityCreated: ${it.handle}")
        })

    }
}