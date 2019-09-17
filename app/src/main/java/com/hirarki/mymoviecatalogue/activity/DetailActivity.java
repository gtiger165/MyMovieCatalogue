package com.hirarki.mymoviecatalogue.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hirarki.mymoviecatalogue.R;
import com.hirarki.mymoviecatalogue.database.FavMovieHelper;
import com.hirarki.mymoviecatalogue.database.FavShowHelper;
import com.hirarki.mymoviecatalogue.model.FavMovies;
import com.hirarki.mymoviecatalogue.model.FavShows;
import com.hirarki.mymoviecatalogue.model.Movie;
import com.hirarki.mymoviecatalogue.model.TvShow;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_SHOW = "extra_show";
    public static final String EXTRA_MOVIE_FAVORITE = "extra_movie_favorite";
    public static final String EXTRA_SHOWS_FAVORITE = "extra_shows_favorite";
    public static final String EXTRA_POSITION = "extra_position";

    private String url_image = "https://image.tmdb.org/t/p/w185";
    private String imgMovie, imgShow;
    private ConstraintLayout layoutDetail;
    private boolean isEdit = false;
    public static final int RESULT_ADD = 101;
    private FavMovies favMovies;
    private FavShows favShows;
    private FavMovieHelper movieHelper;
    private FavShowHelper showsHelper;
    private int position;

    TextView tvTitle, tvDate, tvVoteCount, tvRating, tvOverview;
    ImageView imgPoster;
    Button btnAddFav, btnRemoveFav;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        prepare();

        if (getIntent().getStringExtra("cek_data").equals("movie") ||
                getIntent().getStringExtra("cek_data").equals("fav_movie")) {
            getMovieDetail();
            prepareFavMovies();
        }
        if (getIntent().getStringExtra("cek_data").equals("tv_show") ||
                getIntent().getStringExtra("cek_data").equals("fav_shows")) {
            getShowDetail();
            prepareFavShows();
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
        layoutDetail = findViewById(R.id.layout_detail);
        btnAddFav = findViewById(R.id.btn_fav_add);
        btnRemoveFav = findViewById(R.id.btn_fav_remove);

        btnAddFav.setOnClickListener(this);
        btnRemoveFav.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void getMovieDetail() {

        progressBar.setVisibility(View.VISIBLE);
        layoutDetail.setVisibility(View.GONE);

        if (getIntent().getStringExtra("cek_data").equals("movie")) {
            getSupportActionBar().setTitle("Movie Detail");

            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            imgMovie = url_image + movie.getPosterPath();

            tvTitle.setText(movie.getTitle());
            tvRating.setText(String.valueOf(movie.getVoteAverage()));
            tvVoteCount.setText(String.valueOf(movie.getVoteCount()));
            tvDate.setText(movie.getReleaseDate());
            tvOverview.setText(movie.getOverview());
            Glide.with(DetailActivity.this)
                    .load(imgMovie)
                    .into(imgPoster);
        }

        if (getIntent().getStringExtra("cek_data").equals("fav_movie")) {
            getSupportActionBar().setTitle("Favorite Movie Detail");

            FavMovies mfavMovies = getIntent().getParcelableExtra(EXTRA_MOVIE_FAVORITE);

            String imgPhoto = mfavMovies.getPhoto();

            tvTitle.setText(mfavMovies.getTitle());
            tvRating.setText(String.valueOf(mfavMovies.getVoteAverage()));
            tvVoteCount.setText(String.valueOf(mfavMovies.getVoteCount()));
            tvDate.setText(mfavMovies.getReleaseDate());
            tvOverview.setText(mfavMovies.getOverview());
            Glide.with(DetailActivity.this)
                    .load(imgPhoto)
                    .into(imgPoster);
        }

        layoutDetail.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void prepareFavMovies() {
        movieHelper= FavMovieHelper.getInstance(getApplicationContext());
        movieHelper.open();
        favMovies = getIntent().getParcelableExtra(EXTRA_MOVIE_FAVORITE);
        if (favMovies != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
            btnAddFav.setVisibility(View.GONE);
            btnRemoveFav.setVisibility(View.VISIBLE);
        } else {
            favMovies = new FavMovies();
            btnRemoveFav.setVisibility(View.GONE);
            btnAddFav.setVisibility(View.VISIBLE);
        }
    }

    private void prepareFavShows() {
        showsHelper= FavShowHelper.getInstance(getApplicationContext());
        showsHelper.open();
        favShows = getIntent().getParcelableExtra(EXTRA_SHOWS_FAVORITE);
        if (favShows != null) {
            position = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isEdit = true;
            btnAddFav.setVisibility(View.GONE);
            btnRemoveFav.setVisibility(View.VISIBLE);
        } else {
            favShows = new FavShows();
            btnRemoveFav.setVisibility(View.GONE);
            btnAddFav.setVisibility(View.VISIBLE);
        }
    }

    private void getShowDetail() {
        progressBar.setVisibility(View.VISIBLE);
        layoutDetail.setVisibility(View.GONE);

        if (getIntent().getStringExtra("cek_data").equals("tv_show")) {
            getSupportActionBar().setTitle("TV Show Detail");

            TvShow tvShow = getIntent().getParcelableExtra(EXTRA_SHOW);

            imgShow = url_image + tvShow.getPosterPath();

            tvTitle.setText(tvShow.getName());
            tvRating.setText(String.valueOf(tvShow.getVoteAverage()));
            tvVoteCount.setText(String.valueOf(tvShow.getVoteCount()));
            tvDate.setText(tvShow.getAirDate());
            tvOverview.setText(tvShow.getOverview());
            Glide.with(DetailActivity.this)
                    .load(imgShow)
                    .into(imgPoster);
        }

        if (getIntent().getStringExtra("cek_data").equals("fav_shows")) {
            getSupportActionBar().setTitle("Favorite Show Detail");

            FavShows mFavShow = getIntent().getParcelableExtra(EXTRA_SHOWS_FAVORITE);

            String imgPhoto = mFavShow.getTvPhoto();

            tvTitle.setText(mFavShow.getTvTitle());
            tvRating.setText(mFavShow.getTvVoteAverage());
            tvVoteCount.setText(mFavShow.getTvVoteCount());
            tvDate.setText(mFavShow.getTvReleaseDate());
            tvOverview.setText(mFavShow.getTvOverview());
            Glide.with(DetailActivity.this)
                    .load(imgPhoto)
                    .into(imgPoster);
        }

        layoutDetail.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        String title        = tvTitle.getText().toString().trim();
        String voteCount   = tvVoteCount.getText().toString().trim();
        String overview     = tvOverview.getText().toString().trim();
        String releaseDate = tvDate.getText().toString().trim();

        String voteAverage = tvRating.getText().toString().trim();

        if (view.getId() == R.id.btn_fav_add) {
            if (getIntent().getStringExtra("cek_data").equals("movie")) {
                String imagePath = imgMovie;

                favMovies.setTitle(title);
                favMovies.setVoteCount(voteCount);
                favMovies.setOverview(overview);
                favMovies.setReleaseDate(releaseDate);
                favMovies.setVoteAverage(voteAverage);
                favMovies.setPhoto(imagePath);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_MOVIE_FAVORITE, favMovies);
                intent.putExtra(EXTRA_POSITION, position);

                if (!isEdit) {

                    long result = movieHelper.insertFavMovie(favMovies);

                    if (result > 0) {
                        favMovies.setId((int) result);
                        setResult(RESULT_ADD, intent);
                        Toast.makeText(DetailActivity.this, getString(R.string.succes_add_fav), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(DetailActivity.this, getString(R.string.failed_add_fav), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if (getIntent().getStringExtra("cek_data").equals("tv_show")){
                String imagePath = imgShow;

                favShows.setTvTitle(title);
                favShows.setTvVoteCount(voteCount);
                favShows.setTvOverview(overview);
                favShows.setTvReleaseDate(releaseDate);
                favShows.setTvVoteAverage(voteAverage);
                favShows.setTvPhoto(imagePath);

                Intent intent = new Intent();
                intent.putExtra(EXTRA_SHOWS_FAVORITE, favShows);
                intent.putExtra(EXTRA_POSITION, position);

                if (!isEdit) {

                    long result = showsHelper.insertTv(favShows);

                    if (result > 0) {
                        favShows.setId((int) result);
                        setResult(RESULT_ADD, intent);
                        Toast.makeText(DetailActivity.this,
                                getString(R.string.succes_add_fav), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(DetailActivity.this,
                                getString(R.string.failed_add_fav), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        }
        if (view.getId() == R.id.btn_fav_remove) {
            Toast.makeText(this, "Test Delete", Toast.LENGTH_SHORT).show();
        }
    }
}
