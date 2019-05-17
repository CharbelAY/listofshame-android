package com.charbelay.listofshame.Model;

import com.charbelay.listofshame.Presenter.ListFragmentPresenter;

/**
 * Created by Charbel on 2019-05-17.
 */
public interface IDataProviderFirebase {
    public void pleaseTakeMyReference(ListFragmentPresenter listFragmentPresenter);
    public void getMeThatData();
}
