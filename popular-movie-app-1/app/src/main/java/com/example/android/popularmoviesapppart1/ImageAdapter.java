package com.example.android.popularmoviesapppart1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by odhrandaly on 15/03/2017.
 */

public class ImageAdapter extends ArrayAdapter<String> {

    private Context context;
    private int resourceId;
    ArrayList<String> imageUrls;


    public ImageAdapter(Activity activity, int resource, ArrayList<String> urls){
        super(activity, resource, urls);
        context = activity;
        resourceId = resource;
        imageUrls = urls;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resourceId, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_poster);
        String url = getItem(position);
        Picasso.with(context).load(url).placeholder(R.drawable.ic_loading).error(R.drawable.ic_not_found).into(imageView);
        return convertView;
    }

    public void setMovieData(ArrayList<String> imageUrls) {
        this.imageUrls.addAll(imageUrls);
        notifyDataSetChanged();
    }
}
