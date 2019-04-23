package com.charbelay.listofshame;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Charbel on 2019-04-23.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private ArrayList<String> ImageNames = new ArrayList<>();
    private ArrayList<String> Images = new ArrayList<>();
    private Context mContext;

    public ListAdapter(ArrayList<String> imageNames, ArrayList<String> images, Context mContext) {
        ImageNames = imageNames;
        Images = images;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext)
                .asBitmap()
                .load(Images.get(i))
                .into(viewHolder.image);

        viewHolder.imageName.setText(ImageNames.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"ok",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return ImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.Image);
            imageName = itemView.findViewById(R.id.ImageName);
            parentLayout = itemView.findViewById(R.id.ParentLayout);
        }
    }
}
