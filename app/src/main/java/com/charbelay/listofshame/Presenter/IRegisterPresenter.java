package com.charbelay.listofshame.Presenter;

/**
 * Created by Charbel on 2019-05-17.
 */
public interface IRegisterPresenter {
     void getInformedRegister(String email , String password);
     void onRegisterResult(String message);
}
