package com.example.androidapp_1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PageRankingActivity extends AppCompatActivity {

    private EditText inputText;
    private Button extractButton;
    private RecyclerView recyclerView;
    public PageAdapter pageAdapter;
    private TextView noOccurrenceMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_ranking);

        inputText = findViewById(R.id.page_edit);
        extractButton = findViewById(R.id.search_button);
        recyclerView = findViewById(R.id.recycler_view_page);
        noOccurrenceMessage = findViewById(R.id.no_occurrence_message);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pageAdapter = new PageAdapter(new ArrayList<>());
        recyclerView.setAdapter(pageAdapter);

        extractButton.setOnClickListener(v -> {
            String keyword = inputText.getText().toString().trim();
            if (!keyword.isEmpty()) {
                new FetchPageDataTask(this, keyword).execute();
            } else {
                Toast.makeText(this, "Please enter a keyword", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class FetchPageDataTask extends AsyncTask<Void, Void, List<Page>> {

        private Context context;
        private String keyword;
        private PageRankingSystem2 prs;

        FetchPageDataTask(Context context, String keyword) {
            this.context = context;
            this.keyword = keyword;
            prs = new PageRankingSystem2();
        }

        @Override
        protected List<Page> doInBackground(Void... voids) {
            List<String> urls = Arrays.asList(
                    "https://www.fit4less.ca/",
                    "https://www.worldgym.com/",
                    "https://www.fit4less.ca/",
                    "https://www.fit4less.ca/join-now",
                    "https://www.fit4less.ca/memberships/lock-in-your-rate",
                    "https://www.fit4less.ca/memberships/free-workout",
                    "https://www.fit4less.ca/memberships/black-card-workout-area",
                    "https://www.fit4less.ca/faq/membership-changes",
                    "https://www.fit4less.ca/join-now-flow?gym=272#",
                    "https://www.worldgym.com/gyms/",
                    "https://www.worldgym.com/classes/yoga/",
                    "https://www.worldgym.com/classes/cycling/",
                    "https://www.worldgym.com/classes/strength/",
                    "https://www.worldgym.com/classes/hiit/",
                    "https://www.worldgym.com/classes/zumba/",
                    "https://www.worldgym.com/classes/les-mills/",
                    "https://www.worldgym.com/classes/",
                    "https://www.worldgym.com/training/",
                    "https://www.worldgym.com/training/world-gym-athletics/",
                    "https://www.worldgym.com/training/personal-training/",
                    "https://www.worldgym.com/video-center/workout-tips/",
                    "https://www.worldgym.com/about/",
                    "https://www.worldgym.com/about/photo-gallery/",
                    "https://www.worldgym.com/blog/",
                    "https://www.worldgym.com/about/testimonials/",
                    "https://www.worldgymfranchising.com/",
                    "https://shopworldgym.com/",
                    "https://www.worldgym.com/barrie/classes/schedule/",
                    "https://www.planetfitness.ca/",
                    "https://www.planetfitness.ca/gyms/",
                    "https://www.planetfitness.ca/login",
                    "https://www.planetfitness.ca/about-planet-fitness",
                    "https://www.planetfitness.ca/gyms/?q=",
                    "https://www.planetfitness.ca/gyms/?q=#current-location",
                    "https://www.planetfitness.com/gyms/dearborn-ford-rd-mi",
                    "https://www.planetfitness.ca/gyms/brampton-centennial-mall",
                    "https://www.planetfitness.ca/gyms/etobicoke",
                    "https://www.planetfitness.ca/about-planet-fitness/why-planet-fitness",
                    "https://www.planetfitness.ca/gym-memberships",
                    "https://www.planetfitness.ca/about-planet-fitness/welcome-booklet",
                    "https://www.planetfitness.ca/",
                    "https://www.planetfitness.ca/gyms/",
                    "https://www.planetfitness.ca/login",
                    "https://www.planetfitness.ca/about-planet-fitness",
                    "https://www.planetfitness.ca/gyms/?q=",
                    "https://www.planetfitness.ca/gyms/?q=#current-location",
                    "https://www.planetfitness.com/gyms/dearborn-ford-rd-mi",
                    "https://www.planetfitness.ca/gyms/brampton-centennial-mall",
                    "https://www.planetfitness.ca/gyms/etobicoke",
                    "https://www.planetfitness.ca/about-planet-fitness/why-planet-fitness",
                    "https://www.planetfitness.ca/gym-memberships",
                    "https://www.planetfitness.ca/about-planet-fitness/welcome-booklet",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/#",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/program",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/service",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/about",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/company",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/join",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/getstarted",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/bookaclass",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/basic",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/weekly ",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/monthly",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/business",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/franchise",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/partnership",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/network",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/#",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/program",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/service",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/about",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/company",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/join",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/getstarted",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/bookaclass",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/basic",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/weekly ",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/monthly",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/business",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/franchise",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/partnership",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/network",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/#",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/program",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/service",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/about",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/company",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/join",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/getstarted",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/bookaclass",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/basic",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/weekly ",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/monthly",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/business",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/franchise",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/partnership",
                    "https://webdesignmastery.github.io/Fitclub_25-07-23/network"


                    // Add more URLs as needed
            );

            List<Page> matchedPages = new ArrayList<>();
            List<String> searchKeywords = Collections.singletonList(keyword);

            for (int i = 0; i < urls.size(); i++) {
                String url = urls.get(i);
                String pageContent;

                try {
                    pageContent = prs.fetchHTMLContent(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                String textContent = prs.extractTextFromHTML(pageContent);
                List<String> keywords = prs.extractKeywords(textContent);
                AVLTree tree = new AVLTree();
                for (String kw : keywords) {
                    tree.insert(kw);
                }

                int rank = prs.calculatePageRank(tree, searchKeywords);
                if (rank > 0) {
                    Page page = new Page(url, rank, searchKeywords, i + 1);
                    matchedPages.add(page);
                }
            }

            // Sort pages by rank in descending order
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                matchedPages.sort((p1, p2) -> p2.rank - p1.rank);
            }

            return matchedPages;
        }

        @Override
        protected void onPostExecute(List<Page> pages) {
            super.onPostExecute(pages);
            if (context instanceof PageRankingActivity) {
                PageRankingActivity activity = (PageRankingActivity) context;
                if (pages.isEmpty()) {
                    activity.noOccurrenceMessage.setVisibility(View.VISIBLE);
                    activity.recyclerView.setVisibility(View.GONE);
                } else {
                    activity.pageAdapter.updatePages(pages);
                    activity.noOccurrenceMessage.setVisibility(View.GONE);
                    activity.recyclerView.setVisibility(View.VISIBLE);
                }
                //Toast.makeText(context, "Page Rank Displayed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
