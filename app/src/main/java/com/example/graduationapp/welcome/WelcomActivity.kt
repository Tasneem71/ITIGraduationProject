package com.example.graduationapp.welcome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graduationapp.databinding.ActivityWelcomBinding

class WelcomActivity : AppCompatActivity() {

    // declare viewPager
    var viewPager: WelcomeViewPager? = null
    lateinit var binding:ActivityWelcomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager= WelcomeViewPager(supportFragmentManager,1)
        binding.pager.adapter=viewPager

    }
}