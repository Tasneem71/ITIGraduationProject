package com.example.graduationapp

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.ViewPagerAdapter

class ScrollingActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(findViewById(R.id.toolbar))
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = " "


        val imagesList = listOf(
            "https://pbs.twimg.com/media/EyElBJyWUAY-2YE.jpg",
            "https://pbs.twimg.com/profile_images/712703916358537217/mcOketun_400x400.jpg",
            "https://i.pinimg.com/originals/44/ce/2c/44ce2cfa6267fde44790205135a78051.jpg"
        )

        val adapter = ViewPagerAdapter(imagesList)
        viewPager2 = findViewById(R.id.viewPager)
        viewPager2.adapter = adapter



    }
}