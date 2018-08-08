package com.whatstools.statussaver_directmessages.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.whatstools.statussaver_directmessages.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {

    private List<File> imagesList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivThumbnail;

        public MyViewHolder(View view) {
            super(view);
            ivThumbnail = view.findViewById(R.id.thumbnail);
        }
    }

    public GalleryAdapter(Context context, ArrayList<File> imagesList) {
        this.context = context;
        this.imagesList = imagesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_thumbnail, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        File file = imagesList.get(position);
        Uri imageUri = Uri.fromFile(file);

        Glide.with(context).load(imageUri).into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }
}
