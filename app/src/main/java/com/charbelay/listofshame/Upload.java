package com.charbelay.listofshame;

/**
 * Created by Charbel on 2019-04-20.
 */
public class Upload {

    private String comment;
    private String imageURL;

    public Upload(){
        //empty constructor needed for firebase
    }

    public Upload(String comment , String imageURL){
        if(comment.trim().equals("")){
            this.comment="No Comment";
        }
        this.comment=comment;
        this.imageURL=imageURL;
    }

    public String getComment(){
        return comment;
    }

    public String getImageURL(){
        return imageURL;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setImageURL(String imageURL){
        this.imageURL=imageURL;
    }
}
