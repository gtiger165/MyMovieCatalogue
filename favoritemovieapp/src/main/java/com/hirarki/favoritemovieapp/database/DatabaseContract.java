package com.hirarki.favoritemovieapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.hirarki.mymoviecatalogue.provider";
    private static final String SCHEME = "content";

    //class untuk tabel favorite movie
    public static final class FavoriteMovies implements BaseColumns {
        public static final String TABLE_NAME_MOVIE = "movie_favorites";

        public static final String ID_MOVIE = "id_movie";
        public static final String TITLE = "title";
        public static final String VOTE_COUNT = "vote_count";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String PHOTO = "photo";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME_MOVIE)
                .build();
    }
}
