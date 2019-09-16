package com.hirarki.mymoviecatalogue.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.TABLE_NAME_MOVIE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteShows.TABLE_NAME_SHOWS;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "moviecatalogues";

    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_MOVIE_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_NAME_MOVIE,
            DatabaseContract.FavoriteMovies._ID,
            DatabaseContract.FavoriteMovies.TITLE,
            DatabaseContract.FavoriteMovies.VOTE_COUNT,
            DatabaseContract.FavoriteMovies.ORIGINAL_LANGUAGE,
            DatabaseContract.FavoriteMovies.OVERVIEW,
            DatabaseContract.FavoriteMovies.RELEASE_DATE,
            DatabaseContract.FavoriteMovies.VOTE_AVERAGE,
            DatabaseContract.FavoriteMovies.PHOTO
    );

    private static final String SQL_CREATE_TABLE_TV_SHOW_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_NAME_SHOWS,
            DatabaseContract.FavoriteShows._ID,
            DatabaseContract.FavoriteShows.TV_TITLE,
            DatabaseContract.FavoriteShows.TV_VOTE_COUNT,
            DatabaseContract.FavoriteShows.TV_ORIGINAL_LANGUAGE,
            DatabaseContract.FavoriteShows.TV_OVERVIEW,
            DatabaseContract.FavoriteShows.TV_RELEASE_DATE,
            DatabaseContract.FavoriteShows.TV_VOTE_AVERAGE,
            DatabaseContract.FavoriteShows.TV_PHOTO
    );

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_MOVIE_FAVORITE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TV_SHOW_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MOVIE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SHOWS);
        onCreate(sqLiteDatabase);
    }
}