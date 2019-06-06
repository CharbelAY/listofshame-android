package com.charbelay.listofshame;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Charbel on 2019-04-20.
 */
public class Upload implements Serializable {

    private String comment;
    private String imageURL;
    private double latitude;
    private double longitude;

    public Upload(){
        //empty constructor needed for firebase
    }

    public Upload(String comment ,double latitude,double longitude, String imageURL){
        if(comment.trim().equals("")){
            this.comment="No Comment";
        }
        this.comment=comment;
        this.imageURL=imageURL;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public String getComment(){
        return comment;
    }

    public double getLatitude(){return latitude;}

    public double getLongitude(){return longitude;}

    public String getImageURL(){
        return imageURL;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setImageURL(String imageURL){
        this.imageURL=imageURL;
    }

    public void setLatitude (double latitude){this.latitude=latitude;}

    public void setLongitude (double longitude){this.longitude=longitude;}


}
