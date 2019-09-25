package com.hirarki.mymoviecatalogue.activity;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
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

import java.util.Objects;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_SHOW = "extra_show";
    public static final String EXTRA_MOVIE_FAVORITE = "extra_movie_favorite";
    public static final String EXTRA_SHOWS_FAVORITE = "extra_shows_favorite";
    public static final String EXTRA_POSITION = "extra_position";
    public static final int REQUEST_UPDATE = 200;
    public static final int RESULT_DELETE = 301;

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
    private int idPrimary;

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

    private void prepareToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        switch (getIntent().getStringExtra("cek_data")) {
            case "movie":
                collapsingToolbarLayout.setTitle(getString(R.string.mov_detail));
                break;
            case "fav_movie":
                collapsingToolbarLayout.setTitle(getString(R.string.fav_mov_detail));
                break;
            case "tv_show":
                collapsingToolbarLayout.setTitle(getString(R.string.shows_detail));
                break;
            case "fav_shows":
                collapsingToolbarLayout.setTitle(getString(R.string.fav_shows_detail));
                break;
        }

        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.colorLight));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.colorLight));
    }

    private void prepare() {
        prepareToolBar();

        tvTitle = findViewById(R.id.tv_detail);
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

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
            getSupportActionBar().setTitle(getString(R.string.mov_detail));

            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

            imgMovie = url_image + movie.getPosterPath();
            idPrimary = movie.getId();
            Log.d("testId", "id : " + idPrimary);

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
            getSupportActionBar().setTitle(getString(R.string.fav_mov_detail));

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
            getSupportActionBar().setTitle(getString(R.string.shows_detail));

            TvShow tvShow = getIntent().getParcelableExtra(EXTRA_SHOW);

            imgShow = url_image + tvShow.getPosterPath();
            idPrimary = tvShow.getId();
            Log.d("testId", "id : " + idPrimary);

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
            getSupportActionBar().setTitle(getString(R.string.fav_shows_detail));

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
        int idFav = idPrimary;
        String title = tvTitle.getText().toString().trim();
        String voteCount = tvVoteCount.getText().toString().trim();
        String overview = tvOverview.getText().toString().trim();
        String releaseDate = tvDate.getText().toString().trim();

        String voteAverage = tvRating.getText().toString().trim();

        if (view.getId() == R.id.btn_fav_add) {
            if (getIntent().getStringExtra("cek_data").equals("movie")) {
                String imagePath = imgMovie;

                favMovies.setIdMovie(idFav);
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

                favShows.setIdShows(idFav);
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
            removeDialog();
        }
    }

    private void removeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (getIntent().getStringExtra("cek_data").equals("fav_movie")) {
            builder.setTitle(getString(R.string.dialod_remove_mov));
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    long result = movieHelper.deleteFavMovie(favMovies.getId());
                    if (result > 0) {
                        Intent mIntent = new Intent(DetailActivity.this, FavMovieActivity.class);
                        mIntent.putExtra(EXTRA_POSITION, position);
                        startActivityForResult(mIntent, RESULT_DELETE);
                        Toast.makeText(DetailActivity.this,
                                getApplicationContext().getString(R.string.success_remove), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivity.this,
                                getApplicationContext().getString(R.string.failed_remove), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        if (getIntent().getStringExtra("cek_data").equals("fav_shows")) {
            builder.setTitle(getString(R.string.dialog_remove_shows));
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    long result = showsHelper.deleteTv(favShows.getId());

                    if (result > 0) {
                        Intent mIntent = new Intent(DetailActivity.this, FavShowsActivity.class);
                        mIntent.putExtra(EXTRA_POSITION, position);
                        startActivityForResult(mIntent, RESULT_DELETE);
                        finish();
                        Toast.makeText(DetailActivity.this,
                                getApplicationContext().getString(R.string.success_remove), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DetailActivity.this,
                                getApplicationContext().getString(R.string.failed_remove), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        builder.setMessage(getString(R.string.remove_message));
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
