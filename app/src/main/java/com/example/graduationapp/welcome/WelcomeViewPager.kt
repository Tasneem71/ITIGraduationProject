package com.example.graduationapp.welcome

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class WelcomeViewPager (fm: FragmentManager, behaviour: Int) :
    FragmentPagerAdapter(fm, behaviour) {
    // create the getItem method of
    // FragmentPagerAdapter class
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return WelcomeFragment1() // Fragment1 is the name of first blank fragment,
            1 -> return WelcomeFragment2() // Fragment2 is the name of second blank fragment,
            2 -> return WelcomeFragment3() // Fragment2 is the name of second blank fragment,
        }
        return WelcomeFragment1()
    }

    override fun getCount(): Int {
        return 3
    }
}