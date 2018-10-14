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
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sanjeev.newsapp.models.NewsModel;
import com.example.sanjeev.newsapp.models.Results;
import com.example.sanjeev.newsapp.rest.ApiClient;
import com.example.sanjeev.newsapp.rest.ApiInterface;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private final String API_KEY = "dcd9ad5e-c852-4c47-bc8f-eab7f3411f07";
    Call<NewsModel> call;
    ApiInterface apiInterface;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
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
            call.enqueue(new Callback<NewsModel>() {
                @Override
                public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                    Log.v("Retrofit", "success");
                    List<Results> results = response.body().getResponse().getResults();
                    recyclerView.setAdapter(new NewsItemAdapter(MainActivity.this, results));
                }

                @Override
                public void onFailure(Call<NewsModel> call, Throwable t) {
                    Log.v("Retrofit", "failed");
                }
            });

        }

    }

    @Override
    protected void onResume() {
        fetchSavedPreferences();
        super.onResume();
    }

    private void fetchSavedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getString(getString(R.string.key), getString(R.string.default_order_value)).equals(getString(R.string.default_order_value))){
            call = apiInterface.getAllNews(API_KEY, "newest");
        }
        else{
            call = apiInterface.getAllNews(API_KEY, "oldest");
        }
        switch(preferences.getString("category_list", "")){
            case "All" :
                call = apiInterface.getAllNews(API_KEY, "newest");
                break;
            case "Politics" :
                call = apiInterface.getCategorizedNews(API_KEY, "newest", "politics");
                break;
            case "Sports" :
                call = apiInterface.getCategorizedNews(API_KEY, "newest", "sports");
                break;
            case "Finance" :
                call = apiInterface.getCategorizedNews(API_KEY, "newest", "finance");
                break;
            case "Education" :
                call = apiInterface.getCategorizedNews(API_KEY, "newest", "education");
                break;
            case "Economics" :
                call = apiInterface.getCategorizedNews(API_KEY, "newest", "economics");
                break;
            default:
                call = apiInterface.getAllNews(API_KEY, "newest");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            // night mode turned on
            // set the title accordingly
            menu.findItem(R.id.night_mode).setTitle("Day Mode");
        }
        else{
            // day mode turned on
            // set the title accordingly
            menu.findItem(R.id.night_mode).setTitle("Night Mode");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.settings){
            Intent in = new Intent(this, SettingsActivity.class);
            startActivity(in);
        }
        else if(id == R.id.choose_topic) {
            Intent in = new Intent(this, ChooseCategory.class);
            startActivity(in);
        }
        else if(id == R.id.night_mode){
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            recreate();
        }
        return true;
    }

    // Checks if device is connected to internet
    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
