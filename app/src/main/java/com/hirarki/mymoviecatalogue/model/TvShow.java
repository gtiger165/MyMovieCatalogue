package com.hirarki.mymoviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class TvShow implements Parcelable {
    private String name, overview, posterPath, voteCount, airDate, originalLanguage;
    private Double voteAverage, popularity;

    public TvShow(String name, String overview, String posterPath, String voteCount, String airDate, String originalLanguage, Double voteAverage, Double popularity) {
        this.name = name;
        this.overview = overview;
        this.posterPath = posterPath;
        this.voteCount = voteCount;
        this.airDate = airDate;
        this.originalLanguage = originalLanguage;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
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
        dest.writeString(this.name);
        dest.writeString(this.overview);
        dest.writeString(this.posterPath);
        dest.writeString(this.voteCount);
        dest.writeString(this.airDate);
        dest.writeString(this.originalLanguage);
        dest.writeValue(this.voteAverage);
        dest.writeValue(this.popularity);
    }

    public TvShow(JSONObject object) {
        try {
            String name = object.getString("name");
            String vote_count = object.getString("vote_count");
            String first_air_date = object.getString("first_air_date");
            String original_language = object.getString("original_language");
            String overview = object.getString("overview");
            String poster_path = object.getString("poster_path");
            Double popularity = object.getDouble("popularity");
            Double vote_average = object.getDouble("vote_average");

            this.name = name;
            this.overview = overview;
            this.originalLanguage = original_language;
            this.airDate = first_air_date;
            this.posterPath = poster_path;
            this.voteCount = vote_count;
            this.voteAverage = vote_average;
            this.popularity = popularity;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JsonException", e.getMessage());
        }
    }

    protected TvShow(Parcel in) {
        this.name = in.readString();
        this.overview = in.readString();
        this.posterPath = in.readString();
        this.voteCount = in.readString();
        this.airDate = in.readString();
        this.originalLanguage = in.readString();
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}
