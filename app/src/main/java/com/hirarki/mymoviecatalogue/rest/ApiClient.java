package com.hirarki.mymoviecatalogue.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private final static String API_KEY = "37357fb4db8a115b4aaf40c9f671efde";
    public static final String  BASE_URL = "https://api.themoviedb.org/3/discover/";
    public static final String BASE_URL_SEARCH = "https://api.themoviedb.org/3/search/";
    public static final String DEFAULT_LANGUAGE = "en-US";

    public static Retrofit retrofit;

    public static String getApiKey() {
        return API_KEY;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientSearch() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_SEARCH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
