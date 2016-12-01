package com.example.guest.starstalker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.movieSearchInput)
    EditText mMovieSearchInput;
    @Bind(R.id.searchMovieButton)
    Button mSearchMovieButton;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mSearchMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMovieSearchInput.getText().toString().length() > 0){
                    String search = mMovieSearchInput.getText().toString();
                    Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                    intent.putExtra("search", search);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "THERE NO SEARCH HERE LOL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
