package com.example.graduationapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graduationapp.R
import com.example.graduationapp.SearchActivity
import com.example.graduationapp.data.Products
import com.example.graduationapp.databinding.FragmentHomeBinding
import com.example.graduationapp.ui.order.OrderActivity
import com.example.graduationapp.ui.category.CategoryViewModel


class HomeFragment : Fragment()  {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var imgas:Array<Int>
    private lateinit var adidusList:ArrayList<Products>
    private lateinit var nikeList:ArrayList<Products>
    private lateinit var pumaList:ArrayList<Products>
    private lateinit var converceList:ArrayList<Products>
    private lateinit var asicsList:ArrayList<Products>
    var  adidasAdapter = ShopCategoryAdapter(arrayListOf())
    var  nikeAdapter = ShopCategoryAdapter(arrayListOf())
    var  pumaAdapter = ShopCategoryAdapter(arrayListOf())
    var  converceAdapter = ShopCategoryAdapter(arrayListOf())
    var  asicsAdapter = ShopCategoryAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        imgas =arrayOf(R.drawable.onlineshopping,R.drawable.op ,R.drawable.lap)

        for (item in imgas)
             showPhotos(item)

        initUI()
        adidusList = ArrayList()
        nikeList = ArrayList()
        pumaList = ArrayList()
        converceList = ArrayList()
        asicsList = ArrayList()

        //*********************************


        binding.cart.setOnClickListener {
            val intent= Intent(this.context, OrderActivity::class.java)
            startActivity(intent)
        }

        binding.search.setOnClickListener {
            val intent= Intent(this.context, SearchActivity::class.java)
            startActivity(intent)
        }


        adidasAdapter.updateCategory(adidusList)
        nikeAdapter.updateCategory(nikeList)
        pumaAdapter.updateCategory(pumaList)
        converceAdapter.updateCategory(converceList)
        asicsAdapter.updateCategory(asicsList)


        loadProducts("268359205062",0)
        loadProducts("268359237830",1)
        loadProducts("268359401670",2)
        loadProducts("268359303366",3)
        loadProducts("268359336134",4)


        return binding.root
    }
    private fun loadProducts(id:String,num:Int) {
        homeViewModel.loadProductData(id,num).observe(requireActivity()) {
            Log.d("data", "  products"+it.products[0].title)
            it?.let {
                when (num) {
                    0 -> {
                        adidusList= it.products as ArrayList<Products>
                        adidasAdapter.updateCategory(adidusList)
                    }
                    1 -> {
                        nikeList= it.products as ArrayList<Products>
                        nikeAdapter.updateCategory(nikeList)
                    }
                    2 -> {
                        pumaList= it.products as ArrayList<Products>
                        pumaAdapter.updateCategory(pumaList)
                    }
                    3 -> {
                        converceList= it.products as ArrayList<Products>
                        converceAdapter.updateCategory(converceList)
                    }
                    4 -> {
                        asicsList= it.products as ArrayList<Products>
                        asicsAdapter.updateCategory(asicsList)
                    }
                }

            }
        }
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