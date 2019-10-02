package com.hirarki.mymoviecatalogue.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.hirarki.mymoviecatalogue.model.TvShow;
import com.hirarki.mymoviecatalogue.model.TvShowList;
import com.hirarki.mymoviecatalogue.rest.ApiClient;
import com.hirarki.mymoviecatalogue.rest.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowRepository {
    private ArrayList<TvShow> shows = new ArrayList<>();
    private final static String api = ApiClient.getApiKey();
    private MutableLiveData<List<TvShow>> listTvShow = new MutableLiveData<>();
    private Application application;

    public ShowRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<TvShow>> getListTvShow() {
        ApiService service = ApiClient.getClient().create(ApiService.class);

        Call<TvShowList> showsCall = service.getShowList(api);
        showsCall.enqueue(new Callback<TvShowList>() {
            @Override
            public void onResponse(Call<TvShowList> call, Response<TvShowList> response) {
                TvShowList list = response.body();
                if (list != null && list.getResults() != null) {
                    shows = (ArrayList<TvShow>) list.getResults();
                    listTvShow.setValue(shows);
                }

            }

            @Override
            public void onFailure(Call<TvShowList> call, Throwable t) {
                Log.e("onFailureShows", t.getMessage());
            }
        });
        return listTvShow;
    }
}
