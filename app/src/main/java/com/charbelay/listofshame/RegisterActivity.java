package com.charbelay.listofshame;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button    registerButton;
    private EditText  emailEditText;
    private EditText  passwordEditText;

    private ProgressDialog progressDialog;
    private FirebaseAuth   firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        firebaseAuth     = FirebaseAuth.getInstance();

        progressDialog   = new ProgressDialog(this);
        registerButton   = findViewById(R.id.RegisterActivityRegister);
        emailEditText    = findViewById(R.id.RegisterActivityEditTextEmail);
        passwordEditText = findViewById(R.id.RegisterActivityEditTextPassword);

        registerButton.setOnClickListener(this);
    }

    private void registerUser(){
        String email     = emailEditText.getText().toString().trim();
        String password  = passwordEditText.getText().toString().trim();

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


        progressDialog.setMessage("Registering you... Please wait");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Registration successful",Toast.LENGTH_SHORT).show();
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(),"You are already registered please sign in",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if(view==registerButton){
            registerUser();
        }
    }
}
