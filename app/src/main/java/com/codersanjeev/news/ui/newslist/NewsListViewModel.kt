package com.codersanjeev.news.ui.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codersanjeev.api.models.Article
import com.codersanjeev.news.data.NewsRepository
import kotlinx.coroutines.launch

class NewsListViewModel : ViewModel() {

    private val newsRepository = NewsRepository()
    private val _newsList = MutableLiveData<List<Article>>()
    val newsList: LiveData<List<Article>> = _newsList

    fun fetchNews(forCategory: String) = viewModelScope.launch {
        newsRepository.fetchLatestNews(forCategory)?.let {
            _newsList.postValue(it)
        }
    }

}