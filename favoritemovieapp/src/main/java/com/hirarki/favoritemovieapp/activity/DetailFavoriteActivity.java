package com.hirarki.favoritemovieapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hirarki.favoritemovieapp.R;
import com.hirarki.favoritemovieapp.entity.FavItem;

import java.util.Objects;

public class DetailFavoriteActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE = "extra_movie";

    TextView tvTitle, tvDate, tvVoteCount, tvRating, tvOverview;
    ImageView imgPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite);

        prepare();
        getFavMovie();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void prepareToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.mov_detail));

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

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void getFavMovie() {
        FavItem mfavMovies = getIntent().getParcelableExtra(EXTRA_MOVIE);

        String imgPhoto = mfavMovies.getPhoto();

        tvTitle.setText(mfavMovies.getTitle());
        tvRating.setText(String.valueOf(mfavMovies.getVoteAverage()));
        tvVoteCount.setText(String.valueOf(mfavMovies.getVoteCount()));
        tvDate.setText(mfavMovies.getReleaseDate());
        tvOverview.setText(mfavMovies.getOverview());
        Glide.with(DetailFavoriteActivity.this)
                .load(imgPhoto)
                .into(imgPoster);
    }
}
