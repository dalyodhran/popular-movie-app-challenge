package com.example.android.popularmoviesapppart1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    private String MovieJsonStr, posterPath;
    private ImageView imageView;
    private TextView movieTitle, movieSynopsis, movieRating, movieRelease;
    private final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w342/";

    public DetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent movieJsonStrIntent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        imageView = (ImageView)rootView.findViewById(R.id.movie_image);
        movieTitle = (TextView)rootView.findViewById(R.id.movie_title);
        movieSynopsis = (TextView)rootView.findViewById(R.id.movie_synopsis_json);
        movieRating = (TextView)rootView.findViewById(R.id.movie_rating_json);
        movieRelease = (TextView)rootView.findViewById(R.id.movie_release_json);
        if(movieJsonStrIntent != null && movieJsonStrIntent.hasExtra(Intent.EXTRA_TEXT)){
            ArrayList<String> movieDetails = movieJsonStrIntent.getStringArrayListExtra(Intent.EXTRA_TEXT);
            posterPath = movieDetails.get(0);
            Picasso.with(getContext()).load(IMAGE_BASE_URL+posterPath).placeholder(R.drawable.ic_loading).error(R.drawable.ic_not_found).into(imageView);
            MovieJsonStr = movieDetails.get(1);
            setMovieDetails();
        }

        return rootView;
    }

    private void setMovieDetails() {
        final String TMDB_RESULT = "results";
        final String TMDB_POSTER = "poster_path";
        final String TMDB_TITLE = "original_title";
        final String TMDB_SYNOP = "overview";
        final String TMDB_RATING = "vote_average";
        final String TMDB_REL = "release_date";

        JSONObject jsonToStr = null;
        JSONArray detailsArray = null;

        try {
            jsonToStr = new JSONObject(MovieJsonStr);
            detailsArray = jsonToStr.getJSONArray(TMDB_RESULT);

            Integer arraySize = detailsArray.length();

            for(int i = 0; i < arraySize; i++){
                JSONObject movieResult = detailsArray.getJSONObject(i);
                if(movieResult.getString(TMDB_POSTER).equals(posterPath)){
                    movieTitle.setText(movieResult.getString(TMDB_TITLE));
                    movieSynopsis.setText(movieResult.getString(TMDB_SYNOP));
                    movieRating.setText(movieResult.getString(TMDB_RATING));
                    formatMySqlDate(movieResult.getString(TMDB_REL));
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void formatMySqlDate(String date){
        String year = "";
        String month = "";
        String day = "";

        if(date != null){
            year = date.substring(0, 4);
            month = date.substring(5, 7);
            day = date.substring(8, 10);
        }

        movieRelease.setText(day + "-" + month + "-" + year);
    }
}
