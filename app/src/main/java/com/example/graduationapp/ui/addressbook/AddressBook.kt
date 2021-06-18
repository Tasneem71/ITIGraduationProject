package com.example.graduationapp.ui.addressbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.core.subFeature.GridSpacingItemDecoration
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.databinding.ActivityAdressBookBinding
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.DefaultRemote
import com.example.graduationapp.remote.RemoteDataSource
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.addressbook.adapater.AddressAdapter
import com.example.graduationapp.ui.checkoutAddress.CustomerDataActivity
import com.example.graduationapp.ui.favoriteFeature.FavoriteViewModel
import com.example.graduationapp.ui.search.SearchViewModel
import com.example.graduationapp.ui.search.SearchViewModelFactory
import com.example.graduationapp.utils.Validation
import com.google.android.material.snackbar.Snackbar

class AddressBook : AppCompatActivity(),AddressAdapter.OnClickAddressListener {
    private lateinit var addressBookViewModel: AddressBookViewModel
    private lateinit var userId :String
    lateinit var binding: ActivityAdressBookBinding
    private  var addressAdapter= AddressAdapter(emptyList(),this)
    lateinit var repository: DefaultRepo
    lateinit var local: DefaultLocal
    lateinit var remote: DefaultRemote


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


        local= LocalSource(this.application)
        remote= RemoteDataSource()
        repository= ApiRepository(this.application,local,remote)

        val factory = AddressBookViewModelFactory(this.application,repository)
        addressBookViewModel = ViewModelProviders.of(this,factory).get(AddressBookViewModel::class.java)


        addressBookViewModel = ViewModelProvider(this).get(AddressBookViewModel::class.java)
        userId = SharedPref.getUserID().toString()
        Log.i("Menna", "AddressBook user id == $userId")

        if(Validation.isOnline(this)) {
            addressBookViewModel.getAllCustomerAddress(userId)
        }
        else
        {
            Toast.makeText(this,getString(R.string.no_internet),Toast.LENGTH_LONG).show()

        }
        addressBookViewModel.allCustomerAddresses.observe(this) {
            it?.let {
                //adapterList
                addressAdapter.setData(it as List<Addresse>)

            }
        }

        binding.addAddress.setOnClickListener{
            val intent=Intent(this,CustomerDataActivity::class.java)
            intent.putExtra("key","New")
            startActivity(intent)
        }

    }

    override fun onDeleteAddressClick(item: Addresse) {

        if (item.default)
        {
            Snackbar.make(binding.addressRecycler, "Please Choose another address as default",
                Snackbar.LENGTH_LONG).show()
        }
        else
        {
            if(Validation.isOnline(this)) {
                addressBookViewModel.deleteAddress(userId, item.id)
            }else{
                Toast.makeText(this,getString(R.string.no_internet),Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onNameAddressClick(item: Addresse) {

        val intent=Intent(this,CustomerDataActivity::class.java)
        val b = Bundle()
        b.putSerializable("Addresse", item)
        intent.putExtras(b)
        intent.putExtra("key","Edit")
        startActivity(intent)

    }

    override fun onAddressClick(item: Addresse) {

        val intent=Intent(this,CustomerDataActivity::class.java)

        val b = Bundle()
        b.putSerializable("Addresse", item)
        intent.putExtras(b)
        intent.putExtra("key","Edit")
        startActivity(intent)
    }

    override fun onCheckBoxChange(item: Addresse) {


        Log.i("Menna", "cjcccccccccccccccc: ${item.id}")
        if(Validation.isOnline(this)) {
            addressBookViewModel.setDefaultAddress(userId, item.id)
        }else{
            Toast.makeText(this,getString(R.string.no_internet),Toast.LENGTH_LONG).show()

        }

    }

    override fun onResume() {
        super.onResume()
        updateList()
    }

    private fun updateList()
    {
        if(Validation.isOnline(this)) {
            addressBookViewModel.getAllCustomerAddress(userId)
        }else{
            Toast.makeText(this,getString(R.string.no_internet),Toast.LENGTH_LONG).show()

        }
        addressBookViewModel.allCustomerAddresses.observe(this) {
            it?.let {
                //adapterList
                addressAdapter.setData(it as List<Addresse>)

            }
        }
    }



}