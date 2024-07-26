package com.example.androidapp_1;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvertedIndexingActivity extends AppCompatActivity {

    private EditText inputText;
    private Button processButton;
    private TextView invertedIndexTextView;
    private Map<String, List<Position>> invertedIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inverted_indexing);

        inputText = findViewById(R.id.input_text);
        processButton = findViewById(R.id.process_button);
        invertedIndexTextView = findViewById(R.id.inverted_index_text_view);
        invertedIndex = new HashMap<>();

        // Load and process the single document
        loadDocumentFromFile();

        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = inputText.getText().toString().trim().toLowerCase();
                if (!TextUtils.isEmpty(query)) {
                    JsonObject jsonResult = getJsonResult(query);
                    invertedIndexTextView.setText(jsonResult.toString());
                }
            }
        });
    }

    private void loadDocumentFromFile() {
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.pagetext );
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder documentBuilder = new StringBuilder();
            String line;
            int pageIndex = 0;

            while ((line = reader.readLine()) != null) {
                processLine(line, pageIndex);
                documentBuilder.append(line).append(" ");
                pageIndex++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            invertedIndexTextView.setText("Error reading document.");
        }
    }

    private void processLine(String line, int pageIndex) {
        String[] words = line.split("\\s+");
        for (int pos = 0; pos < words.length; pos++) {
            String word = words[pos].toLowerCase().replaceAll("[^a-zA-Z0-9]", "");
            if (!word.isEmpty()) {
                Position position = new Position(pageIndex, pos);
                if (!invertedIndex.containsKey(word)) {
                    invertedIndex.put(word, new ArrayList<>());
                }
                invertedIndex.get(word).add(position);
            }
        }
    }

    private JsonObject getJsonResult(String query) {
        JsonObject jsonObject = new JsonObject();
        List<Position> positions = invertedIndex.get(query);
        if (positions != null) {
            JsonArray jsonArray = new JsonArray();
            for (Position pos : positions) {
                JsonObject positionObject = new JsonObject();
                positionObject.addProperty("pageIndex", pos.pageIndex);
                positionObject.addProperty("position", pos.position);
                jsonArray.add(positionObject);
            }
            jsonObject.add(query, jsonArray);
        } else {
            jsonObject.addProperty(query, "Term not found pls enter the crt text");
        }
        return jsonObject;
    }

    private static class Position {
        int pageIndex;
        int position;

        Position(int pageIndex, int position) {
            this.pageIndex = pageIndex;
            this.position = position;
        }
    }
}
