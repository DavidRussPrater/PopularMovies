package com.example.popularmovies;

class Movie {

    private final String mPosterImage;

    private final String mMovieTitle;

    private final String mReleaseDate;

    private final String mVoteAverage;

    private final String mPlotSynopsis;


    /**
     * Constructs a new {@link Movie} object
     *
     * @param posterImage is the movies poster image
     * @param movieTitle is the title of the movies
     * @param releaseDate is the release date of the movies
     * @param voteAverage is the average vote of the movies
     * @param plotSynopsis is the plot synopsis of the movies
     */
    public Movie(String posterImage, String movieTitle, String releaseDate, String voteAverage, String plotSynopsis){
        mPosterImage = posterImage;
        mMovieTitle = movieTitle;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;
        mPlotSynopsis = plotSynopsis;
    }

    // Returns the string for the movie's poster
    public String getPosterImage(){
        return mPosterImage;
    }

    // Returns the title of the movies
    public String getMovieTitle(){
        return mMovieTitle;
    }

    // Returns the release date fo the movies
    public String getReleaseDate(){
        return mReleaseDate;
    }

    // Returns the average vote ov the movies
    public String getVoteAverage(){
        return mVoteAverage;
    }

    // Returns the plot synopsis of the movie
    public String getPlotSynopsis(){
        return mPlotSynopsis;
    }

}
