package com.example.guest.starstalker;

import org.parceler.Parcel;

/**
 * Created by Guest on 12/1/16.
 */
@Parcel
public class Movie {
    String mtitle;
    int mId;
    String mPosterPath;
    String mOverview;
    String mReleaseDate;
    double mRating;

    public Movie(String mtitle, int id) {
        this.mtitle = mtitle;
        mId = id;
    }

    public Movie() {
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        mRating = rating;
    }
}
