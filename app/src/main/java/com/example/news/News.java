package com.example.news;

public class News {

    //  News thumbnail
    private String mThumbnail;

    //  News title
    private String mTitle;

    //  Author name
    private String mAuthor;

    //  News section
    private String mSection;

    //  Publish time of the news
    private String mTime;

    //  News url
    private String mUrl;

    /**
     * Create a new News object.
     */
    public News(String thumbnail, String title, String author, String section, String time, String url) {
        mThumbnail = thumbnail;
        mTitle = title;
        mAuthor = author;
        mSection = section;
        mTime = time;
        mUrl = url;
    }

    /**
     * Get the news thumbnail
     */
    public String getThumbnail() {
        return mThumbnail;
    }

    /**
     * Get the news title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     *  Get the author name
     */
    public String getAuthor(){
        return mAuthor;
    }

    /**
     * Get the news section
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Get the publish time of the news
     */
    public String getTime() {
        return mTime;
    }

    /**
     *  Get the news url
     */
    public String getUrl(){
        return mUrl;
    }
}