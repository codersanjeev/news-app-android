package com.example.sanjeev.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

// Custom ArrayAdapter for each NewsItem
public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsViewHolder> {

    private LayoutInflater mInflater;
    private final ArrayList<NewsItem> mNewsItems;

    public NewsItemAdapter(Context context, ArrayList<NewsItem> newsItems){
        mInflater = LayoutInflater.from(context);
        this.mNewsItems = newsItems;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mNewsItemView = mInflater.inflate(R.layout.list_item_view, viewGroup, false);
        return new NewsViewHolder(mNewsItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int i) {
        NewsItem currentItem = mNewsItems.get(i);
        String title = currentItem.getmNewsTitle();
        String category = currentItem.getmSection();
        String author = currentItem.getmAuthorName();
        String date = currentItem.getmDate();
        newsViewHolder.titleView.setText(title);
        newsViewHolder.sectionView.setText(category);
        newsViewHolder.authorNameView.setText(author);
        newsViewHolder.dateView.setText(date);
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{

        final NewsItemAdapter mAdapter;
        @BindView(R.id.title_view)
        TextView titleView;
        @BindView(R.id.section_view)
        TextView sectionView;
        @BindView(R.id.author_name_view)
        TextView authorNameView;
        @BindView(R.id.date_view)
        TextView dateView;

        public NewsViewHolder(View view, NewsItemAdapter adapter){
            super(view);
            ButterKnife.bind(this, view);
            this.mAdapter = adapter;
        }
    }

}
