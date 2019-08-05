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
    @GET("movie")
    Call<MovieList> getMovieList(@Query("api_key") String apiKey);

    @GET("tv")
    Call<TvShowList> getShowList(@Query("api_key") String apiKey);
}
