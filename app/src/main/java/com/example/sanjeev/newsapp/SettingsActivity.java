package com.example.sanjeev.newsapp;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    public static class NewsPreferenceFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            ListPreference listPreference = (ListPreference) findPreference("order_by");
            listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
                    String orderBy = sharedPreferences.getString("order_by", "Newest");
                    if(orderBy.equals("Newest")){
                        // Order the news accordingly
                        // we need to build the URL according to it
                        // URL = https://content.guardianapis.com/search?&show-tags=contributor&api-key=dcd9ad5e-c852-4c47-bc8f-eab7f3411f07
                        Utility.setmURL("https://content.guardianapis.com/search?&show-tags=contributor&api-key=dcd9ad5e-c852-4c47-bc8f-eab7f3411f07");
                    }
                    else{
                        // Order the oldest news first
                        // URL = https://content.guardianapis.com/search?&show-tags=contributor&order-by=oldest&api-key=dcd9ad5e-c852-4c47-bc8f-eab7f3411f07
                        Utility.setmURL("https://content.guardianapis.com/search?&show-tags=contributor&order-by=oldest&api-key=dcd9ad5e-c852-4c47-bc8f-eab7f3411f07");
                    }
                    return true;
                }
            });
        }
    }
}
