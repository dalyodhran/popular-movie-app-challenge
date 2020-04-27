package com.example.android.popularmovie2.UI;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovie2.R;
import com.example.android.popularmovie2.adapter.TrailerAdapter;
import com.example.android.popularmovie2.data.GlobleMovie;
import com.squareup.picasso.Picasso;

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

/**
 * Created by odhrandaly on 30/08/2017.
 */

public class FragmentDetailActivity extends Fragment {

    private ImageView poster;
    private TextView tv_title, tv_year, tv_duration, tv_rating, tv_overview;
    private GlobleMovie gm;
    private String MovieJsonStr = "";
    private String TrailerJsonStr = "";
    private String imageUrl;
    private String id;
    private Bundle imageDetail;
    private RecyclerView mTrailerView;
    private TrailerAdapter mAdapter;
    private int mNumberItems;
    private ImageButton favourite;

    public FragmentDetailActivity(){
        gm = new GlobleMovie();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_activity, container, false);
        poster = (ImageView)rootView.findViewById(R.id.image);
        tv_title = (TextView)rootView.findViewById(R.id.tv_title);
        tv_year = (TextView)rootView.findViewById(R.id.year);
        tv_duration = (TextView)rootView.findViewById(R.id.duration);
        tv_rating = (TextView)rootView.findViewById(R.id.rating);
        tv_overview = (TextView)rootView.findViewById(R.id.overview);
        imageDetail = getActivity().getIntent().getExtras();
        if(imageDetail.getString("imageUrl") != null){
            imageUrl = imageDetail.getString("imageUrl");
            Picasso.with(getContext()).load(gm.getIMAGE_BASE_URL_SMALL() + imageUrl).placeholder(R.drawable.ic_loading).error(R.drawable.ic_not_found).into(poster);
        }


        mTrailerView = (RecyclerView)rootView.findViewById(R.id.rv_trailer_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mTrailerView.setLayoutManager(layoutManager);
        mTrailerView.setHasFixedSize(true);

        favourite = (ImageButton)rootView.findViewById(R.id.btn_favorite);
        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        String id = imageDetail.getString("imageId");
        if(id!= null) {
            GetMovieDetailTask task = new GetMovieDetailTask();
            task.execute(id);
            GetTrailerTask task1 = new GetTrailerTask();
            task1.execute(id);
        }
    }

    class GetMovieDetailTask extends AsyncTask<String, Void, Void>{


        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            InputStream stream = null;
            String urlbase = gm.getBASE_URL().concat(params[0]);
            Uri uriBuilder = Uri.EMPTY.parse(urlbase).buildUpon()
                    .appendQueryParameter(gm.getAPP_ID(), gm.getAPI_KEY())
                    .build();

            try{
                URL url = new URL(uriBuilder.toString());
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                stream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(stream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(stream));
                String line;

                while((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0){
                    return null;
                }

                MovieJsonStr = buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
                if (reader != null){
                    try{
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                getMovieDetailFromJson(MovieJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void getMovieDetailFromJson(String movieJsonStr) throws JSONException{
            JSONObject movieJson = new JSONObject(movieJsonStr);
            if(movieJson.getString(gm.getTMDB_TITLE()) != null) {
                tv_title.setText(movieJson.getString(gm.getTMDB_TITLE()));
            }

            if(movieJson.getString(gm.getTMDB_REL()) != null){
                setDate(movieJson.getString(gm.getTMDB_REL()));
            }

            if(movieJson.getString(gm.getTMDB_DURATION()) != null){
                tv_duration.setText(movieJson.getString(gm.getTMDB_DURATION()) + "m");
            }

            if(movieJson.getString(gm.getTMDB_RATING()) != null) {
                tv_rating.setText(movieJson.getString(gm.getTMDB_RATING()) + "\\10");
            }

            if(movieJson.getString(gm.getTMDB_SYNOP()) != null){
                tv_overview.setText(movieJson.getString(gm.getTMDB_SYNOP()));
            }
        }

        private void setDate(String date){
            String year = date.substring(0, 4);
            tv_year.setText(year);
        }
    }

    class GetTrailerTask extends AsyncTask<String, Void, String[]>{

        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            InputStream stream = null;
            String urlbase = gm.getBASE_URL().concat(params[0]);
            urlbase += "/videos?";
            Uri uriBuilder = Uri.EMPTY.parse(urlbase).buildUpon()
                    .appendQueryParameter(gm.getAPP_ID(), gm.getAPI_KEY())
                    .build();

            System.out.println(uriBuilder.toString());
            try{
                URL url = new URL(uriBuilder.toString());
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                stream = connection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(stream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(stream));
                String line;

                while((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0){
                    return null;
                }

                TrailerJsonStr = buffer.toString();

                return getTrailerFromJson(TrailerJsonStr);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
                if (reader != null){
                    try{
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String[] resultKey) {
            super.onPostExecute(resultKey);
            gm.setTrailerKey(new ArrayList<String>(Arrays.asList(resultKey)));
            mAdapter = new TrailerAdapter();
            mTrailerView.setAdapter(mAdapter);

        }

        private String[] getTrailerFromJson(String movieJsonStr) throws JSONException {
            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(gm.getTMDB_RESULT());
            Integer MOVIE_SIZE = movieArray.length();


            String []resultKey = new String[MOVIE_SIZE];
            for(int i = 0; i < MOVIE_SIZE; i++){
                JSONObject keyId = movieArray.getJSONObject(i);
                if(keyId.getString("site") == "YouTube");{
                    resultKey[i] = keyId.getString(gm.getTMDB_TRAILER_KEY());
                    System.out.println(resultKey[i]);
                    mNumberItems++;
                }
            }

            gm.setNumTrailers(mNumberItems);

            return resultKey;
        }
    }
}
