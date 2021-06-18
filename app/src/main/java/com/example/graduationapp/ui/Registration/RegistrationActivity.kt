package com.example.graduationapp.ui.Registration

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.example.graduationapp.MainActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.data.CreatedCustomer
import com.example.graduationapp.data.Customer
import com.example.graduationapp.databinding.ActivityRegistrationBinding
import com.example.graduationapp.local.DefaultLocal
import com.example.graduationapp.local.LocalSource
import com.example.graduationapp.remote.ApiRepository
import com.example.graduationapp.remote.DefaultRemote
import com.example.graduationapp.remote.RemoteDataSource
import com.example.graduationapp.remote.retro.DefaultRepo
import com.example.graduationapp.ui.search.SearchViewModel
import com.example.graduationapp.utils.Validation

class RegistrationActivity : AppCompatActivity() {
    private lateinit var rlayout: RelativeLayout
    private lateinit var animation: Animation
    lateinit var binding:ActivityRegistrationBinding
    private lateinit var registrationViewModel : RegistrationViewModel
    lateinit var repository: DefaultRepo
    lateinit var local: DefaultLocal
    lateinit var remote: DefaultRemote
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_registration)
        //val toolbar: Toolbar = findViewById(R.id.bgHeader)
        setSupportActionBar(binding.bgHeader)
        supportActionBar!!.setTitle("")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal)
        binding.rlayout.animation = animation



        local= LocalSource(this.application)
        remote=RemoteDataSource()
        repository= ApiRepository(this.application,local,remote)

        val factory = RegistrationViewModelFactory(this.application,repository)
        registrationViewModel = ViewModelProviders.of(this,factory).get(RegistrationViewModel::class.java)


        //registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)

        binding.registerBtn.setOnClickListener {
            if (Validation.isOnline(this)){
            registrationViewModel.validate_Registration(CreatedCustomer(Customer(binding.fnameEdt.text.toString(),binding.lnameEdt.text.toString()
            ,binding.emailEdt.text.toString(),null,binding.passwordEdt.text.toString(),true,null,binding.passwordEdt.text.toString()
            ,binding.passwordEdt.text.toString(),false)))
            }else{
                Toast.makeText(this,this.getString(R.string.no_internet), Toast.LENGTH_LONG).show()
            }
        }

        registrationViewModel.customerLiveData.observe(this) {
            it?.let {
                println(it)
                settingSharedPrefs(it.email,it.id,it.first_name)
            }
        }

        registrationViewModel.network.observe(this) {
            it?.let {
                Toast.makeText(this,this.getString(R.string.no_internet), Toast.LENGTH_LONG).show()
            }
        }

    }




    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



    private fun settingSharedPrefs(email: String,id:String,fname: String){
        val intent = Intent(this, MainActivity::class.java)
        SharedPref.setUserEmail(email)
        SharedPref.setUserId(id)
        SharedPref.setUserInfo(fname)
        SharedPref.setUserState(true)
        startActivity(intent)
        finish()
    }

}