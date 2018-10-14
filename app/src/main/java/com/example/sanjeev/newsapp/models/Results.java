package com.example.sanjeev.newsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {
    @Expose
    @SerializedName("tags")
    private List<Tags> tags;
    @Expose
    @SerializedName("webTitle")
    private String webTitle;
    @Expose
    @SerializedName("webPublicationDate")
    private String webPublicationDate;
    @Expose
    @SerializedName("sectionName")
    private String sectionName;

    public List<Tags> getTags() {
        return tags;
    }

    public void setTags(List<Tags> tags) {
        this.tags = tags;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public void setWebPublicationDate(String webPublicationDate) {
        this.webPublicationDate = webPublicationDate;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
