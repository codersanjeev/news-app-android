package com.example.sanjeev.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

// Custom ArrayAdapter for each NewsItem
public class NewsItemAdapter extends ArrayAdapter<NewsItem> {
    NewsItemAdapter(Context context, ArrayList<NewsItem> newsItems){
        super(context, 0, newsItems);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_view, parent, false);
        }
        NewsItem newsItem = getItem(position);
        TextView sectionView = listItemView.findViewById(R.id.section_view);
        TextView titleView = listItemView.findViewById(R.id.title_view);
        TextView dateView = listItemView.findViewById(R.id.date_view);
        TextView authorView = listItemView.findViewById(R.id.author_name_view);

        sectionView.setText(newsItem != null ? newsItem.getmSection() : null);
        titleView.setText(newsItem != null ? newsItem.getmNewsTitle() : null);
        dateView.setText(newsItem != null ? newsItem.getmDate() : null);
        authorView.setText(newsItem != null ? newsItem.getmAuthorName() : null);

        return listItemView;
    }
}
