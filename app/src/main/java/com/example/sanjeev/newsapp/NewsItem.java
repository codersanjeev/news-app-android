package com.example.sanjeev.newsapp;


/**
 * Each News Item contains
 * <p>
 * Title of the News = mNewsTitle
 * Date of Publication = mDate
 * Section to which it belongs = mSection
 * URL for details = mUrl
 */
public class NewsItem {
    private String mNewsTitle;
    private String mDate;
    private String mSection;
    private String mUrl;

    public String getmNewsTitle() {
        return mNewsTitle;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmUrl() {
        return mUrl;
    }

    NewsItem(String newsItem, String date, String section, String url) {
        this.mNewsTitle = newsItem;
        this.mDate = date;
        this.mSection = section;
        this.mUrl = url;
    }
}
