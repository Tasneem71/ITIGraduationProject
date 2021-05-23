package com.example.graduationapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graduationapp.R
import com.example.graduationapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() , TabLayout.OnTabSelectedListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var imgas:Array<Int>
    private lateinit var dataList:ArrayList<Category>
    var  shopCategoryAdapter = ShopCategoryAdapter(arrayListOf())

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        imgas =arrayOf(R.drawable.onlineshopping,R.drawable.op ,R.drawable.lap)

        for (item in imgas)
        {
            showPhotos(item)
        }
        initUI()
        dataList = ArrayList()
        dataList.add(Category("the ",R.drawable.lap))
        dataList.add(Category("the ",R.drawable.lap))
        dataList.add(Category("the ",R.drawable.lap))
        dataList.add(Category("the ",R.drawable.lap))
        dataList.add(Category("the ",R.drawable.lap))
        dataList.add(Category("the ",R.drawable.lap))
        dataList.add(Category("the ",R.drawable.lap))
        dataList.add(Category("the ",R.drawable.lap))
        //*********************************
        binding.tabLayout.addTab( binding.tabLayout.newTab().setText("Woman"));
        binding.tabLayout.addTab( binding.tabLayout.newTab().setText("Man"));
        binding.tabLayout.addTab( binding.tabLayout.newTab().setText("Kid"));
        binding.tabLayout.addTab( binding.tabLayout.newTab().setText("Kitchen"));
        binding.tabLayout.addTab( binding.tabLayout.newTab().setText("tab5"));
        binding.tabLayout.addTab( binding.tabLayout.newTab().setText("tab6"));

        shopCategoryAdapter.updateCategory(dataList)
        return binding.root
    }
    private fun initUI() {
        binding.recyclerShopCategory.apply {
            layoutManager = GridLayoutManager(context,3)
            adapter = shopCategoryAdapter
        }

    }
    private fun showPhotos(img: Int){
        val image:ImageView = ImageView(context)
        image.setBackgroundResource(img)
        binding.flipper.addView(image)
        binding.flipper.flipInterval = 4000
        binding.flipper.isAutoStart = true

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        TODO("Not yet implemented")
    }
}