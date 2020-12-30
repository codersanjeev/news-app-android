package com.codersanjeev.news.ui.adapter

import android.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class NewsPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        val fm = Fragment()
        val colors = listOf(Color.RED, Color.GREEN, Color.BLUE)
        fm.view?.setBackgroundColor(colors[position % 3])
        return fm
    }
}