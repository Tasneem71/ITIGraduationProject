package com.example.graduationapp.ui.me

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.graduationapp.LoginActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.databinding.FragmentMeBinding
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.HttpMethod
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth


class MeFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var binding: FragmentMeBinding
    var fAuth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_me, container, false)
        fAuth = FirebaseAuth.getInstance()

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

        return binding.root
    }
    private fun signOut() {
        LoginActivity.mGoogleSignInClient?.signOut()
    }

}