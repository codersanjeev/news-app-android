package com.codersanjeev.api

import com.codersanjeev.api.services.NewsAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object NewsClient {

    var authToken: String? = null

    private val authInterceptor = Interceptor { chain ->
        var request = chain.request()
        authToken?.let {
            request = request.newBuilder()
                .header("Authorization", it)
                .build()
        }
        chain.proceed(request)
    }

    private val okHttpBuilder = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .readTimeout(5, TimeUnit.SECONDS)
        .connectTimeout(2, TimeUnit.SECONDS)

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(MoshiConverterFactory.create())

    val newsApi = retrofitBuilder
        .client(okHttpBuilder.build())
        .build()
        .create(NewsAPI::class.java)

}