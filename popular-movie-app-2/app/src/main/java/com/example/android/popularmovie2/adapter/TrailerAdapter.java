package com.example.android.popularmovie2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovie2.R;
import com.example.android.popularmovie2.data.GlobleMovie;
import com.squareup.picasso.Picasso;

/**
 * Created by odhrandaly on 10/09/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private int mNumberItemHolder;
    private GlobleMovie gm;
    private Context context;
    private int count = 1;

    public TrailerAdapter() {
        gm = new GlobleMovie();
        mNumberItemHolder = gm.getTrailerKey().size();
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdFromListItem = R.layout.trailer_layout_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdFromListItem, parent, shouldAttachToParentImmediately);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItemHolder;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        ImageView trailerImage;
        TextView tv_trailer_number;

        public TrailerViewHolder(View itemView) {
            super(itemView);

            trailerImage = (ImageView)itemView.findViewById(R.id.trailer_thumbnail);
            tv_trailer_number = (TextView)itemView.findViewById(R.id.trailer_number);
        }

        void bind(int listItem){
            //set image and trailer number

            if(!gm.getTrailerKey().isEmpty()) {
                String url = "http://img.youtube.com/vi/"+ gm.getTrailerKey(listItem) + "/0.jpg";
                System.out.println(url);
                Picasso.with(context).load(url).placeholder(R.drawable.ic_loading).error(R.drawable.ic_not_found).into(trailerImage);
                tv_trailer_number.setText("Trailer " + count);
                count++;
            }
        }
    }
}
