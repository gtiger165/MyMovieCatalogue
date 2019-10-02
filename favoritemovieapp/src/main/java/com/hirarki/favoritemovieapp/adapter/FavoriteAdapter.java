package com.hirarki.favoritemovieapp.adapter;

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
import com.hirarki.favoritemovieapp.R;
import com.hirarki.favoritemovieapp.activity.DetailFavoriteActivity;
import com.hirarki.favoritemovieapp.entity.FavItem;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {
    private ArrayList<FavItem> favList = new ArrayList<>();
    private AppCompatActivity activity;

    public FavoriteAdapter(AppCompatActivity activity) {
        this.activity = activity;
    }

    public ArrayList<FavItem> getFavList() {
        return favList;
    }

    public void setFavList(ArrayList<FavItem> favList) {
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
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fav_cardview, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        holder.bind(getFavList().get(position));
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }

    public class FavViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPoster;
        TextView tvTitle, tvOverview, tvReleaseDate, tvRating, tvVote;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_item_judul);
            tvOverview = itemView.findViewById(R.id.tv_item_description);
            tvReleaseDate = itemView.findViewById(R.id.tv_item_tanggal);
            tvRating = itemView.findViewById(R.id.tv_item_rating);
            tvVote = itemView.findViewById(R.id.tv_item_vote);
            imgPoster = itemView.findViewById(R.id.img_poster_movie);

            itemView.setOnClickListener(this);
        }

        void bind(FavItem favItem) {
            String url_image = favItem.getPhoto();

            tvTitle.setText(favItem.getTitle());
            tvOverview.setText(favItem.getOverview());
            tvReleaseDate.setText(favItem.getReleaseDate());
            tvRating.setText(favItem.getVoteAverage());
            tvVote.setText(favItem.getVoteCount());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .into(imgPoster);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            FavItem favItem = favList.get(position);

            Intent detailFavIntent = new Intent(view.getContext(), DetailFavoriteActivity.class);
            detailFavIntent.putExtra("cek_data", "fav_movie");
            detailFavIntent.putExtra(DetailFavoriteActivity.EXTRA_MOVIE, favItem);
            view.getContext().startActivity(detailFavIntent);
        }
    }
}
