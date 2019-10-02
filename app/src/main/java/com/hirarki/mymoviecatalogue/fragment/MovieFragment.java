package com.hirarki.mymoviecatalogue.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.adapter.MovieAdapter;
import com.hirarki.mymoviecatalogue.model.Movie;
import com.hirarki.mymoviecatalogue.model.MovieList;
import com.hirarki.mymoviecatalogue.viewModel.MovieViewModel;

import java.util.List;


public class MovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private MovieViewModel movieViewModel;
    private RecyclerView rv;
    private SwipeRefreshLayout swipe;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        loadMovies();

        swipe.setOnRefreshListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        movieViewModel.getAllMovies().removeObservers(this);
        Log.d("MovieFragment", "onStop: executed, has active observer ? "
                + movieViewModel.getAllMovies().hasActiveObservers());
    }

    private void initView(View view) {
        rv = view.findViewById(R.id.rv_movie);
        swipe = view.findViewById(R.id.swipe_container);
    }

    public void loadMovies() {
        swipe.setRefreshing(true);
        movieViewModel.getAllMovies().observe(getViewLifecycleOwner(), getMovie);
    }

    private void prepareList(List<Movie> movieList) {
        MovieAdapter adapter = new MovieAdapter(getActivity(), movieList);
        adapter.notifyDataSetChanged();

        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(adapter);
        swipe.setRefreshing(false);

        movieViewModel.getAllMovies().removeObservers(this);
    }


    private Observer<List<Movie>> getMovie = new Observer<List<Movie>>() {
        @Override
        public void onChanged(List<Movie> movies) {
            Log.d("onChangedMovies", "onChanged onCreateView");
            prepareList(movies);
        }
    };

//    private Observer<MovieList> getMovie = new Observer<MovieList>() {
//        @Override
//        public void onChanged(@Nullable MovieList movieList) {
//            adapter = new MovieAdapter(getContext(), movieList.getResults());
//            rv.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//            swipe.setRefreshing(false);
//        }
//    };

    @Override
    public void onRefresh() {
        Log.d("OnRefresh", "On Refresh State");
        swipe.setRefreshing(false);
        loadMovies();

//        movieViewModel.getAllMovies().observe(this, new Observer<MovieList>() {
//            @Override
//            public void onChanged(@Nullable MovieList movieList) {
//                Log.d("OnChangedRefresh", "Changed Complete");
//                swipe.setRefreshing(false);
//                adapter = new MovieAdapter(getContext(), movieList.getResults());
//                adapter.notifyDataSetChanged();
//                rv.setAdapter(adapter);
//            }
//        });

//        movieViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
//            @Override
//            public void onChanged(List<Movie> movies) {
//                Log.d("OnChangedRefresh", "Changed Complete");
//                swipe.setRefreshing(false);
//                adapter = new MovieAdapter(getContext(), movies);
//                adapter.notifyDataSetChanged();
//                rv.setAdapter(adapter);
//            }
//        });
    }
}
