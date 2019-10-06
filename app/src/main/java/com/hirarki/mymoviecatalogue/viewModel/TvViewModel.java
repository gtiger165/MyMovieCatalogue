package com.hirarki.mymoviecatalogue.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.hirarki.mymoviecatalogue.model.TvShowList;
import com.hirarki.mymoviecatalogue.rest.ApiClient;
import com.hirarki.mymoviecatalogue.rest.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvViewModel extends ViewModel {
    private static String api = ApiClient.getApiKey();
    private MutableLiveData<TvShowList> listTvShow;

    public void loadShows() {
        ApiService service = ApiClient.getClient().create(ApiService.class);
        Log.d("TvViewModel", "api key: " + api);

        Call<TvShowList> showsCall = service.getShowList(api);
        showsCall.enqueue(new Callback<TvShowList>() {
            @Override
            public void onResponse(Call<TvShowList> call, Response<TvShowList> response) {
                if (response.isSuccessful()) {
                    listTvShow.setValue(response.body());
                }
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
