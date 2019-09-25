package com.hirarki.mymoviecatalogue.fragment;


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
import com.hirarki.mymoviecatalogue.adapter.TvShowAdapter;
import com.hirarki.mymoviecatalogue.model.TvShowList;
import com.hirarki.mymoviecatalogue.viewModel.TvViewModel;


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
