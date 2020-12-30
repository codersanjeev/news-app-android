package com.codersanjeev.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codersanjeev.news.databinding.ActivityMainBinding
import com.codersanjeev.news.ui.adapter.NewsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val viewPager = _binding.newsAppViewPager
        val viewPagerAdapter = NewsPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(_binding.newsAppTabs, viewPager) { tab, position ->
            tab.text = "Category Here"
            viewPager.setCurrentItem(tab.position, true)
        }.attach()
    }
}