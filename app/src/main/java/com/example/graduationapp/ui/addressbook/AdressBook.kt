package com.example.graduationapp.ui.addressbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduationapp.R
import com.example.graduationapp.data.Addresse
import com.example.graduationapp.ui.addressbook.adapater.AddressAdapter

class AddressBook : AppCompatActivity(),AddressAdapter.OnClickAddressListener {
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

    }
}