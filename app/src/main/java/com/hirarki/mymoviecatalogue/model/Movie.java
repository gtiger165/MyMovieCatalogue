package com.hirarki.mymoviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie implements Parcelable {
    private String title, overview, originalLanguage, releaseDate, photo, banner, voteCount;
    private Double voteAverage, popularity;

    public Movie(String title, String overview, String originalLanguage, String releaseDate, String photo, String banner, String voteCount, Double voteAverage, Double popularity) {
        this.title = title;
        this.overview = overview;
        this.originalLanguage = originalLanguage;
        this.releaseDate = releaseDate;
        this.photo = photo;
        this.banner = banner;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.releaseDate);
        dest.writeString(this.photo);
        dest.writeString(this.banner);
        dest.writeString(this.voteCount);
        dest.writeValue(this.voteAverage);
        dest.writeValue(this.popularity);
    }

    public Movie(JSONObject object) {
        try {
            String title = object.getString("title");
            String overview = object.getString("overview");
            String original_language = object.getString("original_language");
            String release_date = object.getString("release_date");
            String poster_path = object.getString("poster_path");
            String backdrop_path = object.getString("backdrop_path");
            String vote_count = object.getString("vote_count");
            Double vote_average = object.getDouble("vote_average");
            Double popularity = object.getDouble("popularity");

            this.title = title;
            this.overview = overview;
            this.originalLanguage = original_language;
            this.releaseDate = release_date;
            this.photo = poster_path;
            this.banner = backdrop_path;
            this.voteCount = vote_count;
            this.voteAverage = vote_average;
            this.popularity = popularity;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.releaseDate = in.readString();
        this.photo = in.readString();
        this.banner = in.readString();
        this.voteCount = in.readString();
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
