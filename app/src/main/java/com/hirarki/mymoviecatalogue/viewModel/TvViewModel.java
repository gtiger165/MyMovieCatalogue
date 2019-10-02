package com.hirarki.mymoviecatalogue.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Application;
import android.util.Log;

import com.hirarki.mymoviecatalogue.model.TvShow;
import com.hirarki.mymoviecatalogue.model.TvShowList;
import com.hirarki.mymoviecatalogue.repository.ShowRepository;
import com.hirarki.mymoviecatalogue.rest.ApiClient;
import com.hirarki.mymoviecatalogue.rest.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvViewModel extends AndroidViewModel {
    private ShowRepository repository;

    public TvViewModel(@NonNull Application application) {
        super(application);
        repository = new ShowRepository(application);
    }

    public LiveData<List<TvShow>> getAllShows() {
        return repository.getListTvShow();
    }
//    private final static String api = ApiClient.getApiKey();
//    private MutableLiveData<TvShowList> listTvShow;
//
//    public void loadShows() {
//        ApiService service = ApiClient.getClient().create(ApiService.class);
//
//        Call<TvShowList> showsCall = service.getShowList(api);
//        showsCall.enqueue(new Callback<TvShowList>() {
//            @Override
//            public void onResponse(Call<TvShowList> call, Response<TvShowList> response) {
//                listTvShow.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<TvShowList> call, Throwable t) {
//                Log.e("onFailureShows", t.getMessage());
//            }
//        });
//    }
//
//    public LiveData<TvShowList> getShows() {
//        if (listTvShow == null) {
//            listTvShow = new MutableLiveData<>();
//            loadShows();
//        }
//        return listTvShow;
//    }
}
