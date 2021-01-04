package com.codersanjeev.news.ui.adapter

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.codersanjeev.news.ui.newslist.NewsListFragment
import com.codersanjeev.news.utils.Utility

class NewsPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return Utility().getTabTitles().size
    }

    override fun createFragment(position: Int): NewsListFragment {
        return NewsListFragment.newInstance(Utility().getTabTitles()[position])
    }
}