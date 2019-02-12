package com.example.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie>{


    /**
     * Create a new {@link MovieAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param movies is the list of {@link Movie}s to be displayed.
     *    */
    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public  View getView(int position, View convertView, ViewGroup parent) {
        View gridView = convertView;
        if (gridView == null) {
            gridView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_item, parent, false);
        }

        Movie currentMovie = getItem(position);

        ImageView posterImageView = gridView.findViewById(R.id.poster_grid);

        String posterMovieString = currentMovie.getPosterImage();

        if (posterMovieString != "null") {
            String picassoPosterImage = "http://image.tmdb.org/t/p/w185/" + posterMovieString;
            Picasso.get().load(picassoPosterImage).into(posterImageView);
        } else {
            posterImageView.setImageResource(R.drawable.posetplaceholder);
        }


        TextView titleTextView = gridView.findViewById(R.id.movie_title);

        titleTextView.setText(currentMovie.getMovieTitle());

        TextView releaseDateTextView = gridView.findViewById(R.id.release_date);

        releaseDateTextView.setText(currentMovie.getReleaseDate());

        TextView voteAverageTextView = gridView.findViewById(R.id.vote_average);

        voteAverageTextView.setText(currentMovie.getVoteAverage());

        TextView plotSynopsisTextView = gridView.findViewById(R.id.plot_synopsis);

        plotSynopsisTextView.setText(currentMovie.getPlotSynopsis());

        return gridView;
    }

}
