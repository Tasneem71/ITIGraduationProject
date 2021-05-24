package com.example.graduationapp

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.graduationapp.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_login)


        //@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        binding.btRegister.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            val pairs =
                Pair<View, String>(binding.tvLogin, "tvLogin")
            val activityOptions =
                ActivityOptions.makeSceneTransitionAnimation(this, pairs)
            startActivity(intent, activityOptions.toBundle())
        }


    }
}