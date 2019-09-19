package com.hirarki.mymoviecatalogue.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hirarki.mymoviecatalogue.model.FavMovies;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.ID_MOVIE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.OVERVIEW;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.PHOTO;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.RELEASE_DATE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.TABLE_NAME_MOVIE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.TITLE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.VOTE_AVERAGE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.VOTE_COUNT;

public class FavMovieHelper {
    private static final String DATABASE_TABLE = TABLE_NAME_MOVIE;
    private static DatabaseHelper dbHelper;
    private static FavMovieHelper instance;

    private static SQLiteDatabase database;

    private FavMovieHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static FavMovieHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (instance == null) {
                    instance = new FavMovieHelper(context);
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

        if (database.isOpen()) {
            database.close();
        }
    }

    public ArrayList<FavMovies> getAllMoviesFav() {
        ArrayList<FavMovies> list = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();
        FavMovies favMovies;
        if (cursor.getCount() > 0) {
            do {
                favMovies = new FavMovies();
                favMovies.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favMovies.setIdMovie(cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE)));
                favMovies.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                favMovies.setVoteCount(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_COUNT)));
                favMovies.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favMovies.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));
                favMovies.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                favMovies.setVoteAverage(cursor.getString(cursor.getColumnIndexOrThrow(VOTE_AVERAGE)));

                list.add(favMovies);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return list;
    }

    public long insertFavMovie(FavMovies favMovies) {
        ContentValues args = new ContentValues();
        args.put(ID_MOVIE, favMovies.getIdMovie());
        args.put(TITLE, favMovies.getTitle());
        args.put(VOTE_COUNT, favMovies.getVoteCount());
        args.put(OVERVIEW, favMovies.getOverview());
        args.put(RELEASE_DATE, favMovies.getReleaseDate());
        args.put(VOTE_AVERAGE, favMovies.getVoteAverage());
        args.put(PHOTO, favMovies.getPhoto());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteFavMovie(int id) {
        return database.delete(TABLE_NAME_MOVIE, _ID + " = '" + id + "'", null);
    }
}
