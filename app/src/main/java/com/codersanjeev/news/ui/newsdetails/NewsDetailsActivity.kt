package com.codersanjeev.news.ui.newsdetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.codersanjeev.news.databinding.ActivityNewsDetailsBinding

class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(_binding.root)
    }
}