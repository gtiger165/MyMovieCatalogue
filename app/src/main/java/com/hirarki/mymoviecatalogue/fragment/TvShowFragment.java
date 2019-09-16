package com.hirarki.mymoviecatalogue.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.adapter.TvShowAdapter;
import com.hirarki.mymoviecatalogue.model.TvShow;
import com.hirarki.mymoviecatalogue.model.TvShowList;
import com.hirarki.mymoviecatalogue.viewModel.TvViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private TvShowAdapter adapter;
    private TvViewModel tvViewModel;
    private RecyclerView rv;
    private SwipeRefreshLayout swipeShow;

    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        rv = view.findViewById(R.id.rv_show);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));

        swipeShow = view.findViewById(R.id.swipe_container_show);

        swipeShow.setRefreshing(true);

        tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        tvViewModel.getShows().observe(this, getShow);

        swipeShow.setOnRefreshListener(this);

        return view;
    }

    private Observer<TvShowList> getShow = new Observer<TvShowList>() {
        @Override
        public void onChanged(@Nullable TvShowList tvShowList) {
            adapter = new TvShowAdapter(getContext(), tvShowList.getResults());
            rv.setAdapter(adapter);
            swipeShow.setRefreshing(false);
        }
    };

    @Override
    public void onRefresh() {
        Log.d("OnRefresh", "On Refresh State");
        swipeShow.setRefreshing(true);
        tvViewModel.loadShows();
        tvViewModel.getShows().observe(this, new Observer<TvShowList>() {
            @Override
            public void onChanged(@Nullable TvShowList tvShowList) {
                Log.d("OnChangedRefresh", "Changed Complete");
                swipeShow.setRefreshing(false);
                adapter = new TvShowAdapter(getContext(), tvShowList.getResults());
                rv.setAdapter(adapter);
            }
        });
    }
}
