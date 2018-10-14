package com.example.sanjeev.newsapp.rest;

import com.example.sanjeev.newsapp.models.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("search?&show-tags=contributor")
    Call<NewsModel> getAllNews(@Query("api-key") String apiKey, @Query("order-by") String orderBy);

    @GET("search?&show-tags=contributor")
    Call<NewsModel> getCategorizedNews(@Query("api-key") String apiKey, @Query("order-by") String orderBy, @Query("category") String category);

}
