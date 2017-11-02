package com.matapp.matapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.matapp.matapp.MainActivity;
import com.matapp.matapp.R;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by kathrinkoebel on 25.10.17.
 */

public class LogoutFragment extends Fragment {
    private Button loginBtn, registerBtn;
    private EditText emailInput, passwordInput;

    private FirebaseAuth auth;

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_logout, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);


        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //Check if logged in
        if (auth.getCurrentUser() != null) {
            //logout
        }

        loginBtn = (Button) getView().findViewById(R.id.loginBtn);
        registerBtn = (Button) getView().findViewById(R.id.registerBtn);
        emailInput = (EditText) getView().findViewById(R.id.emailInput);
        passwordInput = (EditText) getView().findViewById(R.id.passwordInput);

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
                                    getActivity().getSupportFragmentManager().popBackStackImmediate();
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
