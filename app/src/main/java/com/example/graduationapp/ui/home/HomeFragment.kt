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
import com.example.graduationapp.HomeCollectionQuery
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.Products
import com.example.graduationapp.data.priceRules.CreatedDiscount
import com.example.graduationapp.data.priceRules.Discount
import com.example.graduationapp.databinding.FragmentHomeBinding
import com.example.graduationapp.graphql.CollectionsGraphAdapter
import com.example.graduationapp.graphql.GraphViewModel
import com.example.graduationapp.ui.cart.CartActivity
import com.example.graduationapp.ui.favoriteFeature.FavoriteActivity
import com.example.graduationapp.ui.productPageFeature.ProductDetails
import com.example.graduationapp.ui.search.SearchActivity


class HomeFragment : Fragment() ,CollectionsGraphAdapter.OnHomeItemListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var imgas:Array<Int>
    var  adidasAdapter = CollectionsGraphAdapter(arrayListOf(),this)
    var  nikeAdapter = CollectionsGraphAdapter(arrayListOf(),this)
    var  pumaAdapter = CollectionsGraphAdapter(arrayListOf(),this)
    var  converceAdapter = CollectionsGraphAdapter(arrayListOf(),this)
    var  asicsAdapter = CollectionsGraphAdapter(arrayListOf(),this)
    private lateinit var homeViewModel1: GraphViewModel
    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View {

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel1 = ViewModelProvider(this).get(GraphViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        imgas =arrayOf(R.drawable.discount,R.drawable.op ,R.drawable.lap)

        for (item in imgas)
             showPhotos(item)

        initUI()

        //*********************************
        binding.flipper.setOnClickListener {
            Log.i("discount","pressed")
            if (SharedPref.getUserDiscount()==0L) {
                Log.i("discount","pressed if")
                homeViewModel.getDiscount10()
            }else{
                Log.i("discount","pressed else")
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

        homeViewModel1.adidas?.observe(requireActivity(), Observer {
            Log.i("tasneem",""+it)
            adidasAdapter.setData(it)
        })

        homeViewModel1.nike?.observe(requireActivity(), Observer {
            Log.i("tasneem",""+it)
            nikeAdapter.setData(it)
        })

        homeViewModel1.converse?.observe(requireActivity(), Observer {
            Log.i("tasneem",""+it)
            converceAdapter.setData(it)
        })

        homeViewModel1.asicsTiger?.observe(requireActivity(), Observer {
            Log.i("tasneem",""+it)
            asicsAdapter.setData(it)
        })

        homeViewModel1.puma?.observe(requireActivity(), Observer {
            Log.i("tasneem",""+it)
            pumaAdapter.setData(it)
        })

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

    override fun onResume() {
        super.onResume()
        homeViewModel.cartCount(SharedPref.getUserID().toString())
    }

    override fun onImageClick(item: HomeCollectionQuery.Edge1) {
        splitId(item.node.id)
        val intent= Intent(this.context, ProductDetails::class.java)
        intent.putExtra("product_id",splitId(item.node.id))
        this.context?.startActivity(intent)
    }

    fun splitId(id:String): String{
        val delim = "/"
        val list = id.split(delim)
        Toast.makeText(context,""+list.get(list.size-1),Toast.LENGTH_LONG).show()
        return list.get(list.size-1)
    }

}