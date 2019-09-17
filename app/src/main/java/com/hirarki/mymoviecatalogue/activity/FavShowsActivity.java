package com.hirarki.mymoviecatalogue.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.adapter.FavShowsAdapter;
import com.hirarki.mymoviecatalogue.database.FavShowHelper;
import com.hirarki.mymoviecatalogue.helper.LoadFavCallback;
import com.hirarki.mymoviecatalogue.model.FavMovies;
import com.hirarki.mymoviecatalogue.model.FavShows;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavShowsActivity extends AppCompatActivity implements LoadFavCallback{
    RecyclerView rvFavShows;
    SwipeRefreshLayout swipe;
    private FavShowsAdapter adapter;
    private FavShowHelper showsHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_shows);

        prepare();
        showFavList(savedInstanceState);
    }

    private void prepare() {
        getSupportActionBar().setTitle("Favorite Shows List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvFavShows = findViewById(R.id.rv_fav_shows);
        swipe = findViewById(R.id.swipe_fav_shows);
        adapter = new FavShowsAdapter(this);

        rvFavShows.setLayoutManager(new LinearLayoutManager(this));
        rvFavShows.setHasFixedSize(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void showFavList(Bundle savedInstance) {
        showsHelper = FavShowHelper.getInstance(getApplicationContext());
        showsHelper.open();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        rvFavShows.setAdapter(adapter);

        if (savedInstance == null) {
            new FavShowsAsync(showsHelper, this).execute();
        } else {
            ArrayList<FavShows> list = savedInstance.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setFavList(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getFavList());
    }

    @Override
    public void preExecute() {
        swipe.setRefreshing(true);
    }

    @Override
    public void postExecuteMovie(ArrayList<FavMovies> favoriteMovies) {

    }

    @Override
    public void postExecuteShows(ArrayList<FavShows> favoriteShows) {
        swipe.setRefreshing(false);
        adapter.setFavList(favoriteShows);
    }

    private static class FavShowsAsync extends AsyncTask<Void, Void, ArrayList<FavShows>> {
        private final WeakReference<FavShowHelper> weakReference;
        private final WeakReference<LoadFavCallback> weakCallback;

        private FavShowsAsync(FavShowHelper helper, LoadFavCallback callback) {
            weakReference = new WeakReference<>(helper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<FavShows> doInBackground(Void... voids) {
            return weakReference.get().getAllTvFavorite();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<FavShows> favShows) {
            super.onPostExecute(favShows);
            weakCallback.get().postExecuteShows(favShows);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showsHelper.close();
    }
}
