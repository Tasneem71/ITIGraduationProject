package com.example.graduationapp.welcome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.graduationapp.MainActivity
import com.example.graduationapp.R
import com.example.graduationapp.databinding.FragmentMeBinding
import com.example.graduationapp.databinding.FragmentWelcome3Binding
import kotlinx.coroutines.*


class WelcomeFragment3 : Fragment() {
    lateinit var binding: FragmentWelcome3Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentWelcome3Binding.inflate(layoutInflater)
        binding.go.setOnClickListener {
            var intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        return binding.root
    }


}