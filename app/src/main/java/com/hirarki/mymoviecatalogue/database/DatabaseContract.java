package com.hirarki.mymoviecatalogue.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    //class untuk tabel favorite movie
    static final class FavoriteMovies implements BaseColumns {
        static final String TABLE_NAME_MOVIE = "movie_favorites";

        static final String ID_MOVIE = "id_movie";
        static final String TITLE = "title";
        static final String VOTE_COUNT = "vote_count";
        static final String OVERVIEW = "overview";
        static final String RELEASE_DATE = "release_date";
        static final String VOTE_AVERAGE = "vote_average";
        static final String PHOTO = "photo";
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
