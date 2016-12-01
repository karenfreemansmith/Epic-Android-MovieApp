package com.example.guest.starstalker;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Guest on 12/1/16.
 */
@Parcel
public class Actor {
    String mName;
    int mId;
    ArrayList<Movie> mMovies = new ArrayList<>();

    public Actor(String name, int id) {
        mName = name;
        mId = id;
    }

    public Actor() {
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getId() {
        return mId;

    }

    public void setId(int id) {
        mId = id;
    }

    public ArrayList<Movie> getMovies() {
        return mMovies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        mMovies = movies;
    }
}
