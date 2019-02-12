package com.example.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    private static final String API_KEY = BuildConfig.API_KEY;

    private static final int MOVIE_LOADER_ID = 1;

    // Initialize and empty TextView for when there is no network connection
    private TextView mEmptyStateTextView;

    // Initialize an movieAdapter
    private MovieAdapter mAdapter;

    // Initialize a new Date variable to the current date to be used to show movies recently released
    private final Date mDate = new Date();
    private final String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(mDate);

    //Format the current date by adding one year to that way the user can view a list up movies to be released
    private final String yearDate = (currentDate.substring(0,4));
    private final int yearInt = Integer.parseInt(yearDate) + 1;
    private final String formattedDateForNewReleases = Integer.toString(yearInt);
    private final String finalFormattedDate = currentDate.replace(yearDate, formattedDateForNewReleases);


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the {@link GridView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link GridView} with the view ID called grid, which is declared in the
        // activity_main.xml layout file.
        GridView gridView =  findViewById(R.id.grid);

        // Link mEmptyStateTextView to the corresponding xml text view
        mEmptyStateTextView = findViewById(R.id.empty_view);
        gridView.setEmptyView(mEmptyStateTextView);

        mAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        // Make the {@link GridView} use the {@link MovieAdapter} created above, so that the
        // {@link GridView} will display list items for each {@link Movie} in the grid
        gridView.setAdapter(mAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current movie that was clicked
                Movie currentMovie = mAdapter.getItem(position);

                String movieTitle = Objects.requireNonNull(currentMovie).getMovieTitle();
                String moviePoster = currentMovie.getPosterImage();
                String movieReleaseDate = currentMovie.getReleaseDate();
                String movieVoteAverage = currentMovie.getVoteAverage();
                String moviePlotSynopsis = currentMovie.getPlotSynopsis();

                String[] movieDetailsArray = {movieTitle, moviePoster, movieReleaseDate, movieVoteAverage, moviePlotSynopsis};

                // Create variable for the
                Class destinationActivity = DetailActivity.class;
                Intent intentToStartActivity = new Intent(MainActivity.this, destinationActivity);

                intentToStartActivity.putExtra("details", movieDetailsArray);

                // Send the intent to launch a new activity (The detail activity with the movie information)
                startActivity(intentToStartActivity);
            }
        });

        // Check the state of the devices network connectivity
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);

        } else {
            // Display error that there is no network connectivity and hide the progress bar
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(getString(R.string.tmdb_base_url));

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("api_key", API_KEY);
        uriBuilder.appendQueryParameter("region", "US");
        uriBuilder.appendQueryParameter("with_original_language", "en");

        if (orderBy.equals(getString(R.string.settings_order_by_newest_release_value_descending))){
            uriBuilder.appendQueryParameter("sort_by", orderBy);
            uriBuilder.appendQueryParameter("primary_release_date.lte", currentDate);
            uriBuilder.appendQueryParameter("vote_count.gte", "100");
        } else if (orderBy.equals(getString(R.string.settings_order_by_upcoming_releases_value_ascending))) {
            uriBuilder.appendQueryParameter("sort_by", orderBy);
            uriBuilder.appendQueryParameter("primary_release_date.lte", finalFormattedDate);
            uriBuilder.appendQueryParameter("primary_release_date.gte", currentDate);
        } else {
            uriBuilder.appendQueryParameter("sort_by", orderBy);
            uriBuilder.appendQueryParameter("page", "1");
            //Set vote count to a hundred to various movies with distorted ratings
            uriBuilder.appendQueryParameter("vote_count.gte", "200");
        }

        String URIcheck = uriBuilder.toString();
        Log.i("URI String", URIcheck);

        // Create a new loader for the given URL
        return new MovieLoader(this, uriBuilder.toString());

    }


    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        // Hide loading indicator because data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state to display "No movies found"
        mEmptyStateTextView.setText(R.string.no_movies);

        // Clear the adapter of previous movie data
        mAdapter.clear();

        // If there is a valid list of {@link movie}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (movies != null && !movies.isEmpty()) {
            mAdapter.addAll(movies);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }


    @Override
    // This method initialize the contents of the Activity's options main.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
