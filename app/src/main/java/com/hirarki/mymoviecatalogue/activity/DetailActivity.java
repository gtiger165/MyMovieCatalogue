package com.hirarki.mymoviecatalogue.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.model.Movie;
import com.hirarki.mymoviecatalogue.model.TvShow;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_SHOW = "extra_show";
    private String url_image = "https://image.tmdb.org/t/p/w185";
    TextView tvTitle, tvDate, tvVoteCount, tvRating, tvOverview;
    ImageView imgPoster;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        prepare();

        if (getIntent().getStringExtra("cek_data").equals("movie")) {
            getMovieDetail();
        }
        if (getIntent().getStringExtra("cek_data").equals("tv_show")) {
            getShowDetail();
        }
    }

    private void prepare() {
        tvTitle = findViewById(R.id.tv_judul);
        tvDate = findViewById(R.id.tv_tanggal);
        tvVoteCount = findViewById(R.id.tv_vote_count);
        tvRating = findViewById(R.id.tv_rating);
        tvOverview = findViewById(R.id.tv_description);
        imgPoster = findViewById(R.id.img_poster_detail);
        progressBar = findViewById(R.id.progressBarShowDetail);
    }


    private void getMovieDetail() {
        progressBar.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

                        getSupportActionBar().setTitle(movie.getTitle());

                        String imgMovie = url_image + movie.getPosterPath();

                        tvTitle.setText(movie.getTitle());
                        tvRating.setText(String.valueOf(movie.getVoteAverage()));
                        tvVoteCount.setText(String.valueOf(movie.getVoteCount()));
                        tvDate.setText(movie.getReleaseDate());
                        tvOverview.setText(movie.getOverview());
                        Glide.with(DetailActivity.this)
                                .load(imgMovie)
                                .into(imgPoster);

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }

    private void getShowDetail() {
        progressBar.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_SHOW);

                        getSupportActionBar().setTitle(tvShow.getName());

                        String imgMovie = url_image + tvShow.getPosterPath();

                        tvTitle.setText(tvShow.getName());
                        tvRating.setText(String.valueOf(tvShow.getVoteAverage()));
                        tvVoteCount.setText(String.valueOf(tvShow.getVoteCount()));
                        tvDate.setText(tvShow.getAirDate());
                        tvOverview.setText(tvShow.getOverview());
                        Glide.with(DetailActivity.this)
                                .load(imgMovie)
                                .into(imgPoster);

                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
        }).start();
    }
}
