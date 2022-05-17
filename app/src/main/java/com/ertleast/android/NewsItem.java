package com.ertleast.android;


public class NewsItem {
    private String mImage_uri;
    private String mText1;
    private String mText2;

    public NewsItem(String image_uri, String text1, String text2) {
        mImage_uri = image_uri;
        mText1 = text1;
        mText2 = text2;
    }

    public String getImageResource() {
        return mImage_uri;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }
}