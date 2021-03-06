package com.charbelay.listofshame.Model;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.charbelay.listofshame.MainActivity;
import com.charbelay.listofshame.Presenter.ILoginPresenter;
import com.charbelay.listofshame.Presenter.IRegisterPresenter;
import com.charbelay.listofshame.Presenter.LoginPresenter;
import com.charbelay.listofshame.Presenter.RegisterPresenter;
import com.charbelay.listofshame.View.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.concurrent.Executor;

/**
 * Created by Charbel on 2019-05-15.
 */
public class UserModel extends AppCompatActivity implements IUserModel {
    String ResultMessage;
    private FirebaseAuth firebaseAuth;
    LoginPresenter loginPresenter;
    RegisterPresenter registerPresenter;
    String email,password;

    public UserModel(LoginPresenter loginPresenter, String email , String password){
        this.loginPresenter=loginPresenter;
        this.email=email;
        this.password=password;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public UserModel(RegisterPresenter registerPresenter, String email , String password){
        this.registerPresenter=registerPresenter;
        this.email=email;
        this.password=password;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void LoginStatus() {
        if(TextUtils.isEmpty(this.email)){
            //email is empty
            ResultMessage= "Please enter an email";
            loginPresenter.onLoginResult(ResultMessage);
        }
        else if(TextUtils.isEmpty(password)){
            //password is empty
            ResultMessage = "Please enter a password";
            loginPresenter.onLoginResult(ResultMessage);
        }


       else {
            firebaseAuth.signInWithEmailAndPassword(this.email, this.password)
                    .addOnCompleteListener(UserModel.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (firebaseAuth.getCurrentUser().isEmailVerified()) {
                                    ResultMessage = "Login successful";
                                    loginPresenter.onLoginResult(ResultMessage);
                                } else {
                                    ResultMessage = "You need to verify your email first";
                                    loginPresenter.onLoginResult(ResultMessage);
                                }
                            } else {
                                ResultMessage = task.getException().getMessage();
                                loginPresenter.onLoginResult(ResultMessage);
                            }
                        }
                    });
        }

    }

    @Override
    public void RegisterStatus() {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        ResultMessage = "Confirmation email has been sent, Please check your email";
                                        registerPresenter.onRegisterResult(ResultMessage);
                                    }else{
                                        ResultMessage=task.getException().getMessage();
                                        registerPresenter.onRegisterResult(ResultMessage);

                                    }
                                }
                            });
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                ResultMessage = "You are already registered please sign in";
                                registerPresenter.onRegisterResult(ResultMessage);

                            }else{
                                ResultMessage = task.getException().getMessage();
                                registerPresenter.onRegisterResult(ResultMessage);

                            }
                        }
                    }
                });
    }
}
