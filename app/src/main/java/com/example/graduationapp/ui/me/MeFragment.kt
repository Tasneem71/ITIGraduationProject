package com.example.graduationapp.ui.me

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.core.feature.favoriteFeature.Favorite

import com.example.graduationapp.ui.login.LoginActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.CancelOrder
import com.example.graduationapp.data.orders.Orders
import com.example.graduationapp.databinding.FragmentMeBinding
import com.example.graduationapp.ui.cart.CartActivity
import com.example.graduationapp.ui.favoriteFeature.FavoriteActivity
import com.example.graduationapp.ui.favoriteFeature.FavoriteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth


class MeFragment : Fragment() ,  TabLayout.OnTabSelectedListener , orderAdapter.OnCancelOrderListener {

    lateinit var binding: FragmentMeBinding
    var fAuth: FirebaseAuth? = null
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var viewModel: MeViewModel
    private lateinit var wishAdapter: MeAdapter
    private lateinit var orderAdapter: orderAdapter
    private lateinit var wishList:ArrayList<Favorite>
    private lateinit var orderList:ArrayList<Orders>
    private lateinit var userId :String


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentMeBinding.inflate(layoutInflater)
        fAuth = FirebaseAuth.getInstance()
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        viewModel = ViewModelProvider(this).get(MeViewModel::class.java)
        userId = SharedPref.getUserID().toString()

        wishList = ArrayList()
        orderList = ArrayList()
        orderAdapter= orderAdapter(orderList,this)
        wishAdapter= MeAdapter(wishList)
        initUi()



        settingUI(SharedPref.getUserStatus())
        if (SharedPref.getUserStatus()){
            favoriteViewModel.getAllFavorite(userId)
            viewModel.getOpenOrders()
        }

        favoriteViewModel.favorites?.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (!it.isNullOrEmpty())
                wishAdapter.updateList(it.take(4))
            }

        })

        viewModel.network.observe(requireActivity()) {
            it?.let {
                Toast.makeText(requireContext(),this.getString(R.string.no_internet), Toast.LENGTH_LONG).show()
            }
        }

        viewModel.openOrdersLiveData?.observe(viewLifecycleOwner, Observer {
          Log.d("tag","iddddddddddd"+  it!![0].id)
            it?.let {
                    orderAdapter.updateList(it.filter { it.contact_email==SharedPref.getUserEmail() })
            }

        })


        viewModel.cancelOrderLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                cancelOrderDone()
                viewModel.getOpenOrders()
            }

        })
        viewModel.cartCount.observe(viewLifecycleOwner, Observer {
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
        binding.registerLogin.setOnClickListener {

            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)

        }
        binding.settings.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_navigation_me_to_settingsFragment)
        }

        binding.seeMore.setOnClickListener {
            val intent = Intent(context, FavoriteActivity::class.java)
            startActivity(intent)
        }
        binding.cartPic.setOnClickListener {
            val intent = Intent(context, CartActivity::class.java)
            startActivity(intent)
        }

        setUpTabLayoute()
        return binding.root
    }

    private fun initUi() {
        binding.categoryRecycler.apply {
            layoutManager = GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
            adapter = wishAdapter

        }

        binding.orderRecycler.apply {
            layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
            adapter = orderAdapter

        }
    }


    private fun settingUI(userStatus: Boolean) {

        if (userStatus==true){
            binding.welcome.visibility=View.VISIBLE
            binding.welcome.text=this.getString(R.string.welcometoshopfiy)+SharedPref.getUserInfo()
            binding.registerLogin.visibility=View.GONE
            binding.categoryRecycler.visibility=View.VISIBLE
            binding.notLoged.visibility=View.GONE
            binding.seeMore.visibility=View.VISIBLE


        }else{

            binding.welcome.visibility=View.GONE
            binding.registerLogin.visibility=View.VISIBLE
            binding.categoryRecycler.visibility=View.GONE
            binding.notLoged.visibility=View.VISIBLE
            binding.seeMore.visibility=View.GONE

        }

    }

    private fun setUpTabLayoute() {

        val tab1 = binding.tabs.newTab()
        val tab2 = binding.tabs.newTab()
        tab1.setText(this.getString(R.string.wishList))
        tab2.setText(this.getString(R.string.orderList))
        //tab.setTag(source)
        binding.tabs.addTab(tab1)
        binding.tabs.addTab(tab2)
        binding.tabs.addOnTabSelectedListener(this)
        binding.tabs.getTabAt(0)?.select()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.i("tasneem","tabs"+tab?.position)

        when (tab?.position) {
            0 -> {
                Log.i("tasneem","wish")
                binding.categoryRecycler.visibility=View.VISIBLE
                binding.orderRecycler.visibility=View.GONE
                binding.seeMore.visibility=View.VISIBLE

            }
            1 -> {
                Log.i("tasneem","order")
                binding.categoryRecycler.visibility=View.GONE
                binding.orderRecycler.visibility=View.VISIBLE
                binding.seeMore.visibility=View.GONE

            }

        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onCancelClick(it: Orders) {
        val email = SharedPref.getUserEmail()
        val amount = it.total_price
        viewModel.cancelOrder(it.id,CancelOrder())
    }

    private fun cancelOrderDone() {
        val orderDialogBuilder = AlertDialog.Builder(requireContext())
        orderDialogBuilder.setTitle(this.getString(R.string.order))
        orderDialogBuilder.setMessage(this.getString(R.string.order_canceled))
        orderDialogBuilder.setPositiveButton(this.getString(R.string.ok)) { dialog, which ->
            dialog.dismiss()

        }
        orderDialogBuilder.setCancelable(false)
        orderDialogBuilder.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.cartCount(userId)
    }

}


