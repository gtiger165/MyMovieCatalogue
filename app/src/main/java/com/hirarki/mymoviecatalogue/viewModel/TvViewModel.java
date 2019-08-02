package com.hirarki.mymoviecatalogue.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.hirarki.mymoviecatalogue.model.TvShow;
import com.hirarki.mymoviecatalogue.rest.ApiClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvViewModel extends ViewModel {
    private final static String api = ApiClient.getApiKey();
    private MutableLiveData<ArrayList<TvShow>> listTvShow = new MutableLiveData<>();

    public void setTvShow(final String movie) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> itemList = new ArrayList<>();
        String url ="https://api.themoviedb.org/3/discover/tv?api_key=" +api+ "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tvShow = list.getJSONObject(i);
                        TvShow showItems = new TvShow(tvShow);
                        itemList.add(showItems);
                    }
                    listTvShow.postValue(itemList);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public MutableLiveData<ArrayList<TvShow>> getListTvShow() {
        return listTvShow;
    }
}
