package com.example.popularmovies;

public class Movie {

    private String mPosterImage;

    private String mMovieTitle;

    private String mReleaseDate;

    private String mVoteAverage;

    private String mPlotSynopsis;


    public Movie(String posterImage, String movieTitle, String releaseDate, String voteAverage, String plotSynopsis){
        mPosterImage = posterImage;
        mMovieTitle = movieTitle;
        mReleaseDate = releaseDate;
        mVoteAverage = voteAverage;
        mPlotSynopsis = plotSynopsis;
    }

    public String getPosterImage(){
        return mPosterImage;
    }

    public String getMovieTitle(){
        return mMovieTitle;
    }

    public String getReleaseDate(){
        return mReleaseDate;
    }

    public String getVoteAverage(){
        return mVoteAverage;
    }

    public String getPlotSynopsis(){
        return mPlotSynopsis;
    }

}
