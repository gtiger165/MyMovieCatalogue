package com.hirarki.mymoviecatalogue.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.hirarki.mymoviecatalogue.model.TvShow;
import com.hirarki.mymoviecatalogue.model.TvShowList;
import com.hirarki.mymoviecatalogue.rest.ApiClient;
import com.hirarki.mymoviecatalogue.rest.ApiService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvViewModel extends ViewModel {
    private final static String api = ApiClient.getApiKey();
    private MutableLiveData<TvShowList> listTvShow;

    public void loadShows() {
        ApiService service = ApiClient.getClient().create(ApiService.class);

        Call<TvShowList> showsCall = service.getShowList(api);
        showsCall.enqueue(new Callback<TvShowList>() {
            @Override
            public void onResponse(Call<TvShowList> call, Response<TvShowList> response) {
                listTvShow.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TvShowList> call, Throwable t) {
                Log.e("onFailureShows", t.getMessage());
            }
        });
    }

    public LiveData<TvShowList> getShows() {
        if (listTvShow == null) {
            listTvShow = new MutableLiveData<>();
            loadShows();
        }
        return listTvShow;
    }
}
