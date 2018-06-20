package com.example.sanjeev.newsapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsItem>>, AdapterView.OnItemClickListener {

    private ListView listView;
    private NewsItemAdapter adapter;
    private ArrayList<NewsItem> mNewsItems;
    private final String BASE_URL = "https://content.guardianapis.com/search?&show-tags=contributor&api-key=";
    private final String API_KEY = "dcd9ad5e-c852-4c47-bc8f-eab7f3411f07";
    private final String ORDER_BY_ATTR = "&order-by=oldest";

    @Override
    protected void onResume() {
        fetchSavedPreferences();
        super.onResume();
    }

    // get saved preference and build the string url accordingly.
    private void fetchSavedPreferences() {
        String url;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getString(getString(R.string.key), getString(R.string.default_order_value)).equals(getString(R.string.default_order_value))){
            url = BASE_URL + API_KEY;
        }
        else{
            url = BASE_URL + API_KEY + ORDER_BY_ATTR;
        }
        switch(preferences.getString(getString(R.string.category_key), "")){
            case "All" :
                Utility.setmURL(url);
                break;
            case "Politics" :
                Utility.setmURL(url + "&q=politics");
                break;
            case "Sports" :
                Utility.setmURL(url + "&q=sports");
                break;
            case "Finance" :
                Utility.setmURL(url + "&q=finance");
                break;
            case "Education" :
                Utility.setmURL(url + "&q=education");
                break;
            case "Economics" :
                Utility.setmURL(url + "&q=economics");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchSavedPreferences();

        listView = findViewById(R.id.list_view);
        // create an empty list of news items
        mNewsItems = new ArrayList<>();

        if(!isConnected()){
            // Device is not connected
            // Display the error information
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.network_error_message)
                    .setTitle(R.string.network_error_title);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        else{
            // else start to fetch the data in background
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
            // To display details of a news
            // implement onclick for each item in list
            listView.setOnItemClickListener(this);

        }
    }

    // On creation of loader
    // create a http request on guardian server
    @Override
    public Loader<List<NewsItem>> onCreateLoader(int id, Bundle args) {

        Uri baseUri = Uri.parse(Utility.getmURL());
        Uri.Builder uriBuilder = baseUri.buildUpon();
        return new NewsLoader(this, uriBuilder.toString());
    }

    // on receiving the response, Update the UI
    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        mNewsItems = new ArrayList<>(data);
        if(mNewsItems.isEmpty()){
            // No news fetched
            // Display Message to user
            // possible reasons are server is down
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.json_error)
                    .setTitle(R.string.json_error_title);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
        UpdateView(mNewsItems);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        adapter.clear();
    }

    private void UpdateView(ArrayList<NewsItem> newsItems) {
        adapter = new NewsItemAdapter(getApplicationContext(), newsItems);
        listView.setAdapter(adapter);
    }

    // On click over any item in list
    // a webview will be opened with url specific to that item
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent in = new Intent(getApplicationContext(), NewsDetailView.class);
        in.putExtra("URL", mNewsItems.get(position).getmUrl());
        startActivity(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.settings){
            Intent in = new Intent(this, SettingsActivity.class);
            startActivity(in);
            return true;
        }
        else if(id == R.id.choose_topic)
        {
            Intent in = new Intent(this, ChooseCategory.class);
            startActivity(in);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Checks if device is connected to internet
    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
