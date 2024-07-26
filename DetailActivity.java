package com.example.androidapp_1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private ImageView detailImageView;
    private TextView contentTextView;
    private Button openUrlButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleTextView = findViewById(R.id.detail_title);
        detailImageView = findViewById(R.id.detail_image);
        contentTextView = findViewById(R.id.detail_content);

        Intent intent = getIntent();
        String title = intent.getStringExtra("ITEM_NAME");
        int imageResId = intent.getIntExtra("ITEM_IMAGE", 0);
        String url = intent.getStringExtra("ITEM_URL");

        titleTextView.setText(title);
        detailImageView.setImageResource(imageResId);

        // Perform web scraping in the background
        new WebScrapeTask().execute(url);

        openUrlButton = findViewById(R.id.open_url_button);

        openUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the URL you want to open


                // Create an Intent to open the URL
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));

                // Start the activity to handle the URL
                startActivity(intent);
            }
        });
    }

    private class WebScrapeTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String url = urls[0];
            StringBuilder content = new StringBuilder();
            try {
                Document document = Jsoup.connect(url).get();
                Elements elements = document.select("p"); // Adjust selector based on the website's structure
                for (Element element : elements) {
                    content.append(element.text()).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            contentTextView.setText(result);
        }
    }
}