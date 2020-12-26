package com.codersanjeev.api

import kotlinx.coroutines.runBlocking
import org.junit.Test

class NewsClientTests {

    @Test
    fun `GET Top Headlines`() {
        runBlocking {
            val headlines =
                NewsClient.newsApi.getTopHeadlines(country = "in", category = "technology")
            assert(headlines.body()?.articles != null)
        }
    }

    @Test
    fun `GET All Headlines`() {
        runBlocking {
            val headlines = NewsClient.newsApi.getAllNews(queryText = "farmers")
            assert(headlines.body()?.articles != null)
        }
    }

}