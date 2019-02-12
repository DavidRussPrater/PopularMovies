package com.example.popularmovies;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;

class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    // Query the URL
    private final String mUrl;

    // Construct a new {@Link MovieLoader}
    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    //This is performed on a background thread
    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchMovieData(mUrl);

    }
}
