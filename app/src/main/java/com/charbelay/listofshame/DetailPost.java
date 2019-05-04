package com.charbelay.listofshame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailPost extends AppCompatActivity {

    TextView detail;
    ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        Intent intent = getIntent();
        Upload curentUpload =(Upload) intent.getSerializableExtra("upload");
        detail = findViewById(R.id.text_view_detail);
        detailImage = findViewById(R.id.image_view_detail);
        detail.setText(curentUpload.getComment());
        Glide.with(this)
                .asBitmap()
                .load(curentUpload.getImageURL())
                .into(detailImage);

    }
}
