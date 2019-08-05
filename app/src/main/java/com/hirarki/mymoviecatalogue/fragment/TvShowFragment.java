package com.hirarki.mymoviecatalogue.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class TvShowFragment extends Fragment {
    private TvShowAdapter adapter;
    private ProgressBar progressBar;
    private TvViewModel tvViewModel;
    private RecyclerView rv;

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

        progressBar = view.findViewById(R.id.progressBar);

        tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        tvViewModel.getShows().observe(this, getShow);

        showLoading(true);

        return view;
    }

    private Observer<TvShowList> getShow = new Observer<TvShowList>() {
        @Override
        public void onChanged(@Nullable TvShowList tvShowList) {
            adapter = new TvShowAdapter(getContext(), tvShowList.getResults());
            rv.setAdapter(adapter);
            showLoading(false);
        }
    };

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
