package com.example.guest.starstalker;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActorFragment extends Fragment {
    @Bind(R.id.posterImageView)
    ImageView mPosterImageView;
    private Movie mMovie;


    public static ActorFragment newInstance(Movie movie){
        // Required empty public constructor
        ActorFragment actorFragment = new ActorFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie", Parcels.wrap(movie));
        actorFragment.setArguments(args);
        return actorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie = Parcels.unwrap(getArguments().getParcelable("movie"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_actor, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(mMovie.getImageUrl()).into(mPosterImageView);

        mPosterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), MovieDetailActivity.class);
                intent.putExtra("movie_id", mMovie.getId());
                startActivity(intent);
            }
        });

        return view;
    }

}
