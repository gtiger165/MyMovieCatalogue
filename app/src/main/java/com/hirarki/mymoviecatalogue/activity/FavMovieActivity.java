package com.hirarki.mymoviecatalogue.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.adapter.FavMovieAdapter;
import com.hirarki.mymoviecatalogue.database.FavMovieHelper;
import com.hirarki.mymoviecatalogue.helper.LoadFavCallback;
import com.hirarki.mymoviecatalogue.model.FavMovies;
import com.hirarki.mymoviecatalogue.model.FavShows;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.hirarki.mymoviecatalogue.activity.DetailActivity.EXTRA_POSITION;
import static com.hirarki.mymoviecatalogue.activity.DetailActivity.REQUEST_UPDATE;
import static com.hirarki.mymoviecatalogue.activity.DetailActivity.RESULT_DELETE;

public class FavMovieActivity extends AppCompatActivity implements LoadFavCallback{
    RecyclerView rvFavMov;
    SwipeRefreshLayout swipe;
    private FavMovieAdapter adapter;
    private FavMovieHelper movieHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_movie);

        prepare();
        showFavList(savedInstanceState);
    }

    private void prepare() {
        getSupportActionBar().setTitle("Favorite Movie List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvFavMov = findViewById(R.id.rv_fav_mov);
        swipe = findViewById(R.id.swipe_fav);
        adapter = new FavMovieAdapter(this);

        rvFavMov.setLayoutManager(new LinearLayoutManager(this));
        rvFavMov.setHasFixedSize(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void showFavList(final Bundle savedInstance) {
        movieHelper = FavMovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(false);
                onActivityResult(REQUEST_UPDATE, RESULT_DELETE, getIntent());
            }
        });

        rvFavMov.setAdapter(adapter);

        if (savedInstance == null) {
            new FavMovAsync(movieHelper, this).execute();
        } else {
            ArrayList<FavMovies> list = savedInstance.getParcelableArrayList(EXTRA_STATE);
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
        swipe.setRefreshing(false);
        adapter.setFavList(favoriteMovies);
    }

    @Override
    public void postExecuteShows(ArrayList<FavShows> favoriteShows) {

    }

    private static class FavMovAsync extends AsyncTask<Void, Void, ArrayList<FavMovies>> {
        private final WeakReference<FavMovieHelper> weakReference;
        private final WeakReference<LoadFavCallback> weakCallback;

        private FavMovAsync(FavMovieHelper helper, LoadFavCallback callback) {
            weakReference = new WeakReference<>(helper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected ArrayList<FavMovies> doInBackground(Void... voids) {
            return weakReference.get().getAllMoviesFav();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<FavMovies> favMovies) {
            super.onPostExecute(favMovies);
            weakCallback.get().postExecuteMovie(favMovies);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == REQUEST_UPDATE) {
                if (resultCode == RESULT_DELETE) {
                    int position = data.getIntExtra(EXTRA_POSITION, 0);

                    adapter.removeFav(position);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        movieHelper.close();
    }
}
