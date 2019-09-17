package com.hirarki.mymoviecatalogue.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.activity.DetailActivity;
import com.hirarki.mymoviecatalogue.model.FavMovies;

import java.util.ArrayList;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.FavMovieHolder> {
    private ArrayList<FavMovies> favList = new ArrayList<>();
    private final Activity activity;

    public FavMovieAdapter(Activity activity) {
        this.activity = activity;
    }

    public ArrayList<FavMovies> getFavList() {
        return favList;
    }

    public void setFavList(ArrayList<FavMovies> favList) {
        if (favList.size() > 0) {
            this.favList.clear();
        }
        this.favList.addAll(favList);
        notifyDataSetChanged();
    }

    public void addFav(FavMovies favMovies) {
        this.favList.add(favMovies);
        notifyItemInserted(favList.size() - 1);
    }

    public void removeFav(int position) {
        this.favList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, favList.size());
    }

    @NonNull
    @Override
    public FavMovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_movie_cardview, viewGroup, false);
        return new FavMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieHolder favMovieHolder, int i) {
        favMovieHolder.bind(favList.get(i));
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public class FavMovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPoster;
        TextView tvTitle, tvOverview, tvReleaseDate, tvRating, tvVote;

        public FavMovieHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_item_judul);
            tvOverview = itemView.findViewById(R.id.tv_item_description);
            tvReleaseDate = itemView.findViewById(R.id.tv_item_tanggal);
            tvRating = itemView.findViewById(R.id.tv_item_rating);
            tvVote = itemView.findViewById(R.id.tv_item_vote);
            imgPoster = itemView.findViewById(R.id.img_poster_movie);

            itemView.setOnClickListener(this);
        }

        void bind(FavMovies favMovies) {
            String url_image = favMovies.getPhoto();

            tvTitle.setText(favMovies.getTitle());
            tvOverview.setText(favMovies.getOverview());
            tvReleaseDate.setText(favMovies.getReleaseDate());
            tvRating.setText(favMovies.getVoteAverage());
            tvVote.setText(favMovies.getVoteCount());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPoster);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            FavMovies favMovies = favList.get(position);

            Intent detailMovieIntent = new Intent(view.getContext(), DetailActivity.class);
            detailMovieIntent.putExtra("cek_data", "fav_movie");
            detailMovieIntent.putExtra(DetailActivity.EXTRA_MOVIE_FAVORITE, favMovies);
            view.getContext().startActivity(detailMovieIntent);
        }
    }
}
