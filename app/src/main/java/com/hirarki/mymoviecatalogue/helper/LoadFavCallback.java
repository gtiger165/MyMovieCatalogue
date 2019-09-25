package com.hirarki.mymoviecatalogue.helper;

import android.database.Cursor;

import com.hirarki.mymoviecatalogue.model.FavMovies;
import com.hirarki.mymoviecatalogue.model.FavShows;

import java.util.ArrayList;

public interface LoadFavCallback {
    void preExecute();

    void postExecuteMovie(Cursor cursor);

    void postExecuteShows(ArrayList<FavShows> favoriteShows);
}
