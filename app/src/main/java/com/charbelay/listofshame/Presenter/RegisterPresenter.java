package com.charbelay.listofshame.Presenter;

import com.charbelay.listofshame.Model.UserModel;
import com.charbelay.listofshame.View.IRegisterView;
import com.charbelay.listofshame.View.RegisterView;

/**
 * Created by Charbel on 2019-05-17.
 */
public class RegisterPresenter implements IRegisterPresenter {
    public RegisterView registerView;
    public UserModel user;

    public RegisterPresenter(RegisterView registerView){
        this.registerView =registerView;
    }

    @Override
    public void getInformedRegister(String email, String password) {
        user = new UserModel(this,email,password);
        user.RegisterStatus();
    }

    @Override
    public void onRegisterResult(String message) {
        registerView.onRegisterMessageReceived(message);
    }
}
