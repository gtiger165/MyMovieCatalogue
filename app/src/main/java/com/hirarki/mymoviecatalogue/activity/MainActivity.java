package com.hirarki.mymoviecatalogue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.fragment.MovieFragment;
import com.hirarki.mymoviecatalogue.fragment.TvShowFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionMenu fam;
    FloatingActionButton fabMovie, fabShow;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    getSupportActionBar().setTitle(getResources().getString(R.string.actionbar_movie));
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_tv_show:
                    getSupportActionBar().setTitle(getResources().getString(R.string.actionbar_tv_show));
                    fragment = new TvShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.navigation_movie);
        }

        prepareFavMenu();
    }

    private void prepareFavMenu() {
        fam = findViewById(R.id.fam);
        fabMovie = findViewById(R.id.fab_movie);
        fabShow = findViewById(R.id.fab_show);


        fabMovie.setOnClickListener(this);
        fabShow.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_language, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_language) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_movie:
                Intent favMovie = new Intent(MainActivity.this, FavMovieActivity.class);
                startActivity(favMovie);
                fam.close(true);
                break;
            case R.id.fab_show:
                Intent favShows = new Intent(MainActivity.this, FavShowsActivity.class);
                startActivity(favShows);
                fam.close(true);
                break;
        }
    }
}
