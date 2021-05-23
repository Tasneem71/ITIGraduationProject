package com.example.graduationapp.ui.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.graduationapp.R
import androidx.lifecycle.observe


class testActivity : AppCompatActivity() {

    private lateinit var testActivityViewModal: testActivityVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        testActivityViewModal = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(testActivityVM::class.java)
        testActivityViewModal.loadData(applicationContext).observe(this, {
            Toast.makeText(applicationContext,""+it.custom_collections[0].title,Toast.LENGTH_LONG).show()
        })
    }
}