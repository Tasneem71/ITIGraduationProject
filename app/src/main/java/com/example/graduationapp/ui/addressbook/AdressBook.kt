package com.example.graduationapp.ui.addressbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.graduationapp.R
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.ui.addressbook.adapater.AddressAdapter
import com.example.graduationapp.SharedPref

class AddressBook : AppCompatActivity(),AddressAdapter.OnClickAddressListener {
class AddressBook : AppCompatActivity() {
    private lateinit var addressBookViewModel: AddressBookViewModel
    private lateinit var userId :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adress_book)


    }

    override fun onDeleteAddressClick(item: Addresse) {

    }

    override fun onNameAddressClick(item: Addresse) {

    }

    override fun onAddressClick(item: Addresse) {

    }

    override fun onCheckBoxChange(item: Addresse) {

        addressBookViewModel = ViewModelProvider(this).get(AddressBookViewModel::class.java)
        userId = SharedPref.getUserID().toString()
        Log.i("Menna", "AddressBook user id == $userId")

    }
    private fun getAllAddresses(){
        addressBookViewModel.getAllCustomerAddress(userId)
        addressBookViewModel.allCustomerAddresses.observe(this) {
            it?.let {
                //adapterList
            }
        }
    }


}