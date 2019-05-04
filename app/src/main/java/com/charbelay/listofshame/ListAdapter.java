package com.charbelay.listofshame;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Charbel on 2019-04-23.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
//    private ArrayList<String> ImageNames = new ArrayList<>();
//    private ArrayList<String> Images = new ArrayList<>();
    private List<Upload> mUploads;
    private Context mContext;
    private Uri uri1;

    public ListAdapter(List<Upload> uploads, Context mContext) {
//        ImageNames = imageNames;
//        Images = images;
        mUploads = uploads;
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
        Upload uploadCurrent = mUploads.get(i);
        viewHolder.imageName.setText(uploadCurrent.getComment());
        Glide.with(mContext)
                .asBitmap()
                .load(uploadCurrent.getImageURL())
                .into(viewHolder.image);

        viewHolder.imageName.setText(uploadCurrent.getComment());

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"ok",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
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
