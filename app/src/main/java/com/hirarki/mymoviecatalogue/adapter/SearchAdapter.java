package com.hirarki.mymoviecatalogue.adapter;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.activity.DetailActivity;
import com.hirarki.mymoviecatalogue.model.Movie;
import com.hirarki.mymoviecatalogue.model.TvShow;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    List<Movie> movieList = new ArrayList<>();
    List<TvShow> showList = new ArrayList<>();
    String base_url_image = "https://image.tmdb.org/t/p/w185";

    public void setMovieList(List<Movie> movieItem) {
        this.movieList.clear();
        this.movieList.addAll(movieItem);
        notifyDataSetChanged();
    }

    public void setShowList(List<TvShow> showsItem) {
        this.showList.clear();
        this.showList.addAll(showsItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_show_cardview, viewGroup, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder searchViewHolder, int i) {
        if (movieList.size() > 0) {
            searchViewHolder.bindMovie(movieList.get(i));
        }
        if (showList.size() > 0) {
            searchViewHolder.bindShow(showList.get(i));
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (movieList.size() > 0) {
            count = movieList.size();
        }
        if (showList.size() > 0) {
            count = showList.size();
        }
        return count;
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvReleaseDate, tvRating, tvVote;
        ImageView imgPoster;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_item_judul);
            tvReleaseDate = itemView.findViewById(R.id.tv_item_tanggal);
            tvRating = itemView.findViewById(R.id.tv_item_rating);
            tvVote = itemView.findViewById(R.id.tv_item_vote);

            imgPoster = itemView.findViewById(R.id.img_poster_show);

            itemView.setOnClickListener(this);
        }

        public void bindMovie(Movie movie) {
            String url_image = base_url_image + movie.getPosterPath();
            tvTitle.setText(movie.getTitle());
            tvReleaseDate.setText(movie.getReleaseDate());
            tvRating.setText(Double.toString(movie.getVoteAverage()));
            tvVote.setText(Double.toString(movie.getVoteCount()));

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPoster);
        }

        public void bindShow(TvShow show) {
            String url_image = base_url_image + show.getPosterPath();
            tvTitle.setText(show.getName());
            tvReleaseDate.setText(show.getAirDate());
            tvRating.setText(Double.toString(show.getVoteAverage()));
            tvVote.setText(Double.toString(show.getVoteCount()));

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPoster);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (movieList.size() > 0) {
                Movie movie = movieList.get(position);

                Intent detailIntent = new Intent(view.getContext(), DetailActivity.class);
                detailIntent.putExtra("cek_data", "movie");
                detailIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
                view.getContext().startActivity(detailIntent);
            }
            if (showList.size() > 0) {
                TvShow show = showList.get(position);

                Intent detailShowIntent = new Intent(view.getContext(), DetailActivity.class);
                detailShowIntent.putExtra("cek_data", "tv_show");
                detailShowIntent.putExtra(DetailActivity.EXTRA_SHOW, show);
                view.getContext().startActivity(detailShowIntent);
            }
        }
    }
}
