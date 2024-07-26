package com.example.androidapp_1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainPage extends AppCompatActivity {

    private static final String TAG = "MainPage";

    private AutoCompleteTextView searchBar;
    private TextView suggestionsTextView;
    private TextView frequencyCountTextView;
    private RecyclerView completionRecyclerView;

    private SpellChecker spellChecker;
    private TrieAlgorithm trieAlgorithm;
    private ArrayAdapter<String> autoCompleteAdapter;
    private List<String> spellCheckSuggestions;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private FrequencyCounter frequencyCounter;
    private KMP kmp;
    private TextView searchFrequencyTextView;
    private TextView topPricesTextView;

    private Map<String, Integer> searchFrequencyMap;
    private SearchFrequencyManager searchFrequencyManager;

    private RecyclerView locationRecyclerView;

    private List<String> locationList;

    private Button invertedbutton, webcrawler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        searchBar = findViewById(R.id.search_bar);
        suggestionsTextView = findViewById(R.id.suggestions_text);
        frequencyCountTextView = findViewById(R.id.frequency_count_text);
        searchFrequencyTextView = findViewById(R.id.search_frequency_text);
        searchFrequencyManager = new SearchFrequencyManager();

        completionRecyclerView = findViewById(R.id.completion_recycler_view);



        invertedbutton = findViewById(R.id.inverted_button);
        webcrawler = findViewById(R.id.webcrawler_button);

        invertedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, InvertedIndexingActivity.class);
                startActivity(intent);
            }
        });

        webcrawler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, Webcralwer.class);
                startActivity(intent);
            }
        });


        Button pageRankingButton = findViewById(R.id.page_ranking_button);
        pageRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, PageRankingActivity.class);
                startActivity(intent);
            }
        });

        Button extractDataButton = findViewById(R.id.extract_data_button);
        extractDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, ExtractDataActivity.class);
                startActivity(intent);
            }
        });

        spellChecker = new SpellChecker();
        trieAlgorithm = new TrieAlgorithm();
        kmp = new KMP();
        searchFrequencyMap = new HashMap<>();
        new LoadVocabularyTask().execute();

        // Initialize RecyclerView for word completions
        completionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        spellCheckSuggestions = new ArrayList<>();
        autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, spellCheckSuggestions);
        completionRecyclerView.setAdapter(new SpellCheckAdapter(spellCheckSuggestions));



        recyclerView = findViewById(R.id.recycler_view); // Ensure you initialize this
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // 2 columns in grid
        recyclerView.setLayoutManager(gridLayoutManager);

        itemList = new ArrayList<>();
        itemList.add(new Item("World gym", R.drawable.world, "https://www.worldgym.com/"));
        itemList.add(new Item("fit4less", R.drawable.fit4, "https://www.fit4less.ca/"));
        //  itemList.add(new Item("Planet fitness", R.drawable.planet, "https://www.planetfitness.ca/"));
        itemList.add(new Item("GoodLife fitness", R.drawable.good, "https://www.goodlifefitness.com/"));
        itemList.add(new Item("Fitclub", R.drawable.fit, "https://webdesignmastery.github.io/Fitclub_25-07-23/#"));

        itemAdapter = new ItemAdapter(this, itemList);
        recyclerView.setAdapter(itemAdapter);


        Button parseLocationsButton = findViewById(R.id.parse_locations_button);
        parseLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, HtmlParserActivity.class);
                startActivity(intent);
            }
        });
        try {
            frequencyCounter = new FrequencyCounter(this);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Set AutoCompleteTextView listener
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String input = searchBar.getText().toString().trim();
            Log.d(TAG, "Search input: " + input);
            if (!input.isEmpty()) {
                new SpellCheckTask().execute(input);
                new FrequencyCountTask().execute(input);
                trackSearchFrequency(input);
            }
            return true;
        });

        searchBar.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                completionRecyclerView.setVisibility(View.GONE);
            }
        });

        searchBar.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    List<String> completions = new ArrayList<>(trieAlgorithm.getAllWords());
                    autoCompleteAdapter = new ArrayAdapter<>(MainPage.this,
                            android.R.layout.simple_dropdown_item_1line, completions);
                    searchBar.setAdapter(autoCompleteAdapter);
                    searchBar.showDropDown();
                } else {
                    // Clear suggestions and frequency text when search bar is empty
                    suggestionsTextView.setVisibility(View.GONE);
                    frequencyCountTextView.setText(""); // Clear frequency count text
                    searchFrequencyTextView.setText(""); // Clear search frequency text
                    completionRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String query = editable.toString();
                if (!query.isEmpty()) {
                    searchFrequencyManager.insertQuery(query);
                    boolean found = searchFrequencyManager.searchQuery(query);
                    if (found) {
                        updateFrequencyDisplay(query);
                    }
                }
            }
        });


    }


    private void updateFrequencyDisplay(String query) {
        String normalizedQuery = query.trim().toLowerCase();
        Integer frequency = searchFrequencyManager.getQueryLog().get(normalizedQuery);
        if (frequency != null) {
            searchFrequencyTextView.setText(normalizedQuery + ": " + frequency / 2);
        } else {
            searchFrequencyTextView.setText("Query not found.");
        }
    }


    private void trackSearchFrequency(String searchTerm) {
        Log.d(TAG, "Tracking frequency for: " + searchTerm);

        String textData = "This is a sample text for search frequency tracking. The search term will be searched using the KMP algorithm.";

        // Use KMP to search for the term in the text data
        int frequency = kmp.search(textData, searchTerm);
        searchFrequencyMap.put(searchTerm, frequency);

        // Display the frequency count
        StringBuilder frequencyText = new StringBuilder();
        for (Map.Entry<String, Integer> entry : searchFrequencyMap.entrySet()) {
            frequencyText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        searchFrequencyTextView.setText(frequencyText.toString());
        searchFrequencyTextView.setVisibility(View.VISIBLE);

        Log.d(TAG, "Frequency text: " + frequencyText.toString());
    }


    private class LoadVocabularyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            spellChecker.loadVocabulary(MainPage.this);
            Set<String> vocabulary = spellChecker.getVocabulary();
            for (String word : vocabulary) {
                trieAlgorithm.insert(word);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            searchBar.setOnEditorActionListener((v, actionId, event) -> {
                String input = searchBar.getText().toString().trim();
                new SpellCheckTask().execute(input);
                new FrequencyCountTask().execute(input);
                return true;
            });

            Log.d(TAG, "LoadVocabularyTask: Vocabulary loaded and search bar listener set");
        }
    }

    private class SpellCheckTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String input = strings[0];
            if (input.isEmpty()) {
                return ""; // Return empty string if no input
            }
            return spellChecker.checkWord(input);
        }

        @Override
        protected void onPostExecute(String suggestion) {
            if (suggestion.isEmpty()) {
                suggestionsTextView.setVisibility(View.GONE);
            } else {
                suggestionsTextView.setText(suggestion);
                suggestionsTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    private class FrequencyCountTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {
            String input = strings[0];
            if (input.isEmpty()) {
                return 0; // Return 0 if no input
            }
            return frequencyCounter.countWordFrequency(input);
        }

        @Override
        protected void onPostExecute(Integer count) {
            if (count > 0) {
                frequencyCountTextView.setText("Frequency of the entered word: " + count);
                frequencyCountTextView.setVisibility(View.VISIBLE);
            } else {
                frequencyCountTextView.setText("No frequency for the given word found");
                frequencyCountTextView.setVisibility(View.VISIBLE);
            }
        }
    }

}

