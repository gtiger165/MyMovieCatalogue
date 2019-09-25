package com.hirarki.mymoviecatalogue.activity;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.adapter.FavMovieAdapter;
import com.hirarki.mymoviecatalogue.database.FavMovieHelper;
import com.hirarki.mymoviecatalogue.helper.LoadFavCallback;
import com.hirarki.mymoviecatalogue.helper.MappingHelper;
import com.hirarki.mymoviecatalogue.model.FavMovies;
import com.hirarki.mymoviecatalogue.model.FavShows;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.hirarki.mymoviecatalogue.activity.DetailActivity.EXTRA_POSITION;
import static com.hirarki.mymoviecatalogue.activity.DetailActivity.REQUEST_UPDATE;
import static com.hirarki.mymoviecatalogue.activity.DetailActivity.RESULT_DELETE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.CONTENT_URI;

public class FavMovieActivity extends AppCompatActivity implements LoadFavCallback, SwipeRefreshLayout.OnRefreshListener {
    RecyclerView rvFavMov;
    SwipeRefreshLayout swipe;
    private FavMovieAdapter adapter;
    private FavMovieHelper movieHelper;
    private static final String EXTRA_STATE = "EXTRA_STATE";

    private static HandlerThread handlerThread;
    private DataObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_movie);

        prepare();
        showFavList(savedInstanceState);
    }

    private void prepare() {
        getSupportActionBar().setTitle(getString(R.string.fav_movie_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvFavMov = findViewById(R.id.rv_fav_mov);
        swipe = findViewById(R.id.swipe_fav);
        adapter = new FavMovieAdapter(this);

        rvFavMov.setLayoutManager(new LinearLayoutManager(this));
        rvFavMov.setHasFixedSize(true);

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        observer = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, observer);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void showFavList(final Bundle savedInstance) {
        movieHelper = FavMovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        swipe.setOnRefreshListener(this);

        rvFavMov.setAdapter(adapter);

        if (savedInstance == null) {
            new FavMovAsync(this, this).execute();
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
    public void postExecuteMovie(Cursor favCursor) {
        swipe.setRefreshing(false);
        ArrayList<FavMovies> favList = MappingHelper.mapCursorToList(favCursor);

        if (favList.size() > 0) {
            adapter.setFavList(favList);
        } else {
            adapter.setFavList(new ArrayList<FavMovies>());
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void postExecuteShows(ArrayList<FavShows> favoriteShows) {

    }

    @Override
    public void onRefresh() {
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(false);
    }

    private static class FavMovAsync extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavCallback> weakCallback;

        private FavMovAsync(Context context, LoadFavCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
//            return weakReference.get().getAllMoviesFav();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected void onPostExecute(Cursor favMovies) {
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

    public static class DataObserver extends ContentObserver {
        Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new FavMovAsync(context, (LoadFavCallback) context).execute();
        }
    }
}
