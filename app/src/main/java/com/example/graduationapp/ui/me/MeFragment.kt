package com.example.graduationapp.ui.me

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import androidx.navigation.findNavController
import com.example.domain.core.feature.favoriteFeature.Favorite

import com.example.graduationapp.LoginActivity
import com.example.graduationapp.R
import com.example.graduationapp.SharedPref
import com.example.graduationapp.databinding.FragmentMeBinding
import com.example.graduationapp.ui.favoriteFeature.FavoriteActivity
import com.example.graduationapp.ui.favoriteFeature.FavoriteViewModel
import com.google.firebase.auth.FirebaseAuth


class MeFragment : Fragment() {

    lateinit var binding: FragmentMeBinding
    var fAuth: FirebaseAuth? = null
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var wishAdapter: MeAdapter
    private lateinit var wishList:ArrayList<Favorite>

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        binding = FragmentMeBinding.inflate(layoutInflater)
        fAuth = FirebaseAuth.getInstance()
        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        wishList = ArrayList()
        wishAdapter= MeAdapter(wishList)
        initUi()


        settingUI(SharedPref.getUserStatus())
        if (SharedPref.getUserStatus()){
            favoriteViewModel.getAllFavorite()
        }

        favoriteViewModel.favorites?.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (!it.isNullOrEmpty())
                wishAdapter.updateList(it.take(4))
            }

        })
        binding.registerLogin.setOnClickListener {

            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)

        }
        binding.settings.setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_navigation_me_to_settingsFragment)
        }

        binding.seeMore.setOnClickListener {
            val intent = Intent(context, FavoriteActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun initUi() {
        binding.categoryRecycler.apply {
            layoutManager = GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
            adapter = wishAdapter

        }
    }


    private fun settingUI(userStatus: Boolean) {

        if (userStatus==true){

            binding.welcome.visibility=View.VISIBLE
            binding.welcome.text="Welcome "+SharedPref.getUserInfo()
            binding.registerLogin.visibility=View.GONE
            binding.categoryRecycler.visibility=View.VISIBLE
            binding.notLoged.visibility=View.GONE
            binding.seeMore.visibility=View.VISIBLE


        }else{

            binding.welcome.visibility=View.GONE
            binding.registerLogin.visibility=View.VISIBLE
            binding.categoryRecycler.visibility=View.GONE
            binding.notLoged.visibility=View.VISIBLE
            binding.seeMore.visibility=View.GONE

        }

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