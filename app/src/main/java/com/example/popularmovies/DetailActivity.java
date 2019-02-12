package com.example.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView moviePosterImageView;
    TextView movieTitleTextView;
    TextView movieReleaseDateTextView;
    TextView movieVoteAverageTextView;
    TextView moviePlotSynopsisTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        moviePosterImageView = findViewById(R.id.movie_poster);
        movieTitleTextView = findViewById(R.id.movie_title);
        movieReleaseDateTextView = findViewById(R.id.release_date);
        movieVoteAverageTextView = findViewById(R.id.vote_average);
        moviePlotSynopsisTextView = findViewById(R.id.plot_synopsis);

        Intent intentToStartActivity = getIntent();

        Bundle extras = getIntent().getExtras();
        String[] detailsArray = extras.getStringArray("details");

        //if(intentToStartActivity.hasExtra(Intent.EXTRA_TEXT)){

            movieTitleTextView.setText(detailsArray[0]);
            if (detailsArray[0] != "null") {

                String picassoPosterImage = "http://image.tmdb.org/t/p/w500/" + detailsArray[1];
                Picasso.get().load(picassoPosterImage).into(moviePosterImageView);
            } else {
                moviePosterImageView.setImageResource(R.drawable.posetplaceholder);
            }
            movieReleaseDateTextView.setText(detailsArray[2]);
            movieVoteAverageTextView.setText(detailsArray[3]);
            moviePlotSynopsisTextView.setText(detailsArray[4]);

        //}

    }

}
