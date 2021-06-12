package com.example.graduationapp.ui.category

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.core.subFeature.GridSpacingItemDecoration
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.R
import com.example.graduationapp.ui.search.SearchActivity
import com.example.graduationapp.data.Custom_collections
import com.example.graduationapp.data.Products
import com.example.graduationapp.databinding.FragmentCategoryBinding
import com.example.graduationapp.graphql.CollectionsGraphAdapter
import com.example.graduationapp.graphql.GraphViewModel
import com.example.graduationapp.ui.cart.CartActivity
import com.example.graduationapp.ui.favoriteFeature.FavoriteActivity
import com.google.android.material.tabs.TabLayout
import com.example.graduationapp.HomeCollectionQuery


class CategoryFragment : Fragment() ,  TabLayout.OnTabSelectedListener ,CollectionsGraphAdapter.OnHomeItemListener{

    private lateinit var binding : FragmentCategoryBinding
    private lateinit var categoryViewMode : CategoryViewModel
    private lateinit var graphViewModel: GraphViewModel

    var data: List<HomeCollectionQuery.Edge1> = mutableListOf()
    var orignalList: List<HomeCollectionQuery.Edge1> = mutableListOf()


    var kidList: List<HomeCollectionQuery.Edge1> = mutableListOf()
    var menList: List<HomeCollectionQuery.Edge1> = mutableListOf()
    var womenList: List<HomeCollectionQuery.Edge1> = mutableListOf()
    var saleList: List<HomeCollectionQuery.Edge1> = mutableListOf()
    var homepageList: List<HomeCollectionQuery.Edge1> = mutableListOf()
    var  collectionsGraphAdapter = CollectionsGraphAdapter(arrayListOf(),this)


    var currentCollectionTitle=""
    var currentCollectionID : ArrayList<String> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        categoryViewMode = ViewModelProvider(this).get(CategoryViewModel::class.java)
        graphViewModel = ViewModelProvider(this).get(GraphViewModel::class.java)

        setUpTabLayoute()


        graphViewModel.kid.observe(requireActivity(), Observer {
            it?.let{
                kidList= it
            }
        })

        graphViewModel.men.observe(requireActivity(), Observer {
                menList=it
        })

        graphViewModel.women.observe(requireActivity(), Observer {
            it?.let{
                Log.i("category",""+it)
                womenList=it
            }
        })

        graphViewModel.byCollection.observe(requireActivity(), Observer {
            it?.let{
                Log.i("Abdallah", "onCreateView: ${it}")
            }
            Log.i("Abdallah", "onCreateView: ")
        })

        graphViewModel.sale.observe(requireActivity(), Observer {
            it?.let{
                Log.i("category",""+it)
                saleList=it
            }
        })

        graphViewModel.home.observe(requireActivity(), Observer {
            it?.let{
                Log.i("category",""+it)
                homepageList=it
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
            data=orignalList
            var filteredList=data.filter { it.node.productType=="ACCESSORIES" }
            data= filteredList
            collectionsGraphAdapter.setData(data)
        }
        binding.tshirt.setOnClickListener {
            data=orignalList
            var filteredList=data.filter { it.node.productType=="T-SHIRTS" }
            data= filteredList
            collectionsGraphAdapter.setData(data)
        }
        binding.shoes.setOnClickListener {
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

//    private fun loadProducts(id:String) {
//        categoryViewMode.loadProductData(id).observe(requireActivity()) {
//            it?.let {
//                binding.progressBar.visibility=View.GONE
//                data= it.products as ArrayList<Products>
//                orignalList=data
//                adapter.setData(data, requireContext())
//            }
//        }
//    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.i("original","listener")
        when (tab!!.position) {
            0 ->{
                orignalList=homepageList
                collectionsGraphAdapter.setData(homepageList)
            }
            1 -> {
                orignalList=kidList
                collectionsGraphAdapter.setData(kidList)
            }
            2 -> {
                orignalList=menList
                Log.i("original",""+orignalList)
                collectionsGraphAdapter.setData(menList)
            }
            3 ->{
                orignalList=saleList
                collectionsGraphAdapter.setData(saleList)
            }
            4 -> {
                orignalList=womenList
                collectionsGraphAdapter.setData(womenList)
            }
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
        tab1.setText("HOME PAGE")
        tab2.setText("KID")
        tab3.setText("MEN")
        tab4.setText("SALE")
        tab5.setText("WEMEN")
        //tab.setTag(source)
        binding.tabLayout.addTab(tab1)
        binding.tabLayout.addTab(tab2)
        binding.tabLayout.addTab(tab3)
        binding.tabLayout.addTab(tab4)
        binding.tabLayout.addTab(tab5)
        binding.tabLayout.addOnTabSelectedListener(this)
        binding.tabLayout.getTabAt(0)?.select()
    }

    override fun onImageClick(item: HomeCollectionQuery.Edge1) {

    }
}