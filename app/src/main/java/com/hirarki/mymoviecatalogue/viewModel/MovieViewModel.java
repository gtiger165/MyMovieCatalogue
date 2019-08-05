package com.hirarki.mymoviecatalogue.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hirarki.mymoviecatalogue.adapter.MovieAdapter;
import com.hirarki.mymoviecatalogue.model.Movie;
import com.hirarki.mymoviecatalogue.model.MovieList;
import com.hirarki.mymoviecatalogue.rest.ApiClient;
import com.hirarki.mymoviecatalogue.rest.ApiService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private final static String api = ApiClient.getApiKey();
    private MutableLiveData<MovieList> listMovies;

    public void loadMovies() {
        ApiService service = ApiClient.getClient().create(ApiService.class);

        Call<MovieList> movieCall = service.getMovieList(api);
        movieCall.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                listMovies.setValue(response.body());
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.e("onFailureMovie", t.getMessage());
            }
        });
    }

    public LiveData<MovieList> getMovies() {
        if (listMovies == null) {
            listMovies = new MutableLiveData<>();
            loadMovies();
        }
        return listMovies;
    }
}
