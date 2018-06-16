package com.example.sanjeev.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// Utility class to facilitate implementation of fetching and parsing of news
public final class Utility {

    private Utility() {
    }

    public static List<NewsItem> fetchNewsItems(String requestUrl) throws JSONException {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("LOG_TAG", "Problem making the HTTP request.", e);
        }

        return parseJSON(jsonResponse);
    }

    // Creates URL Object from String url.
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("LOG_TAG", "Problem building the URL ", e);
        }
        return url;
    }

    // Makes HTTP Request
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("LOG_TAG", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("LOG_TAG", "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    // Read from fetched InputStream and convert it into String
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    // Extract list of news items from fetched String response
    private static List<NewsItem> parseJSON(String response) throws JSONException {
        String section, pubDate, title, url;
        List<NewsItem> newsItems = new ArrayList<>();
        JSONObject root = new JSONObject(response);
        JSONObject res = root.getJSONObject("response");
        JSONArray resultsArray = res.getJSONArray("results");
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject result = resultsArray.getJSONObject(i);
            section = result.getString("sectionName");
            pubDate = result.getString("webPublicationDate");
            title = result.getString("webTitle");
            url = result.getString("webUrl");
            newsItems.add(new NewsItem(title, pubDate, section, url));
        }
        return newsItems;
    }

}
