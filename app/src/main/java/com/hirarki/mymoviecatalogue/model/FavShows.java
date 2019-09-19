package com.hirarki.mymoviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavShows implements Parcelable {
    private int id;
    private int idShows;
    private String tvTitle;
    private String tvVoteCount;
    private String tvOverview;
    private String tvReleaseDate;
    private String tvVoteAverage;
    private String tvPhoto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdShows() {
        return idShows;
    }

    public void setIdShows(int idShows) {
        this.idShows = idShows;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getTvVoteCount() {
        return tvVoteCount;
    }

    public void setTvVoteCount(String tvVoteCount) {
        this.tvVoteCount = tvVoteCount;
    }

    public String getTvOverview() {
        return tvOverview;
    }

    public void setTvOverview(String tvOverview) {
        this.tvOverview = tvOverview;
    }

    public String getTvReleaseDate() {
        return tvReleaseDate;
    }

    public void setTvReleaseDate(String tvReleaseDate) {
        this.tvReleaseDate = tvReleaseDate;
    }

    public String getTvVoteAverage() {
        return tvVoteAverage;
    }

    public void setTvVoteAverage(String tvVoteAverage) {
        this.tvVoteAverage = tvVoteAverage;
    }

    public String getTvPhoto() {
        return tvPhoto;
    }

    public void setTvPhoto(String tvPhoto) {
        this.tvPhoto = tvPhoto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.idShows);
        dest.writeString(this.tvTitle);
        dest.writeString(this.tvVoteCount);
        dest.writeString(this.tvOverview);
        dest.writeString(this.tvReleaseDate);
        dest.writeString(this.tvVoteAverage);
        dest.writeString(this.tvPhoto);
    }

    public FavShows() {
    }

    protected FavShows(Parcel in) {
        this.id = in.readInt();
        this.idShows = in.readInt();
        this.tvTitle = in.readString();
        this.tvVoteCount = in.readString();
        this.tvOverview = in.readString();
        this.tvReleaseDate = in.readString();
        this.tvVoteAverage = in.readString();
        this.tvPhoto = in.readString();
    }

    public static final Parcelable.Creator<FavShows> CREATOR = new Parcelable.Creator<FavShows>() {
        @Override
        public FavShows createFromParcel(Parcel source) {
            return new FavShows(source);
        }

        @Override
        public FavShows[] newArray(int size) {
            return new FavShows[size];
        }
    };
}
