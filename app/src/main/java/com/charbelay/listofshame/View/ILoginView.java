package com.charbelay.listofshame.View;

/**
 * Created by Charbel on 2019-05-15.
 */
public interface ILoginView {
    void LoginResult(String email , String password);
    void onLoginMessageReceived(String message);
}
