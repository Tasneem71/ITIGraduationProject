package com.example.graduationapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

class MainActivity : AppCompatActivity() {

    lateinit var  bottomNavigationView :MeowBottomNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val navView: MeowBottomNavigation = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_category, R.id.navigation_me))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        SharedPref.createPrefObject(this)
        //navView.setupWithNavController(navController)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.add(MeowBottomNavigation.Model(1,R.drawable.com_facebook_button_icon))
        bottomNavigationView.add(MeowBottomNavigation.Model(2,R.drawable.ic_favorite))
        bottomNavigationView.add(MeowBottomNavigation.Model(3,R.drawable.ic_action_favorite))

        bottomNavigationView.setOnClickMenuListener {
            when(it.id) {
                1->{
                    navController.navigate(R.id.navigation_home)
                }
                2->{
                    navController.navigate(R.id.navigation_category)
                }
                3->{
                    navController.navigate(R.id.navigation_me)
                }
            }
        }


    }
}