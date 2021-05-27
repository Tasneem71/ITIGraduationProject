package com.example.graduationapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graduationapp.R
import com.example.graduationapp.databinding.FragmentHomeBinding
import com.example.graduationapp.ui.order.OrderActivity

class HomeFragment : Fragment()  {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var imgas:Array<Int>
    private lateinit var dataList:ArrayList<Category>
    var  adidasAdapter = ShopCategoryAdapter(arrayListOf())
    var  nikeAdapter = ShopCategoryAdapter(arrayListOf())
    var  pumaAdapter = ShopCategoryAdapter(arrayListOf())
    var  converceAdapter = ShopCategoryAdapter(arrayListOf())
    var  asicsAdapter = ShopCategoryAdapter(arrayListOf())

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        imgas =arrayOf(R.drawable.onlineshopping,R.drawable.op ,R.drawable.lap)

        for (item in imgas)
             showPhotos(item)

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

        adidasAdapter.updateCategory(dataList)
        nikeAdapter.updateCategory(dataList)
        pumaAdapter.updateCategory(dataList)
        converceAdapter.updateCategory(dataList)
        asicsAdapter.updateCategory(dataList)
        //**************

        binding.cart.setOnClickListener {
            val intent= Intent(this.context, OrderActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
    private fun initUI() {
        binding.recyclerShopCategory.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = adidasAdapter
        }
        binding.second.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = nikeAdapter
        }
        binding.third.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = pumaAdapter
        }
        binding.fourth.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = converceAdapter
        }
        binding.fifth.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = asicsAdapter
        }

    }
    private fun showPhotos(img: Int){
        val image:ImageView = ImageView(context)
        image.setBackgroundResource(img)
        binding.flipper.addView(image)
        binding.flipper.flipInterval = 4000
        binding.flipper.isAutoStart = true

    }

}