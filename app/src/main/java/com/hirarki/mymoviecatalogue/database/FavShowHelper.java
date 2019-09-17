package com.hirarki.mymoviecatalogue.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hirarki.mymoviecatalogue.model.FavShows;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteShows.TABLE_NAME_SHOWS;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteShows.TV_OVERVIEW;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteShows.TV_PHOTO;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteShows.TV_RELEASE_DATE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteShows.TV_TITLE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteShows.TV_VOTE_AVERAGE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteShows.TV_VOTE_COUNT;

public class FavShowHelper {
    private static final String DATABASE_TABLE = TABLE_NAME_SHOWS;
    private static DatabaseHelper dbHelper;
    private static FavShowHelper instance;

    private static SQLiteDatabase database;

    private FavShowHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static FavShowHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (instance == null) {
                    instance = new FavShowHelper(context);
                }
            }
        }
        return instance;
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<FavShows> getAllTvFavorite() {
        ArrayList<FavShows> list = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();
        FavShows favShows;
        if (cursor.getCount() > 0) {
            do {
                favShows = new FavShows();
                favShows.setTvTitle(cursor.getString(cursor.getColumnIndexOrThrow(TV_TITLE)));
                favShows.setTvVoteCount(cursor.getString(cursor.getColumnIndexOrThrow(TV_VOTE_COUNT)));
                favShows.setTvOverview(cursor.getString(cursor.getColumnIndexOrThrow(TV_OVERVIEW)));
                favShows.setTvReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(TV_RELEASE_DATE)));
                favShows.setTvPhoto(cursor.getString(cursor.getColumnIndexOrThrow(TV_PHOTO)));
                favShows.setTvVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(TV_VOTE_AVERAGE)));

                list.add(favShows);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return list;
    }

    public long insertTv(FavShows favShows) {
        ContentValues args = new ContentValues();
        args.put(TV_TITLE, favShows.getTvTitle());
        args.put(TV_VOTE_COUNT, favShows.getTvVoteCount());
        args.put(TV_OVERVIEW, favShows.getTvOverview());
        args.put(TV_RELEASE_DATE, favShows.getTvReleaseDate());
        args.put(TV_VOTE_AVERAGE, favShows.getTvVoteAverage());
        args.put(TV_PHOTO, favShows.getTvPhoto());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTv(int id) {
        return database.delete(TABLE_NAME_SHOWS, _ID + " = '" + id + "'", null);
    }
}
