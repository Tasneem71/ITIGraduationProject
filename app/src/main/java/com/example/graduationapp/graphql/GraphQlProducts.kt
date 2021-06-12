package com.example.graduationapp.graphql

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.core.subFeature.GridSpacingItemDecoration
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.HomeCollectionQuery
import com.example.graduationapp.data.Products
import com.example.graduationapp.databinding.ActivityGraphQlProductsBinding


class GraphQlProducts : AppCompatActivity() ,CollectionsGraphAdapter.OnHomeItemListener{
    private lateinit var homeViewModel: GraphViewModel
    private lateinit var binding: ActivityGraphQlProductsBinding
    private lateinit var adidusList:ArrayList<Products>
    private lateinit var nikeList:ArrayList<Products>
    private lateinit var pumaList:ArrayList<Products>
    private lateinit var converceList:ArrayList<Products>
    private lateinit var asicsList:ArrayList<Products>

    var  adidasAdapter = CollectionsGraphAdapter(arrayListOf(),this,homeViewModel)
    var  nikeAdapter = CollectionsGraphAdapter(arrayListOf(),this,homeViewModel)
    var  pumaAdapter = CollectionsGraphAdapter(arrayListOf(),this,homeViewModel)
    var  converceAdapter = CollectionsGraphAdapter(arrayListOf(),this,homeViewModel)
    var  asicsAdapter = CollectionsGraphAdapter(arrayListOf(),this,homeViewModel)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphQlProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel = ViewModelProvider(this).get(GraphViewModel::class.java)

        binding.fifth.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.fifth.setHasFixedSize(true)
        binding.fifth.addItemDecoration(
            GridSpacingItemDecoration(1,
                RecyclerViewAnimation.dpToPx(6),true)
        )
        binding.fifth.itemAnimator= DefaultItemAnimator()
        binding.fifth.adapter = adidasAdapter
        homeViewModel.nike?.observe(this, Observer {
            Log.i("tasneem",""+it)
            adidasAdapter.setData(it)
        })

    }

    override fun onImageClick(item: HomeCollectionQuery.Edge1) {

    }

    override fun onFavImageClick(item: HomeCollectionQuery.Edge1) {
    }

    override fun onFavDeleImageClick(item: HomeCollectionQuery.Edge1) {
    }
}