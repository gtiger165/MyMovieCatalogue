package com.hirarki.favoritemovieapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hirarki.favoritemovieapp.R;
import com.hirarki.favoritemovieapp.adapter.FavoriteAdapter;
import com.hirarki.favoritemovieapp.database.DatabaseContract;
import com.hirarki.favoritemovieapp.entity.FavItem;
import com.hirarki.favoritemovieapp.helper.LoadFavCallback;
import com.hirarki.favoritemovieapp.helper.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.hirarki.favoritemovieapp.database.DatabaseContract.FavoriteMovies.CONTENT_URI;

public class MainActivity extends AppCompatActivity  implements LoadFavCallback {
    private FavoriteAdapter adapter;
    private MainActivity.DataObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareFav();
    }

    private void prepareFav() {
        getSupportActionBar().setTitle(getString(R.string.fav_title));

        RecyclerView rvFav = findViewById(R.id.rv_fav_mov);
        adapter = new FavoriteAdapter(this);

        rvFav.setLayoutManager(new LinearLayoutManager(this));
        rvFav.setHasFixedSize(true);
        rvFav.setAdapter(adapter);

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        observer = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(CONTENT_URI, true, observer);
        new getFavorite(this, this).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_language, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_language) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void postExecute(Cursor favorite) {
        ArrayList<FavItem> listFav = MappingHelper.mapCursorToList(favorite);
        if (listFav.size() > 0) {
            adapter.setFavList(listFav);
        } else {
            adapter.setFavList(new ArrayList<FavItem>());
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    private static class getFavorite extends AsyncTask<Void, Void, Cursor> {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavCallback> weakCallback;

        private getFavorite(Context context, LoadFavCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return weakContext.get().getContentResolver().query(CONTENT_URI, null,
                    null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            weakCallback.get().postExecute(cursor);
        }
    }

    public class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new getFavorite(context, (MainActivity) context).execute();
        }
    }
}
