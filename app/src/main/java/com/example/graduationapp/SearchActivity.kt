package com.example.graduationapp

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.SearchViewModel
import com.example.graduationapp.data.Products
import com.example.graduationapp.databinding.ActivitySearchBinding
import com.example.graduationapp.ui.home.ShopCategoryAdapter


class SearchActivity : AppCompatActivity() {
    lateinit var binding:ActivitySearchBinding
    private lateinit var searchViewMode : SearchViewModel
    private lateinit var filteredList:ArrayList<Products>
    private lateinit var allList:ArrayList<Products>
    var  searchAdapter = ShopCategoryAdapter(arrayListOf())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_search)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewMode = ViewModelProvider(this).get(SearchViewModel::class.java)
        filteredList = ArrayList()
        allList = ArrayList()
        initUi()
        searchAdapter.updateCategory(filteredList)

        searchViewMode.getAllProducts()
        searchViewMode.getAllProductsLiveData.observe(this) {

            it?.let {
                allList= it.products as ArrayList<Products>
            }

        }

        binding.searchtv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence,start: Int,count: Int,after: Int) {}

            override fun onTextChanged(s: CharSequence,start: Int,before: Int,count: Int) {}

            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })


    }

    private fun filter(text: String) {
        val filteredList: ArrayList<Products> = ArrayList()
        for (item in allList) {
            if (item.title.toLowerCase().contains(text.toLowerCase())|| item.product_type.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
        searchAdapter.updateCategory(filteredList)
    }


    private fun initUi() {
        binding.searchRecycle.apply {
            layoutManager = GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
            adapter = searchAdapter

        }




    }

}