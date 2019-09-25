package com.hirarki.mymoviecatalogue.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.notification.DailyNotification;
import com.hirarki.mymoviecatalogue.notification.ReleaseNotification;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String SHARED_PREF = "sharedpref";
    public static final String DAILY_KEY = "daily_key";
    public static final String RELEASE_KEY = "release_key";

    TextView changeLanguage;
    Switch swRelease, swDaily;

    SharedPreferences preferences;
    DailyNotification dailyNotification;
    ReleaseNotification releaseNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prepare();
        setSettings();
    }

    void prepare() {
        getSupportActionBar().setTitle(getString(R.string.title_change_settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changeLanguage = findViewById(R.id.action_change_language);
        swRelease = findViewById(R.id.sw_release);
        swDaily = findViewById(R.id.sw_daily);

        dailyNotification = new DailyNotification();
        releaseNotification = new ReleaseNotification();
    }

    private void setSettings() {
        preferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);

        if (preferences.getString(RELEASE_KEY, null) != null) {
            swRelease.setChecked(true);
        } else {
            swRelease.setChecked(false);
        }

        if (preferences.getString(DAILY_KEY, null) != null) {
            swDaily.setChecked(true);
        } else {
            swDaily.setChecked(false);
        }

        changeLanguage.setOnClickListener(this);

        swRelease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    releaseNotification.setTimeNotif(SettingsActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(RELEASE_KEY, "Release Notification");
                    editor.apply();
                } else {
                    releaseNotification.disableReleaseNotif(SettingsActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove(RELEASE_KEY);
                    editor.apply();
                }
            }
        });

        swDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    dailyNotification.setRepeatNotif(SettingsActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(DAILY_KEY, "Daily Notification");
                    editor.apply();
                } else {
                    dailyNotification.disableNotif(SettingsActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove(DAILY_KEY);
                    editor.apply();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.action_change_language) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
    }
}
