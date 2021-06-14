package com.example.graduationapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.graduationapp.ui.category.CategoryFragment
import com.example.graduationapp.ui.home.HomeFragment
import com.example.graduationapp.ui.me.MeFragment

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
        //navView.setupWithNavController(navController)
        SharedPref.setEver(true)
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.add(MeowBottomNavigation.Model(1,R.drawable.home1))
        bottomNavigationView.add(MeowBottomNavigation.Model(2,R.drawable.hanger))
        bottomNavigationView.add(MeowBottomNavigation.Model(3,R.drawable.follower))

//        val first= HomeFragment()
//        val second= CategoryFragment()
//        val third= MeFragment()
//        setCurrentFragment(first)

        bottomNavigationView.setOnClickMenuListener {
            when(it.id) {
                1->{
                    //setCurrentFragment(first)
                    navController.navigate(R.id.navigation_home)
                }
                2->{
                    //setCurrentFragment(second)
                    navController.navigate(R.id.navigation_category)
                }
                3->{
                    //setCurrentFragment(third)
                    navController.navigate(R.id.navigation_me)
                }
            }
        }


    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.nav_host_fragment,fragment)
            commit()
        }

}