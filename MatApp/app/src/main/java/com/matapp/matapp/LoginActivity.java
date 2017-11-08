package com.matapp.matapp;

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

public class LoginActivity extends AppCompatActivity {
    private Button loginBtn, registerBtn;
    private EditText emailInput, passwordInput;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //Check if logged in
        if (auth.getCurrentUser() != null) {
            //logout
            auth.signOut();
        }

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);

        //Login Button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String email = emailInput.getText().toString();
                final String password = passwordInput.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    return;
                }

                //authenticate user
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // success
                            Toast.makeText(view.getContext(), getString(R.string.login_loginSuccessful), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(view.getContext(), "uid: " + auth.getCurrentUser().getUid(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // there was an error
                            Toast.makeText(view.getContext(), getString(R.string.login_loginFailed) + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        //Register Button
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(view.getContext(), getString(R.string.login_registerMissingEmail), Toast.LENGTH_SHORT).show();
                    return;
                }

                //if (TextUtils.isEmpty(password)) {
                //    Toast.makeText(view.getContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                //    return;
                //}

                if (password.length() < 6) {
                    Toast.makeText(view.getContext(), getString(R.string.login_registerShortPassword), Toast.LENGTH_SHORT).show();
                    return;
                }

                //create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Toast.makeText(view.getContext(), getString(R.string.login_registerSuccessful), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(view.getContext(), getString(R.string.login_registerFailed) + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
