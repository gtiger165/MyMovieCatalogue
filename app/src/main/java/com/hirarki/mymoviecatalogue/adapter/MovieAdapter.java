package com.hirarki.mymoviecatalogue.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<Movie> movieList = new ArrayList<>();

    public void setMovieList(ArrayList<Movie> itemMovie) {
        movieList.clear();
        movieList.addAll(itemMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_cardview, viewGroup, false);
        return new MovieViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        movieViewHolder.bind(movieList.get(i));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvOverview, tvReleaseDate, tvRating, tvVote;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_item_judul);
            tvOverview = itemView.findViewById(R.id.tv_item_description);
            tvReleaseDate = itemView.findViewById(R.id.tv_item_tanggal);
            tvRating = itemView.findViewById(R.id.tv_item_rating);
            tvVote = itemView.findViewById(R.id.tv_item_vote);
            imgPoster = itemView.findViewById(R.id.img_poster_movie);
        }

        void bind(Movie movie) {
            String voteAverage = Double.toString(movie.getVoteAverage());
            String url_image = "https://image.tmdb.org/t/p/w185" + movie.getPhoto();

            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            tvReleaseDate.setText(movie.getReleaseDate());
            tvRating.setText(voteAverage);
            tvVote.setText(movie.getVoteCount());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPoster);
        }
    }
}
