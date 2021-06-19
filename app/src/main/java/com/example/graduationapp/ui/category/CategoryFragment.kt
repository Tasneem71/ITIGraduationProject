package com.example.graduationapp.ui.category

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.core.feature.favoriteFeature.Favorite
import com.example.domain.core.subFeature.GridSpacingItemDecoration
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.GetProductsByCollectionIDQuery
import com.example.graduationapp.R
import com.example.graduationapp.ui.search.SearchActivity
import com.example.graduationapp.databinding.FragmentCategoryBinding
import com.example.graduationapp.graphql.GraphViewModel
import com.example.graduationapp.ui.cart.CartActivity
import com.example.graduationapp.ui.favoriteFeature.FavoriteActivity
import com.google.android.material.tabs.TabLayout
import com.example.graduationapp.SharedPref
import com.example.graduationapp.ui.productPageFeature.ProductDetails
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CategoryFragment : Fragment() ,  TabLayout.OnTabSelectedListener , categoryGraphAdapter.OnHomeItemListener{

    private lateinit var binding : FragmentCategoryBinding
    private lateinit var graphViewModel: GraphViewModel

    var data: List<GetProductsByCollectionIDQuery.Edge> = mutableListOf()
    var orignalList: List<GetProductsByCollectionIDQuery.Edge> = mutableListOf()


    var kidList: List<GetProductsByCollectionIDQuery.Edge> = mutableListOf()
    var menList: List<GetProductsByCollectionIDQuery.Edge> = mutableListOf()
    var womenList: List<GetProductsByCollectionIDQuery.Edge> = mutableListOf()
    var saleList: List<GetProductsByCollectionIDQuery.Edge> = mutableListOf()
    var homepageList: List<GetProductsByCollectionIDQuery.Edge> = mutableListOf()
    lateinit var  collectionsGraphAdapter: categoryGraphAdapter



    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        graphViewModel = ViewModelProvider(this).get(GraphViewModel::class.java)

        setUpTabLayoute()
        collectionsGraphAdapter = categoryGraphAdapter(arrayListOf(),this,graphViewModel)


        graphViewModel.cartCount.observe(viewLifecycleOwner, Observer {

            Log.i("cart", "onCreateView: BADGE #  $it")
            when(it){
                0 -> {
                    binding.badge.visibility=View.INVISIBLE
                }
                else -> {
                    binding.badge.visibility=View.VISIBLE
                    binding.badge.setText(it.toString())

                }
            }
        })

        graphViewModel.getCollection("gid://shopify/Collection/267715608774",0)

        graphViewModel.kid.observe(requireActivity(), Observer {
            it?.let{
                binding.progressBar.visibility=View.GONE
                kidList= it
                orignalList=kidList
                collectionsGraphAdapter.setData(kidList)
            }
        })

        graphViewModel.men.observe(requireActivity(), Observer {
            binding.progressBar.visibility=View.GONE
            menList=it
            orignalList=menList
            Log.i("original",""+orignalList)
            collectionsGraphAdapter.setData(menList)
        })

        graphViewModel.women.observe(requireActivity(), Observer {
            it?.let{
                Log.i("category",""+it)
                binding.progressBar.visibility=View.GONE
                womenList=it
                orignalList=womenList
                collectionsGraphAdapter.setData(womenList)
            }
        })

        graphViewModel.sale.observe(requireActivity(), Observer {
            it?.let{
                Log.i("category",""+it)
                binding.progressBar.visibility=View.GONE
                saleList=it
                orignalList=saleList
                collectionsGraphAdapter.setData(saleList)
            }
        })

        graphViewModel.home.observe(requireActivity(), Observer {
            it?.let{
                Log.i("category",""+it)
                binding.progressBar.visibility=View.GONE
                homepageList=it
                orignalList=homepageList
                collectionsGraphAdapter.setData(homepageList)
            }
        })

        val gridLayoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL,false)
        binding.categoryRecycler.layoutManager = gridLayoutManager
        binding.categoryRecycler.addItemDecoration(GridSpacingItemDecoration(1,
            RecyclerViewAnimation.dpToPx(6),true))
        binding.categoryRecycler.itemAnimator= DefaultItemAnimator()

        collectionsGraphAdapter.setData(data)
        binding.categoryRecycler.adapter = collectionsGraphAdapter

        binding.acce.setOnClickListener {
            binding.acce.setBackgroundColor(Color.GRAY)
            binding.tshirt.setBackgroundColor(Color.WHITE)
            binding.shoes.setBackgroundColor(Color.WHITE)
            data=orignalList
            var filteredList=data.filter { it.node.productType=="ACCESSORIES" }
            data= filteredList
            collectionsGraphAdapter.setData(data)
        }
        binding.tshirt.setOnClickListener {
            binding.tshirt.setBackgroundColor(Color.GRAY)
            binding.acce.setBackgroundColor(Color.WHITE)
            binding.shoes.setBackgroundColor(Color.WHITE)
            data=orignalList
            var filteredList=data.filter { it.node.productType=="T-SHIRTS" }
            data= filteredList
            collectionsGraphAdapter.setData(data)
        }
        binding.shoes.setOnClickListener {
            binding.acce.setBackgroundColor(Color.WHITE)
            binding.tshirt.setBackgroundColor(Color.WHITE)
            binding.shoes.setBackgroundColor(Color.GRAY)
            data=orignalList
            var filteredList=data.filter { it.node.productType=="SHOES" }
            data= filteredList
            collectionsGraphAdapter.setData(data)
        }

        binding.searchIcon.setOnClickListener {
            val intent= Intent(this.context, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.favo.setOnClickListener {
            val intent= Intent(this.context, FavoriteActivity::class.java)
            startActivity(intent)
        }
        binding.cartIcon.setOnClickListener {
            val intent= Intent(this.context, CartActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
    override fun onStart() {
        super.onStart()
        graphViewModel.checkNetwork()
        graphViewModel.network.observe(requireActivity()){
            if(!it){
                Toast.makeText(requireContext(),this.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.i("original","listener")
        when (tab!!.position) {
            0 -> graphViewModel.getCollection("gid://shopify/Collection/267715608774",0)
            1 -> graphViewModel.getCollection("gid://shopify/Collection/268359663814",1)
            2 -> graphViewModel.getCollection("gid://shopify/Collection/268359598278",2)
            3 ->graphViewModel.getCollection("gid://shopify/Collection/268359696582",3)
            4 ->graphViewModel.getCollection("gid://shopify/Collection/268359631046",4)
        }

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
    private fun setUpTabLayoute() {
        val tab1 = binding.tabLayout.newTab()
        val tab2 = binding.tabLayout.newTab()
        val tab3 = binding.tabLayout.newTab()
        val tab4 = binding.tabLayout.newTab()
        val tab5 = binding.tabLayout.newTab()
        tab1.setText(this.getString(R.string.frontpage))
        tab2.setText(this.getString(R.string.kid))
        tab3.setText(this.getString(R.string.men))
        tab4.setText(this.getString(R.string.sale))
        tab5.setText(this.getString(R.string.women))
        //tab.setTag(source)
        binding.tabLayout.addTab(tab1)
        binding.tabLayout.addTab(tab2)
        binding.tabLayout.addTab(tab3)
        binding.tabLayout.addTab(tab4)
        binding.tabLayout.addTab(tab5)
        binding.tabLayout.addOnTabSelectedListener(this)
        binding.tabLayout.getTabAt(0)?.select()
    }

    override fun onImageClick(item: GetProductsByCollectionIDQuery.Edge) {
        //splitId(item.node.id)
        val intent= Intent(this.context, ProductDetails::class.java)
        intent.putExtra("product_id",item.node.legacyResourceId.toString())
        this.context?.startActivity(intent)
    }

    override fun onFavImageClick(item: GetProductsByCollectionIDQuery.Edge) {
        graphViewModel.addToFavorite(
            Favorite(item.node.legacyResourceId.toString().toLong(),item.node.title,item.node.handle,
            item.node.variants.edges[0].node.price.toInt(),item.node.featuredImage!!.originalSrc.toString(),'F',
            1,splitId(item.node.variants.edges[0].node.id),
                SharedPref.getUserID().toString())
        )
    }

    override fun onFavDeleImageClick(item: GetProductsByCollectionIDQuery.Edge) {
        graphViewModel.deleteFromFavorite(item.node.legacyResourceId.toString().toLong(),SharedPref.getUserID().toString())
    }

    override fun oncartImageClick(item: GetProductsByCollectionIDQuery.Edge) {
        CoroutineScope(Dispatchers.IO).launch {
        graphViewModel.addToCart(
            Favorite(item.node.legacyResourceId.toString().toLong(),item.node.title,item.node.handle,
            item.node.variants.edges[0].node.price.toInt(),item.node.featuredImage!!.originalSrc.toString(),'C',
            1,splitId(item.node.variants.edges[0].node.id),
                SharedPref.getUserID().toString()))}.invokeOnCompletion {
            graphViewModel.cartCount(SharedPref.getUserID().toString())
        }
    }

    override fun oncartDeleImageClick(item: GetProductsByCollectionIDQuery.Edge) {
        CoroutineScope(Dispatchers.IO).launch {
        graphViewModel.deleteFromCart(item.node.legacyResourceId.toString().toLong(),
            SharedPref.getUserID().toString())}.invokeOnCompletion {
            graphViewModel.cartCount(SharedPref.getUserID().toString())
        }
    }
    fun splitId(id:String): String{
        val delim = "/"
        val list = id.split(delim)
        return list.get(list.size-1)
    }

    override fun onResume() {
        super.onResume()
        graphViewModel.cartCount(SharedPref.getUserID().toString())
        collectionsGraphAdapter.setData(orignalList)
        if (SharedPref.getUserStatus()){
            binding.badge.visibility=View.VISIBLE
        }else{
            binding.badge.visibility=View.GONE
        }

    }

}