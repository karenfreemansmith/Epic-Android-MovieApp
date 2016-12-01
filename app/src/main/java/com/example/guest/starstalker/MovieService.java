package com.example.guest.starstalker;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 12/1/16.
 */
public class MovieService {

    public static void getMovieSearchResults(String searchString, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_URL).newBuilder();
        urlBuilder.addPathSegment(Constants.SEARCH_PATH);
        urlBuilder.addPathSegment(Constants.MOVIE_PATH);
        urlBuilder.addQueryParameter(Constants.QUERY_QUERY, searchString);
        urlBuilder.addQueryParameter(Constants.API_KEY_QUERY, Constants.API_KEY);
        urlBuilder.addQueryParameter("include_adult", "false");
        String url = urlBuilder.build().toString();
        //Log.i("MovieService", "getMovieSearchResults: " + url);
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void getMovieResult(int movieId, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_URL).newBuilder();
        urlBuilder.addPathSegment(Constants.MOVIE_PATH);
        urlBuilder.addPathSegment(movieId+"");
        urlBuilder.addQueryParameter(Constants.API_KEY_QUERY, Constants.API_KEY);
        urlBuilder.addQueryParameter(Constants.APPEND_TO_RESPONSE_QUERY, "credits");
        String url = urlBuilder.build().toString();
        //Log.i("MovieService", "getMovieSearchResults: " + url);
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Movie> processResults(Response response) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONObject movieJSON = new JSONObject(jsonData);
                JSONArray movieArray = movieJSON.getJSONArray("results");
                for(int i=0; i < movieArray.length(); i++){
                    JSONObject currentMovie = movieArray.getJSONObject(i);
                    String title = currentMovie.getString("title");
                    int id = currentMovie.getInt("id");
                    Movie movie = new Movie(title, id);
                    movies.add(movie);
                }

            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }
}
