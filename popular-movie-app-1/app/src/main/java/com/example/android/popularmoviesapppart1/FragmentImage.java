package com.example.android.popularmoviesapppart1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class FragmentImage extends Fragment {

    private ImageAdapter mAdapter;
    private String MovieJsonStr;
    private final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    SharedPreferences sharedPreferences;
    public static final String PREFERENCES = "catagorie";
    private String PREF;

    public FragmentImage(){}

    @Override
    public void onStart() {
        super.onStart();
        sharedPreferences = getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        PREF = sharedPreferences.getString("prefKey", "popular?");
        updateImages();
    }

    private void updateImages() {
        GetMovieInfoTask task = new GetMovieInfoTask();
        task.execute(PREF);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        GridView grid = (GridView)rootView.findViewById(R.id.poster_grid);
        mAdapter = new ImageAdapter(getActivity(), R.layout.grid_layout, new ArrayList<String>());
        grid.setAdapter(mAdapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String urlImage = mAdapter.getItem(position);
                ArrayList<String> movieDetails = new ArrayList<String>();
                movieDetails.add(urlImage.substring(IMAGE_BASE_URL.length()));
                movieDetails.add(MovieJsonStr);
                Intent detail = new Intent(getContext(), DetailActivity.class);
                detail.putStringArrayListExtra(Intent.EXTRA_TEXT, movieDetails);
                startActivity(detail);
            }
        });

        return rootView;
    }

    public class GetMovieInfoTask extends AsyncTask<String, Void, String[]>{

        private String BASE_URL = "http://api.themoviedb.org/3/movie/";
        private String APP_ID = "";
        private final String API_KEY = "api_key";

        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;


            BASE_URL = BASE_URL.concat(params[0]);
            Uri uriBuilder = Uri.EMPTY.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(API_KEY,APP_ID)
                    .build();
            try {
                URL url = new URL(uriBuilder.toString());
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStream inputstream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputstream == null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputstream));
                String line;
                while((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0){
                    return null;
                }
                MovieJsonStr = buffer.toString();

                return getMovieDataFromJson(MovieJsonStr);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {

                    }
                }
            }

            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] resultJson) {
            if(resultJson != null){
                mAdapter.clear();
                mAdapter.setMovieData(new ArrayList<String>(Arrays.asList(resultJson)));
            }
        }

        private String[] getMovieDataFromJson(String movieJsonStr) throws JSONException {
            final String TMDB_RESULT = "results";
            final String TMDB_POSTER = "poster_path";
            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(TMDB_RESULT);
            Integer MOVIE_SIZE = movieArray.length();

            String []resultStr = new String[MOVIE_SIZE];

            for(int i = 0; i < MOVIE_SIZE; i++){
                JSONObject imageUrl = movieArray.getJSONObject(i);
                String poster = imageUrl.getString(TMDB_POSTER);
                resultStr[i] = IMAGE_BASE_URL + poster;
            }
            return resultStr;
        }
    }
}
