package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ArticleView extends AppCompatActivity {

    WebView articleWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        articleWebView = findViewById(R.id.articleWebView);
        articleWebView.getSettings().setJavaScriptEnabled(true);

        String articleUrl = getIntent().getStringExtra("articleUrl");

        articleWebView.loadUrl(articleUrl);

    }
}
