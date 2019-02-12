package com.example.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Find the image and text views in the detail_activity and set them to their corresponding
        // variables

        ImageView moviePosterImageView = findViewById(R.id.movie_poster);
        TextView movieTitleTextView = findViewById(R.id.movie_title);
        TextView movieReleaseDateTextView = findViewById(R.id.release_date);
        TextView movieVoteAverageTextView = findViewById(R.id.vote_average);
        TextView moviePlotSynopsisTextView = findViewById(R.id.plot_synopsis);

        // Pass in the array of strings using the intent from tha MainActivity and set its values to
        // the corresponding text and image view variables
        Bundle extras = getIntent().getExtras();

        String[] detailsArray = Objects.requireNonNull(extras).getStringArray("details");

        if (detailsArray != null){
            movieTitleTextView.setText(detailsArray[0]);
        }

        // This if statement checks if there is a poster image for the current movie. If the
        // response returns null set it to the posterimageplaceholder.png else set it to the
        // correct image provided.
        
        String picassoPosterImage = "http://image.tmdb.org/t/p/w342/" + Objects.requireNonNull(detailsArray)[1];
        Picasso.get().load(picassoPosterImage)
                .error(R.drawable.posterimageplaceholder)
                .into(moviePosterImageView);


        movieReleaseDateTextView.setText(detailsArray[2]);
        movieVoteAverageTextView.setText(detailsArray[3]);
        moviePlotSynopsisTextView.setText(detailsArray[4]);

    }

}
