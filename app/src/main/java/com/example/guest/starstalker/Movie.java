package com.example.guest.starstalker;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Guest on 12/1/16.
 */
@Parcel
public class Movie {
    String mTitle;
    int mId;
    String mPosterPath;
    String mOverview;
    String mReleaseDate;
    int mRating;
    String mDirector;
    ArrayList<Actor> mActors = new ArrayList<>();

    public Movie(String mTitle, int id) {
        this.mTitle = mTitle;
        mId = id;
    }

    public Movie() {
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
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

    public String getImageUrl() {
        String url = "https://image.tmdb.org/t/p/";
        url += "original/";
        url += mPosterPath;
        return url;
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

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public String getDirector() {
        return mDirector;
    }

    public void setDirector(String director) {
        mDirector = director;
    }

    public ArrayList<Actor> getActors() {
        return mActors;
    }

    public void setActors(ArrayList<Actor> actors) {
        mActors = actors;
    }
}
