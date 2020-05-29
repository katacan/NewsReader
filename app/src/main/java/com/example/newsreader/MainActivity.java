package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView newsListView;
    public static ArrayList<String> newsList;
    public static ArrayList<String> articleUrls;
    public static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsList = new ArrayList<String>();
        articleUrls = new ArrayList<String>();
        newsListView = findViewById(R.id.newsListView);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, newsList);
        newsListView.setAdapter(arrayAdapter);

        ContentDownloader contentDownloader = new ContentDownloader();

        try {
            // Download News
            contentDownloader.execute("http://newsapi.org/v2/top-headlines?country=us&apiKey=bdf4fa92721a4ddaa734f6f3f65eb7da");
        } catch (Exception e) {
            e.printStackTrace();
        }

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent webView = new Intent(getApplicationContext(), ArticleView.class);
                String articleUrl = articleUrls.get(i);
                webView.putExtra("articleUrl", articleUrl);
                startActivity(webView);
            }
        });
    }
}
