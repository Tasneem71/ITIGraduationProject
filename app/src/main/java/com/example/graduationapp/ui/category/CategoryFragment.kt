package com.example.graduationapp.ui.category

import android.R.attr.fragment
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.example.graduationapp.R
import com.example.graduationapp.data.Custom_collections
import com.example.graduationapp.data.Products
import com.example.graduationapp.databinding.FragmentCategoryBinding
import com.google.android.material.tabs.TabLayout


class CategoryFragment : Fragment() ,  TabLayout.OnTabSelectedListener {

    private lateinit var binding : FragmentCategoryBinding
    private lateinit var categoryViewMode : CategoryViewModel
    var data: ArrayList<Products> = ArrayList()
    var orignalList: ArrayList<Products> = ArrayList()
    lateinit var adapter: CategoryAdapter
    var currentCollectionTitle=""
    var currentCollectionID : ArrayList<String> = ArrayList()
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        categoryViewMode = ViewModelProvider(this).get(CategoryViewModel::class.java)

        categoryViewMode.loadData(requireContext()).observe(requireActivity(), {
            Log.d("data", "  custom_collections"+it.custom_collections[1].title)
//            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(it.custom_collections[1].title));
//            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(it.custom_collections[2].title));
//            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(it.custom_collections[3].title));
//            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(it.custom_collections[4].title));
            setUpTabLayoute(it.custom_collections)
        })

        loadProducts("267715608774")


        adapter = CategoryAdapter()
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.categoryRecycler.setLayoutManager(gridLayoutManager)
        adapter.setData(data, requireContext())
        binding.categoryRecycler.adapter = adapter

        binding.acce.setOnClickListener {
            data=orignalList
            var filteredList=data.filter { it.product_type=="ACCESSORIES" }
            data= filteredList as ArrayList<Products>
            adapter.setData(data, requireContext())
        }
        binding.tshirt.setOnClickListener {
            data=orignalList
            var filteredList=data.filter { it.product_type=="T-SHIRTS" }
            data= filteredList as ArrayList<Products>
            adapter.setData(data, requireContext())
        }
        binding.shoes.setOnClickListener {
            data=orignalList
            var filteredList=data.filter { it.product_type=="SHOES" }
            data= filteredList as ArrayList<Products>
            adapter.setData(data, requireContext())
        }

        return binding.root
    }

    private fun loadProducts(id:String) {
        categoryViewMode.loadProductData(id).observe(requireActivity(), {
            Log.d("data", "  products"+it.products[0].title)
            it?.let {
                data= it.products as ArrayList<Products>
                orignalList=data
                adapter.setData(data, requireContext())
            }
        })
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.d("adapter", "tab")
        val source=tab?.tag as Custom_collections
        categoryViewMode.loadProductData(source.id)
        when (tab!!.position) {
            0 -> loadProducts(currentCollectionID[0])
            1 -> loadProducts(currentCollectionID[1])
            2 -> loadProducts(currentCollectionID[2])
            3 -> loadProducts(currentCollectionID[3])
            4 -> loadProducts(currentCollectionID[4])
        }

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }
    private fun setUpTabLayoute(sources: List<Custom_collections?>?) {
        for (source in sources.orEmpty()) {
            val tab = binding.tabLayout.newTab()
            tab.setText(source?.title)
            tab.setTag(source)
            binding.tabLayout.addTab(tab)
            currentCollectionTitle= source!!.title
            currentCollectionID.add(source!!.id)
            Log.i("tasneem",""+currentCollectionID)

        }
        binding.tabLayout.addOnTabSelectedListener(this)
        binding.tabLayout.getTabAt(0)?.select()
    }
}