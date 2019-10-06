package com.hirarki.mymoviecatalogue.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;

import com.hirarki.mymoviecatalogue.model.MovieList;
import com.hirarki.mymoviecatalogue.model.TvShowList;
import com.hirarki.mymoviecatalogue.rest.ApiClient;
import com.hirarki.mymoviecatalogue.rest.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<MovieList> searchedMovie = new MutableLiveData<>();
    private MutableLiveData<TvShowList> searchedShow = new MutableLiveData<>();

    public void getResult(String type, String query) {
        final ApiService service = ApiClient.getClient().create(ApiService.class);

        if (type.equals("movie")) {
            Call<MovieList> movieCall = service.searchMovies(ApiClient.getApiKey(), query);
            Log.d("SearchViewModel", "getResult: " + movieCall.toString());

            movieCall.enqueue(new Callback<MovieList>() {
                @Override
                public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                    if (response.isSuccessful()) {
                        searchedMovie.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<MovieList> call, Throwable t) {
                    Log.e("searchMovie", t.getMessage());
                }
            });
        }
        if (type.equals("tv_show")) {
            Call<TvShowList> showCall = service.searchShows(ApiClient.getApiKey(), query);

            showCall.enqueue(new Callback<TvShowList>() {
                @Override
                public void onResponse(Call<TvShowList> call, Response<TvShowList> response) {
                    if (response.isSuccessful()) {
                        searchedShow.setValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<TvShowList> call, Throwable t) {
                    Log.e("searchShow", t.getMessage());
                }
            });
        }
    }

    public LiveData<MovieList> getMovies() {
        return searchedMovie;
    }

    public LiveData<TvShowList> getTvShow() {
        return searchedShow;
    }
}
