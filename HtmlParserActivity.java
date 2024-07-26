package com.example.androidapp_1;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HtmlParserActivity extends AppCompatActivity {

    private static final String TAG = "HtmlParserActivity";

    private RecyclerView gymRecyclerView;
    private GymLocationAdapter gymLocationAdapter;
    private List<Gym> gymList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_parser);

        // Initialize RecyclerView for gym locations
        gymRecyclerView = findViewById(R.id.gym_location_recycler_view);
        gymRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        gymList = new ArrayList<>();
        gymLocationAdapter = new GymLocationAdapter(this, gymList);
        gymRecyclerView.setAdapter(gymLocationAdapter);

        // Start HTML parsing
        parseLocations();
    }

    private void parseLocations() {
        List<String> gymUrls = new ArrayList<>();
        gymUrls.add("https://www.worldgym.com/");
        gymUrls.add("https://www.fit4less.ca/");
        gymUrls.add("https://www.goodlifefitness.com/");
        gymUrls.add("https://webdesignmastery.github.io/Fitclub_25-07-23/#");

        HtmlParser htmlParser = new HtmlParser(new HtmlParser.Callback() {
            @Override
            public void onLocationsParsed(List<String> locations) {
                for (String location : locations) {
                    gymList.add(new Gym("Gym Name", location));
                }
                gymLocationAdapter.notifyDataSetChanged();
                Log.d(TAG, "Locations parsed: " + locations);
            }
        });
        htmlParser.parseLocations(gymUrls);
    }
}
