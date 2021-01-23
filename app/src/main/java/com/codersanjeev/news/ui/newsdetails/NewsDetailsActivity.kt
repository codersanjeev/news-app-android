package com.codersanjeev.news.ui.newsdetails

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codersanjeev.api.models.Article
import com.codersanjeev.news.R
import com.codersanjeev.news.databinding.ActivityNewsDetailsBinding
import com.codersanjeev.news.utils.toDate
import com.codersanjeev.news.utils.toFormattedString

class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityNewsDetailsBinding
    private var news: Article? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        news = intent.getSerializableExtra("NEWS_DETAILS") as? Article
        setupToolbar()
        setupView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupToolbar() {
        setSupportActionBar(_binding.newsDetailsToolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_cancel)
        supportActionBar?.title = ""
        supportActionBar?.subtitle = ""
    }

    private fun setupView() {
        Glide.with(this).load(news?.urlToImage).into(_binding.newsDetailsImageView)
        _binding.newsTimeTextView.text = news?.publishedAt?.toDate()?.toFormattedString()
        _binding.newsTitleTextView.text = news?.title
        _binding.newsDescriptionTextView.text = news?.content
    }

}