package com.hirarki.mymoviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavMovies implements Parcelable {
    private int id;
    private String title;
    private String voteCount;
    private String overview;
    private String releaseDate;
    private String voteAverage;
    private String photo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.voteCount);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.voteAverage);
        dest.writeString(this.photo);
    }

    public FavMovies() {
    }

    protected FavMovies(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.voteCount = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<FavMovies> CREATOR = new Parcelable.Creator<FavMovies>() {
        @Override
        public FavMovies createFromParcel(Parcel source) {
            return new FavMovies(source);
        }

        @Override
        public FavMovies[] newArray(int size) {
            return new FavMovies[size];
        }
    };
}
