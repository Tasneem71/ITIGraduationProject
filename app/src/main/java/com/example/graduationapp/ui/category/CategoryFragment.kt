package com.example.graduationapp.ui.category

import android.R.attr.fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.graduationapp.R
import com.example.graduationapp.databinding.FragmentCategoryBinding
import com.google.android.material.tabs.TabLayout


class CategoryFragment : Fragment() ,  TabLayout.OnTabSelectedListener {

    private lateinit var binding : FragmentCategoryBinding
    val data: ArrayList<Data> = ArrayList()
    lateinit var adapter: CategoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        data.add(Data(R.drawable.ic_cart, "Image 1"))
        data.add(Data(R.drawable.ic_cart, "Image 2"))
        data.add(Data(R.drawable.ic_cart, "Image 3"))
        data.add(Data(R.drawable.ic_cart, "Image 4"))
        data.add(Data(R.drawable.ic_cart, "Image 5"))
        data.add(Data(R.drawable.ic_cart, "Image 6"))
        adapter = CategoryAdapter()
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        binding.categoryRecycler.setLayoutManager(gridLayoutManager)
        adapter.setData(data, requireContext())
        binding.categoryRecycler.adapter = adapter

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("tab1"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("tab2"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("tab3"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("tab4"));

        return binding.root
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.d("adapter", "tab")
        when (tab!!.position) {
            0 -> Toast.makeText(
                requireContext(),
                "Tab1 " + tab!!.position,
                Toast.LENGTH_LONG
            ).show();
            1 -> Toast.makeText(
                requireContext(),
                "Tab2 " + tab!!.position,
                Toast.LENGTH_LONG
            ).show();
            2 -> Toast.makeText(
                requireContext(),
                "Tab3 " + binding.tabLayout.getSelectedTabPosition(),
                Toast.LENGTH_LONG
            ).show();
            3 -> Toast.makeText(
                requireContext(),
                "Tab4 " + binding.tabLayout.getSelectedTabPosition(),
                Toast.LENGTH_LONG
            ).show();
        }

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

}