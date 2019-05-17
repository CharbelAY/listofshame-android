package com.charbelay.listofshame.Presenter;

import com.charbelay.listofshame.Upload;

import java.util.List;

/**
 * Created by Charbel on 2019-05-17.
 */
public interface IListFragmentPresenter {
    public void getUploads();
    public void pleaseGetYourData( List<Upload> mUploads);
}
