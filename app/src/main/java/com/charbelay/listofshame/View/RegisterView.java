package com.charbelay.listofshame.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.charbelay.listofshame.MainActivity;
import com.charbelay.listofshame.Presenter.IRegisterPresenter;
import com.charbelay.listofshame.Presenter.RegisterPresenter;
import com.charbelay.listofshame.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Charbel on 2019-05-17.
 */
public class RegisterView extends AppCompatActivity implements View.OnClickListener,IRegisterView{

    private Button registerButton;
    private EditText emailEditText;
    private EditText  passwordEditText;
    private ProgressDialog progressDialog;

    RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenter(this);

        progressDialog   = new ProgressDialog(this);
        registerButton   = findViewById(R.id.RegisterActivityRegister);
        emailEditText    = findViewById(R.id.RegisterActivityEditTextEmail);
        passwordEditText = findViewById(R.id.RegisterActivityEditTextPassword);

        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==registerButton){
            registerUser();
        }
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }



        progressDialog.setMessage("Sending you a confirmation email... Please wait");
        progressDialog.show();

        registerPresenter.getInformedRegister(email,password);

    }

    @Override
    public void onRegisterMessageReceived(String message) {
        progressDialog.dismiss();
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();

    }

    public void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
