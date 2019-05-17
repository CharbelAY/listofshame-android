package com.charbelay.listofshame.Presenter;

import com.charbelay.listofshame.Model.DataProviderFirebase;
import com.charbelay.listofshame.Upload;
import com.charbelay.listofshame.View.ListFragmentView;

import java.util.List;

/**
 * Created by Charbel on 2019-05-17.
 */
public class ListFragmentPresenter {
    ListFragmentView listFragmentView;
    private List<Upload> mUploads;

    public ListFragmentPresenter(ListFragmentView listFragmentView){
        this.listFragmentView=listFragmentView;
    }

    public void getUploads(){
        DataProviderFirebase dataProviderFirebase = DataProviderFirebase.createMeMyDataProvider();
        dataProviderFirebase.pleaseTakeMyReference(this);
        dataProviderFirebase.getMeThatData();
    }

    public void pleaseGetYourData( List<Upload> mUploads){
        this.mUploads=mUploads;
        listFragmentView.yourDataHasBeenServed(this.mUploads);
    }

    public void pleaseTakeThatError(String databaseError){

    }
}
