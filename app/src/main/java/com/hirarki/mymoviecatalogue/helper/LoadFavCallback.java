package com.hirarki.mymoviecatalogue.helper;

import com.hirarki.mymoviecatalogue.model.FavMovies;
import com.hirarki.mymoviecatalogue.model.FavShows;

import java.util.ArrayList;

public interface LoadFavCallback {
    void preExecute();

    void postExecuteMovie(ArrayList<FavMovies> favoriteMovies);

    void postExecuteShows(ArrayList<FavShows> favoriteShows);
}
