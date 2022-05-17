package com.ertleast.android;

public class SliderModel {
    private String banner, banner_id;
    private String banner_text;
    private String backgroundColor;

    public SliderModel(String banner_id, String banner, String banner_text, String backgroundColor) {
        this.banner = banner;
        this.banner_id = banner_id;
        this.banner_text = banner_text;
        this.backgroundColor = backgroundColor;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getBanner_text() {
        return banner_text;
    }
    public void setBanner_text(String banner_text) {
        this.banner_text = banner_text;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
