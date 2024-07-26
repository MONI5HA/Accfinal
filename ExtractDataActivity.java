package com.example.androidapp_1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractDataActivity extends AppCompatActivity {

    private EditText inputText;
    private Button extractButton;
    private RecyclerView recyclerView;
    private DataAdapter dataAdapter;
    private List<String> extractedDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract_data);

        inputText = findViewById(R.id.input_text);
        extractButton = findViewById(R.id.extract_button);
        recyclerView = findViewById(R.id.extracted_data_recycler_view);

        extractedDataList = new ArrayList<>();
        dataAdapter = new DataAdapter(extractedDataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(dataAdapter);

        extractButton.setOnClickListener(v -> {
            String text = inputText.getText().toString();
            if (!TextUtils.isEmpty(text)) {
                extractedDataList.clear();
                extractedDataList.addAll(extractData(text));
                dataAdapter.notifyDataSetChanged();
            }
        });

    }
    //Sample text:
    //On 2023-05-15, John Doe visited the website https://www.example.com and https://www.testsite.org. You can contact him at john.doe@example.com or call him at 123-456-7890. Another email to reach him is johndoe@testmail.com. Jane Smith's phone number is 987-654-3210. She visited the site http://www.samplesite.com on 2022-12-01. Her email is jane.smith@sample.org. The price for the product was $12.34, and another product cost 45.67 CAD. For further inquiries, visit our support page at https://support.example.com.


    private List<String> extractData(String text) {
        List<String> data = new ArrayList<>();

        // Example patterns for extraction
        String datePattern = "\\b\\d{4}-\\d{2}-\\d{2}\\b"; // YYYY-MM-DD
        String urlPattern = "\\b(https?://[\\w\\.-]+)\\b";
        String phonePattern = "\\b\\+?\\d{1,4}[-.\\s]?\\(?\\d+\\)?[-.\\s]?\\d+[-.\\s]?\\d+[-.\\s]?\\d+\\b";
        String emailPattern = "\\b[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}\\b";
        String pricePattern = "\\b\\$?\\d+(\\.\\d{2})?\\s?[A-Z]*\\b";

        // Find dates
        Pattern datePat = Pattern.compile(datePattern);
        Matcher dateMatcher = datePat.matcher(text);
        while (dateMatcher.find()) {
            data.add("Date: " + dateMatcher.group());
        }

        // Find URLs
        Pattern urlPat = Pattern.compile(urlPattern);
        Matcher urlMatcher = urlPat.matcher(text);
        while (urlMatcher.find()) {
            data.add("URL: " + urlMatcher.group());
        }

        // Find phone numbers
        Pattern phonePat = Pattern.compile(phonePattern);
        Matcher phoneMatcher = phonePat.matcher(text);
        while (phoneMatcher.find()) {
            data.add("Phone: " + phoneMatcher.group());
        }

        // Find email addresses
        Pattern emailPat = Pattern.compile(emailPattern);
        Matcher emailMatcher = emailPat.matcher(text);
        while (emailMatcher.find()) {
            data.add("Email: " + emailMatcher.group());
        }

        // Find prices
        Pattern pricePat = Pattern.compile(pricePattern);
        Matcher priceMatcher = pricePat.matcher(text);
        while (priceMatcher.find()) {
            data.add("Price: " + priceMatcher.group());
        }

        return data;
    }

}
