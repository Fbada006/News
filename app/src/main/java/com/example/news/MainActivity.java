package com.example.news;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Guardian news url
    public static final String GUARDIAN_URL = "https://content.guardianapis.com/search?order-by=newest&show-tags=contributor&show-fields=thumbnail&page-size=10&api-key=test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

//        Uri baseUri = Uri.parse(GUARDIAN_URL);
//
//        //  Build upon prepares the baseUri that we just parsed so we can add query parameters to it
//        Uri.Builder uriBuilder = baseUri.buildUpon();
//
//        //  Append query parameter and its value
//        uriBuilder.appendQueryParameter("order-by", "newest");
//        uriBuilder.appendQueryParameter("show-tags", "contributor");
//        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
//        uriBuilder.appendQueryParameter("page-size", "10");

        //  Get the list of news from {@link QueryUtils}
        ArrayList<News> news = (ArrayList<News>) QueryUtils.getArticleData(GUARDIAN_URL);

        NewsAdapter newsAdapterObject = new NewsAdapter(this, news);

        ListView listViewObject = findViewById(R.id.listView);

        listViewObject.setAdapter(newsAdapterObject);
    }
}