package com.example.newsreader;

import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.newsreader.MainActivity.arrayAdapter;
import static com.example.newsreader.MainActivity.articleUrls;
import static com.example.newsreader.MainActivity.newsList;

public class ContentDownloader extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... urls) {
        URL url;
        HttpURLConnection httpURLConnection;

        try {
            url  = new URL(urls[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);

            int data = inputStreamReader.read();
            String jsonData = "";

            while (data != -1) {
                char c = (char) data;
                jsonData += c;
                data = inputStreamReader.read();
            }

            JSONObject apiJsonObject = new JSONObject(jsonData);
            JSONArray articles = apiJsonObject.getJSONArray("articles");

            int numOfNews = 15;

            if (articles.length() < 15) {
                numOfNews = articles.length();
            }

            for (int i = 0; i < numOfNews; i++) {
                JSONObject article = articles.getJSONObject(i);

                if (article.isNull("title") || article.isNull("url")) {
                    continue;
                }

                String title = article.getString("title");
                String articleUrl = article.getString("url");

                newsList.add(title);
                articleUrls.add(articleUrl);

                Log.i("API TITLE", title);
                Log.i("API URL", articleUrl);
            }

            return jsonData;

        } catch (Exception exc) {
            exc.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        arrayAdapter.notifyDataSetChanged();
    }
}
