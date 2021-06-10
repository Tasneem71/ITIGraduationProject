package com.example.graduationapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.graduationapp.R
import com.example.graduationapp.data.Products
import com.example.graduationapp.databinding.ActivitySearchBinding
import com.example.graduationapp.ui.home.ShopCategoryAdapter
import com.example.graduationapp.ui.productPageFeature.ProductDetails


class SearchActivity : AppCompatActivity() , ShopCategoryAdapter.OnHomeItemListener , AdapterView.OnItemSelectedListener{
    lateinit var binding: ActivitySearchBinding
    private lateinit var searchViewMode: SearchViewModel
    private lateinit var filteredList: ArrayList<Products>
    private lateinit var vendorFilteredList: ArrayList<Products>
    private lateinit var allList: ArrayList<Products>
    var searchAdapter = ShopCategoryAdapter(arrayListOf(),this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_search)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewMode = ViewModelProvider(this).get(SearchViewModel::class.java)
        filteredList = ArrayList()
        vendorFilteredList = ArrayList()
        allList = ArrayList()
        initUi()
        searchAdapter.updateCategory(filteredList)

        searchViewMode.getAllProducts()
        searchViewMode.getAllProductsLiveData.observe(this) {

            it?.let {
                allList = it.products as ArrayList<Products>
                filteredList=allList
                vendorFilteredList = allList
                searchAdapter.updateCategory(filteredList)
            }

        }

        binding.searchtv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })

        binding.sort1.setOnClickListener {
            sorting("l")
        }
        binding.sort2.setOnClickListener {
            println("pressed on high")
            sorting("h")
        }

        binding.arrowBtn.setOnClickListener {

            println("arrow pressed")

            if (binding.hiddenView.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(binding.baseCardview,AutoTransition())
                binding.hiddenView.setVisibility(View.GONE)
                binding.arrowBtn.setImageResource(R.drawable.ic_action_less)
            } else {
                TransitionManager.beginDelayedTransition(binding.baseCardview,AutoTransition())
                binding.hiddenView.setVisibility(View.VISIBLE)
                binding.arrowBtn.setImageResource(R.drawable.ic_action_more)
            }
        }


    }

    private fun filter(text: String) {
        val filteredList1: ArrayList<Products> = ArrayList()
        for (item in vendorFilteredList) {
            if (item.title.toLowerCase()
                    .contains(text.toLowerCase()) || item.product_type.toLowerCase()
                    .contains(text.toLowerCase())
            ) {
                filteredList1.add(item)
            }
        }

        filteredList = filteredList1
        searchAdapter.updateCategory(filteredList)
    }


    private fun initUi() {
        binding.searchRecycle.apply {
            layoutManager = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            adapter = searchAdapter

        }

        ArrayAdapter.createFromResource(this, R.array.vendor_options,android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinner.adapter = adapter
            binding.spinner.onItemSelectedListener=this
        }


    }

    private fun sorting(sort: String) {
        when (sort) {
            "l" -> {
                filteredList.sortBy { it.variants?.get(0)?.price }
                searchAdapter.updateCategory(filteredList)
            }
            "h" -> {
                println("high sort")
                filteredList.sortByDescending { it.variants?.get(0)?.price }
                searchAdapter.updateCategory(filteredList)

            }
        }
    }

    override fun onImageClick(item: Products) {
        val intent= Intent(this, ProductDetails::class.java)
        intent.putExtra("product_id",item.id .toString())
        startActivity(intent)
    }

    private fun vendorFilter(vendor: String) {
        if (vendor=="ALL"){
            filteredList=allList
            vendorFilteredList= allList
        }else{
            Log.i("search",""+vendor)
            val list=allList.filter { it.vendor == vendor}
            Log.i("search",""+list)
            vendorFilteredList= list as ArrayList<Products>
            filteredList= list as ArrayList<Products>
        }


        Log.i("search",""+filteredList)
        searchAdapter.updateCategory(filteredList)


    }

    private fun getEventActivity(){
        var event = ""
        var arr = this.resources.getStringArray(R.array.vendor_options)
        when (binding.spinner.selectedItemPosition) {
            0 -> vendorFilter(arr[0])
            1 -> vendorFilter(arr[1])
            2 -> vendorFilter(arr[2])
            3 -> vendorFilter(arr[3])
            4 -> vendorFilter(arr[4])
            5 -> vendorFilter(arr[5])
            6 -> vendorFilter(arr[6])
            7 -> vendorFilter(arr[7])
            8 -> vendorFilter(arr[8])
            9 -> vendorFilter(arr[9])
            10 -> vendorFilter(arr[10])
            11 -> vendorFilter(arr[11])
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.i("search","----------------------------------------------"+p2)
        var arr = this.resources.getStringArray(R.array.vendor_options)
        when (p2) {
            0 -> vendorFilter(arr[0])
            1 -> vendorFilter(arr[1])
            2 -> vendorFilter(arr[2])
            3 -> vendorFilter(arr[3])
            4 -> vendorFilter(arr[4])
            5 -> vendorFilter(arr[5])
            6 -> vendorFilter(arr[6])
            7 -> vendorFilter(arr[7])
            8 -> vendorFilter(arr[8])
            9 -> vendorFilter(arr[9])
            10 -> vendorFilter(arr[10])
            11 -> vendorFilter(arr[11])
            12 -> vendorFilter(arr[12])
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}

