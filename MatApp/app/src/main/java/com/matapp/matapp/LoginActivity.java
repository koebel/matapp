package com.matapp.matapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    /* Variables */
    private Button loginBtn;
    private Button registerBtn;

    private EditText emailInput;
    private EditText passwordInput;
    private EditText listNameInput;

    private int backButtonCount;

    private FirebaseAuth auth;
    private DatabaseReference materialReference;

    private String listKey;
    private boolean listWriteable;

    private ProgressDialog progressDialog;

    /* static Variables */
    public static final String LOG_LOGIN_ACTIVITY = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // set Statusbar Color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog = new ProgressDialog(this);

        //Reset backButton counter
        backButtonCount = 0;

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        //Get Firebase database instance
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Get reference to material
        materialReference = database.getReference("material");

        loginBtn = (Button) findViewById(R.id.loginBtn);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        emailInput = (EditText) findViewById(R.id.emailInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        listNameInput = (EditText) findViewById(R.id.listNameInput);

        //Check if logged in
        if (auth.getCurrentUser() != null) {
            emailInput.setText(auth.getCurrentUser().getEmail());
            //Logout
            auth.signOut();
            Log.i(LOG_LOGIN_ACTIVITY, "listKey: " + MatAppSession.getInstance().getListKey());
        }

        //Login Button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String email = emailInput.getText().toString();
                final String password = passwordInput.getText().toString();
                final String listName = listNameInput.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(view.getContext(), getString(R.string.login_missingEmail), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(view.getContext(), getString(R.string.login_shortPassword), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(listName)) {
                    Toast.makeText(view.getContext(), getString(R.string.login_missingList), Toast.LENGTH_SHORT).show();
                    return;
                }

                loginBtn.setEnabled(false);
                registerBtn.setEnabled(false);
                progressDialog.setMessage(getString(R.string.login_loginProgress));
                progressDialog.show();

                //authenticate user
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i(LOG_LOGIN_ACTIVITY, "uid: " + auth.getCurrentUser().getUid());
                            //Select child where listName=listName
                            Query query = materialReference.orderByChild("listName").equalTo(listName);
                            //Add Listener for one read
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChildren()) {
                                        //If list is found get listKey
                                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                            listKey = childDataSnapshot.getKey();
                                            break;
                                        }
                                        if(dataSnapshot.child(listKey).child("listUid").getValue(String.class).equals(auth.getCurrentUser().getUid())) {
                                            listWriteable = true;
                                        } else {
                                            listWriteable = false;
                                        }
                                        Log.i(LOG_LOGIN_ACTIVITY, "found listKey: " + listKey + " writeable: " + listWriteable);
                                    } else {
                                        //Otherwise create new list
                                        listKey = materialReference.push().getKey();
                                        materialReference.child(listKey).child("listName").setValue(listName);
                                        materialReference.child(listKey).child("listUid").setValue(auth.getCurrentUser().getUid());
                                        listWriteable = true;
                                        Log.i(LOG_LOGIN_ACTIVITY, "created new list with listKey=" + listKey);
                                        Toast.makeText(view.getContext(), getString(R.string.login_listCreated), Toast.LENGTH_SHORT).show();
                                    }
                                    MatAppSession.getInstance().setListKey(listKey);
                                    MatAppSession.getInstance().setListName(listName);
                                    MatAppSession.getInstance().setListWriteable(listWriteable);
                                    //Success
                                    Toast.makeText(view.getContext(), getString(R.string.login_loginSuccessful), Toast.LENGTH_SHORT).show();
                                    loginBtn.setEnabled(true);
                                    registerBtn.setEnabled(true);
                                    progressDialog.dismiss();
                                    finish();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(view.getContext(), getString(R.string.db_error), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            //There was an error
                            Toast.makeText(view.getContext(), getString(R.string.login_loginFailed) + task.getException(), Toast.LENGTH_LONG).show();
                            loginBtn.setEnabled(true);
                            registerBtn.setEnabled(true);
                            progressDialog.dismiss();
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
                    Toast.makeText(view.getContext(), getString(R.string.login_missingEmail), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(view.getContext(), getString(R.string.login_shortPassword), Toast.LENGTH_SHORT).show();
                    return;
                }

                loginBtn.setEnabled(false);
                registerBtn.setEnabled(false);
                progressDialog.setMessage(getString(R.string.login_registerProgress));
                progressDialog.show();

                //create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (task.isSuccessful()) {
                            Toast.makeText(view.getContext(), getString(R.string.login_registerSuccessful), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(view.getContext(), getString(R.string.login_registerFailed) + task.getException(), Toast.LENGTH_LONG).show();
                        }
                        loginBtn.setEnabled(true);
                        registerBtn.setEnabled(true);
                        progressDialog.dismiss();
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1) {
            //Close App
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            backButtonCount = 0;
        } else {
            //Ask to hit back again
            Toast.makeText(this, getString(R.string.login_backBtn), Toast.LENGTH_LONG).show();
            backButtonCount++;
        }
    }
}
