package com.hirarki.mymoviecatalogue.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.hirarki.mymoviecatalogue.model.Movie;
import com.hirarki.mymoviecatalogue.model.MovieList;
import com.hirarki.mymoviecatalogue.rest.ApiClient;
import com.hirarki.mymoviecatalogue.rest.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private ArrayList<Movie> movies = new ArrayList<>();
    private final static String api = ApiClient.getApiKey();
    private MutableLiveData<List<Movie>> listMovies = new MutableLiveData<>();
    private Application application;

    public MovieRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Movie>> getListMovies() {
        ApiService service = ApiClient.getClient().create(ApiService.class);

        final Call<MovieList> movieCall = service.getMovieList(api);
        movieCall.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                MovieList list = response.body();
                if (list != null && list.getResults() != null) {
                    movies = (ArrayList<Movie>) list.getResults();
                    listMovies.setValue(movies);
                } else {
//                    Log.d("onResMovie", "onResponse: " + response.body().);
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.e("onFailureMovie", t.getMessage());
            }
        });
        return listMovies;
    }
}
