package com.example.guest.starstalker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MovieDetailActivity extends AppCompatActivity {
    @Bind(R.id.directorTextView)
    TextView mDirectorTextView;
    @Bind(R.id.overviewTextView)
    TextView mOverviewTextView;
    @Bind(R.id.titleTextView)
    TextView mTitleTextView;
    @Bind(R.id.actorListView)
    ListView mActorListView;
    @Bind(R.id.posterImageView)
    ImageView mPosterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("movie_id", 49051);
        getMovie(id);

    }

    public void getMovie(int id){
        final MovieService movieService = new MovieService();
        movieService.getMovieResult(id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final Movie currentMovie = movieService.processMovieResults(response);
                MovieDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDirectorTextView.setText("Directed by: " + currentMovie.getDirector());
                        mOverviewTextView.setText(currentMovie.getOverview());
                        mTitleTextView.setText(currentMovie.getTitle());
                        String[] actorArray = new String[currentMovie.getActors().size()];
                        for(int i = 0; i < currentMovie.getActors().size(); i++){
                            actorArray[i] = currentMovie.getActors().get(i).getName();
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MovieDetailActivity.this, android.R.layout.simple_list_item_1, actorArray);
                        mActorListView.setAdapter(adapter);
                        Picasso.with(MovieDetailActivity.this)
                                .load(currentMovie.getImageUrl())
                                .resize(400, 600)
                                .centerCrop()
                                .into(mPosterImageView);
                    }
                });
            }
        });
    }
}
