package com.example.graduationapp.ui.addressbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.core.subFeature.GridSpacingItemDecoration
import com.example.domain.core.subFeature.RecyclerViewAnimation
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.databinding.ActivityAdressBookBinding
import com.example.graduationapp.ui.addressbook.adapater.AddressAdapter
import com.example.graduationapp.ui.checkoutAddress.CustomerDataActivity
import com.example.graduationapp.ui.favoriteFeature.FavoriteViewModel
import com.google.android.material.snackbar.Snackbar

class AddressBook : AppCompatActivity(),AddressAdapter.OnClickAddressListener {
    private lateinit var addressBookViewModel: AddressBookViewModel
    private lateinit var userId :String
    lateinit var binding: ActivityAdressBookBinding
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

            Log.i("Menna", "hhhhhhhhhhh: ${item.id}")
            addressBookViewModel.deleteAddress(userId,item.id)
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

        addressBookViewModel.setDefaultAddress(userId,item.id)

    }

    override fun onResume() {
        super.onResume()
        updateList()
    }

    private fun updateList()
    {
        addressBookViewModel.getAllCustomerAddress(userId)
        addressBookViewModel.allCustomerAddresses.observe(this) {
            it?.let {
                //adapterList
                addressAdapter.setData(it as List<Addresse>)

            }
        }
    }



}