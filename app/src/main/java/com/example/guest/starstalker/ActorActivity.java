package com.example.guest.starstalker;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ActorActivity extends AppCompatActivity {
    @Bind(R.id.nameTextView)
    TextView mNameTextView;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    private Actor mActor;
    MoviePagerAdapter mMoviePagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int actorId = intent.getIntExtra("actor_id", 14386);
        getActor(actorId);
    }

    public void getActor(int actorId){
        final MovieService movieService = new MovieService();
        movieService.getActorResult(actorId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mActor = movieService.processActorResults(response);
                ActorActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mNameTextView.setText(mActor.getName());
                        mMoviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager(), mActor.getMovies());
                        mViewPager.setAdapter(mMoviePagerAdapter);
                    }
                });
            }
        });
    }
}
