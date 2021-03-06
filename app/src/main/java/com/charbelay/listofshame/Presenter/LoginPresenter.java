package com.charbelay.listofshame.Presenter;

import android.icu.text.StringPrepParseException;

import com.charbelay.listofshame.Model.UserModel;
import com.charbelay.listofshame.View.ILoginView;
import com.charbelay.listofshame.View.LoginView;

/**
 * Created by Charbel on 2019-05-15.
 */
public class LoginPresenter implements ILoginPresenter {


    UserModel userm ;
    LoginView loginView;
    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
    }


    @Override
    public void getInformedLogin(String email, String password) {
        userm = new UserModel(this,email,password);
        userm.LoginStatus();
    }

    @Override
    public void onLoginResult(String message) {
        loginView.onLoginMessageReceived(message);
    }
}
