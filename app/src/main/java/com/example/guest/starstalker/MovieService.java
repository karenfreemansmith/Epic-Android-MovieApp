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

    private static final String TAG = MovieService.class.getSimpleName();

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

    public static void getActorResult(int actorId, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_URL).newBuilder();
        urlBuilder.addPathSegment(Constants.PERSON_PATH);
        urlBuilder.addPathSegment(actorId+"");
        urlBuilder.addQueryParameter(Constants.API_KEY_QUERY, Constants.API_KEY);
        urlBuilder.addQueryParameter(Constants.APPEND_TO_RESPONSE_QUERY, "movie_credits");
        String url = urlBuilder.build().toString();
        //Log.i("MovieService", "getMovieSearchResults: " + url);
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Movie> processListResults(Response response) {
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

    public Movie  processMovieResults(Response response) {
        Movie movie = new Movie();

        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONObject movieJSON = new JSONObject(jsonData);
                movie.setTitle(movieJSON.getString("title"));
                movie.setId(movieJSON.getInt("id"));
                movie.setOverview(movieJSON.getString("overview"));
                movie.setPosterPath(movieJSON.getString("poster_path"));
                movie.setReleaseDate(movieJSON.getString("release_date"));
                movie.setRating((int)Math.round(movieJSON.getDouble("popularity")));
                JSONArray actorArray = movieJSON.getJSONObject("credits").getJSONArray("cast");
                for(int i=0; i < actorArray.length(); i++){
                    JSONObject currentActor = actorArray.getJSONObject(i);
                    String name = currentActor.getString("name");
                    int id = currentActor.getInt("id");
                    Actor actor = new Actor(name, id);
                    movie.getActors().add(actor);
                }
                JSONArray crewArray = movieJSON.getJSONObject("credits").getJSONArray("crew");
                for(int i=0; i < crewArray.length(); i++){
                    JSONObject currentCrew = crewArray.getJSONObject(i);
                    if(currentCrew.getString("job").equals("Director")){
                        movie.setDirector(currentCrew.getString("name"));
                        break;
                    }
                }

            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return movie;
    }

    public Actor  processActorResults(Response response) {
        Actor actor = new Actor();

        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONObject actorJSON = new JSONObject(jsonData);
                actor.setName(actorJSON.getString("name"));
                actor.setId(actorJSON.getInt("id"));
                JSONArray movieArray = actorJSON.getJSONObject("movie_credits").getJSONArray("cast");
                Log.i(TAG, "processActorResults: " + movieArray.toString());
                for(int i=0; i < movieArray.length(); i++){
                    Movie movie = new Movie();
                    JSONObject currentMovie = movieArray.getJSONObject(i);
                    movie.setTitle(currentMovie.getString("title"));
                    movie.setPosterPath(currentMovie.getString("poster_path"));
                    movie.setId(currentMovie.getInt("id"));
                    actor.getMovies().add(movie);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return actor;
    }
}
