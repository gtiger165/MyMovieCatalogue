package com.hirarki.mymoviecatalogue;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hirarki.mymoviecatalogue.adapter.SearchAdapter;
import com.hirarki.mymoviecatalogue.model.Movie;
import com.hirarki.mymoviecatalogue.model.MovieList;
import com.hirarki.mymoviecatalogue.model.TvShow;
import com.hirarki.mymoviecatalogue.model.TvShowList;
import com.hirarki.mymoviecatalogue.viewModel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipe;
    RecyclerView rv;

    private SearchAdapter adapter;
    private SearchViewModel searchViewModel;

    public static final String EXTRA_QUERY = "extra_query";
    public static final String EXTRA_TYPE = "extra_type";
    private String query;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        prepare();
    }

    void prepare() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        query = getIntent().getStringExtra(EXTRA_QUERY);
        type = getIntent().getStringExtra(EXTRA_TYPE);

        swipe = findViewById(R.id.swipe_container_search);
        rv = findViewById(R.id.rv_search);

        adapter = new SearchAdapter();
        adapter.notifyDataSetChanged();

        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(adapter);

        swipe.setRefreshing(true);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.getResult(type, query);

        swipe.setOnRefreshListener(this);

        if (type.equals("movie")) {
            getSupportActionBar().setTitle(R.string.search_result_movie);
            searchViewModel.getMovies().observe(this, getMovie);
        }
        if (type.equals("tv_show")) {
            getSupportActionBar().setTitle(R.string.search_result_show);
            searchViewModel.getTvShow().observe(this, getShow);
        }
    }

    private Observer<MovieList> getMovie = new Observer<MovieList>() {
        @Override
        public void onChanged(@Nullable MovieList movies) {
            adapter.setMovieList(movies.getResults());
            rv.setAdapter(adapter);
            swipe.setRefreshing(false);
        }
    };

    private Observer<TvShowList> getShow = new Observer<TvShowList>() {
        @Override
        public void onChanged(@Nullable TvShowList tvShows) {
            adapter.setShowList(tvShows.getResults());
            rv.setAdapter(adapter);
            swipe.setRefreshing(false);
        }
    };

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRefresh() {
        swipe.setRefreshing(false);
        prepare();
    }
}
