package com.hirarki.mymoviecatalogue.rest;

import com.hirarki.mymoviecatalogue.model.Movie;
import com.hirarki.mymoviecatalogue.model.MovieList;
import com.hirarki.mymoviecatalogue.model.TvShow;
import com.hirarki.mymoviecatalogue.model.TvShowList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("discover/movie")
    Call<MovieList> getMovieList(@Query("api_key") String apiKey);

    @GET("discover/tv")
    Call<TvShowList> getShowList(@Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieList> searchMovies(@Query("api_key") String apiKey,
                             @Query("query") String query);

    @GET("search/tv")
    Call<TvShowList> searchShows(@Query("api_key") String apiKey,
                            @Query("query") String query);

    @GET("discover/movie")
    Call<MovieList> getReleasedMovie(@Query("api_key") String apiKey,
                                 @Query("primary_release_date.gte") String dateGte,
                                 @Query("primary_release_date.lte") String dateLte);
}
