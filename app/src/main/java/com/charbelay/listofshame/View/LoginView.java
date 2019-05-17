package com.charbelay.listofshame.View;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.charbelay.listofshame.DashboardActivity;
import com.charbelay.listofshame.Presenter.ILoginPresenter;
import com.charbelay.listofshame.Presenter.LoginPresenter;
import com.charbelay.listofshame.R;
import com.charbelay.listofshame.RegisterActivity;

/**
 * Created by Charbel on 2019-05-15.
 */
public class LoginView extends AppCompatActivity implements View.OnClickListener,ILoginView {

    BroadcastReceiver receiver;

    private Button buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewLogIn;
    private ProgressDialog progressDialog;
    ILoginPresenter loginPresenter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog   = new ProgressDialog(this);

        loginPresenter = new LoginPresenter(this);


        buttonLogin      = findViewById(R.id.login);
        editTextEmail    = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewLogIn    = findViewById(R.id.textViewRegister);

        buttonLogin.setOnClickListener(this);
        textViewLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==buttonLogin){
            progressDialog.setMessage("Loging you in... Please wait");
            progressDialog.show();
            LoginResult(editTextEmail.getText().toString().trim(),editTextPassword.getText().toString().trim());
        }
        if(view==textViewLogIn){
            gotToRegister();
        }
    }



    public void gotToRegister() {
        // Do something in response to button
        Intent intent = new Intent(this,RegisterView.class);
        startActivity(intent);
    }

    @Override
    public void LoginResult(String email , String password) {
        loginPresenter.getInformedLogin(email,password);
    }

    @Override
    public void onLoginMessageReceived(String message) {
        progressDialog.dismiss();
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        if(message.equals("Login successful")){
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        if (true) {
            moveTaskToBack(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
