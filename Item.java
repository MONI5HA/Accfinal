package com.example.androidapp_1;

public class Item {
    private String title;
    private int imageResource;
    private String url;

    public Item(String title, int imageResource, String url) {
        this.title = title;
        this.imageResource = imageResource;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getUrl() {
        return url;
    }
}
