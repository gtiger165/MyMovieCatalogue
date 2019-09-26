package com.hirarki.favoritemovieapp.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class FavItem implements Parcelable {
    private int id;
    private int idMovie;
    private String title;
    private String voteCount;
    private String overview;
    private String releaseDate;
    private String voteAverage;
    private String photo;

    public FavItem(int id, int idMovie, String title, String voteCount, String overview, String releaseDate, String voteAverage, String photo) {
        this.id = id;
        this.idMovie = idMovie;
        this.title = title;
        this.voteCount = voteCount;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public String getTitle() {
        return title;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.idMovie);
        dest.writeString(this.title);
        dest.writeString(this.voteCount);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.voteAverage);
        dest.writeString(this.photo);
    }

    public FavItem() {
    }

    protected FavItem(Parcel in) {
        this.id = in.readInt();
        this.idMovie = in.readInt();
        this.title = in.readString();
        this.voteCount = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<FavItem> CREATOR = new Parcelable.Creator<FavItem>() {
        @Override
        public FavItem createFromParcel(Parcel source) {
            return new FavItem(source);
        }

        @Override
        public FavItem[] newArray(int size) {
            return new FavItem[size];
        }
    };
}
