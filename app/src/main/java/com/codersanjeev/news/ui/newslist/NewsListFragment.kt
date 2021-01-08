package com.codersanjeev.news.ui.newslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.codersanjeev.api.models.Article
import com.codersanjeev.news.databinding.NewsListFragmentBinding

class NewsListFragment : Fragment() {

    private var category: String? = null
    private var _binding: NewsListFragmentBinding? = null
    private lateinit var viewModel: NewsListViewModel
    private lateinit var newsAdapter: NewsListAdapter

    companion object {
        fun newInstance(category: String): NewsListFragment {
            val fragment = NewsListFragment()
            fragment.category = category
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        newsAdapter = NewsListAdapter { openNews(it) }
        _binding = NewsListFragmentBinding.inflate(inflater, container, false)
        _binding?.newsListRecyclerView?.layoutManager = LinearLayoutManager(context)
        _binding?.newsListRecyclerView?.adapter = newsAdapter
        _binding?.newsListRecyclerView?.addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchNews(category ?: "")
        viewModel.newsList.observe({ lifecycle }) {
            newsAdapter.submitList(it)
        }
    }

    private fun openNews(news: Article) {
        // TODO: open news details activity

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}