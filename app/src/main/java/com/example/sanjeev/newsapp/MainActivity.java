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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    private static ArrayList<NewsItem> newsItems = new ArrayList<>();
    private NewsItemAdapter mAdapter;
    private final String BASE_URL = "https://content.guardianapis.com/search?&show-tags=contributor&api-key=";
    private final String API_KEY = "dcd9ad5e-c852-4c47-bc8f-eab7f3411f07";
    private final String ORDER_BY_ATTR = "&order-by=oldest";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        fetchSavedPreferences();

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

            // will replace it soon with background thread
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                newsItems = Utility.fetchNewsItems(Utility.getmURL());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        mAdapter = new NewsItemAdapter(this, newsItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

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
        Log.v("switch_value", preferences.getString("category_list", ""));
        switch(preferences.getString("category_list", "")){
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
            default:
                Utility.setmURL(url);
        }
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
