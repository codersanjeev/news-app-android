package com.example.sanjeev.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

// Background Thread to fetch news from internet
public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {

    private String mUrl;

    NewsLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    public List<NewsItem> loadInBackground() {
        // Perform Network Calls
        List<NewsItem> newsItems = new ArrayList<>();
        if(mUrl == null){
            return null;
        }
        try {
            newsItems = Utility.fetchNewsItems(mUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsItems;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
