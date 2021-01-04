package com.codersanjeev.news.ui.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.codersanjeev.news.R

class NewsListFragment : Fragment() {

    private var category: String? = null

    companion object {
        fun newInstance(category: String): NewsListFragment {
            val fragment = NewsListFragment()
            fragment.category = category
            return fragment
        }
    }

    private lateinit var viewModel: NewsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.news_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
    }
}