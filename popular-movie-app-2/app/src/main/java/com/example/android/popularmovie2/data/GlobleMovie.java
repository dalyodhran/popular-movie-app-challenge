package com.example.android.popularmovie2.data;

import java.util.ArrayList;

/**
 * Created by odhrandaly on 05/09/2017.
 */

public class GlobleMovie {
    private final String IMAGE_BASE_URL_BIG = "http://image.tmdb.org/t/p/w185/";
    private final String IMAGE_BASE_URL_SMALL = "http://image.tmdb.org/t/p/w342/";
    private final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private final String API_KEY = "6b1f92dad389d3b90bc76e7b65909c26";
    private final String APP_ID = "api_key";
    private final String MovieJsonString = "";
    private final String TMDB_RESULT = "results";
    private final String TMDB_POSTER = "poster_path";
    private final String TMDB_TITLE = "title";
    private final String TMDB_SYNOP = "overview";
    private final String TMDB_RATING = "vote_average";
    private final String TMDB_REL = "release_date";
    private final String TMDB_DURATION = "runtime";
    private final String TMDB_ID = "id";
    private final String TMDB_TRAILER_KEY = "key";
    private String MovieJson;
    private static ArrayList<String> imageUrls;
    private static ArrayList<String> imageIds;
    private static ArrayList<String> trailerKey;
    private int numTrailers;

    public GlobleMovie(){
    }

    public String getTMDB_DURATION() {
        return TMDB_DURATION;
    }

    public String getImageUrls(int num) {
        return imageUrls.get(num);
    }

    public ArrayList<String> getImageUrls(){
        return imageUrls;
    }

    public static void setImageUrls(ArrayList<String> imageUrls) {
        GlobleMovie.imageUrls = imageUrls;
    }

    public String getTrailerKey(int num) {
        return trailerKey.get(num);
    }

    public ArrayList<String> getTrailerKey() {
        return trailerKey;
    }

    public static void setTrailerKey(ArrayList<String> trailerKey) {
        GlobleMovie.trailerKey = trailerKey;
    }

    public String getTMDB_TRAILER_KEY() {
        return TMDB_TRAILER_KEY;
    }

    public int getNumTrailers() {
        return numTrailers;
    }

    public void setNumTrailers(int numTrailers) {
        this.numTrailers = numTrailers;
    }

    public static void setImageIds(ArrayList<String> imageIds) {
        GlobleMovie.imageIds = imageIds;
    }

    public String getImageIds(int numId) {
        return imageIds.get(numId);
    }

    public String getTMDB_ID() {
        return TMDB_ID;
    }

    public String getMovieJson() {
        return MovieJson;
    }

    public void setMovieJson(String movieJson) {
        MovieJson = movieJson;
    }

    public String getIMAGE_BASE_URL_BIG() {
        return IMAGE_BASE_URL_BIG;
    }

    public String getIMAGE_BASE_URL_SMALL() {
        return IMAGE_BASE_URL_SMALL;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public String getAPI_KEY() {
        return API_KEY;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public String getMovieJsonString() {
        return MovieJsonString;
    }

    public String getTMDB_RESULT() {
        return TMDB_RESULT;
    }

    public String getTMDB_POSTER() {
        return TMDB_POSTER;
    }

    public String getTMDB_TITLE() {
        return TMDB_TITLE;
    }

    public String getTMDB_SYNOP() {
        return TMDB_SYNOP;
    }

    public String getTMDB_RATING() {
        return TMDB_RATING;
    }

    public String getTMDB_REL() {
        return TMDB_REL;
    }
}
