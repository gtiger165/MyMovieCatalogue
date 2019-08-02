package com.hirarki.mymoviecatalogue.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.model.TvShow;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvViewHolder> {
    private ArrayList<TvShow> showList = new ArrayList<>();

    public void setShowList(ArrayList<TvShow> itemShow) {
        showList.clear();
        showList.addAll(itemShow);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_show_cardview, viewGroup, false);
        return new TvViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull TvViewHolder tvViewHolder, int i) {
        tvViewHolder.bind(showList.get(i));
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvReleaseDate, tvRating, tvVote;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_item_judul);
            tvReleaseDate = itemView.findViewById(R.id.tv_item_tanggal);
            tvRating = itemView.findViewById(R.id.tv_item_rating);
            tvVote = itemView.findViewById(R.id.tv_item_vote);

            imgPoster = itemView.findViewById(R.id.img_poster_show);
        }

        void bind(TvShow tvShow) {
            String voteAverage = Double.toString(tvShow.getVoteAverage());
            String url_image = "https://image.tmdb.org/t/p/w185" + tvShow.getPosterPath();

            tvTitle.setText(tvShow.getName());
            tvReleaseDate.setText(tvShow.getAirDate());
            tvRating.setText(voteAverage);
            tvVote.setText(tvShow.getVoteCount());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPoster);
        }
    }
}
