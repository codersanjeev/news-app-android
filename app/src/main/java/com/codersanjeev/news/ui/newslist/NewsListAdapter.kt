package com.codersanjeev.news.ui.newslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codersanjeev.api.models.Article
import com.codersanjeev.news.R
import com.codersanjeev.news.databinding.ListItemNewsBinding
import com.codersanjeev.news.utils.toDate
import com.codersanjeev.news.utils.toFormattedString

class NewsListAdapter(val onNewsItemClicked: (news: Article) -> Unit) :
    ListAdapter<Article, NewsListAdapter.NewsItemViewHolder>(
        object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.toString() == newItem.toString()
            }
        }
    ) {
    inner class NewsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        ListItemNewsBinding.bind(holder.itemView).apply {
            val news = getItem(position)
            newsTitleView.text = news.title
            newsDateTimeView.text = news.publishedAt?.toDate()?.toFormattedString()
            Glide.with(holder.itemView.context).load(news.urlToImage).into(newsImageView)
            root.setOnClickListener { onNewsItemClicked(news) }
        }
    }

}