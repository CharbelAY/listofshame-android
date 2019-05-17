package com.charbelay.listofshame.Presenter;

/**
 * Created by Charbel on 2019-05-15.
 */
public interface ILoginPresenter {
    void getInformedLogin(String email , String password);
    void onLoginResult(String message);
}
