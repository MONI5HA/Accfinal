package com.example.androidapp_1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class  Webcralwer extends AppCompatActivity {

    private EditText urlEditText;
    private Button crawlButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webcralwer);

        urlEditText = findViewById(R.id.urlEditText);
        crawlButton = findViewById(R.id.crawlButton);
        resultTextView = findViewById(R.id.resultTextView);

        crawlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = urlEditText.getText().toString();
                if (!url.isEmpty()) {
                    new CrawlTask().execute(url);
                } else {
                    resultTextView.setText("Please enter a URL.");
                }
            }
        });
    }

    private class CrawlTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements links = doc.select("a[href]");
                for (Element link : links) {
                    result.append(link.attr("abs:href")).append("\n");
                }
            } catch (Exception e) {
                result.append("Error: ").append(e.getMessage());
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            resultTextView.setText(result);
        }
    }
}
