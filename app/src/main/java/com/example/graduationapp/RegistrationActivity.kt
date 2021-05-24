package com.example.graduationapp

import android.os.Bundle
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.graduationapp.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var rlayout: RelativeLayout
    private lateinit var animation: Animation
    lateinit var binding:ActivityRegistrationBinding
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

}