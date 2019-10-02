package com.hirarki.mymoviecatalogue.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.fragment.MovieFragment;
import com.hirarki.mymoviecatalogue.fragment.TvShowFragment;

import static com.hirarki.mymoviecatalogue.activity.SearchActivity.EXTRA_QUERY;
import static com.hirarki.mymoviecatalogue.activity.SearchActivity.EXTRA_TYPE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionMenu fam;
    FloatingActionButton fabMovie, fabShow;
    SearchView searchView;
    private String searchedType;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    getSupportActionBar().setTitle(getResources().getString(R.string.actionbar_movie));
                    searchedType = "movie";
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .addToBackStack(null)
                            .commit();
                    return true;
                case R.id.navigation_tv_show:
                    getSupportActionBar().setTitle(getResources().getString(R.string.actionbar_tv_show));
                    searchedType = "tv_show";
                    fragment = new TvShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .addToBackStack(null)
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

        prepareFavMenu(savedInstanceState);
    }

    private void prepareFavMenu(Bundle savedInstance) {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstance == null) {
            navView.setSelectedItemId(R.id.navigation_movie);
        }

        fam = findViewById(R.id.fam);
        fabMovie = findViewById(R.id.fab_movie);
        fabShow = findViewById(R.id.fab_show);


        fabMovie.setOnClickListener(this);
        fabShow.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_language, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent searchIntent = new Intent(MainActivity.this,
                        SearchActivity.class);
                searchIntent.putExtra(EXTRA_QUERY, s);
                searchIntent.putExtra(EXTRA_TYPE, searchedType);

                startActivity(searchIntent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
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
