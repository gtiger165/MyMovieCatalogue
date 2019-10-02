package com.hirarki.mymoviecatalogue.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.activity.DetailActivity;
import com.hirarki.mymoviecatalogue.model.FavShows;

import java.util.ArrayList;

public class FavShowsAdapter extends RecyclerView.Adapter<FavShowsAdapter.FavShowsHolder> {
    private ArrayList<FavShows> favList = new ArrayList<>();
    private final AppCompatActivity activity;

    public FavShowsAdapter(AppCompatActivity activity) {
        this.activity = activity;
    }

    public ArrayList<FavShows> getFavList() {
        return favList;
    }

    public void setFavList(ArrayList<FavShows> favList) {
        if (favList.size() > 0) {
            this.favList.clear();
        }
        this.favList.addAll(favList);
        notifyDataSetChanged();
    }

    public void removeFav(int position) {
        this.favList.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavShowsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_show_cardview, viewGroup, false);
        return new FavShowsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavShowsHolder favShowsHolder, int i) {
        favShowsHolder.bind(favList.get(i));
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public class FavShowsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPoster;
        TextView tvTitle, tvReleaseDate, tvRating, tvVote;

        public FavShowsHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_item_judul);
            tvReleaseDate = itemView.findViewById(R.id.tv_item_tanggal);
            tvRating = itemView.findViewById(R.id.tv_item_rating);
            tvVote = itemView.findViewById(R.id.tv_item_vote);

            imgPoster = itemView.findViewById(R.id.img_poster_show);

            itemView.setOnClickListener(this);
        }

        void bind(FavShows favShows) {
            String url_image = favShows.getTvPhoto();

            tvTitle.setText(favShows.getTvTitle());
            tvReleaseDate.setText(favShows.getTvReleaseDate());
            tvRating.setText(favShows.getTvVoteAverage());
            tvVote.setText(favShows.getTvVoteCount());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPoster);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            FavShows mFavShows = favList.get(position);

            Intent detailShowsIntent = new Intent(view.getContext(), DetailActivity.class);
            detailShowsIntent.putExtra("cek_data", "fav_shows");
            detailShowsIntent.putExtra(DetailActivity.EXTRA_SHOWS_FAVORITE, mFavShows);
            view.getContext().startActivity(detailShowsIntent);
        }
    }
}
