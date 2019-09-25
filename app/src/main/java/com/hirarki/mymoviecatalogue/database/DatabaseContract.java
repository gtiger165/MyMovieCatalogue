package com.hirarki.mymoviecatalogue.database;

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

    //class untuk tabel favorite tv show
    static final class FavoriteShows implements BaseColumns {
        static final String TABLE_NAME_SHOWS           = "tv_show_favorites";

        static final String ID_SHOWS                = "id_shows";
        static final String TV_TITLE                = "tv_title";
        static final String TV_VOTE_COUNT           = "tv_vote_count";
        static final String TV_OVERVIEW             = "tv_overview";
        static final String TV_RELEASE_DATE         = "tv_release_date";
        static final String TV_VOTE_AVERAGE         = "tv_vote_average";
        static final String TV_PHOTO                = "tv_photo";
    }

}
