package com.example.guest.starstalker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieListActivity extends AppCompatActivity {
    private static final String TAG = MovieListActivity.class.getSimpleName();
    @Bind(R.id.movieListView)
    ListView mMovieListView;

    public ArrayList<Movie> mMovies = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String search = intent.getStringExtra("search");
        getMovies(search);
    }

    private void getMovies(String search){
        final MovieService movieService = new MovieService();
        movieService.getMovieSearchResults(search, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mMovies = movieService.processResults(response);
                MovieListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String[] movieTitles = new String[mMovies.size()];
                        for(int i = 0; i < mMovies.size(); i++){
                            movieTitles[i] = mMovies.get(i).getMtitle();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(MovieListActivity.this, android.R.layout.simple_list_item_1, movieTitles);
                        mMovieListView.setAdapter(adapter);
                        mMovieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
                                Movie movie = mMovies.get(position);
                                intent.putExtra("movie_id", movie.getId());
                                startActivity(intent);
                            }
                        });
                    }
                });

            }
        });
    }
}
