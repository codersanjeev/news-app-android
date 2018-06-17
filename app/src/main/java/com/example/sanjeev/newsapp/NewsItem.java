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
    private String mAuthorName;


    public String getmAuthorName() {
        return mAuthorName;
    }

    String getmNewsTitle() {
        return mNewsTitle;
    }

    String getmDate() {
        return mDate;
    }

    String getmSection() {
        return mSection;
    }

    String getmUrl() {
        return mUrl;
    }

    NewsItem(String newsItem, String date, String section, String url, String authorName) {
        this.mNewsTitle = newsItem;
        this.mDate = date;
        this.mSection = section;
        this.mUrl = url;
        this.mAuthorName = authorName;
    }
}
