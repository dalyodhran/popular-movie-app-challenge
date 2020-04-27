package com.example.android.popularmovie2.UI;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmovie2.R;
import com.example.android.popularmovie2.adapter.FragmentImageAdapter;
import com.example.android.popularmovie2.data.GlobleMovie;

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
 * Created by odhrandaly on 26/07/2017.
 */

public class FragmentMainActivity extends Fragment {

    private GlobleMovie gm;
    private FragmentImageAdapter mAdapter;
    private RecyclerView numberList;
    private String MovieJsonString = "";

    public FragmentMainActivity(){
        gm = new GlobleMovie();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);
        numberList = (RecyclerView) rootView.findViewById(R.id.rv_image);
        GridLayoutManager gridManager = new GridLayoutManager(getActivity(), 2);
        numberList.setLayoutManager(gridManager);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateImage();
    }

    private void updateImage(){
        GetMovieInfoTask task = new GetMovieInfoTask();
        task.execute("popular");
    }

    class GetMovieInfoTask extends AsyncTask<String, Void, String[]>{
        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url = null;
            InputStream inputStream = null;
            StringBuffer buffer = null;

            String base_url = gm.getBASE_URL().concat(params[0]);
            Uri buildUri = Uri.EMPTY.parse(base_url).buildUpon()
                    .appendQueryParameter(gm.getAPP_ID(), gm.getAPI_KEY())
                    .build();
            try {
                url = new URL(buildUri.toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                inputStream = connection.getInputStream();
                buffer = new StringBuffer();

                if(inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0){
                    return null;
                }

                MovieJsonString = buffer.toString();
                gm.setMovieJson(MovieJsonString);

                return getMovieDataFromJson(MovieJsonString);

            }catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
                if(reader != null){
                    try {
                        reader.close();
                    } catch (final IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String [] resultJson) {
            if(resultJson != null){
                gm.setImageUrls(new ArrayList<>(Arrays.asList(resultJson)));
                mAdapter = new FragmentImageAdapter();
                numberList.setAdapter(mAdapter);
            }
        }

        private String[] getMovieDataFromJson(String movieJsonStr) throws JSONException{
            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(gm.getTMDB_RESULT());
            Integer MOVIE_SIZE = movieArray.length();
            String []resultStr = new String[MOVIE_SIZE];
            String []resultId = new String[MOVIE_SIZE];
            for(int i = 0; i < MOVIE_SIZE; i++){

                JSONObject imageUrl = movieArray.getJSONObject(i);
                String id = imageUrl.getString(gm.getTMDB_ID());
                String poster = imageUrl.getString(gm.getTMDB_POSTER());
                resultStr[i] = poster;
                resultId[i] = id;
            }

            gm.setImageIds(new ArrayList<String>(Arrays.asList(resultId)));

            return resultStr;
        }
    }
}
