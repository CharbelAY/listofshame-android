package com.charbelay.listofshame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button   buttonLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewLogIn;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth     = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            goToDashboardActivity();
        } else {
        }

        progressDialog   = new ProgressDialog(this);
        buttonLogin      = findViewById(R.id.login);
        editTextEmail    = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewLogIn    = findViewById(R.id.textViewRegister);

        buttonLogin.setOnClickListener(this);
        textViewLogIn.setOnClickListener(this);

    }

    private void LoginUser(){
        String email     = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter an email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please enter a password",Toast.LENGTH_SHORT).show();
            return;
        }


        progressDialog.setMessage("Loging you in... Please wait");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                Toast.makeText(MainActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                goToDashboardActivity();
                            }else{
                                Toast.makeText(MainActivity.this,"You need to verify your email first",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    public void gotToRegister() {
        // Do something in response to button
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToDashboardActivity(){
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if(view==buttonLogin){
            LoginUser();
        }
        if(view==textViewLogIn){
            gotToRegister();
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
}
