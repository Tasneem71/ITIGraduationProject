package com.example.graduationapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graduationapp.R
import com.example.graduationapp.SearchActivity
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.Products
import com.example.graduationapp.data.priceRules.CreatedDiscount
import com.example.graduationapp.data.priceRules.Discount
import com.example.graduationapp.databinding.FragmentHomeBinding
import com.example.graduationapp.ui.cart.CartActivity
import com.example.graduationapp.ui.favoriteFeature.FavoriteActivity
import com.example.graduationapp.ui.productPageFeature.ProductDetails


class HomeFragment : Fragment()  , ShopCategoryAdapter.OnHomeItemListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var imgas:Array<Int>
    private lateinit var adidusList:ArrayList<Products>
    private lateinit var nikeList:ArrayList<Products>
    private lateinit var pumaList:ArrayList<Products>
    private lateinit var converceList:ArrayList<Products>
    private lateinit var asicsList:ArrayList<Products>
    var  adidasAdapter = ShopCategoryAdapter(arrayListOf(),this)
    var  nikeAdapter = ShopCategoryAdapter(arrayListOf(),this)
    var  pumaAdapter = ShopCategoryAdapter(arrayListOf(),this)
    var  converceAdapter = ShopCategoryAdapter(arrayListOf(),this)
    var  asicsAdapter = ShopCategoryAdapter(arrayListOf(),this)

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        imgas =arrayOf(R.drawable.discount,R.drawable.op ,R.drawable.lap)

        for (item in imgas)
             showPhotos(item)

        initUI()
        adidusList = ArrayList()
        nikeList = ArrayList()
        pumaList = ArrayList()
        converceList = ArrayList()
        asicsList = ArrayList()

        //*********************************
        binding.flipper.setOnClickListener {
            if (SharedPref.getUserDiscount()==0L) {
                homeViewModel.generatingDiscount("951388569798", CreatedDiscount(Discount("SUMMERSALE10OFF")))
            }else{
                Toast.makeText(context,"Discount has already been activated",Toast.LENGTH_SHORT).show()
            }
        }

        binding.cart.setOnClickListener {
            val intent= Intent(this.context, CartActivity::class.java)
            startActivity(intent)
        }

        binding.search.setOnClickListener {
            val intent= Intent(this.context, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.favo.setOnClickListener(View.OnClickListener {
            val intent= Intent(context, FavoriteActivity::class.java)
            startActivity(intent)

        })

//        updateBadge()



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

        homeViewModel.generatedDiscountLiveData.observe(requireActivity()) {
            it?.let {
                SharedPref.setUserDiscount(it.id)
                Toast.makeText(context,"Discount activated",Toast.LENGTH_SHORT).show()
            }
        }

        homeViewModel.cartCount.observe(viewLifecycleOwner, Observer {

            Log.i("BADGE", "onCreateView: BADGE #  $it")
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
        val image = ImageView(context)
        image.setBackgroundResource(img)
        binding.flipper.addView(image)
        binding.flipper.flipInterval = 4000
        binding.flipper.isAutoStart = true

    }

    override fun onImageClick(item: Products) {
        val intent= Intent(this.context, ProductDetails::class.java)
        intent.putExtra("product_id",item.id .toString())
        this.context?.startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.cartCount(SharedPref.getUserID().toString())
    }

}