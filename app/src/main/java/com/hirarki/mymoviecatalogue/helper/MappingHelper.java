package com.hirarki.mymoviecatalogue.helper;

import android.database.Cursor;

import com.hirarki.mymoviecatalogue.database.DatabaseContract;
import com.hirarki.mymoviecatalogue.model.FavMovies;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.ID_MOVIE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.OVERVIEW;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.PHOTO;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.RELEASE_DATE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.TITLE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.VOTE_AVERAGE;
import static com.hirarki.mymoviecatalogue.database.DatabaseContract.FavoriteMovies.VOTE_COUNT;

public class MappingHelper {
    public static ArrayList<FavMovies> mapCursorToList(Cursor favCursor) {
        ArrayList<FavMovies> favList = new ArrayList<>();

        while (favCursor.moveToNext()) {
            int id = favCursor.getInt(favCursor.getColumnIndexOrThrow(_ID));
            int idMovie = favCursor.getInt(favCursor.getColumnIndexOrThrow(ID_MOVIE));
            String title = favCursor.getString(favCursor.getColumnIndexOrThrow(TITLE));
            String voteCount = favCursor.getString(favCursor.getColumnIndexOrThrow(VOTE_COUNT));
            String overview = favCursor.getString(favCursor.getColumnIndexOrThrow(OVERVIEW));
            String releaseDate = favCursor.getString(favCursor.getColumnIndexOrThrow(RELEASE_DATE));
            String voteAverage = favCursor.getString(favCursor.getColumnIndexOrThrow(VOTE_AVERAGE));
            String photo = favCursor.getString(favCursor.getColumnIndexOrThrow(PHOTO));

            favList.add(new FavMovies(id, idMovie, title, voteCount, overview,
                    releaseDate, voteAverage, photo));
        }
        return favList;
    }
}
