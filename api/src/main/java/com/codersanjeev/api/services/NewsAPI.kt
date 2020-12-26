package com.codersanjeev.api.services

import com.codersanjeev.api.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String? = null,
        @Query("category") category: String? = null,
        @Query("q") query: String? = null,
        @Query("pageSize") pageSize: Int = 100
    ): Response<NewsResponse>

    @GET("everything")
    suspend fun getAllNews(
        @Query("q") queryText: String? = null,
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null,
        @Query("pageSize") pageSize: Int = 100
    ): Response<NewsResponse>

}