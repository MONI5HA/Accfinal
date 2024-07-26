package com.example.androidapp_1;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HtmlParser {

    public interface Callback {
        void onLocationsParsed(List<String> locations);
    }

    private Callback callback;

    public HtmlParser(Callback callback) {
        this.callback = callback;
    }

    public void parseLocations(List<String> urls) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> locations = new ArrayList<>();
                for (String url : urls) {
                    try {
                        Document doc = Jsoup.connect(url).get();
                        Elements locationElements = doc.select("p");
                        for (org.jsoup.nodes.Element element : locationElements) {
                            locations.add(element.text());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (callback != null) {
                    callback.onLocationsParsed(locations);
                }
            }
        }).start();
    }
}
