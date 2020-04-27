package com.example.android.popularmovie2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.popularmovie2.R;
import com.example.android.popularmovie2.UI.DetailActivity;
import com.example.android.popularmovie2.data.GlobleMovie;
import com.squareup.picasso.Picasso;


/**
 * Created by odhrandaly on 26/07/2017.
 */

public class FragmentImageAdapter extends RecyclerView.Adapter<FragmentImageAdapter.ImageViewHolder>{

    private int mNumberItem;
    private Context context;
    private GlobleMovie gm;

    public FragmentImageAdapter(){
        gm = new GlobleMovie();
        mNumberItem = gm.getImageUrls().size();
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.grid_image_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ImageViewHolder viewHolder = new ImageViewHolder(view);
        

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ImageViewHolder holder, final int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked item " + position, Toast.LENGTH_LONG).show();
                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra("imageUrl",  gm.getImageUrls(position));
                detailIntent.putExtra("imageId", gm.getImageIds(position));
                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNumberItem;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{
        ImageView poster;

        public ImageViewHolder(View itemView){
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.image_view);
        }

        void bind(int listIndex){
            if(!gm.getImageUrls().isEmpty()) {
                String url = gm.getIMAGE_BASE_URL_BIG() + gm.getImageUrls(listIndex);
                Picasso.with(context).load(url).placeholder(R.drawable.ic_loading).error(R.drawable.ic_not_found).into(poster);
            }
        }

    }
}
