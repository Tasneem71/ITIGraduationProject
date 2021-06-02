package com.example.graduationapp.ui.me

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.graduationapp.LoginActivity
import com.example.graduationapp.R
import com.example.graduationapp.RegistrationActivity
import com.example.graduationapp.databinding.ActivityLoginBinding
import com.example.graduationapp.databinding.FragmentMeBinding
import com.facebook.AccessToken
import com.facebook.FacebookSdk
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth


class MeFragment : Fragment() {

    lateinit var binding: FragmentMeBinding
    var fAuth: FirebaseAuth? = null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentMeBinding.inflate(layoutInflater)
        fAuth = FirebaseAuth.getInstance()


        binding.registerLogin.setOnClickListener {

            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)

        }
        binding.settings.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_navigation_me_to_settingsFragment)
        }

        return binding.root
    }

    private fun signOut() {
        LoginActivity.mGoogleSignInClient?.signOut()
    }
}


    /*
    binding.btnLogout.setOnClickListener( {
            Log.d("logout","buttonLogout")

        // configure facebook
        binding.btnLogout.setOnClickListener({
            Log.d("logout", "buttonLogout")

            SharedPref.setLogin(false)
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            if (!SharedPref.checkLoginWithFirebase()!!) {
                signOut()
                LoginActivity.account = null
            }
            if (AccessToken.getCurrentAccessToken() != null) {
                GraphRequest(
                    AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE
                ) {
                    AccessToken.setCurrentAccessToken(null)
                    LoginManager.getInstance().logOut()
                }.executeAsync()
            }
        })
    */