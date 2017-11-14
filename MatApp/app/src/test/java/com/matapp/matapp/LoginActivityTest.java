package com.matapp.matapp;

import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;


/**
 *
 * local unit tests for Login Activity,
 * implemented with Robolectrics,
 * will be execute on the development machine (host)
 *
 **/

@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {

    // test fails because of Illegal State Exception triggered through setupActivity
    // --> java.lang.IllegalStateException: FirebaseApp with name [DEFAULT] doesn't exist.

    @Test
    public void testLoginMatAppSessionValues() throws Exception {
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        Button button = (Button) activity.findViewById(R.id.loginBtn);
        EditText username = (EditText) activity.findViewById(R.id.emailInput);
        EditText password = (EditText) activity.findViewById(R.id.passwordInput);
        EditText listname = (EditText) activity.findViewById(R.id.listNameInput);
        username.setText("test@fhnw.ch");
        password.setText("123456");
        listname.setText("Demo");
        button.performClick();
        String listNameString = MatAppSession.getInstance().getListName().toString();
        assertTrue(listNameString.equals("Demo"));
    }
}









