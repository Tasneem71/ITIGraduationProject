package com.example.graduationapp.ui.addressbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.core.subFeature.GridSpacingItemDecoration
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.R
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.ui.addressbook.adapater.AddressAdapter
import com.example.graduationapp.SharedPref
import com.example.graduationapp.databinding.ActivityAdressBookBinding
import com.example.graduationapp.databinding.ActivityFavoriteBinding
import com.example.graduationapp.ui.favoriteFeature.FavoriteViewModel
import com.example.graduationapp.ui.favoriteFeature.adapater.FavoriteAdapter

class AddressBook : AppCompatActivity(),AddressAdapter.OnClickAddressListener {
    private lateinit var addressBookViewModel: AddressBookViewModel
    private lateinit var userId :String

    lateinit var binding: ActivityAdressBookBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private  var addressAdapter= AddressAdapter(emptyList(),this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdressBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addressRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.addressRecycler.setHasFixedSize(true)
        binding.addressRecycler.addItemDecoration(
            GridSpacingItemDecoration(1,
                RecyclerViewAnimation.dpToPx(6),true)
        )
        binding.addressRecycler.itemAnimator= DefaultItemAnimator()
        binding.addressRecycler.adapter = addressAdapter

        addressBookViewModel = ViewModelProvider(this).get(AddressBookViewModel::class.java)
        userId = SharedPref.getUserID().toString()
        Log.i("Menna", "AddressBook user id == $userId")


        addressBookViewModel.getAllCustomerAddress(userId)
        addressBookViewModel.allCustomerAddresses.observe(this) {
            it?.let {
                //adapterList
                addressAdapter.setData(it as List<Addresse>)
            }
        }

    }

    override fun onDeleteAddressClick(item: Addresse) {

        if (item.default)
            // show alerts
            // please select

        addressBookViewModel.deleteAddress(userId,item.id)

    }

    override fun onNameAddressClick(item: Addresse) {

    }

    override fun onAddressClick(item: Addresse) {

    }

    override fun onCheckBoxChange(item: Addresse) {


    }



}