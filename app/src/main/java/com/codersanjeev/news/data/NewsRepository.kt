package com.codersanjeev.news.data

import com.codersanjeev.api.NewsClient
import com.codersanjeev.api.services.NewsAPI
import com.codersanjeev.news.BuildConfig

class NewsRepository {

    private var newsApi: NewsAPI

    init {
        NewsClient.authToken = BuildConfig.NEWS_API_KEY
        newsApi = NewsClient.newsApi
    }

    suspend fun fetchLatestNews(forCategory: String) =
        newsApi.getTopHeadlines("in", forCategory).body()?.articles

}