package com.example.graduationapp.ui.me

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment

import com.example.graduationapp.RegistrationActivity
import com.example.graduationapp.databinding.ActivityLoginBinding
import com.example.graduationapp.databinding.FragmentMeBinding

class MeFragment : Fragment() {

    lateinit var binding: FragmentMeBinding


    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentMeBinding.inflate(layoutInflater)

        binding.registerLogin.setOnClickListener {

            val intent = Intent(context, RegistrationActivity::class.java)
            startActivity(intent)

        }

        return binding.root
    }
}