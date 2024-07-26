package com.example.androidapp_1;

import java.util.List;

import java.util.List;

public class Page {
    String url;
    int rank;
    List<String> searchKeywords;
    int pageNumber;

    // Constructor
    public Page(String url, int rank, List<String> searchKeywords, int pageNumber) {
        this.url = url;
        this.rank = rank;
        this.searchKeywords = searchKeywords;
        this.pageNumber = pageNumber;
    }

    // Getter and setter methods
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public List<String> getSearchKeywords() {
        return searchKeywords;
    }

    public void setSearchKeywords(List<String> searchKeywords) {
        this.searchKeywords = searchKeywords;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @Override
    public String toString() {
        return "Page{" +
                "url='" + url + '\'' +
                ", rank=" + rank +
                ", searchKeywords=" + searchKeywords +
                ", pageNumber=" + pageNumber +
                '}';
    }
}
