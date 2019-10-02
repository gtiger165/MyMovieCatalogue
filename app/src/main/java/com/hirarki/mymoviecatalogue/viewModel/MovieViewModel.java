package com.hirarki.mymoviecatalogue.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.util.Log;

import com.hirarki.mymoviecatalogue.model.Movie;
import com.hirarki.mymoviecatalogue.model.MovieList;
import com.hirarki.mymoviecatalogue.repository.MovieRepository;
import com.hirarki.mymoviecatalogue.rest.ApiClient;
import com.hirarki.mymoviecatalogue.rest.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository repository;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> getAllMovies() {
        return repository.getListMovies();
    }
//    private final static String api = ApiClient.getApiKey();
//    private MutableLiveData<MovieList> listMovies;



//    public void loadMovies() {
//        ApiService service = ApiClient.getClient().create(ApiService.class);
//
//        Call<MovieList> movieCall = service.getMovieList(api);
//        movieCall.enqueue(new Callback<MovieList>() {
//            @Override
//            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
//                listMovies.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<MovieList> call, Throwable t) {
//                Log.e("onFailureMovie", t.getMessage());
//            }
//        });
//    }
//
//    public LiveData<MovieList> getMovies() {
//        if (listMovies == null) {
//            listMovies = new MutableLiveData<>();
//            loadMovies();
//        }
//        return listMovies;
//    }
}
