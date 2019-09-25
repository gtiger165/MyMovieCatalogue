package com.hirarki.mymoviecatalogue.provider;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hirarki.mymoviecatalogue.database.FavMovieHelper;

import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.TABLE_NAME_MOVIE;

@SuppressLint("Registered")
public class FavMovieProvider extends ContentProvider {
    public static final String AUTHORITIES = "com.hirarki.mymoviecatalogue.provider";
    public static final String TABLE_FAV_MOVIE = TABLE_NAME_MOVIE;
    private static final int FAV = 1;
    private static final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    FavMovieHelper helper;

    static {
        matcher.addURI(AUTHORITIES, TABLE_FAV_MOVIE, FAV);
    }

    @Override
    public boolean onCreate() {
        helper = FavMovieHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        helper.open();
        Cursor cursor;

        if (matcher.match(uri) == FAV) {
            cursor = helper.favMovieProvider();
        } else {
            cursor = null;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
